package main.java.de.gieskerb.tictactoe.visual;

import main.java.de.gieskerb.tictactoe.GamePlayLoop;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoPanel extends JPanel implements ActionListener, ChangeListener {

    private final JLabel playerOneName, playerTwoName;
    private final JLabel playerOneScore, playerTwoScore;
    private final JButton settingsButton;
    private final JSlider gridSizeSlider;
    private int previousSliderValue;

    private final GridPanel gridPanel;
    private final GamePlayLoop gamePlayLoop;

    InfoPanel(GridPanel gridPanel, GamePlayLoop gamePlayLoop) {
        super.setLayout(null);
        super.setSize(GridPanel.PRIME_FACTOR_PRODUCT, GridPanel.PRIME_FACTOR_PRODUCT / 5);
        super.setPreferredSize(super.getSize());

        this.gridPanel = gridPanel;
        this.gamePlayLoop = gamePlayLoop;

        this.playerOneName = new JLabel("Player One");
        this.playerOneName.setHorizontalAlignment(SwingConstants.CENTER);
        this.playerOneName.setSize(super.getWidth() / 3, super.getHeight() / 2);
        this.playerOneName.setLocation(0, 0);
        this.playerOneName.setBackground(GameWindow.BACKGROUND_COLOR);
        this.playerOneName.setForeground(GameWindow.FOREGROUND_COLOR);
        this.playerOneName.setOpaque(true);

        this.playerTwoName = new JLabel("Player Two");
        this.playerTwoName.setHorizontalAlignment(SwingConstants.CENTER);
        this.playerTwoName.setSize(super.getWidth() / 3, super.getHeight() / 2);
        this.playerTwoName.setLocation(2 * super.getWidth() / 3, 0);
        this.playerTwoName.setBackground(GameWindow.BACKGROUND_COLOR);
        this.playerTwoName.setForeground(GameWindow.FOREGROUND_COLOR);
        this.playerTwoName.setOpaque(true);

        this.playerOneScore = new JLabel("0");
        this.playerOneScore.setHorizontalAlignment(SwingConstants.CENTER);
        this.playerOneScore.setSize(super.getWidth() / 3, super.getHeight() / 2);
        this.playerOneScore.setLocation(0, super.getHeight() / 2);
        this.playerOneScore.setBackground(GameWindow.BACKGROUND_COLOR);
        this.playerOneScore.setForeground(GameWindow.FOREGROUND_COLOR);
        this.playerOneScore.setOpaque(true);

        this.playerTwoScore = new JLabel("0");
        this.playerTwoScore.setHorizontalAlignment(SwingConstants.CENTER);
        this.playerTwoScore.setSize(super.getWidth() / 3, super.getHeight() / 2);
        this.playerTwoScore.setLocation(2 * super.getWidth() / 3, super.getHeight() / 2);
        this.playerTwoScore.setBackground(GameWindow.BACKGROUND_COLOR);
        this.playerTwoScore.setForeground(GameWindow.FOREGROUND_COLOR);
        this.playerTwoScore.setOpaque(true);

        this.settingsButton = new JButton("Settings");
        this.settingsButton.addActionListener(this);
        this.settingsButton.setSize(super.getWidth() / 3, super.getHeight() / 2);
        this.settingsButton.setLocation(super.getWidth() / 3, 0);
        this.settingsButton.setFocusable(false);
        this.settingsButton.setBackground(GameWindow.BACKGROUND_COLOR);
        this.settingsButton.setForeground(GameWindow.FOREGROUND_COLOR);

        this.previousSliderValue = 3;
        this.gridSizeSlider = new JSlider(2, 8);
        this.gridSizeSlider.setMajorTickSpacing(3);
        this.gridSizeSlider.setMinorTickSpacing(1);
        this.gridSizeSlider.setValue(previousSliderValue);
        this.gridSizeSlider.setSize(super.getWidth() / 3, super.getHeight() / 2);
        this.gridSizeSlider.setLocation(super.getWidth() / 3, super.getHeight() / 2);
        this.gridSizeSlider.setSnapToTicks(true);
        this.gridSizeSlider.setPaintTicks(true);
        this.gridSizeSlider.setPaintLabels(true);
        this.gridSizeSlider.addChangeListener(this);

        super.add(playerOneName);
        super.add(playerTwoName);
        super.add(playerOneScore);
        super.add(playerTwoScore);
        super.add(settingsButton);
        super.add(gridSizeSlider);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.settingsButton) {
            new SettingsWindow(this.gamePlayLoop);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == this.gridSizeSlider) {
            if (this.gridSizeSlider.getValue() != previousSliderValue) {
                this.previousSliderValue = this.gridSizeSlider.getValue();
                this.gamePlayLoop.changeSize(this.gridSizeSlider.getValue());
                this.gamePlayLoop.restartGame();
            }
        }
    }
}
