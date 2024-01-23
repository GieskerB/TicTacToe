package test.java.de.gieskerb.tictactoe.model;

import main.java.de.gieskerb.tictactoe.exceptions.*;
import main.java.de.gieskerb.tictactoe.model.Board;
import main.java.de.gieskerb.tictactoe.model.Origin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void testServiceWithTile() {
        board.service(new int[]{4}, Origin.CONTROLLER);
        assertFalse(board.isGameOver());
        assertEquals(0b000010000, board.getBitMapPlayer1());
    }

    @Test
    void testServiceWithRowAndCol() {
        board.service(new int[]{1, 1}, Origin.CONTROLLER);
        assertFalse(board.isGameOver());
        assertEquals(0b000010000, board.getBitMapPlayer1());
    }

    @Test
    void testServiceOutOfBounce() {
        assertThrows(OutOfBounceException.class, () -> board.service(new int[]{-1}, Origin.CONTROLLER));
        assertThrows(OutOfBounceException.class, () -> board.service(new int[]{9}, Origin.CONTROLLER));
        assertThrows(OutOfBounceException.class, () -> board.service(new int[]{10, 2}, Origin.CONTROLLER));
    }

    @Test
    void testServiceNonEmptyTile() {
        board.service(new int[]{1, 1}, Origin.CONTROLLER);
        assertThrows(NonEmptyTileException.class, () -> board.service(new int[]{1, 1}, Origin.CONTROLLER));
    }

    @Test
    void testServiceSwitchTurns() {
        board.service(new int[]{0}, Origin.CONTROLLER);
        assertFalse(board.isGameOver());
        board.service(new int[]{4}, Origin.CONTROLLER);
        assertFalse(board.isGameOver());
        assertEquals(0b000010000, board.getBitMapPlayer2());
    }

    @Test
    void testIsGameOverWithWin() {
        board.service(new int[]{0}, Origin.CONTROLLER);
        board.service(new int[]{3}, Origin.CONTROLLER);
        board.service(new int[]{1}, Origin.CONTROLLER);
        board.service(new int[]{4}, Origin.CONTROLLER);
        board.service(new int[]{2}, Origin.CONTROLLER);
        assertTrue(board.isGameOver());
    }

    @Test
    void testIsGameOverWithDraw() {
        board.service(new int[]{0}, Origin.CONTROLLER);
        board.service(new int[]{1}, Origin.CONTROLLER);
        board.service(new int[]{2}, Origin.CONTROLLER);
        board.service(new int[]{3}, Origin.CONTROLLER);
        board.service(new int[]{5}, Origin.CONTROLLER);
        board.service(new int[]{4}, Origin.CONTROLLER);
        board.service(new int[]{6}, Origin.CONTROLLER);
        board.service(new int[]{8}, Origin.CONTROLLER);
        board.service(new int[]{7}, Origin.CONTROLLER);
        assertTrue(board.isGameOver());
    }

    @Test
    void testCustomConstructorWithInvalidSize() {
        assertThrows(WrongBoardSizeException.class, () -> new Board(9));
    }

    @Test
    void testServiceArgSizeToSmall() {
        assertThrows(WrongArgSizeException.class, () -> this.board.service(new int[0], Origin.CONTROLLER));
    }
    @Test
    void testServiceArgSizeToLarge() {
        assertThrows(WrongArgSizeException.class, () -> this.board.service(new int[3], Origin.CONTROLLER));
    }

    @Test
    void testServiceNullPointer() {
        assertThrows(NullPointerException.class, () -> this.board.service(null, Origin.CONTROLLER));
    }

}