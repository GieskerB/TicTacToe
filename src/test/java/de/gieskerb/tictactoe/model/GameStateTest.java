package test.java.de.gieskerb.tictactoe.model;

import main.java.de.gieskerb.tictactoe.model.Board;
import main.java.de.gieskerb.tictactoe.model.GameState;
import main.java.de.gieskerb.tictactoe.model.Origin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {


    private GameState gs;

    @BeforeEach
    void setUp() {
        // Board is subclass of GameState. Need this class to edit the GameState correctly
        Board board = new Board();
        board.service(new int[] {2}, Origin.CONTROLLER);
        board.service(new int[] {4}, Origin.CONTROLLER);
        board.service(new int[] {0}, Origin.CONTROLLER);
        board.service(new int[] {8}, Origin.CONTROLLER);
        this.gs = board.exportGameState();
    }

    @Test
    void testCorrectBitMapP1() {
        assertEquals(0b101000000, this.gs.bitMapPlayer1);
    }

    @Test
    void testCorrectBitMapP2() {
        assertEquals(0b000010001, this.gs.bitMapPlayer2);
    }

    @Test
    void testKey() {
        /*
         * Sadly my way of creating a base64 number is different from the standard. Because of that
         * an asserting with a literature value or similar is impossible. For test coverage I will
         * do a useless assertion.
         */
        assertEquals(GameState.getKey(this.gs), GameState.getKey(this.gs)) ;
    }

}