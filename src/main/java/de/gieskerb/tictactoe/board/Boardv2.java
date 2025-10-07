package main.java.de.gieskerb.tictactoe.board;

public class Boardv2 {

    public enum Tile {
        EMPTY, PLAYER1, PLAYER2
    }

    private long bitMapPlayerOne, bitMapPlayerTwo;

    private final byte SIZE,SQUARED_SIZE;

    private final long[] WINNING_BIT_MAPS;

    private void checkBounds(long index) {
        if (index < 0 || index >= this.SQUARED_SIZE) {
            throw new IllegalArgumentException("Board index must be in range [0, " + (this.SQUARED_SIZE - 1) + "]");
        }
    }

    private long indexToBitMap(long index){
        return 1L << index;
    }

    public Boardv2(int size) {
        if(size < 2 ||  size > 8) {
            throw  new IllegalArgumentException("Board size must be between 2 and 8");
        }
        this.SIZE = (byte) size;
        this.SQUARED_SIZE = (byte) (this.SIZE * this.SIZE);
        this.bitMapPlayerOne = 0L;
        this.bitMapPlayerTwo = 0L;
        this.WINNING_BIT_MAPS = WinningBitMapGenerator.getBitMaps(this.SIZE);
    }

    public Tile getTile(long index) {
        checkBounds(index);
        index = indexToBitMap(index);
        if((this.bitMapPlayerOne & index) != 0) {
            return Tile.PLAYER1;
        } else if ((this.bitMapPlayerTwo & index) != 0) {
            return Tile.PLAYER2;
        }
        return Tile.EMPTY;
    }

    public void makeMovePlayerOne(long index) {
        checkBounds(index);
        if(!Tile.EMPTY.equals(getTile(index))) {
            throw new RuntimeException("The tile " + index + " is already occupied!");
        }
        index = indexToBitMap(index);
        this.bitMapPlayerOne |= index;
    }

    public void makeMovePlayerTwo(long index) {
        checkBounds(index);
        if(!Tile.EMPTY.equals(getTile(index))) {
            throw new RuntimeException("The tile " + index + " is already occupied!");
        }
        index = indexToBitMap(index);
        this.bitMapPlayerTwo |= index;
    }

    public void undoMovePlayerOne(long index) {
        checkBounds(index);
        if(!Tile.PLAYER1.equals(getTile(index))) {
            throw new RuntimeException("The tile " + index + " was not occupied by Player1!");
        }
        index = indexToBitMap(index);
        this.bitMapPlayerOne ^= index;
    }

    public void undoMovePlayerTwo(long index) {
        checkBounds(index);
        if(!Tile.PLAYER2.equals(getTile(index))) {
            throw new RuntimeException("The tile " + index + " was not occupied by Player2!");
        }
        index = indexToBitMap(index);
        this.bitMapPlayerTwo ^= index;
    }

    public boolean checkWinPlayerOne() {
        for(long bitMap: this.WINNING_BIT_MAPS) {
            if((bitMap & this.bitMapPlayerOne) == bitMap) {
                return true;
            }
        }
        return false;
    }

    public boolean checkWinPlayerTwo() {
        for(long bitMap: this.WINNING_BIT_MAPS) {
            if((bitMap & this.bitMapPlayerTwo) == bitMap) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTie() {
        long tieBitMap = (1L << this.SQUARED_SIZE) -1;
        return ((this.bitMapPlayerOne | this.bitMapPlayerTwo) & tieBitMap) == tieBitMap;
    }

    public boolean checkGameOver() {
        return this.checkWinPlayerOne() || this.checkWinPlayerTwo() || this.checkTie();
    }
}
