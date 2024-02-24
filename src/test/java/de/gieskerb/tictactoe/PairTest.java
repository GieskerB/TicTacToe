package test.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PairTest {

    private Pair<String,Integer> pair;
    @BeforeEach
    void setUp() {
        this.pair = new Pair<>("Test",1);
    }

    @Test
    void testHashCodeEquals() {
        Pair<String,Integer> compare = new Pair<>("Test",1);
        assertEquals(this.pair.hashCode(), compare.hashCode());
    }
    @Test
    void testHashCodeNotEquals() {
        Pair<String,Integer> compare = new Pair<>("Test",2);
        assertNotEquals(this.pair.hashCode(), compare.hashCode());
    }

    @Test
    void testEqualsNull() {
        assertFalse(this.pair.equals(null));
    }
    @Test
    void testEqualsWrongType() {
        assertFalse(this.pair.equals(new Pair<>(1,"Test")));
    }

    @Test
    void testEqualsFalse() {
        assertFalse(this.pair.equals(new Pair<>("test",1)));
    }
    @Test
    void testEqualsTrue() {
        assertTrue(this.pair.equals(new Pair<>("Test",1)));
    }

    @Test
    void testToString() {
        assertEquals("{First: Test Second: 1}",this.pair.toString());
    }
}