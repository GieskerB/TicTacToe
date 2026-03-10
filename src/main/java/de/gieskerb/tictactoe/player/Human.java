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


    static Scanner scanner = new Scanner(System.in);

    private static int makeConsoleMove(boolean isPlayerOne) {
        // boardManger
        System.out.print("Enter the tile you want to play [1 - BOARD-SIZE].\n" +
                         "(To change the grid size type 'G' followed by the new grid size [example: G3])\n" +
                         "(To change the game mode type 'P/C' + 'v' + 'P/C' [example PvP])\n"+
                         "=>>> ");
        int move = -1;
        boolean numberInput = true;
        String input = scanner.nextLine();
        try {
            move = Integer.parseInt(input);
            //TODO bounds check
        } catch (NumberFormatException e) {
            numberInput = false;
        }
        if (!numberInput) {
            input = input.toLowerCase();
            switch (input.length()) {
                case 2:
                    if (input.charAt(0) == 'g') { // Turn around (nesting)
                        // Starts with G
                        char gridSize = input.charAt(1);
                        if (gridSize >= '0' && gridSize <= '9') {
                            // Correct format
                            BoardManager.getInstance().changeSize((byte) (gridSize - '0'));
                        } else  {
                            //TODO ERROR PRINT
                        }
                    } else {
                        //TODO ERROR PRINT
                    }
                    break;
                case 3:
                    if(input.charAt(0) == 'v') { // Turn around (nesting)
                        char firstPlayer = input.charAt(0);
                        char secondPlayer = input.charAt(2);
                        if ((firstPlayer == 'p'|| firstPlayer == 'c' ) &&
                            (secondPlayer == 'p' || secondPlayer == 'c')) {
                            // Correct format

                        } else  {
                            //TODO ERROR PRINT
                        }
                    } else {
                        // TODO ERROR PRINT
                    }

                    break;
                default:
                    // TODO ERROR PRINT
            }
        }
        return move-1;
    }

    private static int makeGUIMove(boolean isPlayerOne) {
        gui.updateChanges();
        return gui.getMove();
    }

    public static int makeMove(InputDevice inputDevice, boolean isPlayerOne) {
        switch (inputDevice) {
            case CONSOLE -> {
                gui.setVisible(false);
                return makeConsoleMove(isPlayerOne);
            }
            case GUI -> {
                gui.setVisible(true);
                return makeGUIMove(isPlayerOne);
            }
        }
        return -1;
    }
}
