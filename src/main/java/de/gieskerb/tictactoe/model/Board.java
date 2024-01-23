package main.java.de.gieskerb.tictactoe.model;

import main.java.de.gieskerb.tictactoe.exceptions.NonEmptyTileException;
import main.java.de.gieskerb.tictactoe.exceptions.OutOfBounceException;
import main.java.de.gieskerb.tictactoe.exceptions.WrongArgSizeException;
import main.java.de.gieskerb.tictactoe.exceptions.WrongBoardSizeException;

/**
 * Board handles the game logic:
 * Making a move.
 * Checking for correctness.
 * Win detection
 */
public class Board extends Updater {

    /**
     * number of rows and columns of the board.
     */
    final byte size;

    /**
     * Bitmaps contain information about the location where a player made a move.
     * A one in the map says the player made a move in that location.
     * Going from LSB to MSB in the bitmap, it will represent the game tile
     * from the bottom right going from right to left and bottom to top.
     */
    private long bitMapPlayer1, bitMapPlayer2;

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
     *
     * @param playerOne Determine which bitMap needs to be checked for a win.
     * @return Did the given player win?
     */
    private boolean checkWin(boolean playerOne) {
        for (long bitMapPattern : this.bitMapPatterns) {
            // Logical conjunction between bitMapPattern for a win and the bitmap of the current player
            if ((bitMapPattern & (playerOne ? this.bitMapPlayer1 : this.bitMapPlayer2)) == bitMapPattern) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates all patterns to later check for a win.
     * A pattern is a bitmap that represents each row, column and diagonal.
     * When logically continuing what pattern and a players bitmap and result
     * os the same pattern again, player has won the game.
     */
    private void initPatterns() {
        // Row-patterns:
        long patternTemplate = (1L << this.size) - 1;
        int index = 0;
        for (int i = 0; i < this.size; i++) {
            this.bitMapPatterns[index++] = patternTemplate << (i * this.size);
        }
        // Column-patterns:
        patternTemplate = 1L;
        for (int i = 0; i < this.size - 1; i++) {
            patternTemplate <<= this.size;
            patternTemplate |= 1L;
        }
        for (int i = 0; i < this.size; i++) {
            this.bitMapPatterns[index++] = patternTemplate << i;
        }
        // Diagonal-patterns TL-BR
        patternTemplate = 1L;
        for (int i = 0; i < this.size - 1; i++) {
            patternTemplate <<= this.size + 1;
            patternTemplate |= 1L;
        }
        this.bitMapPatterns[index++] = patternTemplate;
        // Diagonal-patterns BL-TR
        patternTemplate = 1L;
        for (int i = 0; i < this.size - 1; i++) {
            patternTemplate <<= this.size - 1;
            patternTemplate |= 1L;
        }
        patternTemplate <<= this.size - 1;
        this.bitMapPatterns[index] = patternTemplate;

    }


    /**
     * @return Is every tile on the board filled?
     */
    private boolean checkFull() {
        // Left side long has 'size * size' zeros.
        // Right side (if board is truly full) has 'size * size' ones. Plus one equals left.
        return (1L << this.size * this.size) == ((this.bitMapPlayer1 | this.bitMapPlayer2) + 1);
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
     *
     * @param size Number of rows and columns.
     */
    public Board(int size) {
        this.size = (byte) size;
        if (size < 2 || size > 8) {
            throw new WrongBoardSizeException("Size of TicTacToe-Board must be in range 2 to 8");
        }
        this.currentTurn = true;
        // this.size rows + this.size columns + 2 diagonals
        this.bitMapPatterns = new long[this.size * 2 + 2];
        initPatterns();
    }

    /**
     * Allowing for public access to the bitmap
     *
     * @return bitmap form player one
     */
    public long getBitMapPlayer1() {
        return this.bitMapPlayer1;
    }

    /**
     * Allowing for public access to the bitmap
     *
     * @return bitmap form player two
     */
    public long getBitMapPlayer2() {
        return this.bitMapPlayer2;
    }

    /**
     * Everything need to be done after a players move to perpare for the next one.
     * 1. Change which player is currently playing
     */
    private void afterMove() {
        this.currentTurn = !this.currentTurn;
    }

    @Override
    public void service(int[] args, Origin origin) {
        final int ARG_SIZE = args.length;
        switch (origin) {
            case CONTROLLER:
                if (ARG_SIZE != 1 && ARG_SIZE != 2) {
                    throw new WrongArgSizeException("The expected number of args from a controller is one or two.");
                }
                if (args.length == 1) this.makeMove(args[0]);
                else this.makeMove(args[0], args[1]);
                this.afterMove();

        }
    }

    /**
     * Self-Explanatory: Reducing this object to a GameState
     */
    public GameState exportGameState() {
        return new GameState(this);
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
    private void makeMove(int tile) {
        final byte sizeSquared = (byte) (this.size * this.size);
        // Throwing an exception if move is invalid.
        if (tile < 0 || tile >= sizeSquared) {
            throw new OutOfBounceException(tile + " is not in bound for a board of size "
                    + this.size + ". Argument must be in range 0 - " + (sizeSquared - 1) + ".");
        }
        // game over = no moves to make
        if (this.isGameOver()) {
            return;
        }

        // Creating a bitmap with only one bit flipped to 1 at the index where the player wants to make a move.
        final long placeMoveBitmap = 1L << ((sizeSquared - 1) - tile);
        // Logical disjunction of both player bitmaps creates a bitmap of all non-empty tiles.
        final long bitMapOccupied = this.bitMapPlayer1 | this.bitMapPlayer2;

        // Throwing an exception if move is invalid.
        if ((placeMoveBitmap & bitMapOccupied) != 0) {
            throw new NonEmptyTileException("The chosen tile is already occupied. You hit an non empty tile");
        }

        // Making the move by logical disjunction with correct bitmap
        if (this.currentTurn) {
            this.bitMapPlayer1 |= placeMoveBitmap;
        } else {
            this.bitMapPlayer2 |= placeMoveBitmap;
        }
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
     */
    private void makeMove(int row, int col) {
        if (row < 0 || row >= this.size || col < 0 || col >= this.size) {
            throw new OutOfBounceException("Row: " + row + "or Column: " + col + " is not in bound for a board of size "
                    + this.size + ". Argument must be in range 0 - " + (this.size - 1) + ".");
        }
        this.makeMove(row * this.size + col);

    }

    /**
     * There are three reasons why the game ends:
     * 1. Player 1 has won the game.
     * 2. Player 2 has won the game.
     * 3. No one has won the game. Its draw.
     *
     * @return is the game over due to one of the prior reasons.
     */
    public boolean isGameOver() {
        return this.checkWin(true) || this.checkWin(false) || this.checkFull();

    }

}

