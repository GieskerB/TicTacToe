package test.java.de.gieskerb.tictactoe.model;

import main.java.de.gieskerb.tictactoe.model.Board;
import main.java.de.gieskerb.tictactoe.model.DebugPrinter;
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
        Board board = new Board(2);
        board.makeMove(1);
        board.makeMove(0);
        board.makeMove(2);
        dp = new DebugPrinter(board);
    }

    @Test
    void testPrintPatters() {
        dp.printPatterns();
        String rowPatterns = "00\n11\n\n11\n00\n\n";
        String colPatterns = "01\n01\n\n10\n10\n\n";
        String digPatterns = "10\n01\n\n01\n10\n\n";

        assertEquals(rowPatterns + colPatterns + digPatterns, out.toString());
    }

    @Test
    void testPrintBitMapP1() {
        dp.printBitMapP1();
        assertEquals("01\n10\n\n", out.toString());
    }

    @Test
    void testPrintBitMapP2() {
        dp.printBitMapP2();
        assertEquals("10\n00\n\n", out.toString());
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