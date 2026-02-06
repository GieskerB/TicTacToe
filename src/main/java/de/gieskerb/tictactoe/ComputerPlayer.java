package main.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.enums.Player;

import java.util.ArrayList;

public class ComputerPlayer {

//    private static Buffer buffer = new Buffer(16);

    public static int easyDifficulty(Board board) {
        ArrayList<Byte> emptyTiles = board.getEmptyTiles();
        return emptyTiles.get((int) (Math.random() * emptyTiles.size()));
    }

    public static int mediumDifficulty(Board board) {
        return 1;
    }

    static private byte minimax(Board board, byte depth, int alpha, int beta) {
        if (board.checkWinPlayerOne()) {
            return (byte) -depth;
        } else if (board.checkWinPlayerTwo()) {
            return depth;
        } else if (board.checkTie() ||  depth <= 0) {
            return 0;
        }

        final long LAST_VALID_MOVE = 1L << board.getSizeSquared();
        long emptyTilesBitmap = board.getEmptyTilesFast(), move = 1;
        byte bestScore;
        if (board.getCurrentPlayer() == Player.ONE) {
            bestScore = Byte.MIN_VALUE;
            do {
                if ((move & emptyTilesBitmap) != 0) {
                    board.makeMoveFast(move);
                    final byte score = minimax(board, (byte) (depth - 1), alpha, beta);
                    board.undoMoveFast(move);


                    if (score > bestScore) bestScore = score;
                    if (alpha > score) alpha = score;
                    if (beta <= alpha) break;
                }
                move <<= 1;
            } while (move != LAST_VALID_MOVE);
        } else {
            bestScore = Byte.MAX_VALUE;
            do {
                if ((move & emptyTilesBitmap) != 0) {
                    board.makeMoveFast(move);
                    final byte score = minimax(board, (byte) (depth - 1), alpha, beta);
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

    private static Buffer moveBuffer = new Buffer(12);

    public static ArrayList<Byte> hardDifficultyAllMoves(Board board) {
        ArrayList<Byte> emptyTiles = board.getEmptyTiles();
        ArrayList<Byte> bestMoves = new ArrayList<>();
        byte bestScore = Byte.MIN_VALUE;
        for (byte move : emptyTiles) {
            board.makeMove(move);

            byte score;
            boolean containedInBuffer = false;
            if(moveBuffer.contains(board.getBitMapPlayerOne(), board.getBitMapPlayerTwo(), board.getSize())) {
                score = moveBuffer.getValue(board.getBitMapPlayerOne(), board.getBitMapPlayerTwo(), board.getSize());
                containedInBuffer = true;
            } else {
                score = minimax(board, (byte) 5, Integer.MIN_VALUE, Integer.MAX_VALUE);
            }

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

            if(!containedInBuffer) {
                moveBuffer.setValue(board.getBitMapPlayerOne(), board.getBitMapPlayerTwo(), board.getSize(), (byte) -bestScore);
            }

            board.undoMove(move);
        }

        return bestMoves;
    }

}
