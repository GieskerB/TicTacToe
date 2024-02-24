package test.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {



    //TODO

    @Test
    void testLegalMoves() {
        Board board = new Board((Game) null);
        board.invokeMethod(new FriendTestAccess("makeMove",2));
        board.invokeMethod(new FriendTestAccess("afterMove"));
        board.invokeMethod(new FriendTestAccess("makeMove",4));
        board.invokeMethod(new FriendTestAccess("afterMove"));
        board.invokeMethod(new FriendTestAccess("makeMove",0));
        board.invokeMethod(new FriendTestAccess("afterMove"));
        board.invokeMethod(new FriendTestAccess("makeMove",8));
        board.invokeMethod(new FriendTestAccess("afterMove"));

        assertArrayEquals(new int[]{1,3,5,6,7},board.exportGameState().getLegalMoves());
    }

    /*
     * Since the is no  standard base81 number to compare to from the internet I'll just hope,
     * that each number is unique. :-)
     */

    @Test
    void testKeySize2() {
        GameState gs = new Board(2, null).exportGameState();
        assertEquals(GameState.getKey(gs), GameState.getKey(gs)) ;
    }

    @Test
    void testKeySize3() {
        GameState gs = new Board(3, null).exportGameState();
        assertEquals(GameState.getKey(gs), GameState.getKey(gs)) ;
    }

}