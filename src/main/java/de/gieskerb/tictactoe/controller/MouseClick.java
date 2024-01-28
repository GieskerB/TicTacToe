package main.java.de.gieskerb.tictactoe.controller;

import main.java.de.gieskerb.tictactoe.model.Origin;
import main.java.de.gieskerb.tictactoe.model.Updater;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseClick extends InputDevice implements MouseListener {

    private final Updater updater;


    public MouseClick(Updater updater) {
        this.updater = updater;
        this.updater.attach(this);
    }

    @Override
    public void update(Object... obj) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        final JPanel source = ((JPanel)e.getSource());
        final int sourceWidth = source.getSize().width;
        final int rows = ((GridLayout)source.getLayout()).getRows();

        // Switch around for right Indexing.
        this.updater.service(Origin.CONTROLLER,  e.getY() / (sourceWidth / rows),e.getX() / (sourceWidth / rows));

    }

    @Override
    public void mouseClicked(MouseEvent e) {    }


    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
