package main.java.de.gieskerb.tictactoe.view;

import main.java.de.gieskerb.tictactoe.controller.MouseClick;
import main.java.de.gieskerb.tictactoe.model.Updater;

import javax.swing.*;
import java.awt.*;

public class GUI extends Visual {

    private JFrame window;


    public GUI (Updater updater, int pixelSize, int boardSize) {

        JPanel tmp = new JPanel();
        tmp.setSize(pixelSize, pixelSize);
        tmp.setPreferredSize(tmp.getSize());
        tmp.setLayout(new GridLayout(boardSize,boardSize));
        for(byte i = 0; i< boardSize * boardSize; i++) {
            JButton button = new JButton();
            button.addActionListener(new MouseClick(updater, i));
            tmp.add(button);
        }

        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Look and feel not set.");
        }

        this.window = new JFrame();
        this.window.add(tmp);
        this.window.pack();
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setLocationRelativeTo(null);
        this.window.setVisible(true);


    }

    @Override
    public void update(Object obj) {

    }
}
