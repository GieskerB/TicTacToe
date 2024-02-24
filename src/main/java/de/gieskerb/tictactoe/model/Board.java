package main.java.de.gieskerb.tictactoe.model;

import main.java.de.gieskerb.tictactoe.exceptions.OutOfBounceException;
import main.java.de.gieskerb.tictactoe.exceptions.WrongBoardSizeException;
import main.java.de.gieskerb.tictactoe.util.Pair;
import test.java.de.gieskerb.tictactoe.FriendTestAccess;
import test.java.de.gieskerb.tictactoe.Testable;

import javax.swing.*;

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

    private final GameState gameState;


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
        this.gameState = new GameState((byte) size);

    }

    /**
     * Creates a deep copy of a Board object.
     *
     * @param copy is the board to be copied.
     */
    public Board(Board copy) {
        this.gamePointer = copy.gamePointer;
        this.gameState = new GameState(copy.gameState);
    }

    /**
     * Everything need to be done after a players move to perpare for the next one.
     * 1. Change which player is currently playing
     */
    byte afterMove() {
        byte result = -1;
        if (this.gameState.checkWin()) {
            result = (byte) JOptionPane.showOptionDialog(null,
                    "Player " + (!this.gameState.playerOne ? 'X' : 'O') + " has won the game!", "Game  Over",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    new String[]{"Rematch", "Quit"}, "Rematch");
        } else if (this.gameState.checkFull()) {
            result = (byte) JOptionPane.showOptionDialog(null, "Game has ended in a Tie!", "Game  Over",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    new String[]{"Rematch", "Quit"}, "Rematch");
        }

        return result;
    }

    void reset() {
        this.gameState.reset();
    }


    /**
     * Reducing this object to a GameState. GameState is a snapshot of the current game state. For easy and public
     * access to all attributes without messing with the original one.
     */
    public GameState exportGameState() {
        return new GameState(this.gameState);
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
        // Throwing an exception if move is invalid.
        if (tile >= this.gameState.sizeSquared || tile < 0) {
            throw new OutOfBounceException("Index: " + tile + " is not in bound for a board of size "
                    + this.gameState.size + ". Argument must be in range 0 - " + (this.gameState.sizeSquared - 1) + ".");
        }
        this.gameState.makeMove(tile);
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
        // Throwing an exception if move is invalid.
        if (row < 0 || column < 0 || row >= this.gameState.size || column >= this.gameState.size) {
            throw new OutOfBounceException("Row: " + row + " or column " + column + " is not in bound for a board of size "
                    + this.gameState.size + ". Arguments must be in range 0 - " + (this.gameState.size - 1) + ", "
                    + (this.gameState.size - 1) + ".");
        }
        this.makeMove(row * this.gameState.size + column);
    }

    @Override
    protected void serviced(Origin origin, int... args) {
        byte result = -1;
        switch (origin) {
            case CONTROLLER:
                assert (args.length == 2);
                if (this.gameState.isGameOver()) {
                    return;
                }
                this.makeMove(args[0], args[1]);
                super.fireUpdate(args[0] * this.gameState.size + args[1], !this.gameState.playerOne);
                result = this.afterMove();
                break;
            case COMPUTER:
                if (this.gameState.isGameOver()) {
                    return;
                }
                assert (args.length == 1);
                this.makeMove(args[0]);
                super.fireUpdate(args[0], !this.gameState.playerOne);
                result = this.afterMove();
                break;
            default:
                // Unknown do nothing so far

        }

        if (result == -1) {
            this.gamePointer.switchTurns();

            this.gamePointer.askForNextMove();
        } else if (result == 0) {
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

        if (!(obj instanceof Board board)) return false;

        return this.gameState.equals(board.gameState);
    }

}

