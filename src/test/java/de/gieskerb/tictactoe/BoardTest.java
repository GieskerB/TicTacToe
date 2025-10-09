package test.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.board.Board;
import main.java.de.gieskerb.tictactoe.enums.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(3);
    }

    /*
     * Test main board functionalities
     */

    @Test
    void testBoardMaxSize() {
        // Should be fine
        board = new Board(8);
        assertThrows(
                IllegalArgumentException.class,
                () -> new Board(9)
        );
    }

    @Test
    void testBoardMinSize() {
        // Should be fine
        board = new Board(2);
        assertThrows(
                IllegalArgumentException.class,
                () -> new Board(1)
        );
    }

    @Test
    void testNotGameOver() {
        assertFalse(board.checkGameOver());
    }

    @Test
    void testEmptyTileAtStart() {
        Board.Tile tile = board.getTile(4);
        assertEquals(Board.Tile.EMPTY, tile);
    }

    @Test
    void testEmptyTilesAtStart() {
        int[] emptyTiles = board.getEmptyTiles();
        assertEquals(9, emptyTiles.length);
    }

    @Test
    void testEmptyTilesBitMapAtStart() {
        long emptyTiles = board.getEmptyTilesBitMap();
        assertEquals(0b111111111, emptyTiles);
    }

    /*
     * Test player functionalities
     */

    @Test
    void testPlayerNotNull() {
        assertNotNull(board.player1);
    }

    @Test
    void testPlayerOneMakeMove() {
        board.player1.makeMove(4);
        assertEquals(Board.Tile.PLAYER1, board.getTile(4));
    }

    @Test
    void testPlayerOneUndoMove() {
        board.player2.makeMove(4);
        board.player2.undoMove(4);
        assertEquals(Board.Tile.EMPTY, board.getTile(4));
    }


    @Test
    void testPlayerCheckWinFalse() {
        board.player1.makeMove(0);
        board.player1.makeMove(1);
        assertFalse(board.player1.checkWin());
    }

    @Test
    void testPlayerCheckWinTrue() {
        board.player2.makeMove(0);
        board.player2.makeMove(1);
        board.player2.makeMove(2);
        assertTrue(board.player2.checkWin());
    }
}