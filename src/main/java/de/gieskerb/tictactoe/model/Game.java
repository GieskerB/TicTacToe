package main.java.de.gieskerb.tictactoe.model;

import main.java.de.gieskerb.tictactoe.exceptions.NotAllowedActionException;
import main.java.de.gieskerb.tictactoe.exceptions.WrongArgSizeException;
import main.java.de.gieskerb.tictactoe.player.Human;
import main.java.de.gieskerb.tictactoe.player.Player;
import main.java.de.gieskerb.tictactoe.view.Console;
import main.java.de.gieskerb.tictactoe.view.GUI;
import main.java.de.gieskerb.tictactoe.view.Visual;

public class Game extends Updater {

    private Visual visual;

    private Board board;

    private Player player1, player2;

    private byte size;

    private byte round;

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
        this.player1.setGamePointer(this);
        this.player2 = p2;
        this.player2.setGamePointer(this);

        this.size = 0;
        this.round = 0;
        this.board = null;
        this.visual = null;

    }

    public int getRound() {
        return this.round;
    }

    public void newGame() {
        // Default TicTacToeSize
        this.newGame(3);
    }


    public void newGame(int size) {
        this.changeSize(size);
        this.board = new Board(this.size);
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
        var temp = this.player1;
        this.player1 = newPlayer;
        return temp;
    }

    public Player changePlayer2To(Player newPlayer) {
        var temp = this.player2;
        this.player2 = newPlayer;
        return temp;
    }

    public void showConsole() {
        this.visual = new Console(this, this.size);
    }

    public void showGUI() {
        this.visual = new GUI(this, 690, this.size);
    }

    public void reset() {
        this.board.reset();
        super.fireUpdate(-1);
    }


    @Override
    public void service(Origin origin, int... args) {
        final int ARG_SIZE = args.length;
        switch (origin) {
            case CONTROLLER:
            case COMPUTER:
                if (ARG_SIZE != 1 && ARG_SIZE != 2) {
                    throw new WrongArgSizeException("The expected number of args from a controller is one or two.");
                }

                final int tileIndex = args.length == 1 ? args[0] : Board.convertCoordsToIndex(args[0], args[1], this.size);
                this.board.makeMove(tileIndex);
                super.fireUpdate(tileIndex, (this.round % 2 == 1 ? 0 : 1));
                this.board.afterMove();
                break;


        }

        round++;
        player1.switchTurn();
        player2.switchTurn();
    }

}
