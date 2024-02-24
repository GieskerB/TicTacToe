package main.java.de.gieskerb.tictactoe.player;

import main.java.de.gieskerb.tictactoe.model.GameState;


/**
 * Interface defining the contract for brain logic implementations.
 */
interface BrainLogic {

    /**
     * Computes the next move based on the current game state.
     * @param gameState The current state of the game.
     * @return The index of the next move to make.
     */
    int thinkOfMove(GameState gameState);

}
