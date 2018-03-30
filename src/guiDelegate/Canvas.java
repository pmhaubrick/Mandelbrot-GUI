package guiDelegate;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.Model;


public class Canvas extends JPanel {
    // creates new buttons
    private JButton undo, redo, reset, iterations, save, reload, keepAS, savePNG, saveJPEG, red, green, blue, white;
    private JTextArea iterationInput;
    private int iterationSelection;
    private Delegate delegate;


    public Canvas(Delegate d) {
        delegate = d;
    }


    @Override
    public void paintComponent(Graphics g) { // The main paint component that is activated whenever repaint is called.
        g.drawImage(Display.getMandelbrotImage(), 0, 0, null);
        if (delegate.dragX != 0) {
            int recWidth;
            int recHeight;
            int startX;
            int startY;
            if (delegate.dragX > Delegate.getZoomX1()) { // These conditional statements are for drawing the correct  
                recWidth = delegate.dragX - Delegate.getZoomX1(); // square or rectangle depending if aspect ratio
                startX = Delegate.getZoomX1(); // is toggled
            } else {
                recWidth = Delegate.getZoomX1() - delegate.dragX;
                startX = delegate.dragX;
            }
            if (delegate.dragY > Delegate.getZoomY1()) {
                recHeight = delegate.dragY - Delegate.getZoomY1();
                startY = Delegate.getZoomY1();
            } else {
                recHeight = Delegate.getZoomY1() - delegate.dragY;
                startY = delegate.dragY;
            }
            g.setColor(Color.YELLOW); // sets the colour of the rectangle.
            if (model.Model.isRetainAspectRatio()) {
                startY = Delegate.getZoomY1();
                g.drawRect(startX, startY, recWidth, recWidth); // Draws rectangle with aspect ratio.
            } else {
                g.drawRect(startX, startY, recWidth, recHeight);// Draws rectangle without aspect ratio.
            }
        }
    }


    // All button presses are self-explanatory as are mostly repeated code. They each call the appropriate model method individual
    // to them.

    public void setupUI() {
        iterationInput = new JTextArea(String.valueOf(Model.getMaxIterations()));
        iterationInput.setPreferredSize(new Dimension(50, 20));
        this.add(iterationInput);
        iterations = new JButton("Set Iterations");
        iterations.setPreferredSize(new Dimension(135, 20));
        iterations.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    iterationSelection = Integer.parseInt(iterationInput.getText());
                } catch (Exception ex) {
                }
                Model.setMaxIterations(iterationSelection);
                delegate.recalc("");
            }
        });
        this.add(iterations);

        undo = new JButton("Undo");
        undo.setPreferredSize(new Dimension(78, 20));
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delegate.recalc("undo");
                repaint();
            }
        });
        this.add(undo);

        redo = new JButton("Redo");
        redo.setPreferredSize(new Dimension(78, 20));
        redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delegate.recalc("redo");
                repaint();
            }
        });
        this.add(redo);

        reset = new JButton("Reset");
        reset.setPreferredSize(new Dimension(78, 20));
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.MethodsForListeners.reset();
                delegate.recalc("");
                iterationInput.setText(String.valueOf(Model.getMaxIterations()));
            }
        });
        this.add(reset);

        save = new JButton("Save to file");
        save.setPreferredSize(new Dimension(120, 20));
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model.MethodsForListeners.saveState();
                } catch (FileNotFoundException e1) {
                }
            }
        });
        this.add(save);

        reload = new JButton("Reload");
        reload.setPreferredSize(new Dimension(84, 20));
        reload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model.MethodsForListeners.reloadState();
                    delegate.recalc("");
                } catch (FileNotFoundException e1) {
                }
            }
        });
        this.add(reload);

        savePNG = new JButton("Save Image (png)");
        savePNG.setPreferredSize(new Dimension(162, 20));
        savePNG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File("MyFavouriteFractalImage.png");
                try {
                    ImageIO.write(Display.getMandelbrotImage(), "png", file);
                } catch (IOException e1) {
                }
            }
        });
        this.add(savePNG);

        saveJPEG = new JButton("Save Image (jpg)");
        saveJPEG.setPreferredSize(new Dimension(162, 20));
        saveJPEG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File("MyFavouriteFractalImage.jpg");
                try {
                    ImageIO.write(Display.getMandelbrotImage(), "JPEG", file);
                } catch (IOException e1) {
                }
            }
        });
        this.add(saveJPEG);

        keepAS = new JButton("Toggle \"Zoom With Aspect Ratio\"");
        keepAS.setPreferredSize(new Dimension(275, 20));
        keepAS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.MethodsForListeners.toggleRetainAspectRatio();
            }
        });
        this.add(keepAS);

        red = new JButton("Colour map: Red");
        red.setPreferredSize(new Dimension(167, 20));
        red.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model.setColourChoice(0);
                delegate.recalc("");
            }
        });
        this.add(red);

        green = new JButton("Colour map: Green");
        green.setPreferredSize(new Dimension(175, 20));
        green.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model.setColourChoice(1);
                delegate.recalc("");
            }
        });
        this.add(green);

        blue = new JButton("Colour map: Blue");
        blue.setPreferredSize(new Dimension(175, 20));
        blue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model.setColourChoice(2);
                delegate.recalc("");
            }
        });
        this.add(blue);

        white = new JButton("Colour map: White");
        white.setPreferredSize(new Dimension(175, 20));
        white.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model.setColourChoice(3);
                delegate.recalc("");
            }
        });
        this.add(white);
    }

}