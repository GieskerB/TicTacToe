package test.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.Board;
import main.java.de.gieskerb.tictactoe.ComputerPlayer;
import main.java.de.gieskerb.tictactoe.Buffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BufferTest {

    Buffer buffer;
    Board board;

    @BeforeEach
    void setUp() {
        buffer = new Buffer(16);
    }

    void randomBoard() {
        board = new Board(3);
        board.makeMove(ComputerPlayer.easyDifficulty(board)); // P1 - 1
        board.makeMove(ComputerPlayer.easyDifficulty(board)); // P2 - 2
        board.makeMove(ComputerPlayer.easyDifficulty(board)); // P1 - 3
        board.makeMove(ComputerPlayer.easyDifficulty(board)); // P2 - 4
        board.makeMove(ComputerPlayer.easyDifficulty(board)); // P1 - 5
        if(!board.checkGameOver()) board.makeMove(ComputerPlayer.easyDifficulty(board)); // P2 - 6
        if(!board.checkGameOver()) board.makeMove(ComputerPlayer.easyDifficulty(board)); // P1 - 7
        if(!board.checkGameOver()) board.makeMove(ComputerPlayer.easyDifficulty(board)); // P1 - 8
        if(!board.checkGameOver()) board.makeMove(ComputerPlayer.easyDifficulty(board)); // P1 - 9
    }

    @Test
    void testHashTable() {
        randomBoard();
    }
}
