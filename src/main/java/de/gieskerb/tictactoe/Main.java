package main.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.board.WinningBitMapGenerator;
import main.java.de.gieskerb.tictactoe.visual.GameWindow;

import javax.swing.*;

public class Main {
    public static void main (String[] args)  {

        long[] maps = WinningBitMapGenerator.getBitMaps((byte)2);
        maps = WinningBitMapGenerator.getBitMaps((byte)2);
        maps = WinningBitMapGenerator.getBitMaps((byte)3);
        maps = WinningBitMapGenerator.getBitMaps((byte)3);
        maps = WinningBitMapGenerator.getBitMaps((byte)8);
        maps = WinningBitMapGenerator.getBitMaps((byte)8);
        maps = WinningBitMapGenerator.getBitMaps((byte)8);
        maps = WinningBitMapGenerator.getBitMaps((byte)8);

//        try {
//            UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
//               IllegalAccessException ignored) {
//        }
//
//        new GameWindow();
    }
}
