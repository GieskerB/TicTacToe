package main.java.de.gieskerb.tictactoe.exceptions;

/**
 * Custom Exception for handling making an illegal move.
 * In this case, trying to make your move at an already occupied tile.
 */
public class NonEmptyTileException extends IllegalArgumentException{

    public NonEmptyTileException(String errMsg) {
        super(errMsg);
    }

}

