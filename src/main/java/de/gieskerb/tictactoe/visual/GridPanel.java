package main.java.de.gieskerb.tictactoe.visual;

import main.java.de.gieskerb.tictactoe.GamePlayLoop;
import main.java.de.gieskerb.tictactoe.enums.Origin;
import main.java.de.gieskerb.tictactoe.enums.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GridPanel extends JPanel implements MouseListener {


    /**
     * This constant makes sure that each possible grid size will divide each tile equally.
     */
    final static int PRIME_FACTOR_PRODUCT = 2 * 2 * 2 * 3 * 5 * 7;
    private final static int[] PIXEL_BY_SIZE = new int[]{512,256,128,64,32,32,16};

    private byte size;
    private JLabel[] tiles;

    private final GamePlayLoop gamePlayLoop;

    private synchronized void createGrid() {
        super.removeAll();
        super.repaint();
//        Font displayFont = new Font("Tahoma", Font.BOLD, 512 / this.size);
        this.tiles = new JLabel[this.size * this.size];
        super.setLayout(new GridLayout(this.size, this.size));
        for (int i = 0; i < this.tiles.length; i++) {
            this.tiles[i] = new JLabel();
            this.tiles[i].setBackground(GameWindow.BACKGROUND_COLOR);
            this.tiles[i].setForeground(GameWindow.FOREGROUND_COLOR);
            this.tiles[i].setFocusable(false);
//            this.tiles[i].setFont(displayFont);
            this.tiles[i].setOpaque(true);
            this.tiles[i].addMouseListener(this);
            this.tiles[i].setHorizontalAlignment(JLabel.CENTER);
            this.tiles[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            super.add(this.tiles[i]);
        }
        super.revalidate();
        super.repaint();
    }

    GridPanel(GamePlayLoop gamePlayLoop) {
        this.size = 3;
        this.gamePlayLoop = gamePlayLoop;
        super.setSize(GridPanel.PRIME_FACTOR_PRODUCT, GridPanel.PRIME_FACTOR_PRODUCT);
        super.setPreferredSize(super.getSize());
        super.addMouseListener(this);

        this.restart();
    }

    public void restart() {
        this.createGrid();
    }

    public void makeMove(int index, Player player) {
        // Inverted due to automatic player change after move.
        // TODO: dynamic size
        // TODO: slightly larger X

        if (player == Player.ONE) {
            this.tiles[index].setIcon(new ImageIcon("ico/tic-tac-toe/X-" + PIXEL_BY_SIZE[this.size -2] + "px.png"));
        } else {
            this.tiles[index].setIcon(new ImageIcon("ico/tic-tac-toe/O-" + PIXEL_BY_SIZE[this.size -2] + "px.png"));
        }
    }

    public void changeSize(int size) {
        this.size = (byte) size;
        this.restart();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (int i = 0; i < this.size * this.size; i++) {
            if (e.getSource() == this.tiles[i]) {
                this.gamePlayLoop.receiveInput(i, Origin.KEYBOARD);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
