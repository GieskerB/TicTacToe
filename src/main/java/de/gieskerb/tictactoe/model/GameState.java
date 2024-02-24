package main.java.de.gieskerb.tictactoe.model;

import main.java.de.gieskerb.tictactoe.exceptions.NonEmptyTileException;
import main.java.de.gieskerb.tictactoe.exceptions.OutOfBounceException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GameState {

    /**
     * number of rows and columns.
     */
    public final byte size;


    public final short sizeSquared;

    /**
     * An array storing all the information about the board.
     * +1: Player 1
     * -1: Player 2
     * 0: Empty
     */
    public final byte[] tiles;

    public boolean playerOne;

    GameState(byte size) {
        this.size = size;
        this.sizeSquared = (short) (this.size * this.size);
        this.tiles = new byte[sizeSquared];
        this.playerOne = true;
    }

    GameState(GameState copy) {
        this.size = copy.size;
        this.sizeSquared = copy.sizeSquared;
        this.tiles = new byte[this.sizeSquared];
        System.arraycopy(copy.tiles, 0, this.tiles, 0, sizeSquared);
        this.playerOne = copy.playerOne;
    }

    /**
     * @return An array with all indices which represent a legal move (empty tiles)
     */
    public int[] getLegalMoves() {
        List<Integer> legalMoves = new LinkedList<>();
        for (int index = 0; index < this.sizeSquared; index++) {
            if (this.tiles[index] == 0) {
                legalMoves.add(index);
            }
        }
        /*
         * https://stackoverflow.com/questions/960431/how-can-i-convert-listinteger-to-int-in-java
         */
        return legalMoves.stream().mapToInt(Integer::intValue).toArray();
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


    /**
     * @return Is every tile on the board filled?
     */
    public boolean checkFull() {
        for (var tile : this.tiles) {
            if (tile == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checking for a win. Adding all values for each row, column and diagonal and compareing it to
     * the size of the board will tell if a player has won the game.
     *
     * @return If the current gamest ate is a win or not!
     */
    public boolean checkWin() {
        byte diagonalTLBRSum = 0, diagonalTRBLSum = 0;
        for (int i = 0; i < this.size; i++) {
            Result result = getResult(i);
            // If the sum equals the size of the board the row or column is filled. Therefor the game is won.
            if (Math.abs(result.rowSum) == this.size || Math.abs(result.colSum) == this.size) {
                return true;
            }
            // Also adding up the diagonales.
            diagonalTLBRSum += this.tiles[i * this.size + i];
            diagonalTRBLSum += this.tiles[(this.size - i - 1) * this.size + i];
        }
        // Simplified expression: If diagonal is filled: game is won. Otherwise, it isn't.
        return Math.abs(diagonalTLBRSum) == this.size || Math.abs(diagonalTRBLSum) == this.size;
    }

    public byte countWin() {
        byte winningSum = 0;
        byte diagonalTLBRSum = 0, diagonalTRBLSum = 0;
        for (int i = 0; i < this.size; i++) {
            Result result = getResult(i);
            // If the sum equals the size of the board the row or column is filled. Therefor the game is won.
            if (result.rowSum() == this.size) {
                winningSum++;
            } else if (result.rowSum() == -this.size) {
                winningSum--;
            }
            if (result.colSum() == this.size) {
                winningSum++;
            } else if (result.colSum() == -this.size) {
                winningSum--;
            }
            // Also adding up the diagonales.
            diagonalTLBRSum += this.tiles[i * this.size + i];
            diagonalTRBLSum += this.tiles[(this.size - i - 1) * this.size + i];
        }
        if (diagonalTLBRSum == this.size) {
            winningSum++;
        } else if (diagonalTLBRSum == -this.size) {
            winningSum--;
        }
        if (diagonalTRBLSum == this.size) {
            winningSum++;
        } else if (diagonalTRBLSum == -this.size) {
            winningSum--;
        }
        return winningSum;
    }

    private Result getResult(int i) {
        byte rowSum = 0, colSum = 0;
        for (int j = 0; j < this.size; j++) {
            // Adding the values: Once for each row and once for each column.
            rowSum += this.tiles[j * this.size + i];
            colSum += this.tiles[i * this.size + j];
        }
        return new Result(rowSum, colSum);
    }

    private record Result(byte rowSum, byte colSum) {
    }

    void reset() {
        this.playerOne = true;
        for (int i = 0; i < this.sizeSquared; i++) {
            this.tiles[i] = 0;
        }
    }


    public void makeMove(int tile) {
        // game over = no moves to make
        if (this.isGameOver()) {
            return;
        }

        // Throwing an exception if move is invalid.
        if (this.tiles[tile] != 0) {
            throw new NonEmptyTileException("The chosen tile is already occupied. You hit an non empty tile");
        }

        // Making the move by setting the right value in the tiles array
        this.tiles[tile] = (byte) (this.playerOne ? 1 : -1);
        this.playerOne = !this.playerOne;
    }

    public void undoMove(int tile) {
        this.tiles[tile] = 0;
        this.playerOne = !this.playerOne;
    }



    /*
     * One of the Main reasons for separating the GameState into a singular class is a compression algorithm.
     * Converting the tiles into a string for easier storage.
     */

    private static final char[] MY_BASE_81 = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '!', '@', '[', ']', '^', '_', '`'};

    public static String getKey(GameState gameState) {
        StringBuilder stringBuilder = new StringBuilder();
        for (short i = (short) (gameState.sizeSquared - 1); i >= 0; i -= 4) {
            byte tempIndex;
            if (i >= 3) {
                tempIndex = (byte) ((gameState.tiles[i] + 1) +
                        (gameState.tiles[i - 1] + 1) * 3 +
                        (gameState.tiles[i - 2] + 1) * 9 +
                        (gameState.tiles[i - 3] + 1) * 27);

                // Remainder of 3 or 2 does not exist for this problem.
            } else {
                tempIndex = (byte) ((gameState.tiles[i] + 1));
            }
            stringBuilder.append(MY_BASE_81[tempIndex]);
        }
        return stringBuilder.toString();
    }

}
