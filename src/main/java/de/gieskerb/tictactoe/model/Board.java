package main.java.de.gieskerb.tictactoe.model;

import main.java.de.gieskerb.tictactoe.Exceptions.NonEmptyTileException;
import main.java.de.gieskerb.tictactoe.Exceptions.OutOfBounceException;
import main.java.de.gieskerb.tictactoe.Exceptions.WrongBoardSizeException;

public class Board {
    /**
     * number of rows and columns of the board.
     */
    final byte size;

    /**
     * a list of bitmaps to compare to when checking for a win
     */
    final long[] bitMapPatterns;
    long bitMapPlayer1;
    long bitMapPlayer2;

    /**
     * true means its players one turn, false says its players two turn
     */
    private boolean currentTurn;

    private boolean checkWin(boolean playerOne) {
        for (long bitMapPattern : this.bitMapPatterns) {
            if ((bitMapPattern & (playerOne ? this.bitMapPlayer1 : this.bitMapPlayer2)) == bitMapPattern) {
                return true;
            }
        }
        return false;
    }

    private boolean checkFull() {
        return (1L << this.size * this.size) == ((this.bitMapPlayer1 | this.bitMapPlayer2) + 1);
    }

    public Board() {
        //Default size for a tic tac toe game
        this(3);
    }

    public Board(int size) {
        if (size < 2 || size > 8) {
            throw new WrongBoardSizeException("Size of TicTacToe-Board must be in range 2 to 8");
        }
        this.currentTurn = true;
        this.size = (byte) size;
        bitMapPatterns = new long[this.size * 2 + 2];
        initPatterns();
    }

    private void initPatterns() {
        // row-patterns:
        long patternTemplate = (1L << this.size) - 1;
        int index = 0;
        for (int i = 0; i < this.size; i++) {
            this.bitMapPatterns[index++] = patternTemplate << (i * this.size);
        }
        // column-patterns:
        patternTemplate = 1L;
        for (int i = 0; i < this.size - 1; i++) {
            patternTemplate <<= this.size;
            patternTemplate |= 1L;
        }
        for (int i = 0; i < this.size; i++) {
            this.bitMapPatterns[index++] = patternTemplate << i;
        }
        //diagonal-patterns TL-BR
        patternTemplate = 1L;
        for (int i = 0; i < this.size - 1; i++) {
            patternTemplate <<= this.size + 1;
            patternTemplate |= 1L;
        }
        this.bitMapPatterns[index++] = patternTemplate;
        //diagonal-patterns BL-TR
        patternTemplate = 1L;
        for (int i = 0; i < this.size - 1; i++) {
            patternTemplate <<= this.size - 1;
            patternTemplate |= 1L;
        }
        patternTemplate <<= this.size - 1;
        this.bitMapPatterns[index] = patternTemplate;

    }

    /**
     * Tries to make a move on the board. Illegal placements like out of bounce and already occupies
     * will throw an exception.
     *
     * @param tile is the corresponding number starting in the top left with zero and right left to
     *             right top to bottom:
     *             +-+-+-+
     *             |0|1|2|
     *             +-+-+-+
     *             |3|4|5|
     *             +-+-+-+
     *             |6|7|8|
     *             +-+-+-+
     */
    public void makeMove(int tile) {
        final byte sizeSquared = (byte) (this.size * this.size);
        if (tile < 0 || tile >= sizeSquared) {
            throw new OutOfBounceException(tile + " is not in bound for a board of size "
                    + this.size + ". Argument must be in range 0 - " + (sizeSquared - 1) + ".");
        }
        if(this.isGameOver()) {
            return;
        }

        final long placeMoveBitmap = 1L << ((sizeSquared - 1) - tile);
        final long bitMapOccupied = this.bitMapPlayer1 | this.bitMapPlayer2;

        if ((placeMoveBitmap & bitMapOccupied) != 0) {
            throw new NonEmptyTileException("The chosen tile is already occupied. You hit an non empty Tile");
        }

        if (this.currentTurn) {
            this.bitMapPlayer1 |= placeMoveBitmap;
        } else {
            this.bitMapPlayer2 |= placeMoveBitmap;
        }
        this.currentTurn = !this.currentTurn;

    }

    /**
     * Tries to make a move on the board. Illegal placements like out of bounce and already occupies
     * * will throw an exception.
     *
     * @param row starting at zero from top to bottom and
     * @param col also starting at zero form left to right identifies location:
     *            +---+---+---+
     *            |0,0|0,1|0,2|
     *            +---+---+---+
     *            |1,0|1,1|1,2|
     *            +---+---+---+
     *            |2,0|2,1|2,2|
     *            +---+---+---+
     *
     */
    public void makeMove(int row, int col) {
        if (row < 0 || row >= this.size || col < 0 || col >= this.size) {
            throw new OutOfBounceException("Row: "+row + "or Column: " + col +" is not in bound for a board of size "
                    + this.size + ". Argument must be in range 0 - " + (this.size - 1) + ".");
        }
        this.makeMove(row * this.size + col);

    }

    /**
     * There are three reasons why the game ends:
     *      1. Player 1 has won the game.
     *      2. Player 2 has won the game.
     *      3. No one has won the game. Its draw.
     * @return is the game over due to one of the prior reasons.
     */
    public boolean isGameOver() {
        return this.checkWin(true) || this.checkWin(false) || this.checkFull();

    }


    public long getBitMapPlayer1() {
        return this.bitMapPlayer1;
    }

    public long getBitMapPlayer2() {
        return this.bitMapPlayer2;
    }
}

