package test.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.model.Board;
import main.java.de.gieskerb.tictactoe.model.DebugPrinter;
import main.java.de.gieskerb.tictactoe.model.Origin;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class DebugPrinterTest {
    private DebugPrinter dp;

    private final static ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final static PrintStream originalOut = System.out;

    @BeforeAll
    static void setUpAll() {
        System.setOut(new PrintStream(out));
    }

    @BeforeEach
    void setUp() {
        Board board = new Board(2, null);
        board.invokeMethod(new FriendTestAccess("makeMove", 1));
        board.invokeMethod(new FriendTestAccess("afterMove"));
        board.invokeMethod(new FriendTestAccess("makeMove", 0));
        board.invokeMethod(new FriendTestAccess("afterMove"));
        board.invokeMethod(new FriendTestAccess("makeMove", 2));
        dp = new DebugPrinter(board);
    }

    @Test
    void testPrintBoard() {
        String expectedResult = "" + DebugPrinter.PLAYER2 + DebugPrinter.PLAYER1 + "\r\n" + DebugPrinter.PLAYER1 + DebugPrinter.EMPTY +  "\r\n" ;
       //String expectedResult = "OX\nX_\n";
        dp.printBoard();

        assertEquals(expectedResult, out.toString());
    }

    @AfterEach
    void tearDown() {
        out.reset();
    }

    @AfterAll
    static void tearDownAll() {
        System.setOut(originalOut);
    }
}