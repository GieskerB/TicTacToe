package main.java.de.gieskerb.tictactoe.model;

import main.java.de.gieskerb.tictactoe.exceptions.NotAllowedActionException;
import main.java.de.gieskerb.tictactoe.exceptions.WrongArgSizeException;
import main.java.de.gieskerb.tictactoe.player.Human;
import main.java.de.gieskerb.tictactoe.player.Player;
import main.java.de.gieskerb.tictactoe.view.Console;
import main.java.de.gieskerb.tictactoe.view.GUI;
import main.java.de.gieskerb.tictactoe.view.Visual;

public class Game implements Updatable {

    private Visual visual;

    private Board board;

    private Player player1, player2;

    private byte size;


    public Game() {
        this(new Human(true));
    }

    public Game(String nameP1) {
        this(new Human(nameP1, true));
    }

    public Game(Player p1) {
        this(p1, new Human(false));
    }

    public Game(String nameP1, String nameP2) {
        this(new Human(nameP1, true), new Human(nameP2, false));
    }

    public Game(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;

        this.size = 0;
        this.board = null;
        this.visual = null;

    }

    public Board getBoard() {
        return new Board(board);
    }

    public void newGame() {
        // Default TicTacToeSize
        this.newGame(3);
    }


    public void newGame(int size) {
        this.changeSize(size);
        this.board = new Board(this.size,this);
        this.reset();
    }

    public void restartGame() {
        if (this.size == 0 || this.board == null) {
            throw new NotAllowedActionException("You can not restart a game if you never started one in the first place.");
        }
        this.reset();
    }

    public void changeSize(int newSize) {
        this.size = (byte) newSize;
    }

    public void swapPlayers() {
        var temp = this.player1;
        this.player1 = this.player2;
        this.player2 = temp;
    }

    public Player changePlayer1To(Player newPlayer) {
        var oldPlayer = this.player1;
        this.player1 = newPlayer;
        return oldPlayer;
    }

    public Player changePlayer2To(Player newPlayer) {
        var oldPlayer = this.player2;
        this.player2 = newPlayer;
        return oldPlayer;
    }

    public void showConsole() {
        this.visual = new Console(this.board, this.size);
    }

    public void showGUI() {
        this.visual = new GUI(this.board, 690, this.size);
    }

    public void reset() {
        this.board.reset();
    }


    @Override
    public void update(Object... obj) {
        if((int)obj[0] == -1) {
            if(!this.player1.isMyTurn());
            this.switchTurns();
        }
    }

    public void switchTurns() {
        this.player1.switchTurn();
        this.player2.switchTurn();
    }

    public void askForNextMove() {
        int nextMove;
        if (this.player1.isMyTurn()) {
            nextMove = this.player1.getMove(this.board.exportGameState());
        } else {
            nextMove = this.player2.getMove(this.board.exportGameState());
        }

        if(nextMove != -1) {
            new Thread(() -> {
                try {
                    Thread.sleep(250);
                    board.service(Origin.COMPUTER, nextMove);
                } catch (InterruptedException ignored) {

                }

            }).start();

        }
    }
}
