package main.java.de.gieskerb.tictactoe.exceptions;

/**
 * Custom Exception for handling making an illegal move.
 * In this case, trying to make your move outside the board.
 */
public class OutOfBounceException extends IllegalArgumentException{

    public OutOfBounceException(String errMsg) {
        super(errMsg);
    }

}

