package test.java.de.gieskerb.tictactoe;

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
    void testServiceWithTile() {

        board.invokeMethod(new FriendTestAccess("makeMove", 4));
        assertFalse(board.isGameOver());
        assertEquals(0b000010000, board.getBitMapPlayer1());
    }

    @Test
    void testServiceWithRowAndCol() {

        board.invokeMethod(new FriendTestAccess("makeMove", 1, 1));
        assertFalse(board.isGameOver());
        assertEquals(0b000010000, board.getBitMapPlayer1());
    }

    @Test
    void testServiceOutOfBounce() {
        assertThrows(OutOfBounceException.class, () -> board.invokeMethod(new FriendTestAccess("makeMove", -1)));
        assertThrows(OutOfBounceException.class, () -> board.invokeMethod(new FriendTestAccess("makeMove", 9)));
        assertThrows(OutOfBounceException.class, () -> board.invokeMethod(new FriendTestAccess("makeMove", 10, 2)));
    }

    @Test
    void testServiceNonEmptyTile() {
        board.invokeMethod(new FriendTestAccess("makeMove", 1, 1));
        assertThrows(NonEmptyTileException.class, () -> board.invokeMethod(new FriendTestAccess("makeMove", 1, 1)));
    }

    @Test
    void testServiceSwitchTurns() {
        board.invokeMethod(new FriendTestAccess("makeMove", 0));
        board.invokeMethod(new FriendTestAccess("afterMove"));
        assertFalse(board.isGameOver());
        board.invokeMethod(new FriendTestAccess("makeMove", 4));
        board.invokeMethod(new FriendTestAccess("afterMove"));
        assertFalse(board.isGameOver());
        assertEquals(0b000010000, board.getBitMapPlayer2());
    }

    @Test
    void testIsGameOverWithWin() {
        board.invokeMethod(new FriendTestAccess("makeMove", 0));
        board.invokeMethod(new FriendTestAccess("afterMove"));
        board.invokeMethod(new FriendTestAccess("makeMove", 3));
        board.invokeMethod(new FriendTestAccess("afterMove"));
        board.invokeMethod(new FriendTestAccess("makeMove", 1));
        board.invokeMethod(new FriendTestAccess("afterMove"));
        board.invokeMethod(new FriendTestAccess("makeMove", 4));
        board.invokeMethod(new FriendTestAccess("afterMove"));
        board.invokeMethod(new FriendTestAccess("makeMove", 2));
        assertTrue(board.isGameOver());
    }

    @Test
    void testIsGameOverWithDraw() {
        board.invokeMethod(new FriendTestAccess("makeMove", 0));
        board.invokeMethod(new FriendTestAccess("afterMove"));
        board.invokeMethod(new FriendTestAccess("makeMove", 1));
        board.invokeMethod(new FriendTestAccess("afterMove"));
        board.invokeMethod(new FriendTestAccess("makeMove", 2));
        board.invokeMethod(new FriendTestAccess("afterMove"));
        board.invokeMethod(new FriendTestAccess("makeMove", 3));
        board.invokeMethod(new FriendTestAccess("afterMove"));
        board.invokeMethod(new FriendTestAccess("makeMove", 5));
        board.invokeMethod(new FriendTestAccess("afterMove"));
        board.invokeMethod(new FriendTestAccess("makeMove", 4));
        board.invokeMethod(new FriendTestAccess("afterMove"));
        board.invokeMethod(new FriendTestAccess("makeMove", 6));
        board.invokeMethod(new FriendTestAccess("afterMove"));
        board.invokeMethod(new FriendTestAccess("makeMove", 8));
        board.invokeMethod(new FriendTestAccess("afterMove"));
        board.invokeMethod(new FriendTestAccess("makeMove", 7));
        assertTrue(board.isGameOver());
    }

    @Test
    void testCustomConstructorWithInvalidSize() {
        assertThrows(WrongBoardSizeException.class, () -> new Board(9));
        assertThrows(WrongBoardSizeException.class, () -> new Board(1));
        assertThrows(WrongBoardSizeException.class, () -> new Board(-2));
    }

    /*
    @Test
    void testServiceArgSizeToSmall() {
        assertThrows(WrongArgSizeException.class, () -> this.board.invokeMethod(new FriendTestAccess("makeMove", 0)));
    }

    @Test
    void testServiceArgSizeToLarge() {
        assertThrows(WrongArgSizeException.class, () -> this.board.invokeMethod(new FriendTestAccess("makeMove", 3)));
    }

    @Test
    void testServiceNullPointer() {
        assertThrows(NullPointerException.class, () -> this.board.invokeMethod(new FriendTestAccess("makeMove", (Object) null)));
    }
    */

}