package main.java.de.gieskerb.tictactoe.view;

import main.java.de.gieskerb.tictactoe.controller.MouseClick;
import main.java.de.gieskerb.tictactoe.model.Board;
import main.java.de.gieskerb.tictactoe.model.Updater;
import main.java.de.gieskerb.tictactoe.player.Player;

import javax.swing.*;
import java.awt.*;

public class GUI extends Visual {

    /**
     * GUI - Window: Storing and displaying all the necessary components for user interactions.
     */
    private JFrame window;

    /**
     * Array with all tiles of the board. A tile in this case is a button for a more easy check if player
     * clicked that tile. Need to store all the tiles in an array for later access when displaying the board
     * for example.
     */
    private JLabel[] tiles;

    /**
     * Creates a simple JFrame UI with a tic-tac-toe board being displayed. Number of rows and
     * columns are determent by the boardSize.
     * @param updater This implementation uses the MVC concept for updating each action. Therefore the Model
     *               (Updater) is required here.
     * @param pixelSize Literal visual size of the board.
     * @param boardSize number of row and column of the board.
     */


    public GUI (Updater updater, int pixelSize, byte boardSize) {

        super(updater, boardSize);

        final byte BOARD_SIZE_SQUARED = (byte) (super.boardSize * super.boardSize);
        // background is a container which holds all the tiles.
        JPanel background = new JPanel();
        background.setBackground(new Color (16,16,16));
        background.setSize(pixelSize, pixelSize);
        background.setPreferredSize(background.getSize());
        background.setLayout(new GridLayout(super.boardSize,super.boardSize, 3, 3));
        background.addMouseListener(new MouseClick(updater));

        // Setting up each tile form the board with some extra visual benefits like color and font.
        this.tiles = new JLabel[BOARD_SIZE_SQUARED];
        for(byte i = 0; i< BOARD_SIZE_SQUARED; i++) {
            this.tiles[i] = new JLabel();
            this.tiles[i].setFocusable(false);
            this.tiles[i].setOpaque(true);
            this.tiles[i].setHorizontalAlignment(JLabel.CENTER);
            this.tiles[i].setBackground(new Color (69,69,69));
            this.tiles[i].setForeground(new Color (255,127,15));
            this.tiles[i].setFont(new Font("Tahoma", Font.BOLD, 69));
            // this.tiles[i].addMouseListener(new MouseClick(super.updater, i));
            background.add(this.tiles[i]);
        }

        // Using a way better look and feel then the default "metal" design.
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Look and feel not set.");
        }

        // Finally initializing the JFrame and adding all the stuff to it.
        this.window = new JFrame();
        this.window.add(background);
        this.window.pack();
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setLocationRelativeTo(null);
        this.window.setVisible(true);

    }


    @Override
    public void update(Object... obj) {
        for(Object object: obj) {
            System.out.print((int) object + " ");
        }
        System.out.println();
        if (obj.length == 1) {
            for(var b: this.tiles) {
                b.setText("");
            }
        } else {
            this.tiles[(int)obj[0]].setText( (int)obj[1] == 1 ? "X": "O");
        }
    }
}
