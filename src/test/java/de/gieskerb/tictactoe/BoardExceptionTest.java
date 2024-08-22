package test.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardExceptionTest {

    @Test
    void testConstructorTooSmall() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Board(0)
        );
    }

    @Test
    void testConstructorTooBig() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Board(9)
        );
    }

    void testGetSmallIndex() {
        Board board = new Board(4);
        assertThrows(
                IllegalArgumentException.class,
                () -> board.getTile(-23)
        );
    }

    @Test
    void testGetBigIndex() {
        Board board = new Board(2);
        assertThrows(
                IllegalArgumentException.class,
                () -> board.getTile(6)
        );
    }

    @Test
    void testMakeMoveSmallIndex() {
        Board board = new Board(3);
        assertThrows(
                IllegalArgumentException.class,
                () -> board.makeMove(-1)
        );
    }

    @Test
    void testMakeMoveBigIndex() {
        Board board = new Board(7);
        assertThrows(
                IllegalArgumentException.class,
                () -> board.makeMove(60)
        );
    }

    @Test
    void testMakeMoveTwice() {
        Board board = new Board(3);
        board.makeMove(4);
        assertThrows(
                RuntimeException.class,
                () -> board.makeMove(4)
        );
    }
}
