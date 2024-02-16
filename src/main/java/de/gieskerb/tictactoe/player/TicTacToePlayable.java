package main.java.de.gieskerb.tictactoe.player;

import main.java.de.gieskerb.tictactoe.model.GameState;

public interface TicTacToePlayable {

    int getMove(GameState gameState);

    void switchTurn();

    boolean isMyTurn();
}
