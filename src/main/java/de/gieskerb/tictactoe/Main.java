package main.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.board.Board;

import javax.swing.*;

public class Main {
    public static void main (String[] args)  {
        Board board = new Board(8);
        for(int i = 0; i< 64; i++) {
            board.player1.makeMove(i);
        }
        System.out.println(board.getEvaluation(0));
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
