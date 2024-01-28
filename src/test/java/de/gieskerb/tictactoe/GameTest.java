package test.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }


}