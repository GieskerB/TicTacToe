package main.java.de.gieskerb.tictactoe.player;

import main.java.de.gieskerb.tictactoe.model.GameState;

public class Computer extends Player{

    private static byte totalComputerCount = 0;
    Computer(boolean isPlayerOne) {
        super("Computer " + ++totalComputerCount, isPlayerOne);
    }

    @Override
    public byte getMove(GameState gameState) {
        return 0;
    }
}
