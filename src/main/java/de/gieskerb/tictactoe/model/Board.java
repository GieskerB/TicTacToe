package main.java.de.gieskerb.tictactoe.model;

import main.java.de.gieskerb.tictactoe.exceptions.NonEmptyTileException;
import main.java.de.gieskerb.tictactoe.exceptions.OutOfBounceException;
import main.java.de.gieskerb.tictactoe.exceptions.WrongBoardSizeException;
import main.java.de.gieskerb.tictactoe.util.Pair;
import test.java.de.gieskerb.tictactoe.FriendTestAccess;
import test.java.de.gieskerb.tictactoe.Testable;

import javax.swing.*;
import java.util.Arrays;

/**
 * Board handles the game logic:
 * Making a move.
 * Checking for correctness.
 * Win detection
 */
public class Board extends Updater implements Testable {

    final Game gamePointer;

    /**
     * Even though the game should be scalable the size will be caped at a specific value. This one is set based on
     * performance and visual clustering.
     */
    final static byte MAX_BOARD_SIZE = 15;

    /**
     * number of rows and columns of the board.
     */
    final byte size;

    /**
     * Two-dimensional array storing all the information about the board.
     * +1: Player 1
     * -1: Player 2
     * 0: Empty
     */
    byte[][] tiles;

    /**
     * True means it's players one turn, false says its players two turn.
     */
    private boolean currentTurn;


    /**
     * Simple helper function to convert from index to coordinates. But when converting
     * out of bounce this method will throw an exception.
     * <p>
     * Example:
     * +---+---+---+    +---+---+---+
     * | 0 | 1 | 2 |    |0,0|0,1|0,2|
     * +---+---+---+    +---+---+---+
     * | 3 | 4 | 5 | => |1,0|1,1|1,2|
     * +---+---+---+    +---+---+---+
     * | 6 | 7 | 8 |    |2,0|2,1|2,2|
     * +---+---+---+    +---+---+---+
     *
     * @param index will be converted into a Pair of coordinates.
     * @param size  of the board
     * @return Pair of coordinates: row and column based on index.
     */
    static Pair<Byte, Byte> convertIndexToCoords(int index, byte size) {
        if (index < 0 || index >= size * size) {
            throw new OutOfBounceException("Index: " + index + " does not match with board of size "
                    + size + "x" + size + "!");
        }
        return new Pair<>((byte) (index / size), (byte) (index % size));
    }

    /**
     * Checking for a win. Adding all values for each row, column and diagonal and compareing it to
     * the size of the board will tell if a player has won the game.
     *
     * @return If the current gamest ate is a win or not!
     */
    private boolean checkWin() {
        byte diagonalTLBRSum = 0, diagonalTRBLSum = 0;
        for (int i = 0; i < this.size; i++) {
            byte rowSum = 0, colSum = 0;
            for (int j = 0; j < this.size; j++) {
                // Adding the values: Once for each row and once for each column.
                rowSum += this.tiles[j][i];
                colSum += this.tiles[i][j];
            }
            // If the sum equals the size of the board the row or column is filled. Therefor the game is won.
            if (Math.abs(rowSum) == this.size || Math.abs(colSum) == this.size) {
                return true;
            }
            // Also adding up the diagonales.
            diagonalTLBRSum += this.tiles[i][i];
            diagonalTRBLSum += this.tiles[this.size - i - 1][i];
        }
        // Simplified expression: If diagonal is filled: game is won. Otherwise, it isn't.
        return Math.abs(diagonalTLBRSum) == this.size || Math.abs(diagonalTRBLSum) == this.size;
    }


    /**
     * @return Is every tile on the board filled?
     */
    private boolean checkFull() {
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                if (this.tiles[row][col] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Creates a Board with a default size of 3x3.
     */
    public Board(Game gamePointer) {
        //Default size for a tic tac toe game
        this(3, gamePointer);
    }

    /**
     * Creates a Board with a variable size from 2x2 to 15x15.
     *
     * @param size Number of rows and columns.
     */
    public Board(int size, Game gamePointer) {
        if (size < 2 || size > MAX_BOARD_SIZE) {
            throw new WrongBoardSizeException("Size of TicTacToe-Board must be in range 2 to " + MAX_BOARD_SIZE);
        }
        this.gamePointer = gamePointer;
        if (this.gamePointer != null) {
            this.attach(gamePointer);
        }
        this.size = (byte) size;
        this.currentTurn = true;
        this.tiles = new byte[this.size][this.size];

    }

    /**
     * Creates a deep copy of a Board object.
     *
     * @param copy is the board to be copied.
     */
    public Board(Board copy) {
        this.gamePointer = copy.gamePointer;
        this.size = copy.size;
        this.tiles = new byte[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            this.tiles[i] = Arrays.copyOf(copy.tiles[i], this.size);
        }
        this.currentTurn = copy.currentTurn;
    }

    /**
     * Everything need to be done after a players move to perpare for the next one.
     * 1. Change which player is currently playing
     */
    byte afterMove() {
        this.currentTurn = !this.currentTurn;
        byte result = -1;
        if (this.checkWin()) {
            result = (byte) JOptionPane.showOptionDialog(null,
                    "Player " + (!this.currentTurn ? 'X' : 'O') + " has won the game!", "Game  Over",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    new String[]{"Rematch", "Quit"}, "Rematch");
        } else if (this.checkFull()) {
            result = (byte) JOptionPane.showOptionDialog(null, "Game has ended in a Tie!", "Game  Over",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    new String[]{"Rematch", "Quit"}, "Rematch");
        }

        return result;
    }

    void reset() {
        this.currentTurn = true;
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                this.tiles[row][col] = 0;
            }
        }
    }


