package Tricky;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

public class TrickyGUI extends JFrame /*implements ActionListener*/{
    private JFrame frame = new JFrame();
    private JPanel whosTurn;
    private JPanel buttonPanel;
    private JTextField display;
    private int n;

    public TrickyGUI (int n) {
        this.n = n;
        Grid2 g = new Grid2(n);
    }

}