package main.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.enums.Player;

import java.util.ArrayList;

public class Board {
    /**
     * Simple way to communicate which state a tile has, because Board uses a bitmap.
     */
    public enum Tile {
        EMPTY, PLAYER1, PLAYER2
    }

    /**
     * Size constants for easy bounds checking
     */
    private final byte SIZE, SIZE_SQUARED;

    /**
     * Bitmap per player: using a long, to still be able to represent an 8 by 8 grid.
     */
    private long bitMapPlayerOne, bitMapPlayerTwo;

    /**
     * List of all bitmaps required to check if a win occurred in a players bitmap.
     */
    private final ArrayList<Long> winningBitMaps;

    /**
     * Remembers current player.
     */
    private Player currentPlayer;

    /**
     * Creates all bitmaps to check for horizontal, vertical ior diagonal wins.
     *
     * @param size of the board is required to construct the correct bitmaps.
     * @return list of all bitmaps for a given size,
     */
    private static ArrayList<Long> createWinningBitMaps(byte size) {
        ArrayList<Long> bitMaps = new ArrayList<>();
        long tempBitMap;

        // Horizontal lines:
        tempBitMap = 1;
        for (byte i = 0; i < size - 1; i++) {
            tempBitMap <<= 1;
            tempBitMap |= 1;
        }
        for (byte i = 0; i < size; i++) {
            bitMaps.add(tempBitMap);
            tempBitMap <<= size;
        }

        // Vertical lines:
        tempBitMap = 1;
        for (byte i = 0; i < size - 1; i++) {
            tempBitMap <<= size;
            tempBitMap |= 1;
        }
        for (byte i = 0; i < size; i++) {
            bitMaps.add(tempBitMap);
            tempBitMap <<= 1;
        }

        //Diagonal lines:
        tempBitMap = 1;
        for (byte i = 0; i < size - 1; i++) {
            tempBitMap <<= size + 1;
            tempBitMap |= 1;
        }
        bitMaps.add(tempBitMap);

        tempBitMap = 1L << size - 1;
        for (byte i = 0; i < size - 1; i++) {
            tempBitMap <<= size - 1;
            tempBitMap |= 1L << size - 1;
        }
        bitMaps.add(tempBitMap);

        return bitMaps;
    }

    private void checkBounds(long index) {
        if (index < 0 || index >= this.SIZE_SQUARED) {
            throw new IllegalArgumentException("Board index must be in range [0, " + (this.SIZE_SQUARED - 1) + "]");
        }
    }

    public Board(Board board) {
        this.SIZE = board.SIZE;
        this.SIZE_SQUARED = board.SIZE_SQUARED;
        this.bitMapPlayerOne = board.bitMapPlayerOne;
        this.bitMapPlayerTwo = board.bitMapPlayerTwo;
        this.winningBitMaps = board.winningBitMaps;
        this.currentPlayer = board.currentPlayer;
    }

    /**
     * Constructs an empty TicTacToe Board ready to be played on.
     *
     * @param size determines the number of row and columns. Must be in range [2, 8].
     */
    public Board(int size) {
        if (size < 2 || size > 8) {
            throw new IllegalArgumentException("Board size must be in range [2, 8]");
        }
        this.SIZE = (byte) size;
        this.SIZE_SQUARED = (byte) (this.SIZE * this.SIZE);
        this.bitMapPlayerOne = 0;
        this.bitMapPlayerTwo = 0;
        this.winningBitMaps = Board.createWinningBitMaps(this.SIZE);
        this.currentPlayer = Player.ONE;
    }

    public long getBitMapPlayerOne() {
        return this.bitMapPlayerOne;
    }

    public long getBitMapPlayerTwo() {
        return this.bitMapPlayerTwo;
    }

    public byte getSizeSquared() {
        return this.SIZE_SQUARED;
    }

    public boolean isEmptyTile(long index) {
        this.checkBounds(index);
        // Convert index to bitmap
        index = 1L << index;

        //returns true if index is empty in either bitmap
        return ((this.bitMapPlayerOne | this.bitMapPlayerTwo) & index) == 0;
    }

