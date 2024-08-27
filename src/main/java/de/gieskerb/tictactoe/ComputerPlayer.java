package main.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.enums.Player;

import java.util.ArrayList;

public class ComputerPlayer {

    public static int easyDifficulty(Board board) {
        ArrayList<Byte> emptyTiles = board.getEmptyTiles();
        return emptyTiles.get((int) (Math.random() * emptyTiles.size()));
    }

    public static int mediumDifficulty(Board board) {
        return 1;
    }

    static private int minimax(Board board, int depth, int alpha, int beta) {
        if (board.checkWinPlayerOne()) {
            return -depth;
        } else if (board.checkWinPlayerTwo()) {
            return depth;
        } else if (board.checkTie() ||  depth <= 0) {
            return 0;
        }

        final long LAST_VALID_MOVE = 1L << board.getSizeSquared();
        long emptyTilesBitmap = board.getEmptyTilesFast(), move = 1;
        int bestScore;
        if (board.getCurrentPlayer() == Player.ONE) {
            bestScore = Integer.MIN_VALUE;
            do {
                if ((move & emptyTilesBitmap) != 0) {
                    board.makeMoveFast(move);
                    final int score = minimax(board, depth - 1, alpha, beta);
                    board.undoMoveFast(move);


                    if (score > bestScore) bestScore = score;
                    if (alpha > score) alpha = score;
                    if (beta <= alpha) break;
                }
                move <<= 1;
            } while (move != LAST_VALID_MOVE);
        } else {
            bestScore = Integer.MAX_VALUE;
            do {
                if ((move & emptyTilesBitmap) != 0) {
                    board.makeMoveFast(move);
                    final int score = minimax(board, depth - 1, alpha, beta);
                    board.undoMoveFast(move);


                    if (score < bestScore) bestScore = score;
                    if (beta < score) beta = score;
                    if (beta <= alpha) break;
                }
                move <<= 1;
            } while (move != LAST_VALID_MOVE);
        }
        return bestScore;
    }

    public static int hardDifficulty(Board board) {
        ArrayList<Byte> allMoves = ComputerPlayer.hardDifficultyAllMoves(board);
        return allMoves.get((int) (Math.random() * allMoves.size()));
    }

    public static ArrayList<Byte> hardDifficultyAllMoves(Board board) {
        ArrayList<Byte> emptyTiles = board.getEmptyTiles();
        ArrayList<Byte> bestMoves = new ArrayList<>();
        int bestScore = Integer.MIN_VALUE;
        for (byte move : emptyTiles) {
            System.out.print(move);
            board.makeMove(move);

            int score = minimax(board, 11, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (board.getPreviousPlayer() == Player.TWO) {
                score *= -1;
            }

            if (score > bestScore) {
                bestScore = score;
                bestMoves.clear();
                bestMoves.add(move);
            } else if (score == bestScore) {
                bestMoves.add(move);
            }

            board.undoMove(move);
        }

        return bestMoves;
    }

}
