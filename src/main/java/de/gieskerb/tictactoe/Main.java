package main.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.board.BoardManager;

import javax.swing.*;

public class Main {
    public static void main (String[] args)  {
        BoardManager boardManager = BoardManager.getInstance();
        BoardManager.Player winningPlayer = boardManager.playFullRound();
        // Print WINNER -> Move to loop for infinite play
    }

//        try {
//            UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
//               IllegalAccessException ignored) {
//        }
//
//        new GameWindow();
//    }
}
