package main.java.de.gieskerb.tictactoe.visual;

import main.java.de.gieskerb.tictactoe.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GridPanel extends JPanel implements ActionListener {

    /**
     * This constant makes sure that each possible grid size will divide each tile equally.
     */
    static final int PRIME_FACTOR_PRODUCT = 2*2*2*3*5*7;

    private byte size;
    private JButton[] tiles;

    private Board board;

    private synchronized void createGrid() {
        this.board = new Board(this.size);
        super.removeAll();
        super.repaint();
        this.tiles = new JButton[this.size * this.size];
        super.setLayout(new GridLayout(this.size, this.size));
        for (int i = 0; i < this.tiles.length; i++) {
            this.tiles[i] = new JButton();
            this.tiles[i].addActionListener(this);
            super.add(this.tiles[i]);
        }
        super.revalidate();
        super.repaint();
    }

    GridPanel ()    {
        this.size = 3;
        super.setSize(GridPanel.PRIME_FACTOR_PRODUCT, GridPanel.PRIME_FACTOR_PRODUCT);
        super.setPreferredSize(super.getSize());

        this.createGrid();
    }

    void changeSize(int size) {
        this.size = (byte) size;
        createGrid();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < this.size * this.size; i++) {
            JButton tile = this.tiles[i];
            if (tile == e.getSource()) {
                this.board.makeMove(i);
                // Inverted due to automatic player change after move.
                tile.setText(this.board.getCurrentPlayer() ? "O" : "X");
                if(this.board.checkWinPlayerOne()) {
                    System.out.println("Player 1 wins");
                }
                if(this.board.checkWinPlayerTwo()) {
                    System.out.println("Player 2 wins");
                }
            }
        }
    }
}
