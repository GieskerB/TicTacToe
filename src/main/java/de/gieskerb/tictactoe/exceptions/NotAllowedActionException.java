package main.java.de.gieskerb.tictactoe.exceptions;

/**
 * Custom Exception for handling making an illegal move.
 * In this case, trying to make your move at an already occupied tile.
 */
public class NotAllowedActionException extends IllegalArgumentException{

    public NotAllowedActionException(String errMsg) {
        super(errMsg);
    }

}

