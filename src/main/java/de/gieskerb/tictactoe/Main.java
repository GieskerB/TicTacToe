package main.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.board.BoardManager;

import javax.swing.*;

public class Main {
    public static void main (String[] args)  {
        BoardManager boardManager = new BoardManager();
        BoardManager.Player winningPlayer = boardManager.playFullRound();
        System.out.println(winningPlayer);
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
