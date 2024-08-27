package test.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.Board;
import main.java.de.gieskerb.tictactoe.ComputerPlayer;
import main.java.de.gieskerb.tictactoe.enums.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class MiniMaxTest {

    static private int stdMinimax(Board board, int depth) {
        if (board.checkWinPlayerOne()) {
            return -depth;
        } else if (board.checkWinPlayerTwo()) {
            return depth;
        } else if (board.checkTie()) {
            return 0;
        }

        ArrayList<Byte> emptyTiles = board.getEmptyTiles();
        int bestScore;
        if(board.getCurrentPlayer()== Player.ONE) {
            bestScore = Integer.MIN_VALUE;
            for(byte move: emptyTiles) {
                board.makeMove(move);

                int score = stdMinimax(board, depth - 1);

                if(score > bestScore) {
                    bestScore = score;
                }

                board.undoMove(move);
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for(byte move: emptyTiles) {
                board.makeMove(move);

                int score = stdMinimax(board, depth - 1);

                if(score < bestScore) {
                    bestScore = score;
                }

                board.undoMove(move);
            }
        }
        return bestScore;
    }

    private static ArrayList<Byte> bestMoves(Board board) {
        ArrayList<Byte> emptyTiles = board.getEmptyTiles();
        ArrayList<Byte> bestMoves = new ArrayList<>();
        int bestScore = Integer.MIN_VALUE;
        for(byte move: emptyTiles) {
            board.makeMove(move);

            int score = stdMinimax(board, board.getSizeSquared());
            if (board.getPreviousPlayer() == Player.TWO) {
                score *= -1;
            }

            if (score > bestScore) {
                bestScore = score;
                bestMoves.clear();
                bestMoves.add(move);
            } else if(score == bestScore) {
                bestMoves.add(move);
            }

            board.undoMove(move);
        }

        return bestMoves;
    }

    Board board;

    @BeforeEach
    void setUp() {
        this.board = new Board(3);
    }

    @Test
    void testFirstMove() {
        var checkList = bestMoves(board);
        var testList = ComputerPlayer.hardDifficultyAllMoves(board);

        assertEquals(checkList,testList);
    }

}
