package main.java.de.gieskerb.tictactoe.visual;

import javax.swing.*;
import java.awt.*;

public class MyCheckBox extends JPanel {

    MyCheckBox(String name ,JCheckBox checkBox) {
        super();
        super.add(checkBox,BorderLayout.NORTH);
        super.add(new JLabel(name),BorderLayout.CENTER);
    }

}