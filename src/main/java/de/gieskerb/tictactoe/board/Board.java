package main.java.de.gieskerb.tictactoe.board;

public class Board {

    public enum Tile {
        EMPTY, PLAYER1, PLAYER2
    }

    public class Player {
        private long bitMap;
        private final Board board;
        private final Tile tile;

        private Player(Board board, Tile tile) {
            this.bitMap = 0L;
            this.board = board;
            this.tile = tile;
        }

        public void makeMove(int index) {
            board.checkBounds(index);
            if (!Tile.EMPTY.equals(getTile(index))) {
                throw new RuntimeException("The tile " + index + " is already occupied!");
            }
            this.bitMap |= board.indexToBitMap(index);
        }

        public void undoMove(int index) {
            board.checkBounds(index);
            if (!this.tile.equals(getTile(index))) {
                throw new RuntimeException("The tile " + index + " was not occupied before");
            }
            this.bitMap ^= board.indexToBitMap(index);
        }

        public boolean checkWin() {
            for (long bitMap : board.WINNING_BIT_MAPS) {
                if ((bitMap & this.bitMap) == bitMap) {
                    return true;
                }
            }
            return false;
        }
    }

    public final Player player1, player2;

    private final byte SIZE, SQUARED_SIZE;

    private final long[] WINNING_BIT_MAPS;
    private final long fullBoardBitMap;

    private void checkBounds(int index) {
        if (index < 0 || index >= this.SQUARED_SIZE) {
            throw new IllegalArgumentException(index + " is out of bounds!");
        }
    }

    private long indexToBitMap(int index) {
        return 1L << index;
    }

    public Board(int size) {
        if (size < 2 || size > 8) {
            throw new IllegalArgumentException("Board size " + size + " invalid. Must be between 2 and 8");
        }
        this.SIZE = (byte) size;
        this.SQUARED_SIZE = (byte) (this.SIZE * this.SIZE);
        this.player1 = new Player(this, Tile.PLAYER1);
        this.player2 = new Player(this, Tile.PLAYER2);
        this.WINNING_BIT_MAPS = WinningBitMapGenerator.getBitMaps(this.SIZE);
        this.fullBoardBitMap = (1L << this.SQUARED_SIZE) - 1;
    }

    public byte getSize() {
        return SIZE;
    }

    public Tile getTile(int index) {
        checkBounds(index);
        final long bitMap = indexToBitMap(index);
        if ((this.player1.bitMap & bitMap) != 0) {
            return Tile.PLAYER1;
        } else if ((this.player2.bitMap & bitMap) != 0) {
            return Tile.PLAYER2;
        }
        return Tile.EMPTY;
    }

    public boolean checkTie() {
        return ((this.player1.bitMap | this.player2.bitMap) & this.fullBoardBitMap) == this.fullBoardBitMap;
    }

    public boolean checkGameOver() {
        return this.player1.checkWin() || this.player2.checkWin() || this.checkTie();
    }

    public long getEmptyTilesBitMap() {
        long emptyTiles = this.player1.bitMap | this.player2.bitMap;
        emptyTiles = ~emptyTiles;
        return emptyTiles & this.fullBoardBitMap;
    }

    public int[] getEmptyTiles() {
        final long emptyBitMap = getEmptyTilesBitMap();
        long compareBitMap = 1L;
        int[] emptyTiles = new int[this.SQUARED_SIZE];
        int emptyTilesCount = 0;
        for (int i = 0; i < this.SQUARED_SIZE; i++) {
            if ((compareBitMap & emptyBitMap) != 0) {
                emptyTiles[emptyTilesCount++] = i;
            }
            compareBitMap <<= 1;
        }
        int[] reducedEmptyTiles = new int[emptyTilesCount];
        System.arraycopy(emptyTiles, 0, reducedEmptyTiles, 0, emptyTilesCount);
        return reducedEmptyTiles;
    }

}
