package main.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.enums.Difficulty;
import main.java.de.gieskerb.tictactoe.enums.GameMode;
import main.java.de.gieskerb.tictactoe.enums.Origin;
import main.java.de.gieskerb.tictactoe.enums.Player;
import main.java.de.gieskerb.tictactoe.visual.GridPanel;

import javax.swing.*;

public class GamePlayLoop {

    private int size;
    private Board board;
    private GridPanel gridPanel;

    private Difficulty difficultyOne, difficultyTwo;
    private GameMode gameMode;

    private void startGamePlayLoop() {

        Thread gameLoop = new Thread(() -> {

            while (true) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (this.board.checkGameOver()){
                    this.handelGameOver();
                    continue;
                }

                switch (this.gameMode) {
                    case PvP -> {
                    }
                    case PvC -> {
                        if (this.board.getCurrentPlayer() == Player.TWO) {
                            int index = computerMove(this.difficultyTwo);
                            this.receiveInput(index, Origin.COMPUTER);
                            this.gridPanel.makeMove(index, Player.TWO);
                        }
                    }
                    case CvP -> {
                        if (this.board.getCurrentPlayer() == Player.ONE) {
                            int index = computerMove(this.difficultyOne);
                            this.receiveInput(index, Origin.COMPUTER);
                            this.gridPanel.makeMove(index, Player.ONE);
                        }
                    }
                    case CvC -> {

                        if (this.board.getCurrentPlayer() == Player.ONE) {
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
            }

        });

        gameLoop.setDaemon(true);
        gameLoop.start();

    }

    private void handelGameOver() {
        if(this.board.checkWinPlayerOne()) {
            JOptionPane.showMessageDialog(null,"Player 1 has won the game.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        } else if(this.board.checkWinPlayerTwo()) {
            JOptionPane.showMessageDialog(null,"Player 2 has won the game.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,"The games has ended in a tie.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
        this.restartGame();
    }

    private int computerMove(Difficulty difficulty) {
        switch (difficulty) {
            case EASY -> {
                return ComputerPlayer.easyDifficulty(new Board(this.board));
            }
            case MEDIUM -> {
                return ComputerPlayer.mediumDifficulty(new Board(this.board));
            }
            case HARD -> {
                return ComputerPlayer.hardDifficulty(new Board(this.board));
            }
            default ->
                throw new RuntimeException("Difficulty not recognized");

        }
    }

    public GamePlayLoop() {
        this.size = 3;
        this.board = new Board(this.size);
        this.gameMode = GameMode.PvP;
        this.gridPanel = null;

        this.startGamePlayLoop();
    }

    public void attachGridPanel(GridPanel gridPanel) {
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
        this.gridPanel.changeSize(size);
        this.restartGame();
    }

    public void restartGame() {
        this.board = new Board(this.size);
        this.gridPanel.restart();
    }

    public void receiveInput(int index, Origin origin) {
        if (this.board.checkGameOver()) return;

        switch (this.gameMode) {
            case PvP:
                if (origin == Origin.COMPUTER) return;

                break;
            case PvC:
                if ((this.board.getCurrentPlayer() == Player.ONE && origin == Origin.COMPUTER)
                        || (this.board.getCurrentPlayer() == Player.TWO && origin == Origin.KEYBOARD)) return;
                break;
            case CvP:
                if ((this.board.getCurrentPlayer() == Player.ONE && origin == Origin.KEYBOARD)
                        || (this.board.getCurrentPlayer() == Player.TWO && origin == Origin.COMPUTER)) return;
                break;
            case CvC:
                if (origin == Origin.KEYBOARD) return;
                break;
        }


        this.board.makeMove(index);
        this.gridPanel.makeMove(index, this.board.getPreviousPlayer());
        if (board.checkWinPlayerOne()) {
            System.out.println("Player 1 wins!");
        }
        if (board.checkWinPlayerTwo()) {
            System.out.println("Player 2 wins!");
        }
    }

}
