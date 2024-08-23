package main.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.enums.Difficulty;
import main.java.de.gieskerb.tictactoe.enums.GameMode;
import main.java.de.gieskerb.tictactoe.enums.Origin;
import main.java.de.gieskerb.tictactoe.enums.Player;
import main.java.de.gieskerb.tictactoe.visual.GridPanel;

public class GamePlayLoop {

    private int size;
    private Board board;
    private GridPanel gridPanel;

    private Difficulty difficultyOne, difficultyTwo;
    private GameMode gameMode;

    private void startGamePlayLoop() {

        Thread gameLoop = new Thread(() -> {

            while (true) {
                switch (this.gameMode) {
                    case PvP -> {
                    }
                    case PvC -> {
                        if(this.board.getPreviousPlayer() == Player.TWO) {
                            int index = computerMove(this.difficultyTwo);
                            this.receiveInput(index, Origin.COMPUTER);
                            this.gridPanel.makeMove(index, Player.TWO);
                        }
                    }
                    case CvP -> {
                        if(this.board.getPreviousPlayer() == Player.ONE) {
                            int index = computerMove(this.difficultyOne);
                            this.receiveInput(index, Origin.COMPUTER);
                            this.gridPanel.makeMove(index, Player.ONE);
                        }
                    }
                    case CvC -> {

                        if(this.board.getPreviousPlayer() == Player.ONE) {
                            int index = computerMove(this.difficultyOne);
                            this.board.makeMove(index);
                            this.gridPanel.makeMove(index, Player.ONE);
                        } else {
                            int index = computerMove(this.difficultyTwo);
                            this.receiveInput(index, Origin.COMPUTER);
                            this.gridPanel.makeMove(index, Player.TWO);
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        });

        gameLoop.setDaemon(true);
        gameLoop.start();

    }

    private int computerMove(Difficulty difficulty) {
        return 0;
    }

    public GamePlayLoop() {
        this.size = 3;
        this.board = new Board(this.size);
        this.gameMode = GameMode.PvP;
        this.gridPanel = null;

        this.startGamePlayLoop();
    }

    public void attachGridPanel (GridPanel gridPanel) {
        this.gridPanel = gridPanel;
    }

    public void changeGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
        System.out.println(this.gameMode);
    }

    public void changeDifficulties(Difficulty difficultyOne, Difficulty difficultyTwo) {
        this.difficultyOne = difficultyOne;
        this.difficultyTwo = difficultyTwo;
    }

    public void changeSize(int size) {
        this.size = size;
        this.restartGame();
        this.gridPanel.changeSize(size);
    }

    void restartGame() {
        this.board = new Board(this.size);
    }

    public void receiveInput(int index, Origin origin) {
        this.board.makeMove(index);
        this.gridPanel.makeMove(index, this.board.getPreviousPlayer());
        if(board.checkWinPlayerOne()) {
            System.out.println("Player 1 wins!");
        }
        if(board.checkWinPlayerTwo()) {
            System.out.println("Player 2 wins!");
        }
    }

}
