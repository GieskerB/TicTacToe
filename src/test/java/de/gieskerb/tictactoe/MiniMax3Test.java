package test.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.board.Board;
import main.java.de.gieskerb.tictactoe.player.AI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MiniMax3Test {

    Board board;

    @BeforeEach
    void setUp() {
        board = new Board(3);
    }

    @Test
    public void testMiniMax() {
        int[] bestMoves = AI.getBestMoves(board,true);
        assertArrayEquals(new int[]{0,2,4,6,8}, bestMoves);
    }
}
