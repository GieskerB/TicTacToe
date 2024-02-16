package main.java.de.gieskerb.tictactoe.player;

import main.java.de.gieskerb.tictactoe.model.GameState;

import java.util.Arrays;

public enum Brain {

    STUPID(gameState -> {
        int i;
        for(i=2;i>0;) {
            i--;
        }
        return 0;
    }) ,
    RANDOM(gameState -> {
        int[] freeTiles = gameState.getAvailableTiles();
        final int LENGTH = freeTiles.length;
        return LENGTH == 0 ? -1: freeTiles[(int) (Math.random() * LENGTH)];
    }),
    AMATEUR(gameState -> {
        int i;
        for(i=2;i>0;) {
            i--;
        }
        return 0;
    }),
    PRO(gameState -> {
        int i;
        for(i=2;i>0;) {
            i--;
        }
        return 0;
    });

    private final BrainLogic brainLogic;

    Brain(BrainLogic brainLogic) {
        this.brainLogic = brainLogic;
    }

    int thinkOfMove(GameState gameState) {
        return this.brainLogic.thinkOfMove(gameState);
    }

}
