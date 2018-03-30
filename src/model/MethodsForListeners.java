package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;


public class MethodsForListeners {

    private static Stack<Double> historyMinReal = new Stack<Double>();
    private static Stack<Double> historyMaxReal = new Stack<Double>();
    private static Stack<Double> historyMinImaginary = new Stack<Double>();
    private static Stack<Double> historyMaxImaginary = new Stack<Double>();
    private static Stack<Integer> historyColour = new Stack<Integer>();
    private static Stack<Double> futureMinReal = new Stack<Double>();
    private static Stack<Double> futureMaxReal = new Stack<Double>();
    private static Stack<Double> futureMinImaginary = new Stack<Double>();
    private static Stack<Double> futureMaxImaginary = new Stack<Double>();
    private static Stack<Integer> futureColour = new Stack<Integer>();


    // the following methods are all very similar and straightforward to follow. They use stacks and update the values used by the
    // model, depending on what the user is requesting.

    public static void undo(double minR, double maxR, double minI, double maxI, int col) {
        if (!(historyMaxImaginary.isEmpty())) {
            futureMinReal.push(minR);
            futureMaxReal.push(maxR);
            futureMinImaginary.push(minI);
            futureMaxImaginary.push(maxI);
            futureColour.push(col);
            Model.setMinReal(historyMinReal.pop());
            Model.setMaxReal(historyMaxReal.pop());
            Model.setMinImaginary(historyMinImaginary.pop());
            Model.setMaxImaginary(historyMaxImaginary.pop());
            Model.setColourChoice(historyColour.pop());
        }
    }


    public static void redo(double minR, double maxR, double minI, double maxI, int col) {
        if (!(futureMaxImaginary.isEmpty())) {
            historyMinReal.push(minR);
            historyMaxReal.push(maxR);
            historyMinImaginary.push(minI);
            historyMaxImaginary.push(maxI);
            historyColour.push(col);
            Model.setMinReal(futureMinReal.pop());
            Model.setMaxReal(futureMaxReal.pop());
            Model.setMinImaginary(futureMinImaginary.pop());
            Model.setMaxImaginary(futureMaxImaginary.pop());
            Model.setColourChoice(futureColour.pop());
        }
    }


    public static void zoom(double minR, double maxR, double minI, double maxI, int col) {
        historyMinReal.push(minR);
        historyMaxReal.push(maxR);
        historyMinImaginary.push(minI);
        historyMaxImaginary.push(maxI);
        historyColour.push(col);
    }


    public static void reset() {
        Model.setMaxIterations(MandelbrotCalculator.getInitialMaxIterations());
        Model.setMinReal(MandelbrotCalculator.getInitialMinReal());
        Model.setMaxReal(MandelbrotCalculator.getInitialMaxReal());
        Model.setMinImaginary(MandelbrotCalculator.getInitialMinImaginary());
        Model.setMaxImaginary(MandelbrotCalculator.getInitialMaxImaginary());
        historyMinReal = new Stack<Double>();
        historyMaxReal = new Stack<Double>();
        historyMinImaginary = new Stack<Double>();
        historyMaxImaginary = new Stack<Double>();
        futureMinReal = new Stack<Double>();
        futureMaxReal = new Stack<Double>();
        futureMinImaginary = new Stack<Double>();
        futureMaxImaginary = new Stack<Double>();
        Model.setColourChoice(0);
    }


    public static void saveState() throws FileNotFoundException {
        PrintWriter out = new PrintWriter("saveData.txt");
        out.println(Model.getMinReal());
        out.println(Model.getMaxReal());
        out.println(Model.getMinImaginary());
        out.println(Model.getMaxImaginary());
        out.println(Model.getColourChoice());
        out.close();
    }


    public static void reloadState() throws FileNotFoundException {
        FileReader fReader = new FileReader("saveData.txt");
        BufferedReader in = new BufferedReader(fReader);
        try {
            Model.setMinReal(Double.parseDouble(in.readLine()));
            Model.setMaxReal(Double.parseDouble(in.readLine()));
            Model.setMinImaginary(Double.parseDouble(in.readLine()));
            Model.setMaxImaginary(Double.parseDouble(in.readLine()));
            Model.setColourChoice(Integer.parseInt(in.readLine()));
        } catch (NumberFormatException e) {
        } catch (IOException e) {
        }
    }


    public static void toggleRetainAspectRatio() {
        Model.setRetainAspectRatio(!Model.isRetainAspectRatio());
    }
}
