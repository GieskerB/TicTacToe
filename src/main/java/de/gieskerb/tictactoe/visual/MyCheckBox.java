package main.java.de.gieskerb.tictactoe.visual;

import javax.swing.*;
import java.awt.*;

public class MyCheckBox extends JPanel {

    final JCheckBox checkBox;
    final JLabel label;

    MyCheckBox(String name ,JCheckBox checkBox) {
        super();
        super.setLayout(new GridLayout(2,1));
        this.checkBox = checkBox;
        this.label = new JLabel(name);
        super.add(this.checkBox);
        super.add(this.label);
    }

}