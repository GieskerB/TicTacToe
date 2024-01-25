package main.java.de.gieskerb.tictactoe.view;

import main.java.de.gieskerb.tictactoe.model.Updatable;
import main.java.de.gieskerb.tictactoe.model.Updater;

public abstract class Visual implements Updatable {

    Updater updater;

    byte boardSize;
    public Visual(Updater updater, byte boardSize) {
        this.updater = updater;
        this.updater.attach(this);

        this.boardSize = boardSize;
    }
}
