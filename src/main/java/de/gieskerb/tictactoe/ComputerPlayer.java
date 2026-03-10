package main.java.de.gieskerb.tictactoe;

import java.util.ArrayList;

public class ComputerPlayer {


    public static byte easyDifficulty(Board board) {
        return board.getEmptyTiles().get((int) (Math.random() * board.getEmptyTiles().size()));
    }

    public static byte mediumDifficulty(Board board) {
        return easyDifficulty(board);
    }

    public static byte hardDifficulty(Board board) {
        return bestMoves(board);
    }

    private static byte bestMoves(Board board) {
        ArrayList<Byte> bestMoves = new ArrayList<>();

        for (byte move: board.getEmptyTiles()) {
            board.makeMove(move);

            board.undoMove(move);
        }

        return bestMoves.get((int) (Math.random() * bestMoves.size()));
    }

    private static int miniMax(Board board, byte depth, byte alpha, byte beta, boolean isMaximizingPlayer) {
        // Basisfall: Wenn die maximale Tiefe erreicht ist oder das Spiel zu Ende ist, gib die Bewertung des aktuellen Boards zurück
        if (depth == 0 || board.checkGameOver()) {
            return evaluateBoard(board);
        }

        // Maximierung: Der Maximierer versucht, den höchsten Wert zu erzielen
        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;

            for (byte move : board.getEmptyTiles()) {
                board.makeMove(move);
                int eval = miniMax(board, (byte) (depth - 1), alpha, beta, false);
                board.undoMove(move);

                maxEval = Math.max(maxEval, eval);
                alpha = (byte) Math.max(alpha, eval);

                // Beta-Pruning: Wenn der Wert von maxEval >= beta, breche die Schleife ab
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        } else {
            // Minimierung: Der Minimierer versucht, den niedrigsten Wert zu erzielen
            int minEval = Integer.MAX_VALUE;

            for (byte move : board.getEmptyTiles()) {
                board.makeMove(move);
                int eval = miniMax(board, (byte) (depth - 1), alpha, beta, true);
                board.undoMove(move);

                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);

                // Alpha-Pruning: Wenn der Wert von minEval <= alpha, breche die Schleife ab
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }
}
