package main.java.de.gieskerb.tictactoe.view;

import main.java.de.gieskerb.tictactoe.model.Updater;

import java.io.IOException;

public class Console extends Visual {

    char[][] displayBoard;

    public Console(Updater updater, byte boardSize) {
        super(updater, boardSize);

        this.displayBoard = new char[super.boardSize][super.boardSize];
        this.clearBoard();

        this.printBoard();
    }



    @Override
    public void update(Object obj) {
        int[] args = (int[]) obj;
        if (args.length == 1) {
            this.clearBoard();
        } else {
            this.displayBoard[args[0] / super.boardSize][args[0] % super.boardSize] = (args[1] == 0 ? 'X' : 'O');
        }
        clearConsole();
        this.printBoard();
    }

    private void clearBoard() {
        for (byte row = 0; row < super.boardSize; row++) {
            for (byte col = 0; col < super.boardSize; col++) {
                this.displayBoard[row][col] = ' ';
            }
        }
    }

    private StringBuilder horizontalLine() {
        StringBuilder horizontalLine = new StringBuilder();
        horizontalLine.append('+');
        for(byte i = 0; i < super.boardSize; i++) {
            horizontalLine.append("---+");
        }
        horizontalLine.append('\n');
        return horizontalLine;
    }

    private void printBoard() {
        StringBuilder result = new StringBuilder();
        result.append(horizontalLine());
        for (byte row = 0; row < super.boardSize; row++) {
            result.append("| ");
            for (byte col = 0; col < super.boardSize; col++) {
                result.append(this.displayBoard[row][col]);result.append(" | ");
            }
            result.append('\n');
            result.append(horizontalLine());
        }
        System.out.println(result);
    }

    private static void clearConsole() {
        try {

            if (System.getProperty("os.name").contains("Windows"))

                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

            else

                Runtime.getRuntime().exec("clear");

        } catch (IOException | InterruptedException ignored) {
        }

    }
}
