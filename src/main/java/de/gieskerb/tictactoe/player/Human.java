package main.java.de.gieskerb.tictactoe.player;

import main.java.de.gieskerb.tictactoe.model.GameState;

public class Human extends Player{

    private static byte unnamedPlayerCount = 0;

    Human (boolean isPlayerOne) {
        this("Player " + ++unnamedPlayerCount, isPlayerOne);
    }
    Human(String name, boolean isPlayerOne) {
        super(name, isPlayerOne);
    }

    @Override
    public byte getMove(GameState gameState) {
        return 0;
    }
}
