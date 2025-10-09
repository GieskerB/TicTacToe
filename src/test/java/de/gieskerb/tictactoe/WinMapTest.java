package test.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.board.WinningBitMapGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class WinMapTest {

    static long[] winningBitMaps;

    boolean contains(long[] array, long value) {
        for (long bit : array) {
            if (bit == value) {
                return true;
            }
        }
        return false;
    }

    /*
     * Test with default size
     */

    @BeforeAll
    static void setUp() {
        winningBitMaps = WinningBitMapGenerator.getBitMaps(3);
    }

    @Test
    void testNotNull() {
        assertNotNull(winningBitMaps);
    }

    @Test
    void testArraySize() {
        assertEquals(3 + 3 + 2, winningBitMaps.length);
    }

    @Test
    void testContains0() {
        assertTrue(contains(winningBitMaps,0b010010010));
    }

    @Test
    void testContains1() {
        assertTrue(contains(winningBitMaps,0b001001001));
    }

    @Test
    void testContains2() {
        assertTrue(contains(winningBitMaps,0b111000000));
    }

    @Test
    void testContains3() {
        assertTrue(contains(winningBitMaps,0b000111000));
    }

    @Test
    void testContains4() {
        assertTrue(contains(winningBitMaps,0b001010100));
    }

    @Test
    void testContains5() {
        assertTrue(contains(winningBitMaps,0b100010001));
    }

    @Test
    void testNoExtraCalculation() {
        assertEquals(winningBitMaps, WinningBitMapGenerator.getBitMaps(3));
    }

    /*
     * Test with other sizes
     */

    @Test
    void testBoardSize2Length() {
        assertEquals(2+2+2, WinningBitMapGenerator.getBitMaps(2).length);
    }

    @Test
    void testBoardSize4Length() {
        assertEquals(4+4+2, WinningBitMapGenerator.getBitMaps(4).length);
    }

    @Test
    void testBoardSize5Length() {
        assertEquals(5+5+2, WinningBitMapGenerator.getBitMaps(5).length);
    }

    @Test
    void testBoardSize6Length() {
        assertEquals(6+6+2, WinningBitMapGenerator.getBitMaps(6).length);
    }

    @Test
    void testBoardSize7Length() {
        assertEquals(7+7+2, WinningBitMapGenerator.getBitMaps(7).length);
    }

    @Test
    void testBoardSize8Length() {
        assertEquals(8+8+2, WinningBitMapGenerator.getBitMaps(8).length);
    }

    /*
     * Test other BitMap sizes
     */

    @Test
    void testBoardSize2BitMap() {
        assertTrue(contains(WinningBitMapGenerator.getBitMaps(2),0b1100));
    }
    @Test
    void testBoardSize4BitMap() {
        assertTrue(contains(WinningBitMapGenerator.getBitMaps(4),0b1111000000000000));
    }
    @Test
    void testBoardSize5BitMap() {
        assertTrue(contains(WinningBitMapGenerator.getBitMaps(5),0b1111100000000000000000000));
    }
    @Test
    void testBoardSize6BitMap() {
        assertTrue(contains(WinningBitMapGenerator.getBitMaps(6),0b111111000000000000000000000000000000L));
    }
    @Test
    void testBoardSize7BitMap() {
        assertTrue(contains(WinningBitMapGenerator.getBitMaps(7),0b1111111000000000000000000000000000000000000000000L));
    }
    @Test
    void testBoardSize8BitMap() {
        assertTrue(contains(WinningBitMapGenerator.getBitMaps(8),0b1111111100000000000000000000000000000000000000000000000000000000L));
    }

}
