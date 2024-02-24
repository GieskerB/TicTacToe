package main.java.de.gieskerb.tictactoe.model;


import main.java.de.gieskerb.tictactoe.util.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class Updater {

    private final List<Updatable> updatableList;

    protected Updater() {
        this.updatableList = new ArrayList<>();
    }

    public final void attach(Updatable updatable) {
        if (updatable == null) {
            return;
        }
        if (!this.updatableList.contains(updatable)) {
            this.updatableList.add(updatable);
        }
    }

    public final void detach(Updatable updatable) {
        if (updatable == null) {
            return;
        }
        this.updatableList.remove(updatable);
    }

    public final void fireUpdate(Object... obj) {
        for (var updatable : this.updatableList) {
            updatable.update(obj);
        }
    }

    public final int getNumberAttached() {
        return this.updatableList.size();
    }

    protected abstract void serviced(Origin origin, int... args);

    public final void service(Origin origin, int... args) {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(5);
                serviced(origin, args);
            } catch (InterruptedException ignored) {
            }
        });

        thread.start();
    }


}
