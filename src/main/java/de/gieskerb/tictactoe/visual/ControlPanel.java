package main.java.de.gieskerb.tictactoe.visual;

import main.java.de.gieskerb.tictactoe.GamePlayLoop;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel implements ActionListener, ChangeListener {

    private final JLabel gameNameLabel, playerOneNameLabel, playerTwoNameLabel, playerOneScoreLabel, playerTwoScoreLabel;
    private final JCheckBox computerOneCheckBox, computerTwoCheckBox;
    private final JSlider gridSizeSlider;
    private final JRadioButton[] difficultyOneGroup, difficultyTwoGroup;


    private final GridPanel gridPanel;
    private final GamePlayLoop gamePlayLoop;

    private void setUpLabel(JLabel label, int x, int y, int width, int height) {
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setLocation(x, y);
        label.setSize(width, height);
        label.setBackground(GameWindow.BACKGROUND_COLOR);
        label.setForeground(GameWindow.FOREGROUND_COLOR);
        label.setOpaque(true);
        System.out.println("HI");
    }

    private void setUpCheckBock(JCheckBox checkBox, int x, int y, int width, int height) {
        checkBox.setLocation(x, y);
        checkBox.setSize(width, height);
        checkBox.setHorizontalAlignment(SwingConstants.CENTER);
        checkBox.setBackground(GameWindow.BACKGROUND_COLOR);
        checkBox.setForeground(GameWindow.FOREGROUND_COLOR);
        checkBox.setOpaque(true);
    }

    private void setUpSlider(JSlider slider, int x, int y, int width, int height, int majorTickSpacing) {
        slider.setMajorTickSpacing(majorTickSpacing);
        slider.setMinorTickSpacing(1);
        slider.setLocation(x, y);
        slider.setSize(width, height);
        slider.setBackground(GameWindow.BACKGROUND_COLOR);
        slider.setForeground(GameWindow.FOREGROUND_COLOR);
        slider.setOpaque(true);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(this);

    }

    private void setUpRadioButtons(JRadioButton[] radioButtons, int x, int y, int width, int height) {
        ButtonGroup group = new ButtonGroup();
        final String[] difficultyNames = {"Easy", "Medium", "Hard"};
        for (int i = 0; i < radioButtons.length; i++) {
            radioButtons[i] = new JRadioButton(difficultyNames[i]);
            radioButtons[i].setHorizontalAlignment(SwingConstants.CENTER);
            radioButtons[i].setLocation((int) Math.ceil(x + (width / 3.0) * i), y);
            radioButtons[i].setSize((int) Math.ceil(width / 3.0), height);
            radioButtons[i].setBackground(GameWindow.BACKGROUND_COLOR);
            radioButtons[i].setForeground(GameWindow.FOREGROUND_COLOR);
            radioButtons[i].setOpaque(true);
            group.add(radioButtons[i]);
        }
    }

    ControlPanel(GridPanel gridPanel, GamePlayLoop gamePlayLoop) {
        super();
        super.setLayout(null);
        super.setSize(GridPanel.PRIME_FACTOR_PRODUCT, GridPanel.PRIME_FACTOR_PRODUCT / 5);
        super.setPreferredSize(super.getSize());

        this.gridPanel = gridPanel;
        this.gamePlayLoop = gamePlayLoop;

        this.gameNameLabel = new JLabel("TicTacToe");
        setUpLabel(this.gameNameLabel, super.getWidth() / 3, 0, super.getWidth() / 3, super.getHeight() / 2);

        this.playerOneNameLabel = new JLabel("Player One");
        setUpLabel(playerOneNameLabel, 0, 0, super.getWidth() / 4, super.getHeight() / 2);

        this.playerTwoNameLabel = new JLabel("Player Two");
        setUpLabel(playerTwoNameLabel, super.getWidth() - super.getWidth() / 4, 0, super.getWidth() / 4, super.getHeight() / 2);

        this.playerOneScoreLabel = new JLabel("0/0/0");
        setUpLabel(playerOneScoreLabel, 0, super.getHeight() / 2, super.getWidth() / 3, super.getHeight() / 2);

        this.playerTwoScoreLabel = new JLabel("0/0/0");
        setUpLabel(playerTwoScoreLabel, 2 * super.getWidth() / 3, super.getHeight() / 2, super.getWidth() / 3
                , super.getHeight() / 2);


        this.computerOneCheckBox = new JCheckBox("Computer");
        setUpCheckBock(computerOneCheckBox, super.getWidth() / 4, 0, super.getWidth() / 3 - super.getWidth() / 4, super.getHeight() / 2);

        this.computerTwoCheckBox = new JCheckBox("Computer");
        setUpCheckBock(computerTwoCheckBox, 2 * super.getWidth() / 3, 0, super.getWidth() / 3 - super.getWidth() / 4, super.getHeight() / 2);


        this.gridSizeSlider = new JSlider(2, 8);
        setUpSlider(this.gridSizeSlider, super.getWidth() / 3, super.getHeight() / 2, super.getWidth() / 3, super.getHeight() / 2, 3);
        this.gridSizeSlider.setValue(3);


        this.difficultyOneGroup = new JRadioButton[3];
        setUpRadioButtons(this.difficultyOneGroup, 0, super.getHeight() / 2, super.getWidth() / 3, super.getHeight() / 2);

        this.difficultyTwoGroup = new JRadioButton[3];
        setUpRadioButtons(this.difficultyTwoGroup, 2 * super.getWidth() / 3, super.getHeight() / 2, super.getWidth() / 3, super.getHeight() / 2);

        super.add(this.gameNameLabel);
        super.add(this.playerOneNameLabel);
        super.add(this.playerTwoNameLabel);
        super.add(this.playerOneScoreLabel);
        super.add(this.playerTwoScoreLabel);

        super.add(this.computerOneCheckBox);
        super.add(this.computerTwoCheckBox);

        super.add(this.gridSizeSlider);

        for (int i = 0; i < 3; i++) {
            super.add(this.difficultyOneGroup[i]);
            super.add(this.difficultyTwoGroup[i]);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void stateChanged(ChangeEvent e) {

    }
}
