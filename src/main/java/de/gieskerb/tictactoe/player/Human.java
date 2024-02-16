package main.java.de.gieskerb.tictactoe.player;

import main.java.de.gieskerb.tictactoe.model.GameState;

public class Human extends Player {

    private static byte unnamedPlayerCount = 0;

    public Human(boolean isPlayerOne) {
        this("Player " + ++unnamedPlayerCount, isPlayerOne);
    }

    public Human(String name, boolean isPlayerOne) {
        super(name, isPlayerOne);
    }


    @Override
    public int getMove(GameState gameState) {
        return -1;
    }
}
