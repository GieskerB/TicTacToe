package test.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.exceptions.*;
import main.java.de.gieskerb.tictactoe.model.Board;
import main.java.de.gieskerb.tictactoe.model.Game;
import main.java.de.gieskerb.tictactoe.model.Updater;
import main.java.de.gieskerb.tictactoe.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardTest {

    private Board board;

    private void fill3by3Board() {
        this.board.invokeMethod(new FriendTestAccess("makeMove", 0));
        this.board.invokeMethod(new FriendTestAccess("afterMove"));
        this.board.invokeMethod(new FriendTestAccess("makeMove", 1));
        this.board.invokeMethod(new FriendTestAccess("afterMove"));
        this.board.invokeMethod(new FriendTestAccess("makeMove", 2));
        this.board.invokeMethod(new FriendTestAccess("afterMove"));
        this.board.invokeMethod(new FriendTestAccess("makeMove", 3));
        this.board.invokeMethod(new FriendTestAccess("afterMove"));
        this.board.invokeMethod(new FriendTestAccess("makeMove", 5));
        this.board.invokeMethod(new FriendTestAccess("afterMove"));
        this.board.invokeMethod(new FriendTestAccess("makeMove", 4));
        this.board.invokeMethod(new FriendTestAccess("afterMove"));
        this.board.invokeMethod(new FriendTestAccess("makeMove", 6));
        this.board.invokeMethod(new FriendTestAccess("afterMove"));
        this.board.invokeMethod(new FriendTestAccess("makeMove", 8));
        this.board.invokeMethod(new FriendTestAccess("afterMove"));
        this.board.invokeMethod(new FriendTestAccess("makeMove", 7));

    }

    @BeforeEach
    void setUp() {
        board = new Board((Game) null);
    }


    @Test
    void testConvertIndexToCoordsCorrect() {
        assertEquals(new Pair<>((byte) 2, (byte) 1), board.invokeMethod(new FriendTestAccess("convertIndexToCoords", 7, (byte) 3)));
    }

    @Test
    void testConvertIndexToCoordsFalse() {
        assertNotEquals(new Pair<>((byte) 1, (byte) 0), board.invokeMethod(new FriendTestAccess("convertIndexToCoords", 8, (byte) 3)));
    }

    @Test
    void testConvertIndexToCoordsThrowsToSmall() {
        assertThrows(OutOfBounceException.class, () -> board.invokeMethod(new FriendTestAccess("convertIndexToCoords", -2, (byte) 3)));
    }

    @Test
    void testConvertIndexToCoordsThrowsToBig() {
        assertThrows(OutOfBounceException.class, () -> board.invokeMethod(new FriendTestAccess("convertIndexToCoords", 10, (byte) 3)));
    }

    @Test
    void testBoardConstructorThrowsToSmall() {
        assertThrows(WrongBoardSizeException.class, () -> new Board(1,null));
    }

    @Test
    void testBoardConstructorThrowsToBig() {
        assertThrows(WrongBoardSizeException.class, () -> new Board(120, null));
    }

    @Test
    void testGameStateCreation() {
        assertNotNull(this.board.exportGameState());
    }

    @Test
    void testMakeMoveThrowsOutOfBounce1() {
        assertThrows(OutOfBounceException.class, () -> board.invokeMethod(new FriendTestAccess("makeMove", -1)));
    }

    @Test
    void testMakeMoveThrowsOutOfBounce2() {
        assertThrows(OutOfBounceException.class, () -> board.invokeMethod(new FriendTestAccess("makeMove", 9)));
    }

    @Test
    void testMakeMoveThrowsOutOfBounce3() {
        assertThrows(OutOfBounceException.class, () -> board.invokeMethod(new FriendTestAccess("makeMove", 10, 2)));
    }

    @Test
    void testMakeMoveThrowsOutOfBounce4() {
        assertThrows(OutOfBounceException.class, () -> board.invokeMethod(new FriendTestAccess("makeMove", 0, 4)));
    }

    @Test
    void testMakeMoveThrowsOutOfBounce5() {
        assertThrows(OutOfBounceException.class, () -> board.invokeMethod(new FriendTestAccess("makeMove", -1, 1)));
    }

    @Test
    void testMakeMoveThrowsOutOfBounce6() {
        assertThrows(OutOfBounceException.class, () -> board.invokeMethod(new FriendTestAccess("makeMove", 0, -4)));
    }

    @Test
    void testMakeMoveNonEmptyTile() {
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
        //TODO

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
        this.fill3by3Board();
        assertTrue(board.isGameOver());
    }

    @Test
    void testCopyConstructor() {
        this.fill3by3Board();
        Board copyBoard = new Board(this.board);
        assertEquals(this.board, copyBoard);
    }

    @Test
    void testReset() {
        this.fill3by3Board();
        this.board.invokeMethod(new FriendTestAccess("reset"));
        assertEquals(new Board((Game) null), this.board);
    }

    @Test
    public void testEqualsNull() {
        assertNotEquals(null,this.board);
    }
    @Test
    public void testEqualsNonBoard() {
        assertNotEquals(new Object(),this.board);
    }
    @Test
    public void testEqualsWrongSize() {
        assertNotEquals(new Board(4,  null),this.board);
    }
    @Test
    public void testEqualsWrongCurrentTurn() {
        Board copy = new Board(this.board);
        this.board.invokeMethod(new FriendTestAccess("afterMove"));
        assertNotEquals(copy,this.board);
    }
    @Test
    public void testEqualsWrongTiles() {
        Board copy = new Board(this.board);
        this.board.invokeMethod(new FriendTestAccess("makeMove",0));
        assertNotEquals(copy,this.board);
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