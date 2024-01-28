package main.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.model.Board;
import main.java.de.gieskerb.tictactoe.model.DebugPrinter;
import main.java.de.gieskerb.tictactoe.model.Game;
import main.java.de.gieskerb.tictactoe.model.GameState;
import main.java.de.gieskerb.tictactoe.player.Computer;
import main.java.de.gieskerb.tictactoe.player.Human;

public class Main {
    public static void main (String[] args)  {
        Game game = new Game(new Human("Kalkulixi",true), new Computer("Calculator",false));
        game.newGame();
        game.showGUI();
    }

}
