package main.java.de.gieskerb.tictactoe.board;

public class BoardManager {

    enum Player {
        PLAYER_1, PLAYER_2
    }

    public enum GameMode {
        PVP,PVC,CVP,CVC
    }

    private Board board;
    private Player currentPlayer;
    private GameMode gameMode;

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

    public BoardManager() {
        this.board = new Board(3);
        this.currentPlayer = Player.PLAYER_1;
        this.gameMode = GameMode.PVP;
    }

    public void changeSize(byte newSize) {
        this.board = new Board(newSize);
        this.currentPlayer = Player.PLAYER_1;
    }

    public void nextRound() {
        if (this.isComputerMove()) {

        } else {

        }
        changePlayer();
    }

    public void changeGameMode(GameMode newGameMode) {
        this.gameMode = newGameMode;
    }

}
