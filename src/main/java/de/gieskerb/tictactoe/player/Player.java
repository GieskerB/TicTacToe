package main.java.de.gieskerb.tictactoe.player;

import main.java.de.gieskerb.tictactoe.model.GameResult;
import main.java.de.gieskerb.tictactoe.model.GameState;
import main.java.de.gieskerb.tictactoe.model.Game;

import java.awt.event.MouseAdapter;

public abstract class Player  implements TicTacToePlayable {

    Game gamePointer;

    final String name;

    boolean isPlayerOne, isMyTurn;

    /**
     * This array will store the game outcomes.
     * 0: win
     * 1: draw
     * 2: lose
     */
    private final short[] score;

    Player(String name, boolean isPlayerOne) {
        this.name = name;
        this.isPlayerOne = isPlayerOne;
        this.isMyTurn = this.isPlayerOne;
        this.gamePointer = null;
        this.score = new short[3];

    }

    public void setGamePointer (Game gamePointer) {
        this.gamePointer = gamePointer;
    }

    public void notifyGameResult(GameResult gr) {
        switch (gr) {
            case WIN:
                ++this.score[0];
                break;
            case DRAW:
                ++this.score[1];
                break;
            case LOSE:
                ++this.score[2];
                break;
        }
    }

    @Override
    public String toString() {
        return this.name + " scored as followed" +
                "\n\tWin:  " +
                this.score[0] +
                "\n\tDraw: " +
                this.score[1] +
                "\n\tLose: " +
                this.score[2] +
                '\n';
    }

    @Override
    public byte getMove(GameState gameState) {
        return 0;
    }

    abstract void myTurn();

    @Override
    public void switchTurn() {
        this.isMyTurn = !this.isMyTurn;
        if (this.isMyTurn) {
            this.myTurn();
        }
    }
}
