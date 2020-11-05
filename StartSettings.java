package Tricky;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

public class StartSettings extends JFrame{
    private JFrame frame;
    private JPanel displayPanel;
    private JPanel buttonPanel;
    private JTextField display;
    public StartSettings() {
        frame = new JFrame("Start");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        displayPanel = new JPanel();
        display = new JTextField("Select grid size");
        displayPanel.add(display);
        frame.getContentPane().add(displayPanel ,BorderLayout.NORTH);

        buttonPanel = new JPanel();
        String[] buttonTexts = new String[] {"6x6", "10x10", "16x16"};

        for (String buttonText : buttonTexts) {
            JButton b = new JButton(buttonText);
            switch (buttonText)
            {
                case "6x6":
                    b.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            TrickyGUI application = new TrickyGUI(6);
                            application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            frame.dispose();
                        }
                    });
                    break;
                case "10x10":
                    b.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            TrickyGUI application = new TrickyGUI(10);
                            application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            frame.dispose();
                        }
                    });
                    break;
                case "16x16":
                    b.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            TrickyGUI application = new TrickyGUI(16);
                            application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            frame.dispose();
                        }
                    });
                    break;
            }
            buttonPanel.add(b);
        }
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }
}
