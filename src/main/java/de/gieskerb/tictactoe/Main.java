package main.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.visual.GameWindow;

import javax.swing.*;

public class Main {
    public static void main (String[] args)  {

        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
               IllegalAccessException e) {
            // handle exception
        }

        new GameWindow();
    }
}
