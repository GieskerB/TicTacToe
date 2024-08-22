package main.java.de.gieskerb.tictactoe.visual;

import main.java.de.gieskerb.tictactoe.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {

    public GameWindow() {
        super();
        super.setTitle("Tic-Tac-Toe");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLayout(new BorderLayout());

        GridPanel gridPanel = new GridPanel();
        InfoPanel infoPanel = new InfoPanel(gridPanel);
        super.add(infoPanel, BorderLayout.NORTH);
        super.add(gridPanel, BorderLayout.SOUTH);

        super.pack();
        super.setLocationRelativeTo(null);
        super.setVisible(true);
    }

}
