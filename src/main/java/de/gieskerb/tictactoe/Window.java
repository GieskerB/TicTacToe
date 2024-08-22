package main.java.de.gieskerb.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame implements ActionListener {

    private Board board;
    private final int SIZE = 3;

    private JButton[] tiles;

    Window() {
        super();
        super.setTitle("Tic-Tac-Toe");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setSize(1000, 1000);
        super.setPreferredSize(super.getSize());
        super.setLayout(new GridLayout(SIZE, SIZE));

        this.board = new Board(SIZE);
        this.tiles = new JButton[SIZE * SIZE];

        for (int i = 0; i < SIZE * SIZE; i++) {
            this.tiles[i] = new JButton();
            this.tiles[i].addActionListener(this);
            super.add(this.tiles[i]);
        }

        super.pack();
        super.setLocationRelativeTo(null);
        super.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < SIZE * SIZE; i++) {
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
