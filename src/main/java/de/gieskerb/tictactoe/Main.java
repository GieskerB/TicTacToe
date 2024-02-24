package main.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.model.Game;
import main.java.de.gieskerb.tictactoe.player.Brain;
import main.java.de.gieskerb.tictactoe.player.Computer;
import main.java.de.gieskerb.tictactoe.player.Human;

public class Main {
    public static void main (String[] args)  {
        Game game = new Game(
                new Computer("Calculator",false, Brain.HARD),
                new Human("Kalkulixi",true)
        );
        game.newGame(3);
        game.showGUI();
        game.start();
    }

}
