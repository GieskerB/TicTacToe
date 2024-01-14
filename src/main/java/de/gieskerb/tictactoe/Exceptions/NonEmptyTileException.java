package main.java.de.gieskerb.tictactoe.Exceptions;

public class NonEmptyTileException extends IllegalArgumentException{

    public NonEmptyTileException(String errMsg) {
        super(errMsg);
    }

}

