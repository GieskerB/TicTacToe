package main.java.de.gieskerb.tictactoe.model;

import main.java.de.gieskerb.tictactoe.exceptions.NotAllowedActionException;
import main.java.de.gieskerb.tictactoe.player.Human;
import main.java.de.gieskerb.tictactoe.player.Player;
import main.java.de.gieskerb.tictactoe.view.GUI;
import main.java.de.gieskerb.tictactoe.view.Visual;

public class Game {

    private Visual visual;

    private Board board;

    private Player player1, player2;

    private byte size;

    public Game() {
        this( new Human(true));
    }

    public Game( String nameP1) {
        this(new Human(nameP1,true));
    }

    public Game( Player p1) {
        this(p1, new Human(false));
    }

    public Game( String nameP1, String nameP2) {
        this( new Human(nameP1,true), new Human(nameP2, false));
    }

    public Game(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;

        this.size = 0;
        this.board = null;

        this.visual = new GUI(690,size);

    }

    public void newGame() {
        // Default TicTacToeSize
        this.newGame(3);
    }


    public void newGame(int size) {
        this.changeSize(size);
        this.board = new Board( this.size);
    }

    public void restartGame() {
        if(this.size == 0|| this.board == null) {
            throw new NotAllowedActionException("You can not restart a game if you never started one in the first place.");
        }
    }

    public void changeSize(int newSize) {
        this.size = (byte) newSize;
    }

    public void swapPlayers() {
        var temp = this.player1;
        this.player1 = this.player2;
        this.player2 = temp;
    }

    public Player changePlayer1To (Player newPlayer) {
        var temp = this.player1;
        this.player1 = newPlayer;
        return temp;
    }

    public Player changePlayer2To (Player newPlayer) {
        var temp = this.player2;
        this.player2 = newPlayer;
        return temp;
    }

}
