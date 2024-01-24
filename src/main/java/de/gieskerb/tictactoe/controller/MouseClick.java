package main.java.de.gieskerb.tictactoe.controller;

import main.java.de.gieskerb.tictactoe.model.Origin;
import main.java.de.gieskerb.tictactoe.model.Updater;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MouseClick extends InputDevice implements ActionListener {

    private Updater updater;
    private byte index;


    public MouseClick(Updater updater, byte index) {
        this.updater = updater;
        this.index = index;
    }

    @Override
    public void update(Object obj) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this) {
            this.updater.service(index, Origin.CONTROLLER);
        }
    }
}
