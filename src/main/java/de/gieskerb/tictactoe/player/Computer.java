package main.java.de.gieskerb.tictactoe.player;

import main.java.de.gieskerb.tictactoe.model.DebugPrinter;
import main.java.de.gieskerb.tictactoe.model.Game;
import main.java.de.gieskerb.tictactoe.model.GameState;
import main.java.de.gieskerb.tictactoe.model.Origin;

import java.awt.event.MouseEvent;

public class Computer extends Player{

    private static byte totalComputerCount = 0;
    public Computer(boolean isPlayerOne) {
        this("Computer " + ++totalComputerCount, isPlayerOne);
    }

    public Computer(String name, boolean isPlayerOne) {
        super(name, isPlayerOne);
    }

    /**
     * Counts in O(1) the number of ones in the binary representation of a number
     * <p>
     * Source:
     * <a href="https://stackoverflow.com/questions/8871204/count-number-of-1s-in-binary-representation">...</a>
     *
     * @param bitmap count it's number of ones
     * @return the number
     */
    private byte bitCount(long bitmap) {
        long count = bitmap - ((bitmap >> 1) & 033333333333) - ((bitmap >> 2) & 011111111111);
        return (byte) (((count + (count >> 3)) & 030707070707) % 63);
    }

    @Override
    void myTurn() {
        // Let's do a random move first:
        GameState gameState = super.gamePointer.getBoard().exportGameState();

        // This bitmap has a one on each bit / tile which is still playable



    }
}
