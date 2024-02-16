package main.java.de.gieskerb.tictactoe.player;

import main.java.de.gieskerb.tictactoe.model.GameState;

interface BrainLogic {

    int thinkOfMove(GameState gameState);

}
