package test.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.board.Board;
import main.java.de.gieskerb.tictactoe.enums.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        assertArrayEquals(new int[]{0,1,2,3,4,5,6,7,8}, emptyTiles);
    }

    @Test
    void testEmptyTilesLengthAtStart() {
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

    @Test
    void testEmptyTilesBitMapAfterMove() {
        board.player1.makeMove(0);
        assertEquals(0b111111110, board.getEmptyTilesBitMap());
    }

    @Test
    void testEmptyTilesAfterMove() {
        board.player1.makeMove(0);
        assertArrayEquals(new int[]{1,2,3,4,5,6,7,8}, board.getEmptyTiles());
    }

    @Test
    void testMakeMoveFastOne() {
        Board fastBoard = new Board(3);
        board.makeMove(3);
        fastBoard.makeMoveFast(0b1000);
        assertEquals(board.getTile(3), fastBoard.getTile(3));
    }
    @Test
    void testMakeMoveFastTwo() {
        Board fastBoard = new Board(3);
        board.makeMove(1);
        board.makeMove(7);
        fastBoard.makeMoveFast(0b10);
        fastBoard.makeMoveFast(0b10000000);
        assertEquals(board.getTile(7), fastBoard.getTile(7));
    }

    @Test
    void testUndoMoveFast() {
        board.makeMoveFast(0b1000);
        board.undoMoveFast(0b1000);
        assertEquals(board.getTile(3), Board.Tile.EMPTY);
    }

    @Test
    void testEmptyTilesSize() {
        board.makeMove(0);
        board.makeMove(4);
        board.makeMove(5);
        ArrayList<Byte> tiles = board.getEmptyTiles();
        assertEquals(tiles.size(), 6);
    }

    @Test
    void testEmptyTilesContent() {
        board.makeMove(0);
        board.makeMove(4);
        board.makeMove(5);
        ArrayList<Byte> emptyTiles = board.getEmptyTiles();

        byte[] correct = new byte[]{1, 2, 3, 6, 7, 8};
        for (byte test : correct) {
            assertTrue(                    emptyTiles.contains(test));

        }
    }

    @Test
    void testEmptyTilesFast() {
        board.makeMove(4);
        board.makeMove(3);
        board.makeMove(7);
        ArrayList<Byte> emptyTiles = board.getEmptyTiles();
        long emptyTilesBitmap = board.getEmptyTilesFast();

        for(byte i = 0; i< board.getSizeSquared(); i++) {
            long moveBitmap = 1L << i;
            if(emptyTiles.contains(i)) {
                // Tile is empty -> Bitmap has 1 at this place
                assertNotEquals(0,(moveBitmap & emptyTilesBitmap));
            } else {
                // Tile is not empty -> Bitmap has 0 at this place
                assertEquals(0, (moveBitmap & emptyTilesBitmap));
            }
        }

    }

}
