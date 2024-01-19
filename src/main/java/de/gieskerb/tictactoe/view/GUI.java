package main.java.de.gieskerb.tictactoe.view;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    public GUI (int pixelSize, int boardSize) {

        JPanel tmp = new JPanel();
        tmp.setSize(pixelSize, pixelSize);
        tmp.setPreferredSize(tmp.getSize());
        tmp.setLayout(new GridLayout(boardSize,boardSize));
        for(int i = 0; i< boardSize * boardSize; i++) {
            tmp.add(new JButton());
        }

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            System.err.println("Look and feel not set.");
        }

        super.add(tmp);
        super.pack();
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLocationRelativeTo(null);
        super.setVisible(true);


    }

}
