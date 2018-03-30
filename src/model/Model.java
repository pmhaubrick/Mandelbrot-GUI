package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Observable;

import guiDelegate.Delegate;


public class Model extends Observable {

    private static double minReal;
    private static double maxReal;
    private static double minImaginary;
    private static double maxImaginary;
    private static int maxIterations;
    private double radiusSquared;
    private int[][] mSet;
    private static BufferedImage colouredImage;
    private static boolean retainAspectRatio = false;
    private static int colourChoice = 0;


    public Model() { // Sets default parameters
        minReal = MandelbrotCalculator.INITIAL_MIN_REAL;
        maxReal = MandelbrotCalculator.INITIAL_MAX_REAL;
        minImaginary = MandelbrotCalculator.INITIAL_MIN_IMAGINARY;
        maxImaginary = MandelbrotCalculator.INITIAL_MAX_IMAGINARY;
        maxIterations = MandelbrotCalculator.getInitialMaxIterations();
        radiusSquared = MandelbrotCalculator.DEFAULT_RADIUS_SQUARED;
    }


    public void updateMandelbrot(String undoRedo) { 
        if (undoRedo.equals("undo")) { // This part is only for undo, and calls the correct method
            try {
                MethodsForListeners.undo(minReal, maxReal, minImaginary, maxImaginary, colourChoice);
            } catch (Exception exception) {
            }
        } else if (undoRedo.equals("redo")) { // This part is only for redo, and calls the correct method
            try {
                MethodsForListeners.redo(minReal, maxReal, minImaginary, maxImaginary, colourChoice);
            } catch (Exception exception) {
            }
        } else if (Delegate.getZoomX1() != 0) { // This part is only for zooming, and does the calcs.
            double xRatio = Delegate.FRAME_WIDTH / (maxReal - minReal);
            double yRatio = Delegate.FRAME_HEIGHT / (maxImaginary - minImaginary);
            double minRTemp = minReal;
            double minITemp = minImaginary;
            double minR = minRTemp + (Delegate.getZoomX1() / xRatio);
            double maxR = minRTemp + (Delegate.getZoomX2() / xRatio);
            double minI = minITemp + (Delegate.getZoomY1() / yRatio);
            double maxI;
            MethodsForListeners.zoom(minReal, maxReal, minImaginary, maxImaginary, colourChoice);
            if (isRetainAspectRatio()) { // This determines how the zoom works based on the zoom type selected/
                maxI = minITemp + ((Delegate.getZoomY1() + (Delegate.getZoomX2() - Delegate.getZoomX1())) / yRatio);
            } else {
                maxI = minITemp + (Delegate.getZoomY2() / yRatio);
            }
            minReal = Math.min(maxR, minR); //Updates the correct new values that can be used to finally zoom
            maxReal = Math.max(maxR, minR);
            minImaginary = Math.min(maxI, minI);
            maxImaginary = Math.max(maxI, minI);
        } // performs the new calculation
        mSet = MandelbrotCalculator.calcMandelbrotSet(Delegate.FRAME_WIDTH, Delegate.FRAME_HEIGHT, minReal, maxReal, minImaginary, maxImaginary, maxIterations, radiusSquared);
        colourMSet(); // goes through the colour mapping methods
        Delegate.setZoomX1(0); // these reset the clicked/released values to zero until the next zoom.
        Delegate.setZoomX2(0);
        Delegate.setZoomY1(0);
        Delegate.setZoomY2(0);
        setChanged();
        notifyObservers(); // does as said.... very important!!!
    }


    private void colourMSet() { // cycles through pixels.
        colouredImage = new BufferedImage(Delegate.FRAME_WIDTH, Delegate.FRAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < Delegate.FRAME_WIDTH; i++) {
            for (int j = 0; j < Delegate.FRAME_HEIGHT; j++) {
                colouredImage.setRGB(i, j, mapColour(mSet[j][i]));
            }
        }
    }


    private int mapColour(int mNum) { //  maps colours based on user selection
        if (mNum == maxIterations) {
            return Color.BLACK.getRGB();
        } else {
            int rValue = (200 / maxIterations) * mNum;
            if (colourChoice == 0) {
                return new Color(rValue + 55, 0, 0).getRGB();
            } else if (colourChoice == 1) {
                return new Color(0, rValue + 55, 0).getRGB();
            } else if (colourChoice == 2) {
                rValue = (150 / maxIterations) * mNum;
                return new Color(0, 0, rValue + 105).getRGB();
            } else if (colourChoice == 3) {
                return Color.WHITE.getRGB();
            } else {
                return Color.WHITE.getRGB();
            }
        }
    }


    public static BufferedImage getColouredImage() {
        return colouredImage;
    }


    public static double getMinReal() {
        return minReal;
    }


    public static void setMinReal(double minReal) {
        Model.minReal = minReal;
    }


    public static double getMaxReal() {
        return maxReal;
    }


    public static void setMaxReal(double maxReal) {
        Model.maxReal = maxReal;
    }


    public static double getMinImaginary() {
        return minImaginary;
    }


    public static void setMinImaginary(double minImaginary) {
        Model.minImaginary = minImaginary;
    }


    public static double getMaxImaginary() {
        return maxImaginary;
    }


    public static void setMaxImaginary(double maxImaginary) {
        Model.maxImaginary = maxImaginary;
    }


    public static int getMaxIterations() {
        return maxIterations;
    }


    public static void setMaxIterations(int maxIterations) {
        Model.maxIterations = maxIterations;
    }


    public static boolean isRetainAspectRatio() {
        return retainAspectRatio;
    }


    public static void setRetainAspectRatio(boolean retainAspectRatio) {
        Model.retainAspectRatio = retainAspectRatio;
    }


    public static void setColourChoice(int c) {
        colourChoice = c;
    }

    
    public static int getColourChoice() {
        return colourChoice;
    }
}
