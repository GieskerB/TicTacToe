package main.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.visual.GameWindow;

import javax.swing.*;

public class Main {

    /*
        TODO
        IMPORTANT: <<< is the unsigned shift operator!!!
     */

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                 IllegalAccessException ignored) {
        }

        new GameWindow();
    }
}
