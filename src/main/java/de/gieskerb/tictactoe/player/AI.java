package main.java.de.gieskerb.tictactoe.player;

import main.java.de.gieskerb.tictactoe.board.Board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AI {

    public enum Difficulty {
        EASY, NORMAL, HARD
    }

    private static Random random = new Random();

    private static int makeEasyMove(Board board) {
        final int[] moves = board.getEmptyTiles();
        return moves[random.nextInt(moves.length)];
    }

    private static int makeNormalMove(Board board) {
        // TODO
        return makeEasyMove(board);
    }

    // PlayerOne is maximizing
    private static int miniMaxMove(Board board, boolean maximizing, int alpha, int beta, int depth) {
        if (board.player1.checkWin()) {
            return 100  -depth;
        } else if (board.player2.checkWin()) {
            return -100 + depth;
        } else if(board.checkTie()) {
            return 0;
        }

        int bestScore = maximizing ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        Board.Player currentPlayer = maximizing ? board.player1 : board.player2;
        for (int move: board.getEmptyTiles()) {
            currentPlayer.makeMove(move);
            int score = miniMaxMove(board, maximizing, 0, 0, depth + 1);
            currentPlayer.undoMove(move);
            if (score > bestScore) {
                bestScore = score;
            }
        }
        return bestScore;
    }

    private static int makeHardMove(Board board, boolean isPlayerOne) {
        int[] bestMoves =  getBestMoves(board, isPlayerOne);
        return bestMoves[random.nextInt(bestMoves.length)];
    }

    public static int[] getBestMoves(Board board, boolean isPlayerOne) {
        ArrayList<Integer> bestMoves = new ArrayList<>();
        int bestScore = isPlayerOne ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Board.Player currentPlayer = isPlayerOne ? board.player1 : board.player2;
        for (int move: board.getEmptyTiles()) {
            currentPlayer.makeMove(move);
            int score = miniMaxMove(board, !isPlayerOne,  0,0, 0);
            currentPlayer.undoMove(move);
            if(score > bestScore) {
                bestScore = score;
                bestMoves.clear();
                bestMoves.add(move);
            } else if (score == bestScore) {
                bestMoves.add(move);
            }
        }
        // TODO maybe for loops.
        return Arrays.stream(bestMoves.toArray(Integer[]::new)).mapToInt(Integer::intValue).toArray();
    }

    public static int makeMove(Board board, Difficulty difficulty, boolean isPlayerOne) {
        switch (difficulty) {
            case EASY -> {
                return makeEasyMove(board);
            }
            case NORMAL -> {
                return makeNormalMove(board);
            }
            case HARD -> {
                return makeHardMove(board, isPlayerOne);
            }
        }
        return -1;
    }

}
