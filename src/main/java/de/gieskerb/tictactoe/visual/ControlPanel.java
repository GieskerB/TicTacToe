package main.java.de.gieskerb.tictactoe.visual;

import main.java.de.gieskerb.tictactoe.GamePlayLoop;
import main.java.de.gieskerb.tictactoe.enums.Difficulty;
import main.java.de.gieskerb.tictactoe.enums.GameMode;
import main.java.de.gieskerb.tictactoe.enums.Player;

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

    private int previousSliderValue, previousGameModeIndex;

    private final GamePlayLoop gamePlayLoop;

    private void setUpLabel(JLabel label, int x, int y, int width, int height, int fontSize) {
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setLocation(x, y);
        label.setSize(width, height);
        label.setBackground(GameWindow.BACKGROUND_COLOR);
        label.setForeground(GameWindow.FOREGROUND_COLOR);
        label.setOpaque(true);
        label.setFont(new Font("Cooper Black", fontSize > 40 ? Font.BOLD : Font.PLAIN, fontSize));
    }

    private void setUpCheckBock(MyCheckBox checkBox, int x, int y, int width, int height) {
        checkBox.setLocation(x, y);
        checkBox.setSize(width, height);
        checkBox.checkBox.setHorizontalAlignment(SwingConstants.CENTER);
        checkBox.label.setHorizontalAlignment(SwingConstants.CENTER);
        checkBox.checkBox.setBackground(GameWindow.BACKGROUND_COLOR);
        checkBox.label.setBackground(GameWindow.BACKGROUND_COLOR);
        checkBox.checkBox.setForeground(GameWindow.FOREGROUND_COLOR);
        checkBox.label.setForeground(GameWindow.FOREGROUND_COLOR);
        checkBox.checkBox.setOpaque(true);
        checkBox.label.setOpaque(true);
        checkBox.label.setFont(new Font("Cooper Black", Font.PLAIN, 10));
        checkBox.checkBox.addChangeListener(this);
        checkBox.checkBox.setSelectedIcon(new ImageIcon("ico/toggle-button/on-64px.png"));
        checkBox.checkBox.setIcon(new ImageIcon("ico/toggle-button/off-64px.png"));
    }

    private void setUpSlider(JSlider slider, int x, int y, int width, int height) {
        slider.setMajorTickSpacing(3);
        slider.setMinorTickSpacing(1);
        slider.setLocation(x, y);
        slider.setSize(width, height);
        slider.setBackground(GameWindow.BACKGROUND_COLOR);
        slider.setForeground(GameWindow.FOREGROUND_COLOR);
        slider.setFont(new Font("Cooper Black", Font.PLAIN, 16));
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
            if (i == 0) {
                radioButtons[i].setSelected(true);
            }
            radioButtons[i].setHorizontalAlignment(SwingConstants.CENTER);
            radioButtons[i].setLocation((int) Math.ceil(x + (width / 3.0) * i), y);
            radioButtons[i].setSize((int) Math.ceil(width / 3.0), height);
            radioButtons[i].setBackground(GameWindow.BACKGROUND_COLOR);
            radioButtons[i].setForeground(GameWindow.FOREGROUND_COLOR);
            radioButtons[i].setOpaque(true);
            radioButtons[i].setVisible(false);
            radioButtons[i].addActionListener(this);
            group.add(radioButtons[i]);
        }
    }

    private int getSelected(JRadioButton[] radioButtons) {
        for (int i = 0; i < radioButtons.length; i++) {
            if (radioButtons[i].isSelected()) {
                return i;
            }
        }
        return 0;
    }

    ControlPanel(GamePlayLoop gamePlayLoop) {
        super();
        super.setLayout(null);
        super.setSize(GridPanel.PRIME_FACTOR_PRODUCT, GridPanel.PRIME_FACTOR_PRODUCT / 5);
        super.setPreferredSize(super.getSize());

        this.gamePlayLoop = gamePlayLoop;

        this.gameNameLabel = new JLabel("TicTacToe");
        setUpLabel(this.gameNameLabel, super.getWidth() / 3, 0, super.getWidth() / 3, super.getHeight() / 2, 50);

        this.playerOneNameLabel = new JLabel("Player One");
        setUpLabel(playerOneNameLabel, 0, 0, super.getWidth() / 4, super.getHeight() / 2, 32);

        this.playerTwoNameLabel = new JLabel("Player Two");
        setUpLabel(playerTwoNameLabel, super.getWidth() - super.getWidth() / 4, 0, super.getWidth() / 4, super.getHeight() / 2, 32);

        this.playerOneScoreLabel = new JLabel("0/0/0");
        setUpLabel(playerOneScoreLabel, 0, super.getHeight() / 2, super.getWidth() / 3, super.getHeight() / 2, 24);

        this.playerTwoScoreLabel = new JLabel("0/0/0");
        setUpLabel(playerTwoScoreLabel, 2 * super.getWidth() / 3, super.getHeight() / 2, super.getWidth() / 3, super.getHeight() / 2, 24);

        this.computerOneCheckBox = new JCheckBox();
        MyCheckBox myCheckBoxOne = new MyCheckBox("Computer", this.computerOneCheckBox);
        setUpCheckBock(myCheckBoxOne, super.getWidth() / 4, 0, super.getWidth() / 3 - super.getWidth() / 4, super.getHeight() / 2);


        this.computerTwoCheckBox = new JCheckBox();
        MyCheckBox myCheckBoxTwo = new MyCheckBox("Computer", this.computerTwoCheckBox);
        setUpCheckBock(myCheckBoxTwo, 2 * super.getWidth() / 3, 0, super.getWidth() / 3 - super.getWidth() / 4, super.getHeight() / 2);


        this.gridSizeSlider = new JSlider(2, 8);
        this.previousSliderValue = 3;
        setUpSlider(this.gridSizeSlider, super.getWidth() / 3, super.getHeight() / 2, super.getWidth() / 3, super.getHeight() / 2);
        this.gridSizeSlider.setValue(this.previousSliderValue);


        this.difficultyOneGroup = new JRadioButton[3];
        setUpRadioButtons(this.difficultyOneGroup, 0, super.getHeight() / 2, super.getWidth() / 3, super.getHeight() / 2);

        this.difficultyTwoGroup = new JRadioButton[3];
        setUpRadioButtons(this.difficultyTwoGroup, 2 * super.getWidth() / 3, super.getHeight() / 2, super.getWidth() / 3, super.getHeight() / 2);

        super.add(this.gameNameLabel);
        super.add(this.playerOneNameLabel);
        super.add(this.playerTwoNameLabel);
        super.add(this.playerOneScoreLabel);
        super.add(this.playerTwoScoreLabel);

        super.add(myCheckBoxOne);
        super.add(myCheckBoxTwo);

        super.add(this.gridSizeSlider);

        for (int i = 0; i < 3; i++) {
            super.add(this.difficultyOneGroup[i]);
            super.add(this.difficultyTwoGroup[i]);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == difficultyOneGroup[0]) {
            this.gamePlayLoop.changeDifficulty(Difficulty.EASY, Player.ONE);
        } else if (e.getSource() == difficultyOneGroup[1]) {
            this.gamePlayLoop.changeDifficulty(Difficulty.MEDIUM, Player.ONE);
        } else if (e.getSource() == difficultyOneGroup[2]) {
            this.gamePlayLoop.changeDifficulty(Difficulty.HARD, Player.ONE);
        } else if (e.getSource() == difficultyTwoGroup[0]) {
            this.gamePlayLoop.changeDifficulty(Difficulty.EASY, Player.TWO);
        } else if (e.getSource() == difficultyTwoGroup[1]) {
            this.gamePlayLoop.changeDifficulty(Difficulty.MEDIUM, Player.TWO);
        } else if (e.getSource() == difficultyTwoGroup[2]) {
            this.gamePlayLoop.changeDifficulty(Difficulty.HARD, Player.TWO);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == this.computerOneCheckBox) {
            if (this.computerOneCheckBox.isSelected()) {
                this.playerOneScoreLabel.setVisible(false);
                this.difficultyOneGroup[0].setVisible(true);
                this.difficultyOneGroup[1].setVisible(true);
                this.difficultyOneGroup[2].setVisible(true);

                this.gamePlayLoop.changeDifficulty(Difficulty.values()[getSelected(this.difficultyOneGroup)], Player.ONE);
            } else {
                this.playerOneScoreLabel.setVisible(true);
                this.difficultyOneGroup[0].setVisible(false);
                this.difficultyOneGroup[1].setVisible(false);
                this.difficultyOneGroup[2].setVisible(false);

                this.gamePlayLoop.changeDifficulty(Difficulty.HUMAN, Player.ONE);
            }
        } else if (e.getSource() == this.computerTwoCheckBox) {
            if (this.computerTwoCheckBox.isSelected()) {
                this.playerTwoScoreLabel.setVisible(false);
                this.difficultyTwoGroup[0].setVisible(true);
                this.difficultyTwoGroup[1].setVisible(true);
                this.difficultyTwoGroup[2].setVisible(true);

                this.gamePlayLoop.changeDifficulty(Difficulty.values()[getSelected(this.difficultyTwoGroup)], Player.TWO);
            } else {
                this.playerTwoScoreLabel.setVisible(true);
                this.difficultyTwoGroup[0].setVisible(false);
                this.difficultyTwoGroup[1].setVisible(false);
                this.difficultyTwoGroup[2].setVisible(false);

                this.gamePlayLoop.changeDifficulty(Difficulty.HUMAN, Player.TWO);
            }
        } else if (e.getSource() == this.gridSizeSlider) {
            if (this.gridSizeSlider.getValue() != previousSliderValue) {
                this.previousSliderValue = this.gridSizeSlider.getValue();
                this.gamePlayLoop.changeSize(this.gridSizeSlider.getValue());
            }
        }


        if (e.getSource() == this.computerOneCheckBox || e.getSource() == this.computerTwoCheckBox) {
            int gameModeIndex = (this.computerOneCheckBox.isSelected() ? 0b10 : 0)
                    | (this.computerTwoCheckBox.isSelected() ? 0b01 : 0);
            if (this.previousGameModeIndex != gameModeIndex) {
                this.previousGameModeIndex = gameModeIndex;
                GameMode gameMode = GameMode.values()[gameModeIndex];
                this.gamePlayLoop.changeGameMode(gameMode);
            }
        }
    }
}
