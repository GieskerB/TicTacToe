package main.java.de.gieskerb.tictactoe.controller;

import main.java.de.gieskerb.tictactoe.model.Origin;
import main.java.de.gieskerb.tictactoe.model.Updater;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseClick extends InputDevice implements MouseListener {

    private Updater updater;
    private byte index;


    public MouseClick(Updater updater, byte index) {
        this.updater = updater;
        this.updater.attach(this);
        this.index = index;
    }

    @Override
    public void update(Object obj) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            this.updater.service(this.index, Origin.CONTROLLER);
        }}

    @Override
    public void mouseClicked(MouseEvent e) {    }


    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
