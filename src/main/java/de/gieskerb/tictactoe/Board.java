package main.java.de.gieskerb.tictactoe;

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
     * Remembers current player. (Either true := Player1 | false := Player2)
     */
    private boolean currentPlayer;

    /**
     * Creates all bitmaps to check for horizontal, vertical ior diagonal wins.
     * @param size of the board is required to construct the correct bitmaps.
     * @return list of all bitmaps for a given size,
     */
    private static ArrayList<Long> createWinningBitMaps(byte size) {
        ArrayList<Long> bitMaps = new ArrayList<>();
        long tempBitMap;

        // Horizontal lines:
        tempBitMap = 1;
        for(byte i = 0; i< size-1; i++) {
            tempBitMap <<= 1;
            tempBitMap |= 1;
        }
        for(byte i = 0; i< size; i++) {
            bitMaps.add(tempBitMap);
            tempBitMap <<= size;
        }

        // Vertical lines:
        tempBitMap = 1;
        for(byte i = 0; i< size-1; i++) {
            tempBitMap <<= size;
            tempBitMap |= 1;
        }
        for(byte i = 0; i< size; i++) {
            bitMaps.add(tempBitMap);
            tempBitMap <<= 1;
        }

        //Diagonal lines:
        tempBitMap = 1;
        for(byte i = 0; i< size-1; i++) {
            tempBitMap <<= size + 1;
            tempBitMap |= 1;
        }
        bitMaps.add(tempBitMap);

        tempBitMap = 1L << size-1;
        for(byte i = 0; i< size-1; i++) {
            tempBitMap <<= size - 1;
            tempBitMap |=  1L << size-1;
        }
        bitMaps.add(tempBitMap);

        return bitMaps;
    }

    /**
     * Constructs an empty TicTacToe Board ready to be played on.
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
        this.currentPlayer = true;
    }

    /**
     * @param index refers to a single tile counted form top left to bottom right, starting with 0.
     * @return Tile value for given index
     */
    public Tile getTile(long index) {
        // Check bounds
        if (index < 0 || index >= this.SIZE_SQUARED) {
            throw new IllegalArgumentException("Board index must be in range [0, " + (this.SIZE_SQUARED - 1) + "]");
        }
        // Convert index to bitmap
        index = 1L << index;;
        // Return corresponding value
        if((this.bitMapPlayerOne & index) != 0) {
            return Tile.PLAYER1;
        } else if ((this.bitMapPlayerTwo & index) != 0) {
            return Tile.PLAYER2;
        }
        return Tile.EMPTY;
    }

    /**
     * Playing a move by index. Automatically switches players after each move.
     * @param index refers to a single tile counted form top left to bottom right, starting with 0.
     */
    public void makeMove(long index) {
        // Check bounds
        if (index < 0 || index >= this.SIZE_SQUARED) {
            throw new IllegalArgumentException("Board index must be in range [0, " + (this.SIZE_SQUARED - 1) + "]");
        }
        {
            // Check occupancy
            byte tempIndex = (byte) index;
            index = 1L << index;

            if (((this.bitMapPlayerOne | this.bitMapPlayerTwo) & index) != 0) {
                throw new RuntimeException("The tile " + tempIndex + " is allready occupied!");
            }
        }
        // making the move by storing it in the right bitmap.
        if(this.currentPlayer) {
            this.bitMapPlayerOne |= index;
        } else {
            this.bitMapPlayerTwo |= index;
        }
        this.currentPlayer = !this.currentPlayer;
    }

    public boolean getCurrentPlayer() {
        return this.currentPlayer;
    }

    public boolean checkWinPlayerOne() {
        for(long bitMap: this.winningBitMaps) {
            if((bitMap & this.bitMapPlayerOne) == bitMap) {
                return true;
            }
        }
        return false;
    }

    public boolean checkWinPlayerTwo() {
        for(long bitMap: this.winningBitMaps) {
            if((bitMap & this.bitMapPlayerTwo) == bitMap) {
                return true;
            }
        }
        return false;
    }

}
