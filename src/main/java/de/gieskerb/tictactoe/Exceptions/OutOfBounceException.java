package main.java.de.gieskerb.tictactoe.Exceptions;

public class OutOfBounceException extends IllegalArgumentException{

    public OutOfBounceException(String errMsg) {
        super(errMsg);
    }

}

