package main.java.de.gieskerb.tictactoe.model;

import main.java.de.gieskerb.tictactoe.exceptions.NonEmptyTileException;
import main.java.de.gieskerb.tictactoe.exceptions.OutOfBounceException;
import main.java.de.gieskerb.tictactoe.exceptions.WrongBoardSizeException;

/**
 * Board handles the game logic:
 * Making a move.
 * Checking for correctness.
 * Win detection
 *
 */
public class Board extends GameState {

    /**
     * A list of bitmaps to compare to when checking for a win.
     */
    final long[] bitMapPatterns;

    /**
     * True means it's players one turn, false says its players two turn.
     */
    private boolean currentTurn;

    /**
     * Checking for a win by comparing each row, column and diagonal with the winning patterns generated earlier.
     * @param playerOne Determine which bitMap needs to be checked for a win.
     * @return Did the given player win?
     */
    private boolean checkWin(boolean playerOne) {
        for (long bitMapPattern : this.bitMapPatterns) {
            // Bitmap #TODO
            if ((bitMapPattern & (playerOne ? super.bitMapPlayer1 : super.bitMapPlayer2)) == bitMapPattern) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return Is every tile on the board filled?
     */
    private boolean checkFull() {
        // Left side long has 'size * size' zeros.
        // Right side (if board is truly full) has 'size * size' ones. Plus one equals left.
        return (1L << super.size * super.size) == ((super.bitMapPlayer1 | super.bitMapPlayer2) + 1);
    }

    /**
     * Creates a Board with a default size of 3x3.
     */
    public Board() {
        //Default size for a tic tac toe game
        this(3);
    }

    /**
     * Creates a Board with a variable size from 2x2 to 8x8.
     * @param size Number of rows and columns.
     */
    public Board(int size) {
        super((byte) size);
        if (size < 2 || size > 8) {
            throw new WrongBoardSizeException("Size of TicTacToe-Board must be in range 2 to 8");
        }
        this.currentTurn = true;
        // super.size rows + super.size columns + 2 diagonals
        this.bitMapPatterns = new long[super.size * 2 + 2];
        initPatterns();
    }

    /**
     * Creates all patterns to later check for a win.
     * A pattern is a bitmap that represents each row, column and diagonal.
     * #TODO -> logical conjunction
     */
    private void initPatterns() {
        // Row-patterns:
        long patternTemplate = (1L << super.size) - 1;
        int index = 0;
        for (int i = 0; i < super.size; i++) {
            this.bitMapPatterns[index++] = patternTemplate << (i * super.size);
        }
        // Column-patterns:
        patternTemplate = 1L;
        for (int i = 0; i < this.size - 1; i++) {
            patternTemplate <<= this.size;
            patternTemplate |= 1L;
        }
        for (int i = 0; i < super.size; i++) {
            this.bitMapPatterns[index++] = patternTemplate << i;
        }
        // Diagonal-patterns TL-BR
        patternTemplate = 1L;
        for (int i = 0; i < super.size - 1; i++) {
            patternTemplate <<= super.size + 1;
            patternTemplate |= 1L;
        }
        this.bitMapPatterns[index++] = patternTemplate;
        // Diagonal-patterns BL-TR
        patternTemplate = 1L;
        for (int i = 0; i < super.size - 1; i++) {
            patternTemplate <<= super.size - 1;
            patternTemplate |= 1L;
        }
        patternTemplate <<= super.size - 1;
        this.bitMapPatterns[index] = patternTemplate;

    }

    /**
     * Self-Explanatory: Reducing this Object to its Parent class. The GameState
     */
    public GameState exportGameState() {
        return this;
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
        final byte sizeSquared = (byte) (super.size * super.size);
        // Throwing an exception if move is invalid.
        if (tile < 0 || tile >= sizeSquared) {
            throw new OutOfBounceException(tile + " is not in bound for a board of size "
                    + super.size + ". Argument must be in range 0 - " + (sizeSquared - 1) + ".");
        }
        // game over = no moves to make
        if(this.isGameOver()) {
            return;
        }

        // Creating a bitmap with only one bit flipped to 1 at the index where the player wants to make a move.
        final long placeMoveBitmap = 1L << ((sizeSquared - 1) - tile);
        // Logical disjunction of both player bitmaps creates a bitmap of all non-empty tiles.
        final long bitMapOccupied = super.bitMapPlayer1 | super.bitMapPlayer2;

        // Throwing an exception if move is invalid.
        if ((placeMoveBitmap & bitMapOccupied) != 0) {
            throw new NonEmptyTileException("The chosen tile is already occupied. You hit an non empty Tile");
        }

        // Making the move by logical disjunction with correct bitmap
        if (this.currentTurn) {
            super.bitMapPlayer1 |= placeMoveBitmap;
        } else {
            super.bitMapPlayer2 |= placeMoveBitmap;
        }

        this.currentTurn = !this.currentTurn;

    }
    // #TODO
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
     */
    public void makeMove(int row, int col) {
        if (row < 0 || row >= super.size || col < 0 || col >= super.size) {
            throw new OutOfBounceException("Row: "+row + "or Column: " + col +" is not in bound for a board of size "
                    + super.size + ". Argument must be in range 0 - " + (super.size - 1) + ".");
        }
        this.makeMove(row * super.size + col);

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

}

