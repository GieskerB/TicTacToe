package main.java.de.gieskerb.tictactoe.board;

import main.java.de.gieskerb.tictactoe.player.Computer;
import main.java.de.gieskerb.tictactoe.player.Human;

public class BoardManager {

    public enum Player {
        PLAYER_1, PLAYER_2
    }

    public enum GameMode {
        PVP,PVC,CVP,CVC
    }

    private Board board;
    private Player currentPlayer;
    private GameMode gameMode;
    private Computer.Difficulty difficultyAi1, difficultyAi2;
    private Human.InputDevice inputDevice;

    private void changePlayer() {
        if(currentPlayer == Player.PLAYER_1) {
            currentPlayer = Player.PLAYER_2;
        } else {
            currentPlayer = Player.PLAYER_1;
        }
    }

    private boolean isComputerMove() {
        if(this.gameMode == GameMode.CVC) {
            return true;
        } else if(this.gameMode == GameMode.PVP) {
            return false;
        } else if(this.gameMode == GameMode.CVP) {
            return this.currentPlayer == Player.PLAYER_1;
        } else {//this.gameMode == GameMode.PVC
            return this.currentPlayer == Player.PLAYER_2;
        }
    }

    private Player nextRound() {
        final boolean playerOnePlaying = this.currentPlayer == Player.PLAYER_1;
        int move;
        if (this.isComputerMove()) {
            move = Computer.makeMove(board,playerOnePlaying ? this.difficultyAi1 : this.difficultyAi2,playerOnePlaying);
        } else {
            move = Human.makeMove(board, this.inputDevice, playerOnePlaying);
        }

        if(playerOnePlaying) {
            this.board.player1.makeMove(move);
            if(this.board.player1.checkWin()) {
                return this.currentPlayer;
            }
        } else {
            this.board.player2.makeMove(move);
            if(this.board.player2.checkWin()) {
                return this.currentPlayer;
            }
        }

        changePlayer();
        return null;
    }

    public BoardManager() {
        this.board = new Board(3);
        this.currentPlayer = Player.PLAYER_1;
        this.gameMode = GameMode.PVP;
        this.difficultyAi1 = Computer.Difficulty.EASY;
        this.difficultyAi2 = Computer.Difficulty.EASY;
        this.inputDevice = Human.InputDevice.CONSOLE;
    }

    public Player playFullRound() {
        while (!board.checkGameOver()) {
            Player winningPlayer = nextRound();
            if(winningPlayer != null) {
                return winningPlayer;
            }
        }
        // Must be a tie!
        return null;
    }

    public void changeSize(byte newSize) {
        this.board = new Board(newSize);
        this.currentPlayer = Player.PLAYER_1;
    }

    public void changeGameMode(GameMode newGameMode) {
        this.gameMode = newGameMode;
    }

    public void changeDifficulty(Computer.Difficulty newDifficulty, Player aiPlayer) {
        if (aiPlayer == null) {
            return;
        }
        if(aiPlayer == Player.PLAYER_1) {
            this.difficultyAi1 = newDifficulty;
        } else {
            this.difficultyAi2 = newDifficulty;
        }
    }

    public void changeInputDevice(Human.InputDevice newInputDevice) {
        this.inputDevice = newInputDevice;
    }

}
