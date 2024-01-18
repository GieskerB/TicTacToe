package test.java.de.gieskerb.tictactoe.model;

import main.java.de.gieskerb.tictactoe.exceptions.*;
import main.java.de.gieskerb.tictactoe.model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void testMakeMoveWithTile() {
        board.makeMove(4);
        assertFalse(board.isGameOver());
        assertEquals(0b000010000, board.getBitMapPlayer1());
    }

    @Test
    void testMakeMoveWithRowAndCol() {
        board.makeMove(1, 1);
        assertFalse(board.isGameOver());
        assertEquals(0b000010000, board.getBitMapPlayer1());
    }

    @Test
    void testMakeMoveOutOfBounce() {
        assertThrows(OutOfBounceException.class, () -> board.makeMove(-1));
        assertThrows(OutOfBounceException.class, () -> board.makeMove(9));
        assertThrows(OutOfBounceException.class, () -> board.makeMove(10, 2));
    }

    @Test
    void testMakeMoveNonEmptyTile() {
        board.makeMove(1, 1);
        assertThrows(NonEmptyTileException.class, () -> board.makeMove(1, 1));
    }

    @Test
    void testMakeMoveSwitchTurns() {
        board.makeMove(0);
        assertFalse(board.isGameOver());
        board.makeMove(4);
        assertFalse(board.isGameOver());
        assertEquals(0b000010000, board.getBitMapPlayer2());
    }

    @Test
    void testIsGameOverWithWin() {
        board.makeMove(0);
        board.makeMove(3);
        board.makeMove(1);
        board.makeMove(4);
        board.makeMove(2);
        assertTrue(board.isGameOver());
    }

    @Test
    void testIsGameOverWithDraw() {
        board.makeMove(0);
        board.makeMove(1);
        board.makeMove(2);
        board.makeMove(3);
        board.makeMove(5);
        board.makeMove(4);
        board.makeMove(6);
        board.makeMove(8);
        board.makeMove(7);
        assertTrue(board.isGameOver());
    }

    @Test
    void testCustomConstructorWithInvalidSize() {
        assertThrows(WrongBoardSizeException.class, () -> new Board(9));
    }

    @Test
    void testMakeMoveWithInvalidSize() {
        assertThrows(OutOfBounceException.class, () -> board.makeMove(9));
    }

    @Test
    void testMakeMoveWithInvalidSizeInRowAndCol() {
        assertThrows(OutOfBounceException.class, () -> board.makeMove(10, 2));
    }

}