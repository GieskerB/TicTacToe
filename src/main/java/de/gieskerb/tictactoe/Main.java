package main.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.model.Board;
import main.java.de.gieskerb.tictactoe.model.DebugPrinter;

public class Main {
    public static void main (String[] args)  {
        var b3 = new Board();
        b3.makeMove(0, 2);
        b3.makeMove(0, 0);
        b3.makeMove(1, 2);
        b3.makeMove(1, 0);
        b3.makeMove(2, 2);
        var printer = new DebugPrinter(b3);
        printer.printBitMapP1();
        printer.printBitMapP2();
        printer.printPatterns();
    }

}
