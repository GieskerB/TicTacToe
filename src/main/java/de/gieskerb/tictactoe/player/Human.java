package main.java.de.gieskerb.tictactoe.player;

public class Human extends Player {

    private static byte unnamedPlayerCount = 0;

    public Human(boolean isPlayerOne) {
        this("Player " + ++unnamedPlayerCount, isPlayerOne);
    }

    public Human(String name, boolean isPlayerOne) {
        super(name, isPlayerOne);
    }

    @Override
    void myTurn() {
        // Wait for Player Input via mouse or console
    }
}
