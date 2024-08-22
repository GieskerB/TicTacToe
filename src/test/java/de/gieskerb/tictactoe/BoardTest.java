package test.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(3);
    }

    @Test
    void testIsEmpty() {
        assertEquals(board.getTile(0), Board.Tile.EMPTY);
    }

    @Test
    void testPlacement() {
        board.makeMove(3);
        assertEquals(board.getTile(3), Board.Tile.PLAYER1);
    }

    @Test
    void testPlayerOrder() {
        board.makeMove(3);
        board.makeMove(4);
        assertEquals(board.getTile(4), Board.Tile.PLAYER2);
    }

    private void fillBoardNotWin() {
        board.makeMove(0);
        board.makeMove(1);
        board.makeMove(2);
        board.makeMove(4);
        board.makeMove(3);
        board.makeMove(5);
        board.makeMove(7);
        board.makeMove(6);
        board.makeMove(8);
    }

    @Test
    void testNoWinPlayerOne() {
        fillBoardNotWin();
        assertFalse(board.checkWinPlayerOne());
    }

    @Test
    void testNoWinPlayerTwo() {
        fillBoardNotWin();
        assertFalse(board.checkWinPlayerTwo());
    }

    @Test
    void testWinPlayerOneHorizontal() {
        board.makeMove(0);
        board.makeMove(3);
        board.makeMove(1);
        board.makeMove(4);
        board.makeMove(2);
        assertTrue(board.checkWinPlayerOne());
    }

    @Test
    void testWinPlayerTwoVertical() {
        board.makeMove(0);
        board.makeMove(1);
        board.makeMove(8);
        board.makeMove(4);
        board.makeMove(2);
        board.makeMove(7);
        assertFalse(board.checkWinPlayerOne());
        assertTrue(board.checkWinPlayerTwo());
    }

    @Test
    void testWinVerticalTLBR() {
        board.makeMove(0);
        board.makeMove(1);
        board.makeMove(4);
        board.makeMove(2);
        board.makeMove(8);
        assertTrue(board.checkWinPlayerOne());
    }
    @Test
    void testWinVerticalBLTR() {
        board.makeMove(6);
        board.makeMove(1);
        board.makeMove(4);
        board.makeMove(0);
        board.makeMove(2);
        assertTrue(board.checkWinPlayerOne());
    }

}