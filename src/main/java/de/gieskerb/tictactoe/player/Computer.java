package main.java.de.gieskerb.tictactoe.player;

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

    @Override
    void myTurn() {
        // Calculates the Best move
        gamePointer.service(Origin.COMPUTER, 4);

    }
}
