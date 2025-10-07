package main.java.de.gieskerb.tictactoe.board;

public class Boardv3 {

    public enum Tile {
        EMPTY, PLAYER1, PLAYER2
    }

    public class Player {
        private long bitMap;
        private final Boardv3 board;
        private final Tile tile;

        private Player(Boardv3 board,  Tile tile) {
            this.bitMap = 0L;
            this.board = board;
            this.tile = tile;
        }

        public void makeMove(long index) {
            board.checkBounds(index);
            if(!Tile.EMPTY.equals(getTile(index))) {
                throw new RuntimeException("The tile " + index + " is already occupied!");
            }
            this.bitMap |= board.indexToBitMap(index);
        }

        public void undoMove(long index) {
            board.checkBounds(index);
            if(!this.tile.equals(getTile(index))) {
                throw new RuntimeException("The tile " + index + " was not occupied before");
            }
            this.bitMap ^= board.indexToBitMap(index);
        }

        public boolean checkWin() {
            for(long bitMap: board.WINNING_BIT_MAPS) {
                if((bitMap & this.bitMap) == bitMap) {
                    return true;
                }
            }
            return false;
        }
    }

    public final Player player1,player2;

    private final byte SIZE,SQUARED_SIZE;

    private final long[] WINNING_BIT_MAPS;

    private void checkBounds(long index) {
        if (index < 0 || index >= this.SQUARED_SIZE) {
            throw  new IllegalArgumentException("Board size must be between 2 and 8");
        }
    }

    private long indexToBitMap(long index){
        return 1L << index;
    }

    public Boardv3(int size) {
        checkBounds(size);
        this.SIZE = (byte) size;
        this.SQUARED_SIZE = (byte) (this.SIZE * this.SIZE);
        this.player1 = new Player(this, Tile.PLAYER1);
        this.player2 = new Player(this, Tile.PLAYER2);
        this.WINNING_BIT_MAPS = WinningBitMapGenerator.getBitMaps(this.SIZE);
    }

    public byte getSize() {
        return SIZE;
    }

    public Tile getTile(long index) {
        checkBounds(index);
        final long bitMap = indexToBitMap(index);
        if((this.player1.bitMap & bitMap) != 0) {
            return Tile.PLAYER1;
        } else if ((this.player2.bitMap & bitMap) != 0) {
            return Tile.PLAYER2;
        }
        return Tile.EMPTY;
    }

    public boolean checkTie() {
        final long tieBitMap = (1L << this.SQUARED_SIZE) -1;
        return ((this.player1.bitMap | this.player2.bitMap) & tieBitMap) == tieBitMap;
    }

    public boolean checkGameOver() {
        return this.player1.checkWin() || this.player2.checkWin() || this.checkTie();
    }
}
