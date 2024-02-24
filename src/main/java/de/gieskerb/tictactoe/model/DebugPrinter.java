package main.java.de.gieskerb.tictactoe.model;

public class DebugPrinter {
    public final static char PLAYER1 = 'X', PLAYER2 = 'O', EMPTY = '_';
    /**
     * Pointer to Board. Get access to package Vars like BitMaps and size
     */
    private final Board boardPointer;

    public DebugPrinter(Board corresponding) {
        this.boardPointer = corresponding;
    }


    public void printBoard() {
        GameState gs = this.boardPointer.exportGameState();
        for (int row = 0; row < gs.size; row++) {
            for (int col = 0; col < gs.size; col++) {
                switch (gs.tiles[row* gs.size + col]) {
                    case 1:
                        System.out.print(PLAYER1);
                        break;
                    case -1:
                        System.out.print(PLAYER2);
                        break;
                    case 0:
                        System.out.print(EMPTY);
                        break;
                    default:
                }
            }
            System.out.println();
        }
    }
}
