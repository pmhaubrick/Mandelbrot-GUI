package guiDelegate;

import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import model.Model;


public class Display implements Observer { // Is an observer of the model.

    private static BufferedImage mandelbrotImage; // This is the image that holds the Mandelbrot picture needed to display.
    private Model model;
    private Delegate delegate;


    public Display(Model m, Delegate d) {
        model = m;
        model.addObserver(this); // adds itself to the obesrver list that the model has so it knows who to update.
        delegate = d;
    }


    public static BufferedImage getMandelbrotImage() { // returns the newly created image.
        return mandelbrotImage;
    }


    public static void creatMandelbrotImage() { // creates the image at the beginning.
        mandelbrotImage = new BufferedImage(Delegate.FRAME_WIDTH, Delegate.FRAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
    }


    @Override
    public void update(Observable o, Object arg) { // needed so this class knows what to do when the model notifies us of a change.
        mandelbrotImage = Model.getColouredImage();
        delegate.invokeRepaint(); // Draws the new image.
    }
}
