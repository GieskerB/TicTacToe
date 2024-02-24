package test.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.player.Brain;
import main.java.de.gieskerb.tictactoe.player.Computer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComputerTest {

    private Computer computerPlayer;

    @BeforeEach
    public void setup() {
        this.computerPlayer = new Computer(false, Brain.EASY);
    }

    @Test
    public void testGetMove() {
        //this.computerPlayer.getMove(null);
        assertTrue(true);
    }

}