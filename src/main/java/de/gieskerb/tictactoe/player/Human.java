package main.java.de.gieskerb.tictactoe.player;

import main.java.de.gieskerb.tictactoe.model.Origin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Human extends Player {

    private static byte unnamedPlayerCount = 0;

    public Human(boolean isPlayerOne) {
        this("Player " + ++unnamedPlayerCount, isPlayerOne);
    }

    public Human(String name, boolean isPlayerOne) {
        super(name, isPlayerOne);
    }


    @Override
    public void mousePressed(MouseEvent e) {
        final JPanel source = ((JPanel)e.getSource());
        final int sourceWidth = source.getSize().width;
        final int rows = ((GridLayout)source.getLayout()).getRows();

        Thread run = new Thread(() -> {
            if (isMyTurn) {

                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

                // Switch around for right Indexing.
                gamePointer.service(Origin.PLAYER,  e.getY() / (sourceWidth / rows),e.getX() / (sourceWidth / rows));
            }
        });

        run.start();

    }
}
