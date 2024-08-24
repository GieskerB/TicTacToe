package main.java.de.gieskerb.tictactoe;

import java.util.ArrayList;

class ComputerPlayer {

    static ArrayList<Byte> getEmptyTiles(Board board) {
        ArrayList<Byte> emptyTiles = new ArrayList<>(board.getSize());
        for (byte i = 0; i < board.getSizeSquared(); i++ ) {
            if(board.isEmptyTile(i)) {
                emptyTiles.add(i);
            }
        }
        return emptyTiles;
    }

    static int easyDifficulty(Board board) {
        ArrayList<Byte> emptyTiles = getEmptyTiles(board);
        return emptyTiles.get((int) (Math.random() * emptyTiles.size()));
    }

    static int mediumDifficulty(Board board) {
        return 1;
    }

    static int hardDifficulty(Board board) {
        return 1;
    }

}
