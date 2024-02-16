package main.java.de.gieskerb.tictactoe.player;

import main.java.de.gieskerb.tictactoe.model.DebugPrinter;
import main.java.de.gieskerb.tictactoe.model.Game;
import main.java.de.gieskerb.tictactoe.model.GameState;
import main.java.de.gieskerb.tictactoe.model.Origin;

import java.awt.event.MouseEvent;

public class Computer extends Player{

    private static byte totalComputerCount = 0;

    private Brain brain;

    public Computer(boolean isPlayerOne, Brain brain) {
        this("Computer " + ++totalComputerCount, isPlayerOne, brain);
    }

    public Computer(String name, boolean isPlayerOne, Brain brain) {
        super(name, isPlayerOne);
        this.brain = brain;
    }


    @Override
    public int getMove(GameState gameState) {
        return this.brain.thinkOfMove(gameState);
    }
}
