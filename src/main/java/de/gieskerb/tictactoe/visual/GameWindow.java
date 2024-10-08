package main.java.de.gieskerb.tictactoe.visual;

import main.java.de.gieskerb.tictactoe.GamePlayLoop;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {


     static final Color BACKGROUND_COLOR = new Color(69,69,69);
     static final Color FOREGROUND_COLOR = new Color(255,127,31);

    public GameWindow() {
        super();
        super.setTitle(">>> The ultimate Tic-Tac-Toe <<<");
        super.setIconImage(new ImageIcon("ico/logo/icons8-tic-tac-toe-100.png").getImage());
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLayout(new BorderLayout());

        GamePlayLoop gamePlayLoop = new GamePlayLoop();
        GridPanel gridPanel = new GridPanel(gamePlayLoop);
        gamePlayLoop.attachGridPanel(gridPanel);
        ControlPanel infoPanel = new ControlPanel(gamePlayLoop);

        super.add(infoPanel, BorderLayout.NORTH);
        super.add(gridPanel, BorderLayout.SOUTH);

        super.pack();
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        super.setVisible(true);
    }

}
