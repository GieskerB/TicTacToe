package main.java.de.gieskerb.tictactoe.player;

import main.java.de.gieskerb.tictactoe.board.Board;

import java.util.Random;

public class AI {

    public enum Difficulty {
        EASY, NORMAL, HARD
    }

    private static Random random = new Random();

    private static int makeEasyMove(Board board) {
        int[] moves = board.getEmptyTiles();
        return moves[random.nextInt(moves.length)];
    }

    private static int makeNormalMove(Board board) {
        // TODO
        return makeEasyMove(board);
    }

    private static int makeHardMove(Board board) {

    }

    public static int makeMove(Board board, Difficulty difficulty) {
        switch (difficulty) {
            case EASY -> {
                return makeEasyMove(board);
            }
            case NORMAL -> {
                return makeNormalMove(board);
            }
            case HARD -> {
                return makeHardMove(board);
            }
        }
        return -1;
    }

}
