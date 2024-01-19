package main.java.de.gieskerb.tictactoe.model;

import main.java.de.gieskerb.tictactoe.player.Human;
import main.java.de.gieskerb.tictactoe.player.Player;
import main.java.de.gieskerb.tictactoe.view.GUI;

public class Game {

    private GUI gui;

    private Board board;

    private Player player1, player2;

    public Game(int size) {
        this(size, new Human(true));
    }

    public Game(int size, String nameP1) {
        this(size,new Human(nameP1,true));
    }

    public Game(int size, Player p1) {
        this(size,p1, new Human(false));
    }

    public Game(int size, String nameP1, String nameP2) {
        this(size, new Human(nameP1,true), new Human(nameP2, false));
    }

    public Game(int size, Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;

        this.board = new Board(size);

        this.gui = new GUI(690,size);

    }
}