    /**
     * @param index refers to a single tile counted form top left to bottom right, starting with 0.
     * @return Tile value for given index
     */
    public Tile getTile(long index) {
        this.checkBounds(index);
        // Convert index to bitmap
        index = 1L << index;
        ;
        // Return corresponding value
        if ((this.bitMapPlayerOne & index) != 0) {
            return Tile.PLAYER1;
        } else if ((this.bitMapPlayerTwo & index) != 0) {
            return Tile.PLAYER2;
        }
        return Tile.EMPTY;
    }

    /**
     * Playing a move by index. Automatically switches players after each move.
     *
     * @param index refers to a single tile counted form top left to bottom right, starting with 0.
     */
    public void makeMove(long index) {
        this.checkBounds(index);
        {
            // Check occupancy
            byte tempIndex = (byte) index;
            index = 1L << index;

            if (((this.bitMapPlayerOne | this.bitMapPlayerTwo) & index) != 0) {
                throw new RuntimeException("The tile " + tempIndex + " is allready occupied!");
            }
        }
        // making the move by storing it in the right bitmap.
        if (this.currentPlayer == Player.ONE) {
            this.bitMapPlayerOne |= index;
        } else {
            this.bitMapPlayerTwo |= index;
        }
        this.currentPlayer = this.currentPlayer.otherPlayer();
    }

    public void undoMove(long index) {
        this.checkBounds(index);
        {
            // Check occupancy
            byte tempIndex = (byte) index;
            index = 1L << index;

            if (((this.bitMapPlayerOne | this.bitMapPlayerTwo) & index) == 0) {
                throw new RuntimeException("No Player has made a move at this index: " + tempIndex + "!");
            }
        }

        this.currentPlayer = this.currentPlayer.otherPlayer();
        if (this.currentPlayer == Player.ONE) {
            this.bitMapPlayerOne ^= index;
        } else {
            this.bitMapPlayerTwo ^= index;
        }
    }

    public ArrayList<Byte> getEmptyTiles() {
        ArrayList<Byte> emptyTiles = new ArrayList<>(this.SIZE_SQUARED);
        for (byte i = 0; i < this.SIZE_SQUARED; i++) {
            if (this.isEmptyTile(i)) {
                emptyTiles.add(i);
            }
        }
        return emptyTiles;
    }

    /**
     * IMPORTANT: This method does not make any check regarding placement or in bounce check! Be carefully while using it!
     */
    public void makeMoveFast(final long bitmap) {
        if (this.currentPlayer == Player.ONE) {
            this.bitMapPlayerOne |= bitmap;
        } else {
            this.bitMapPlayerTwo |= bitmap;
        }
        this.currentPlayer = this.currentPlayer.otherPlayer();
    }

    /**
     * IMPORTANT: This method does not make any check regarding placement or in bounce check! Be carefully while using it!
     */
    public void undoMoveFast(final long bitmap) {
        this.currentPlayer = this.currentPlayer.otherPlayer();
        if (this.currentPlayer == Player.ONE) {
            this.bitMapPlayerOne ^= bitmap;
        } else {
            this.bitMapPlayerTwo ^= bitmap;
        }
    }

    public long getEmptyTilesFast() {
        return (~(this.bitMapPlayerOne | this.bitMapPlayerTwo)) & ((1L << this.SIZE_SQUARED) - 1);
    }

    public Player getPreviousPlayer() {
        return this.currentPlayer.otherPlayer();
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public boolean checkWinPlayerOne() {
        for (long bitMap : this.winningBitMaps) {
            if ((bitMap & this.bitMapPlayerOne) == bitMap) {
                return true;
            }
        }
        return false;
    }

    public boolean checkWinPlayerTwo() {
        for (long bitMap : this.winningBitMaps) {
            if ((bitMap & this.bitMapPlayerTwo) == bitMap) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTie() {
        long tieBitMap = (1L << this.SIZE_SQUARED) - 1;
        return ((this.bitMapPlayerOne | this.bitMapPlayerTwo) & tieBitMap) == tieBitMap;
    }

    public boolean checkGameOver() {
        return this.checkWinPlayerOne() || this.checkWinPlayerTwo() || this.checkTie();
    }

}
