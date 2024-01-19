package main.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.model.Board;
import main.java.de.gieskerb.tictactoe.model.DebugPrinter;
import main.java.de.gieskerb.tictactoe.model.Game;
import main.java.de.gieskerb.tictactoe.model.GameState;

public class Main {
    public static void main (String[] args)  {
        new Game(3);
        /*
        var b3 = new Board();
        b3.makeMove(0, 2);
        System.out.println(GameState.getKey(b3.exportGameState()));
        b3.makeMove(0, 0);
        System.out.println(GameState.getKey(b3.exportGameState()));
        b3.makeMove(1, 2);
        System.out.println(GameState.getKey(b3.exportGameState()));
        b3.makeMove(1, 0);
        System.out.println(GameState.getKey(b3.exportGameState()));
        b3.makeMove(2, 2);
        System.out.println(GameState.getKey(b3.exportGameState()));
        var printer = new DebugPrinter(b3);

        printer.printBitMapP1();
        printer.printBitMapP2();
        printer.printPatterns();
         */
    }

}
