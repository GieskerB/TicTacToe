package main.java.de.gieskerb.tictactoe.enums;

public enum Player {
    ONE, TWO;

    public Player otherPlayer() {
        if (this == ONE) {
            return TWO;
        }
        return ONE;
    }
}
