package main.java.de.gieskerb.tictactoe.player;

import main.java.de.gieskerb.tictactoe.board.Board;

import java.util.Scanner;

public class Human {

    public enum InputDevice {
        CONSOLE, GUI
    }

    private static int makeConsoleMove(Board board, boolean isPlayerOne) {
        // TODO "Beautify"!
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private static int makeGUIMove(Board board, boolean isPlayerOne) {
        // TODO implement!
        return 0;
    }

    public static int makeMove(Board board, InputDevice inputDevice, boolean isPlayerOne) {
        switch (inputDevice) {
            case CONSOLE -> {
                return makeConsoleMove(board, isPlayerOne);
            }
            case GUI -> {
                return makeGUIMove(board, isPlayerOne);
            }
        }
        return -1;
    }
}