    /**
     * Reducing this object to a GameState. GameState is a snapshot of the current game state. For easy and public
     * access to all attributes without messing with the original one.
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
     *             +---+---+---+
     *             | 0 | 1 | 2 |
     *             +---+---+---+
     *             | 3 | 4 | 5 |
     *             +---+---+---+
     *             | 6 | 7 | 8 |
     *             +---+---+---+
     */
    void makeMove(int tile) {
        if (tile >= size * size) {
            throw new OutOfBounceException("Index: " + tile + " is not in bound for a board of size "
                    + size + ". Argument must be in range 0 - " + (size * size - 1) + ".");
        }
        this.makeMove(tile / this.size, tile % this.size);
    }

    /**
     * Tries to make a move on the board. Illegal placements like out of bounce and already occupies
     * will throw an exception.
     *
     * @param row    Self Explanatory
     * @param column Self Explanatory
     *               +---+---+---+
     *               |0,0|0,1|0,2|
     *               +---+---+---+
     *               |1,0|1,1|1,2|
     *               +---+---+---+
     *               |2,0|2,1|2,2|
     *               +---+---+---+
     */
    void makeMove(int row, int column) {
        final byte sizeSquared = (byte) (this.size * this.size);
        // Throwing an exception if move is invalid.
        if (row < 0 || column < 0 || row >= this.size || column >= this.size) {
            throw new OutOfBounceException("Row: " + row + " or column " + column + " is not in bound for a board of size "
                    + this.size + ". Arguments must be in range 0 - " + (this.size - 1) + ", " + (this.size - 1) + ".");
        }
        // game over = no moves to make
        if (this.isGameOver()) {
            return;
        }

        // Throwing an exception if move is invalid.
        if (this.tiles[row][column] != 0) {
            throw new NonEmptyTileException("The chosen tile is already occupied. You hit an non empty tile");
        }

        // Making the move by setting the right value in the tiles array
        this.tiles[row][column] = (byte) (this.currentTurn ? 1 : -1);
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
        return this.checkWin() || this.checkFull();
    }


    @Override
    protected void serviced(Origin origin, int... args) {
        byte result = -1;
        switch (origin) {
            case CONTROLLER:
                assert (args.length == 2);
                this.makeMove(args[0], args[1]);
                super.fireUpdate(args[0] * this.size + args[1], this.currentTurn);
                result = this.afterMove();
                break;
            case COMPUTER:
                assert (args.length == 1);
                this.makeMove(args[0]);
                super.fireUpdate(args[0], this.currentTurn);
                result = this.afterMove();
                break;
            default:
                // Unknown do nothing so far

        }

        if(result == -1) {
            this.gamePointer.switchTurns();

            this.gamePointer.askForNextMove();
        } else if(result == 0) {
            this.reset();
            super.fireUpdate(-1);
        } else {
            System.exit(0);
        }

    }

    @Override
    public Object invokeMethod(FriendTestAccess fta) {

        switch (fta.getMethodName()) {
            case "convertIndexToCoords":
                return Board.convertIndexToCoords((int) fta.getArg(0), (byte) fta.getArg(1));
            case "makeMove":
                if (fta.argsLength() == 1) {
                    this.makeMove((int) fta.getArg(0));
                } else {
                    this.makeMove((int) fta.getArg(0), (int) fta.getArg(1));
                }
                break;
            case "afterMove":
                this.afterMove();
                break;
            case "reset":
                this.reset();
                break;

        }

        return null;
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Board board)) {
            return false;
        }

        if (board.size != this.size) {
            return false;
        }

        if (board.currentTurn != this.currentTurn) {
            return false;
        }

        return Arrays.deepEquals(board.tiles, this.tiles);
    }

}

