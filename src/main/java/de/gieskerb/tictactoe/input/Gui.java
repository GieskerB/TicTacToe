package main.java.de.gieskerb.tictactoe.input;

import main.java.de.gieskerb.tictactoe.board.BoardManager;
import main.java.de.gieskerb.tictactoe.player.Computer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class Gui {

    private final static int WINDOW_SCALE = 4;
    private final static int SETTINGS_HEIGHT = 25;
    private final static int PRIME_FACTOR_PRODUCT = 2 * 3 * 5 * 7;

    private final static String[] MODES = new String[]{"Human", "Easy", "Medium", "Hard"};
    private final static BoardManager.GameMode[] GAME_MODES = new BoardManager.GameMode[]
            {               /*P2:0 ------------------- P2:1 --------------------- P2:2 --------------------- P2:3*/
                    /*P1:0*/BoardManager.GameMode.PVP, BoardManager.GameMode.PVC, BoardManager.GameMode.PVC, BoardManager.GameMode.PVC,
                    /*P1:1*/BoardManager.GameMode.CVP, BoardManager.GameMode.CVC, BoardManager.GameMode.CVC, BoardManager.GameMode.CVC,
                    /*P1:2*/BoardManager.GameMode.CVP, BoardManager.GameMode.CVC, BoardManager.GameMode.CVC, BoardManager.GameMode.CVC,
                    /*P1:3*/BoardManager.GameMode.CVP, BoardManager.GameMode.CVC, BoardManager.GameMode.CVC, BoardManager.GameMode.CVC
            };

    private final static Computer.Difficulty[] DIFFICULTIES = new Computer.Difficulty[]{null, Computer.Difficulty.EASY, Computer.Difficulty.NORMAL, Computer.Difficulty.HARD};

    private final JFrame window;
    private final JPanel settingsPanel, gridPanel;
    private final JPanel player1Background, player2Background;

    private final JLabel[] tiles;
    private final ButtonGroup groupPlayer1, groupPlayer2;
    private final JRadioButton[] modeSelectorPlayer1, modeSelectorPlayer2;
    private final JSlider gridSizeSlider;

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO add return if action not from Button
            int index = 0;
            for (int i = 0; i < 4; ++i) {
                if (groupPlayer1.getSelection() == modeSelectorPlayer1[i].getModel()) {
                    index = i;
                    index <<= 2;
                    break;
                }
            }
            for (int i = 0; i < 4; ++i) {
                if (groupPlayer2.getSelection() == modeSelectorPlayer2[i].getModel()) {
                    index += i;
                    break;
                }
            }
            System.out.println(GAME_MODES[index]);
            System.out.println(DIFFICULTIES[(index & 0b1100) >> 2]);
            System.out.println(DIFFICULTIES[index & 0b11]);
            System.out.println();
        }
    };

    private final ChangeListener changeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            if (e.getSource() == gridSizeSlider) {
                System.out.println(gridSizeSlider.getValue());
            }
        }
    };

    private final MouseListener mouseListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            for (JLabel tile : tiles) {
                if (tile == e.getSource()) {
                    System.out.println(tile.getText());
                }
            }
        }
    };

    private void setUpModeSelector(JPanel background, JRadioButton[] radio, ButtonGroup group) {
        background.setLayout(new GridLayout(1, 4));
        for (int i = 0; i < 4; ++i) {
            radio[i] = new JRadioButton();
            radio[i].setHorizontalAlignment(JRadioButton.CENTER);
            radio[i].addActionListener(actionListener);
            JLabel textLabel = new JLabel(MODES[i]);
            textLabel.setHorizontalAlignment(JRadioButton.CENTER);
            JPanel subPanel = new JPanel();
            subPanel.setLayout(new GridLayout(2, 1));
            subPanel.add(radio[i]);
            subPanel.add(textLabel);
            group.add(radio[i]);
            background.add(subPanel);
        }
        radio[0].setSelected(true);
    }

    private void setUpMiscellaneous(JLabel[] tiles, JPanel background1, JRadioButton[] radio1, ButtonGroup group1, JPanel background2, JRadioButton[] radio2, ButtonGroup group2, JSlider slider) {
        for (int i = 0; i < 64; ++i) {
            tiles[i] = new JLabel("" + i);
            tiles[i].setHorizontalAlignment(JLabel.CENTER);
            tiles[i].addMouseListener(mouseListener);
        }

        this.setUpModeSelector(background1, radio1, group1);
        this.setUpModeSelector(background2, radio2, group2);

        slider.setMinimum(2);
        slider.setMaximum(8);
        slider.setValue(3);
        slider.setSnapToTicks(true);
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(3);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(changeListener);

    }

    private void setUpSettingsPanel(JPanel panel) {
        panel.setSize(PRIME_FACTOR_PRODUCT * WINDOW_SCALE, SETTINGS_HEIGHT * WINDOW_SCALE);
        panel.setPreferredSize(panel.getSize());
        panel.setMinimumSize(panel.getSize());
        panel.setMaximumSize(panel.getSize());
        panel.setLocation(0, 0);
        panel.setLayout(new GridLayout(2, 3));
        JLabel player1 = new JLabel("Player 1");
        player1.setHorizontalAlignment(JLabel.CENTER);
        JLabel slider = new JLabel("Grid Size");
        slider.setHorizontalAlignment(JLabel.CENTER);
        JLabel player2 = new JLabel("Player 2");
        player2.setHorizontalAlignment(JLabel.CENTER);
        panel.add(player1);
        panel.add(slider);
        panel.add(player2);
        panel.add(this.player1Background);
        panel.add(this.gridSizeSlider);
        panel.add(this.player2Background);
    }

    private void setUpGridPanel(JPanel panel) {
        panel.setSize(PRIME_FACTOR_PRODUCT * WINDOW_SCALE, PRIME_FACTOR_PRODUCT * WINDOW_SCALE);
        panel.setPreferredSize(panel.getSize());
        panel.setMinimumSize(panel.getSize());
        panel.setMaximumSize(panel.getSize());
        panel.setLocation(0, SETTINGS_HEIGHT * WINDOW_SCALE);
        panel.setLayout(new GridLayout(3, 3)); // Subject to change
        for (int i = 0; i < ((GridLayout) panel.getLayout()).getRows() * ((GridLayout) panel.getLayout()).getColumns(); ++i) {
            panel.add(tiles[i]);
        }
    }

    public Gui() {
        this.window = new JFrame();
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setLayout(new BorderLayout());

        this.tiles = new JLabel[64];
        this.player1Background = new JPanel();
        this.modeSelectorPlayer1 = new JRadioButton[4];
        this.groupPlayer1 = new ButtonGroup();
        this.player2Background = new JPanel();
        this.modeSelectorPlayer2 = new JRadioButton[4];
        this.groupPlayer2 = new ButtonGroup();
        this.gridSizeSlider = new JSlider();
        this.setUpMiscellaneous(this.tiles, this.player1Background, this.modeSelectorPlayer1, this.groupPlayer1, this.player2Background, this.modeSelectorPlayer2, this.groupPlayer2, this.gridSizeSlider);

        this.settingsPanel = new JPanel();
        this.setUpSettingsPanel(this.settingsPanel);
        this.window.add(this.settingsPanel, BorderLayout.NORTH);

        this.gridPanel = new JPanel();
        this.setUpGridPanel(this.gridPanel);
        this.window.add(this.gridPanel, BorderLayout.CENTER);

        this.window.pack();
        this.window.setLocationRelativeTo(null);
        this.window.setVisible(true);
    }

    public static void main(String[] args) {
        new Gui();
    }

}
