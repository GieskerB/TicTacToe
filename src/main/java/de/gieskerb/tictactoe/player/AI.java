package main.java.de.gieskerb.tictactoe.player;

import main.java.de.gieskerb.tictactoe.board.Boardv3;

public class AI {

    enum Difficulty {
        EASY, NORMAL, HARD
    }

    public static void makeMove(Boardv3 board) {
        for(int emptyTile: board.getEmptyTiles()) {
            System.out.println(emptyTile);
        }
    }

}
