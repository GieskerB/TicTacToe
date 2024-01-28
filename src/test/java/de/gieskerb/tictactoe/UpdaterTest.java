package test.java.de.gieskerb.tictactoe;

import main.java.de.gieskerb.tictactoe.model.Origin;
import main.java.de.gieskerb.tictactoe.model.Updatable;
import main.java.de.gieskerb.tictactoe.model.Updater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdaterTest {

    private Updater updater;
    private Updatable[] updatables;
    private final static int NUM_UPDATEBALES = 5;

    private int editableCounter = 0;

    @BeforeEach
    void setUP() {
        this.updater = new Updater() {
            @Override
            public void service(Origin origin, int... args) {
                editableCounter += 100;
            }
        };

        this.updatables = new Updatable[NUM_UPDATEBALES];
        for (int i = 0; i < NUM_UPDATEBALES; i++) {
            this.updatables[i] = new Updatable() {
                @Override
                public void update(Object... obj) {
                    editableCounter += 1;
                }
            };
        }
    }

    private void fillUpdater() {
        this.updater.attach(this.updatables[0]);
        this.updater.attach(this.updatables[1]);
        this.updater.attach(this.updatables[2]);
        this.updater.attach(this.updatables[3]);
        this.updater.attach(this.updatables[4]);
    }

    @Test
    void testAttach() {
        this.fillUpdater();
        assertDoesNotThrow(() -> this.updater.attach(this.updatables[0]));
        assertDoesNotThrow(() -> this.updater.attach(null));
    }

    @Test
    void testDetach() {
        this.fillUpdater();
        this.updater.detach(this.updatables[0]);
        assertDoesNotThrow(() -> this.updater.detach(this.updatables[0]));
        assertDoesNotThrow(() -> this.updater.detach(null));
    }

    @Test
    void testFireUpdateNormal() {
        fillUpdater();
        this.updater.fireUpdate(null);
        assertEquals(this.editableCounter, 5);
    }

    @Test
    void testFireUpdateOverfilled() {
        fillUpdater();
        fillUpdater();
        this.updater.fireUpdate(null);
        assertEquals(this.editableCounter, 5);
    }

    @Test
    void testFireUpdateFew() {
        this.updater.attach(this.updatables[1]);
        this.updater.attach(this.updatables[2]);
        this.updater.fireUpdate(null);
        assertEquals(this.editableCounter, 2);
    }

    @Test
    void testService() {
        this.updater.service(Origin.CONTROLLER, 0);
        assertEquals(this.editableCounter, 100);
    }

}