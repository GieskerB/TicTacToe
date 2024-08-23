package main.java.de.gieskerb.tictactoe.visual;

import main.java.de.gieskerb.tictactoe.Board;
import main.java.de.gieskerb.tictactoe.GamePlayLoop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {


     static final Color BACKGROUND_COLOR = new Color(69,69,69);
     static final Color FOREGROUND_COLOR = new Color(255,127,31);

    public GameWindow() {
        super();
        super.setTitle("Tic-Tac-Toe");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLayout(new BorderLayout());

        GamePlayLoop gamePlayLoop = new GamePlayLoop();
        GridPanel gridPanel = new GridPanel(gamePlayLoop);
        gamePlayLoop.attachGridPanel(gridPanel);
        InfoPanel infoPanel = new InfoPanel(gridPanel,gamePlayLoop);

        super.add(infoPanel, BorderLayout.NORTH);
        super.add(gridPanel, BorderLayout.SOUTH);

        super.pack();
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        super.setVisible(true);
    }

}
