package main.java.de.gieskerb.tictactoe.Exceptions;

public class WrongBoardSizeException extends IllegalArgumentException{

    public WrongBoardSizeException(String errMsg) {
        super(errMsg);
    }

}

