package main.java.de.gieskerb.tictactoe.player;

import main.java.de.gieskerb.tictactoe.board.Board;
import main.java.de.gieskerb.tictactoe.board.BoardManager;
import main.java.de.gieskerb.tictactoe.input.Console;
import main.java.de.gieskerb.tictactoe.input.Gui;

import java.util.Scanner;

public class Human {

    public enum InputDevice {
        CONSOLE, GUI
    }

    private final static Console console = new Console();
    private final static Gui gui = new Gui();

    private static int makeConsoleMove(BoardManager boardManager, boolean isPlayerOne) {
        // boardManger
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private static int makeGUIMove(BoardManager boardManger, boolean isPlayerOne) {
        // TODO implement!
        gui.checkChange(boardManger);
        return gui.getMove();
    }

    public static int makeMove(BoardManager boardManger, InputDevice inputDevice, boolean isPlayerOne) {
        switch (inputDevice) {
            case CONSOLE -> {
                gui.setVisible(false);
                return makeConsoleMove(boardManger, isPlayerOne);
            }
            case GUI -> {
                gui.setVisible(true);
                return makeGUIMove(boardManger, isPlayerOne);
            }
        }
        return -1;
    }
}
