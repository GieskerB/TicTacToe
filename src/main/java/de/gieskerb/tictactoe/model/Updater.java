package main.java.de.gieskerb.tictactoe.model;


import java.util.ArrayList;
import java.util.List;

public abstract class Updater {

    private final List<Updatable> updatableList;

    Updater() {
        this.updatableList = new ArrayList<Updatable>();
    }

    public final void attach(Updatable updatable) {
        if(updatable == null) {
            return;
        }
        if(!this.updatableList.contains(updatable)) {
            this.updatableList.add(updatable);
        }
    }

    public final void detach(Updatable updatable) {
        if(updatable == null) {
            return;
        }
        this.updatableList.remove(updatable);
    }

    final void fireUpdate(Object obj) {
        for(var notifyable: this.updatableList) {
            notifyable.update(obj);
        }
    }

    public abstract void service();


}
