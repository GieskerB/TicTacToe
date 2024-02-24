package test.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.model.Origin;
import main.java.de.gieskerb.tictactoe.model.Updatable;
import main.java.de.gieskerb.tictactoe.model.Updater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdaterTest {

    private Updater updater;
    private Updatable updatableA, updatableB;

    private int editableCounter;

    @BeforeEach
    void setUP() {
        this.updater = new Updater() {

            @Override
            protected void serviced(Origin origin, int... args) {
                editableCounter += 100;
            }
        };

        this.updatableA = obj -> editableCounter+=1;
        this.updatableB = obj -> editableCounter+=2;

        this.editableCounter = 0;
    }

    @Test
    void testIsEmpty() {
        assertEquals(0, this.updater.getNumberAttached());
    }

    @Test
    void testAttach() {
        this.updater.attach(this.updatableA);
        this.updater.attach(this.updatableB);
        assertEquals(2,this.updater.getNumberAttached());
    }

    @Test
    void testAttachTwice() {
        this.updater.attach(this.updatableA);
        this.updater.attach(this.updatableA);
        assertEquals(1, this.updater.getNumberAttached());
    }

    @Test
    void testAttachNull() {
        this.updater.attach(null);
        assertEquals(0, this.updater.getNumberAttached());
    }

    @Test
    void testDetach() {
        this.updater.attach(this.updatableA);
        this.updater.attach(this.updatableB);
        this.updater.detach(this.updatableA);
        assertEquals(1,this.updater.getNumberAttached());
    }
    @Test
    void testDetachTwiceSize() {
        this.updater.attach(this.updatableA);
        this.updater.detach(this.updatableA);
        this.updater.detach(this.updatableA);
        assertEquals(0, this.updater.getNumberAttached());
    }

    @Test
    void testDetachTwiceThrows() {
        this.updater.attach(this.updatableA);
        this.updater.detach(this.updatableA);
        this.updater.detach(this.updatableA);
        assertDoesNotThrow(()-> this.updater.getNumberAttached());
    }

    @Test
    void testDetachNull() {
        assertDoesNotThrow(() -> this.updater.detach(null));
    }

    @Test
    void testFireUpdate() {
        this.updater.attach(this.updatableA);
        this.updater.attach(this.updatableB);
        this.updater.fireUpdate((Object) null);
        assertEquals(3,this.editableCounter);
    }


    @Test
    void testService() throws InterruptedException {
        this.updater.service(null, 0);
        Thread.sleep(10);
        assertEquals(100,this.editableCounter);
    }

}