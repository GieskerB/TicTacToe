package main.java.de.gieskerb.tictactoe.exceptions;

/**
 * Custom Exception for handling an illegal board size.
 * The Board only is allowed to have a size from 2x2 to 8x8.
 */
public class WrongBoardSizeException extends IllegalArgumentException{

    public WrongBoardSizeException(String errMsg) {
        super(errMsg);
    }

}

