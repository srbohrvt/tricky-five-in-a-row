package Tricky;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinWindow  {
    JFrame frame;
    JPanel winnerPanel;
    JLabel won;

    JPanel buttonPanel;
    private int playerWon;
    public WinWindow(int mark, JFrame grid, int n) {
        frame = new JFrame("Winner");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        switch (mark) {
            case 1:
                won = new JLabel("Congratulations! Player №1 has won!");
                break;
            case 0:
                won = new JLabel("Congratulations! Player №2 has won!");
                break;
        }
        winnerPanel = new JPanel();
        winnerPanel.add(won);
        frame.getContentPane().add(winnerPanel , BorderLayout.NORTH);

        buttonPanel = new JPanel();
        String[] buttonNames = new String[] {"OK", "Restart", "Change field size", "Exit"};

        for (String buttonText : buttonNames) {
            JButton b = new JButton(buttonText);
            switch (buttonText)
            {
                case "Restart":
                    b.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            frame.dispose();
                            grid.dispose();
                            Grid2 g = new Grid2(n);
                        }
                    });
                    break;
                case "OK":
                    b.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            frame.dispose();
                        }
                    });
                    break;
                case "Change field size":
                    b.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            frame.dispose();
                            grid.dispose();
                            Main m = new Main();
                        }
                    });
                    break;
                default:
                    b.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            System.exit(0);
                        }
                    });
                    break;
            }
            buttonPanel.add(b);
        }
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
