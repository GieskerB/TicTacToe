package main.java.de.gieskerb.tictactoe.player;

import main.java.de.gieskerb.tictactoe.model.GameState;

import java.awt.event.MouseEvent;

public class Computer extends Player{

    private static byte totalComputerCount = 0;
    Computer(boolean isPlayerOne) {
        super("Computer " + ++totalComputerCount, isPlayerOne);
    }

}
