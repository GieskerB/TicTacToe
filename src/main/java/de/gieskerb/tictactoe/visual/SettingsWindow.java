package main.java.de.gieskerb.tictactoe.visual;

import main.java.de.gieskerb.tictactoe.GamePlayLoop;
import main.java.de.gieskerb.tictactoe.enums.GameMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow extends JFrame implements ActionListener {

    /*
     * what does the Settings window need:
     * 1. Game selector: PvP / PvC / ...
     * 2. Difficulty selector
     * 3. (Color selector?)
     * 4. Apply Button
     */

    private final GamePlayLoop gamePlayLoop;

    private JPanel contentPane;

    private JLabel playerOneLabel,gameModeLabel, playerTwoLabel;
    private JComboBox<String>difficultySelectorPlayerOne, gameModeSelector,  difficultySelectorPlayerTwo;
    private JButton applyButton;

    SettingsWindow(GamePlayLoop gamePlayLoop) {
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        super.setSize(512,256);

        this.gamePlayLoop = gamePlayLoop;

        this.contentPane = new JPanel();
        this.contentPane.setLayout(null);
        this.contentPane.setLocation(0,0);
        this.contentPane.setSize(super.getWidth(),super.getHeight());
        this.contentPane.setPreferredSize(super.getSize());
        this.contentPane.setBackground(GameWindow.BACKGROUND_COLOR);

        this.difficultySelectorPlayerOne = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
        this.difficultySelectorPlayerOne.setSelectedIndex(0);
        this.difficultySelectorPlayerOne.setLocation(0, 0);
        this.difficultySelectorPlayerOne.setSize(this.contentPane.getWidth()/3, this.contentPane.getHeight()/2);

        this.gameModeSelector = new JComboBox<>(new String[]{"PvP", "PvC", "CvP", "CvC"});
        this.gameModeSelector.setSelectedIndex(0);
        this.gameModeSelector.setLocation(this.contentPane.getWidth()/3, 0);
        this.gameModeSelector.setSize(this.contentPane.getWidth()/3, this.contentPane.getHeight()/2);

        this.difficultySelectorPlayerTwo = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
        this.difficultySelectorPlayerTwo.setSelectedIndex(0);
        this.difficultySelectorPlayerTwo.setLocation(2 * this.contentPane.getWidth()/3, 0);
        this.difficultySelectorPlayerTwo.setSize(this.contentPane.getWidth()/3, this.contentPane.getHeight()/2);

        this.applyButton = new JButton("Apply");
        this.applyButton.setLocation(0, this.contentPane.getHeight()/2);
        this.applyButton.setSize(this.contentPane.getWidth(), this.contentPane.getHeight()/2);
        this.applyButton.setFocusable(false);
        this.applyButton.addActionListener(this);

        this.contentPane.add(this.difficultySelectorPlayerOne);
        this.contentPane.add(this.gameModeSelector);
        this.contentPane.add(this.difficultySelectorPlayerTwo);
        this.contentPane.add(this.applyButton);

        super.add(this.contentPane);

        super.pack();
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        super.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.applyButton) {
            int index = this.gameModeSelector.getSelectedIndex();
            this.gamePlayLoop.changeGameMode(GameMode.values()[index]);
            super.dispose();
        }
    }
}
