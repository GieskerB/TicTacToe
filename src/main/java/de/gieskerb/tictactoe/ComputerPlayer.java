package main.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.enums.Player;

import java.util.ArrayList;

public class ComputerPlayer {

    public static ArrayList<Byte> getEmptyTiles(Board board) {
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

    static private int minimax(Board board, int depth) {
        if (board.checkWinPlayerOne()) {
            return -depth;
        } else if (board.checkWinPlayerTwo()) {
            return depth;
        } else if (board.checkTie()) {
            return 0;
        }

        ArrayList<Byte> emptyTiles = getEmptyTiles(board);
        int bestScore;
        if(board.getCurrentPlayer()== Player.ONE) {
            bestScore = Integer.MIN_VALUE;
            for(byte move: emptyTiles) {
                board.makeMove(move);

                int score = minimax(board, depth - 1);

                if(score > bestScore) {
                    bestScore = score;
                }

                board.undoMove(move);
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for(byte move: emptyTiles) {
                board.makeMove(move);

                int score = minimax(board, depth - 1);

                if(score < bestScore) {
                    bestScore = score;
                }

                board.undoMove(move);
            }
        }
        return bestScore;
    }

    static int hardDifficulty(Board board) {
        System.out.println("Started thinking");
        ArrayList<Byte> emptyTiles = getEmptyTiles(board);
        ArrayList<Byte> bestMoves = new ArrayList<>();
        int bestScore = Integer.MIN_VALUE;
        for(byte move: emptyTiles) {
            board.makeMove(move);

            int score = minimax(board, board.getSizeSquared());
            if (board.getPreviousPlayer() == Player.TWO) {
              score *= -1;
            }

            System.err.println("Score: " + score + " ");
            if (score > bestScore) {
                bestScore = score;
                bestMoves.clear();
                bestMoves.add(move);
            } else if(score == bestScore) {
                bestMoves.add(move);
            }

            board.undoMove(move);
        }

        for(byte move: bestMoves) {
            System.out.print(move + " ");
        } System.out.println();
        return bestMoves.get((int) (Math.random() * bestMoves.size()));
    }

}