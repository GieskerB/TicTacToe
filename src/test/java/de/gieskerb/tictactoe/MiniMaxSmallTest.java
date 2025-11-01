package test.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.board.Board;
import main.java.de.gieskerb.tictactoe.player.AI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MiniMaxSmallTest {

    Board board;

    @BeforeEach
    void setUp() {
        board = new Board(3);
    }

    @Test
    void testFirstMovePlayer1() {
        int[] bestMoves = AI.getBestMoves(board,true);
        // Actually all starting positions lead to a draw when played perfectly
        assertArrayEquals(new int[]{0,1,2,3,4,5,6,7,8}, bestMoves);
    }

    @Test
    void testFirstMovePlayer2() {
        int[] bestMoves = AI.getBestMoves(board,false);
        assertArrayEquals(new int[]{0,1,2,3,4,5,6,7,8}, bestMoves);
    }

    @Test
    void testSecondMoveAfterCorner() {
        board.player1.makeMove(0);
        int[] bestMoves = AI.getBestMoves(board,false);
        assertArrayEquals(new int[]{4}, bestMoves);
    }

    @Test
    void testSecondMoveAfterMiddle() {
        board.player1.makeMove(4);
        int[] bestMoves = AI.getBestMoves(board,false);
        assertArrayEquals(new int[]{0,2,6,8}, bestMoves);
    }

    @Test
    void testSecondMoveAfterEdge() {
        board.player1.makeMove(1);
        int[] bestMoves = AI.getBestMoves(board,false);
        assertArrayEquals(new int[]{0, 2, 4, 7}, bestMoves);
    }

    @Test
    void testWinningNoEnemy() {
        board.player1.makeMove(0);
        board.player1.makeMove(1);
        int[] bestMoves = AI.getBestMoves(board,true);
        assertArrayEquals(new int[]{2}, bestMoves);
    }


}
