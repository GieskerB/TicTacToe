package main.java.de.gieskerb.tictactoe.player;

import main.java.de.gieskerb.tictactoe.model.GameState;

/**
 * Enum representing different levels of intelligence for the computer player in the game.
 */
public enum Brain {

    /**
     * Represents the EASY difficulty level where the computer player makes random moves.
     */
    EASY(gameState -> {
        int[] freeTiles = gameState.getLegalMoves();
        final int LENGTH = freeTiles.length;
        return LENGTH == 0 ? -1: freeTiles[(int) (Math.random() * LENGTH)];
    }),

    /**
     * Represents the MEDIUM difficulty level where the computer player looks 1 or 2 moves ahead.
     */
    MEDIUM(gameState -> {
        return 0; // Placeholder for MEDIUM implementation
    }),

    /**
     * Represents the HARD difficulty level where the computer player aims for perfect gameplay using Minimax algorithm.
     */
    HARD(gameState -> {

        int bestMove = 0;
        short  bestScore = Short.MIN_VALUE, score;

        for(var move: gameState.getLegalMoves()){
            gameState.makeMove(move);
            score = miniMax(gameState, false, (short) (gameState.sizeSquared-1));

            System.out.println(score);
            if(score > bestScore) {
                bestMove = move;
                bestScore = score;
            }

            gameState.undoMove(move);
        }
        System.out.println("---");

        return bestMove;
    });

    private final BrainLogic brainLogic;

    private static short miniMax(GameState gameState, boolean maximizing, short depth) {

        if(gameState.checkWin()) {
            return (short) -gameState.countWin();
        }

        if(gameState.checkFull()) {
            return 0;
        }

        short score, bestScore;
        if(maximizing) {
            bestScore = Short.MIN_VALUE;

            for(var move: gameState.getLegalMoves()){
                gameState.makeMove(move);
                score = miniMax(gameState, false, (short) (depth-1));
                bestScore = bestScore < score ? score: bestScore;
                gameState.undoMove(move);
            }

        } else {
            bestScore = Short.MAX_VALUE;

            for(var move: gameState.getLegalMoves()){
                gameState.makeMove(move);
                score = miniMax(gameState, true, (short) (depth-1));
                bestScore = bestScore > score ? score: bestScore;
                gameState.undoMove(move);

            }

        }


        return bestScore;
    }


    /**
     * Constructs a Brain enum with the provided BrainLogic implementation.
     * @param brainLogic The BrainLogic implementation corresponding to the difficulty level.
     */
    Brain(BrainLogic brainLogic) {
        this.brainLogic = brainLogic;
    }

    /**
     * Computes the next move based on the current game state.
     * @param gameState The current state of the game.
     * @return The index of the next move to make.
     */
    int thinkOfMove(GameState gameState) {
        return this.brainLogic.thinkOfMove(gameState);
    }

}
