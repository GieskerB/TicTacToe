package test.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.Board;
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

    @Test
    void testIsEmpty() {
        assertEquals(board.getTile(0), Board.Tile.EMPTY);
    }

    @Test
    void testMakeMove() {
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

    @Test
    void testEmptyNoTie() {
        assertFalse(board.checkTie());
    }

    @Test
    void testNoTie() {
        board.makeMove(0);
        board.makeMove(1);
        board.makeMove(4);
        board.makeMove(3);
        board.makeMove(8);
        assertFalse(board.checkTie());
    }

    @Test
    void testTie() {
        board.makeMove(0);
        board.makeMove(1);
        board.makeMove(2);
        board.makeMove(5);
        board.makeMove(3);
        board.makeMove(6);
        board.makeMove(4);
        board.makeMove(8);
        board.makeMove(7);
        assertTrue(board.checkTie());
    }

    @Test
    void testUndoMove() {
        board.makeMove(0);
        board.undoMove(0);
        assertEquals(board.getTile(0), Board.Tile.EMPTY);
    }

    @Test
    void testUndoMoveChangePlayer() {
        board.makeMove(0);
        board.undoMove(0);
        assertEquals(board.getCurrentPlayer(), Player.ONE);
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