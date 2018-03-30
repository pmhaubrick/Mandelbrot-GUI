package main;

import guiDelegate.Delegate;
import model.Model;


/**
 * This Main Class contains only the main method which is the entry point to the program. It is simply used to set up the elements
 * of the GUI which then will usually only act in response to user interaction/requests, heard by listeners.
 * 
 * @author 170009629
 *
 */
public class Main {

    /**
     * As mentioned previously, this method simply allows the GUI elements to be initialised.
     * 
     * @param args contains nothing for this practical.
     */
    public static void main(String[] args) {
        Model model = new Model(); // This creates the model.
        new Delegate(model); // Activates Delegate's constructor, passing is the model object (for observing purposes).
        model.updateMandelbrot(""); // Performs the initial calculations to get a start image for the default values.
    }

}
