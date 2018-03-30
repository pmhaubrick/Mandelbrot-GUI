package guiDelegate;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;

import model.Model;


public class Delegate extends JFrame {

    public static final int FRAME_WIDTH = 850; // Width constant for the GUI window.
    public static final int FRAME_HEIGHT = 850; // Height constant for the GUI window.
    protected Canvas canvas; // For adding a canvas to the window.
    private static int zoomX1;
    private static int zoomY1;
    private static int zoomX2;
    private static int zoomY2;
    protected static int dragX;
    protected static int dragY;
    private Model model;


    public Delegate(Model m) {
        initialiseGUI(); // Sets up the basic GUI settings.
        Display.creatMandelbrotImage(); // Creates a new Mandelbrot that is used when adding display.
        model = m;
        new Display(model, this);
    }


    public void addDisplay() {
        setLayout(new BorderLayout());
        canvas = new Canvas(this);
        canvas.setSize(FRAME_WIDTH / 2, FRAME_HEIGHT / 2);
        canvas.setVisible(true); // Makes the new canvas visible.
        add(canvas, BorderLayout.CENTER); // Adds the canvas to the centre of the JFrame.
        canvas.setupUI();
        listeners();
    }


    private void initialiseGUI() {
        setTitle("P4-GUI: Exploration of the Fractal Mandelbrot Set"); // Gives our individual title.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exits the program correctly when 'x' at top of the window is pressed.
        setSize(FRAME_WIDTH, FRAME_HEIGHT); // Sets the window dimensions to the variable constants that were set at the top.
        setResizable(false); // Forces the window to be a fixed size to make later calculations easier.
        setLocationRelativeTo(null); // Ensures the GUI window is generated at the centre of the screen.
        addDisplay(); // Method for adding the display on top of the initial GUI window.
        setVisible(true); // Makes everything visible.
    }


    public void invokeRepaint() {
        canvas.repaint(); // Causes the JPanel to update (repaint).
    }


    public void listeners() { // Used to get top left click when dragging the zoom.
        canvas.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                zoomX1 = e.getX();
                zoomY1 = e.getY();
            }
        });

        canvas.addMouseListener(new MouseAdapter() { // Tracks the second position, when the mouse is released after zoom drag.
            public void mouseReleased(MouseEvent e) {
                zoomX2 = e.getX();
                zoomY2 = e.getY();
                dragX = 0;
                dragY = 0;
                recalc("");
            }
        });

        canvas.addMouseMotionListener(new MouseMotionAdapter() { // Continually tracks the dragging co ordinates, to update
                                                                 // rectangle being drawn as visual aid.
            public void mouseDragged(MouseEvent e) {
                dragX = e.getX();
                dragY = e.getY();
                canvas.repaint();
            }
        });
    }


    public void recalc(String s) {
        model.updateMandelbrot(s); // Used as single point access to the model to recalculate the set.
    }


    public static int getZoomX1() {
        return zoomX1;
    }


    public static int getZoomY1() {
        return zoomY1;
    }


    public static int getZoomX2() {
        return zoomX2;
    }


    public static int getZoomY2() {
        return zoomY2;
    }


    public static void setZoomX1(int zoomX1) {
        Delegate.zoomX1 = zoomX1;
    }


    public static void setZoomY1(int zoomY1) {
        Delegate.zoomY1 = zoomY1;
    }


    public static void setZoomX2(int zoomX2) {
        Delegate.zoomX2 = zoomX2;
    }


    public static void setZoomY2(int zoomY2) {
        Delegate.zoomY2 = zoomY2;
    }
}
