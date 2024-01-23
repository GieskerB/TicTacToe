package main.java.de.gieskerb.tictactoe.exceptions;
/**
 * Custom Exception for handling an illegal arg size.
 * The args when calling the service method need to be the right size.
 */
public class WrongArgSizeException extends IllegalArgumentException{

    public WrongArgSizeException(String errMsg) {
        super(errMsg);
    }

}