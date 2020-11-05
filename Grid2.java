package Tricky;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

public class Grid2 extends JFrame{
    int cellSize = 40;
    public int n;
    private boolean firstPlayerTurn = true;
    private boolean gameOver = false;
    private int[][] matrix;
    private List<CellPane> firstPlayerSigns = new ArrayList<>();
    private List<CellPane> secondPlayerSigns = new ArrayList<>();

    JFrame frame;
    JPanel ui;
    JLabel first;
    JLabel sec;

    public Grid2(int n) {

        this.n = n;
        frame = new JFrame("Tricky five-in-a-row");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(new TestPane(this.n), BorderLayout.NORTH);

        matrix = new int[n+8][n+8];
        for (int i = 0; i < n+8; i++) {
            for (int j = 0; j < n + 8; j++) {
                if (i < 4 || i > n + 3 || j < 4 || j > n + 3)
                    matrix[i][j] = -2;
                else
                    matrix[i][j] = -1;
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        //creating labels to notify Who's step to draw symbol
        JLabel turn = new JLabel("Who's turn?");
        turn.setFont(new Font("Sans Serif", Font.BOLD, 20));

        first = new JLabel("Player 1");
        sec = new JLabel("Player 2");
        first.setFont(new Font("Sans Serif", Font.BOLD, 20));
        sec.setFont(new Font("Sans Serif", Font.BOLD, 20));

        first.setBackground(Color.red);
        first.setForeground(Color.white);
        first.setOpaque(true);
        ui = new JPanel();
        ui.add(turn);
        ui.add(first);
        ui.add(sec);
        frame.getContentPane().add(ui ,BorderLayout.SOUTH);

        //creating menu
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu trickyMenu = new JMenu("Tricky five-in-a-row");
        menuBar.add(trickyMenu);
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        trickyMenu.add(exitMenuItem);
        JMenuItem restartMenuItem = new JMenuItem("Restart");
        trickyMenu.add(restartMenuItem);
        JMenuItem settingsMenuItem = new JMenuItem("Settings");
        trickyMenu.add(settingsMenuItem);

        settingsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.dispose();
                Main m = new Main();
            }
        });
        restartMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.dispose();
                Grid2 g = new Grid2(n);
            }
        });
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //Create grid
    public class TestPane extends JPanel {

        public TestPane(int n) {
            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    gbc.gridx = col;
                    gbc.gridy = row;

                    CellPane cellPane = new CellPane(row+4, col+4);
                    Border border = null;
                    border = new MatteBorder(1, 1, 1, 1, Color.darkGray);
                    cellPane.setBorder(border);
                    add(cellPane, gbc);
                }
            }
        }
    }

    public class CellPane extends JPanel {
        private Color defaultBackground;
        public int idx, idy;

        public CellPane(int x, int y) {
            this.idx = x;
            this.idy = y;
            addMouseListener(new MouseAdapter() {
                /*@Override
                 public void mouseEntered(MouseEvent e) {
                     if (isHidden)
                         setVisible(true);
                 }

                 @Override
                 public void mouseExited(MouseEvent e) {
                    if (isHidden)
                        setVisible(false);
                 }*/
                @Override
                public void mouseClicked(MouseEvent e) {
                    int x, y;
                    super.mouseClicked(e);
                    x = e.getX() / cellSize;
                    y = e.getY() / cellSize;
                    if (!gameOver) {
                        if (firstPlayerTurn == true) {
                            if (matrix[idx][idy] == -1) {
                                firstPlayer(x, y);
                                matrix[idx][idy] = 1;
                                checkWinOrRemoveSigns(matrix, idx, idy, 1);
                            }
                        } else if (firstPlayerTurn == false) {
                            if (matrix[idx][idy] == -1) {
                                secondPlayer(x, y);
                                matrix[idx][idy] = 0;
                                checkWinOrRemoveSigns(matrix, idx, idy, 0);
                            }
                        }
                    }
                }
            });
        }
        private void checkWinOrRemoveSigns(int[][] mat, int curX, int curY, int mark)
        {
            boolean l;
            l = checkInCol(mat, curX, curY, mark);
            if (l == false) {
                l = checkInDiag(mat, curX, curY, mark);
                if (l == false)
                    checkInRow(mat, curX, curY, mark);
            }
        }
        private boolean checkInRow(int[][] mat, int curX, int curY, int mark)
        {
            boolean l = false;

            //check win

            if ((mat[curX][curY + 1] == mark && mat[curX][curY + 2] == mark && mat[curX][curY + 3] == mark && mat[curX][curY + 4] == mark) ||
                    (mat[curX][curY - 1] == mark && mat[curX][curY - 2] == mark && mat[curX][curY - 3] == mark && mat[curX][curY - 4] == mark) ||
                    (mat[curX][curY - 1] == mark && mat[curX][curY - 2] == mark && mat[curX][curY - 3] == mark && mat[curX][curY + 1] == mark ) ||
                    (mat[curX][curY - 1] == mark && mat[curX][curY - 2] == mark && mat[curX][curY + 1] == mark && mat[curX][curY + 2] == mark) ||
                    (mat[curX][curY - 1] == mark && mat[curX][curY + 1] == mark && mat[curX][curY + 2] == mark && mat[curX][curY + 3] == mark))
            {
                System.out.println("Win");
                WinWindow win = new WinWindow(mark, frame, n);
                return l = true;
            }

            //case with 4 adjacent signs - minus 2 signs

            if ((mat[curX][curY + 1] == mark && mat[curX][curY + 2] == mark && mat[curX][curY + 3] == mark) ||
                    (mat[curX][curY - 1] == mark && mat[curX][curY - 2] == mark && mat[curX][curY - 3] == mark) ||
                    (mat[curX][curY - 1] == mark && mat[curX][curY + 1] == mark && mat[curX][curY + 2] == mark) ||
                    (mat[curX][curY - 1] == mark && mat[curX][curY + 1] == mark && mat[curX][curY - 2] == mark))
            {
                if (mark == 0) {
                    Random rand = new Random();
                    int r = rand.nextInt(secondPlayerSigns.size()-1);
                    int r2 = rand.nextInt(secondPlayerSigns.size()-1);
                    while (r == r2)
                        r2 = rand.nextInt(secondPlayerSigns.size()-1);
                    matrix[secondPlayerSigns.get(r).idx][secondPlayerSigns.get(r).idy] = -1;
                    matrix[secondPlayerSigns.get(r2).idx][secondPlayerSigns.get(r2).idy] = -1;
                    secondPlayerSigns.get(r).setBackground(defaultBackground);
                    secondPlayerSigns.get(r2).setBackground(defaultBackground);
                    secondPlayerSigns.remove(r);
                    secondPlayerSigns.remove(r2);
                    return l = true;
                }
                else if (mark == 1){
                    Random rand = new Random();
                    int r = rand.nextInt(firstPlayerSigns.size()-1);
                    int r2 = rand.nextInt(firstPlayerSigns.size()-1);
                    while (r == r2)
                        r2 = rand.nextInt(firstPlayerSigns.size()-1);
                    matrix[firstPlayerSigns.get(r).idx][firstPlayerSigns.get(r).idy] = -1;
                    matrix[firstPlayerSigns.get(r2).idx][firstPlayerSigns.get(r2).idy] = -1;
                    firstPlayerSigns.get(r).setBackground(defaultBackground);
                    firstPlayerSigns.get(r2).setBackground(defaultBackground);
                    firstPlayerSigns.remove(r);
                    firstPlayerSigns.remove(r2);
                    return l = true;
                }
            }

            //case with 3 adjacent signs - minus 1 sign
            if ((mat[curX][curY + 1] == mark && mat[curX][curY + 2] == mark) ||
                    (mat[curX][curY - 1] == mark && mat[curX][curY - 2] == mark) ||
                    (mat[curX][curY - 1] == mark && mat[curX][curY + 1] == mark))
            {
                if (mark == 0) {
                    Random rand = new Random();
                    int r = rand.nextInt(secondPlayerSigns.size()-1);
                    matrix[secondPlayerSigns.get(r).idx][secondPlayerSigns.get(r).idy] = -1;
                    secondPlayerSigns.get(r).setBackground(defaultBackground);
                    secondPlayerSigns.remove(r);
                    return l = true;
                }
                else if (mark == 1){
                    Random rand = new Random();
                    int r = rand.nextInt(firstPlayerSigns.size()-1);
                    matrix[firstPlayerSigns.get(r).idx][firstPlayerSigns.get(r).idy] = -1;
                    firstPlayerSigns.get(r).setBackground(defaultBackground);
                    firstPlayerSigns.remove(r);
                    return l = true;
                }
            }

            return l;
        }
        private boolean checkInCol(int[][] mat, int curX, int curY, int mark)
        {
            boolean l = false;

            //check win

            if ((mat[curX + 1][curY] == mark && mat[curX + 2][curY] == mark && mat[curX + 3][curY] == mark && mat[curX + 4][curY] == mark) ||
                    (mat[curX - 1][curY] == mark && mat[curX - 2][curY] == mark && mat[curX - 3][curY] == mark && mat[curX - 4][curY] == mark) ||
                    (mat[curX - 1][curY] == mark && mat[curX - 2][curY] == mark && mat[curX - 3][curY] == mark && mat[curX + 1][curY] == mark ) ||
                    (mat[curX - 1][curY] == mark && mat[curX - 2][curY] == mark && mat[curX + 1][curY] == mark && mat[curX + 2][curY] == mark) ||
                    (mat[curX - 1][curY] == mark && mat[curX + 1][curY] == mark && mat[curX + 2][curY] == mark && mat[curX + 3][curY] == mark))
            {
                System.out.println("Win");
                WinWindow win = new WinWindow(mark, frame, n);

                return l = true;
            }

            //case with 4 adjacent signs - minus 2 signs

            if ((mat[curX + 1][curY] == mark && mat[curX + 2][curY] == mark && mat[curX + 3][curY] == mark) ||
                    (mat[curX - 1][curY] == mark && mat[curX - 2][curY] == mark && mat[curX - 3][curY] == mark) ||
                    (mat[curX - 1][curY] == mark && mat[curX + 1][curY] == mark && mat[curX + 2][curY] == mark) ||
                    (mat[curX - 1][curY] == mark && mat[curX + 1][curY] == mark && mat[curX - 2][curY] == mark))
            {
                if (mark == 0) {
                    Random rand = new Random();
                    int r = rand.nextInt(secondPlayerSigns.size()-1);
                    int r2 = rand.nextInt(secondPlayerSigns.size()-1);
                    while (r == r2)
                        r2 = rand.nextInt(secondPlayerSigns.size()-1);
                    matrix[secondPlayerSigns.get(r).idx][secondPlayerSigns.get(r).idy] = -1;
                    matrix[secondPlayerSigns.get(r2).idx][secondPlayerSigns.get(r2).idy] = -1;
                    secondPlayerSigns.get(r).setBackground(defaultBackground);
                    secondPlayerSigns.get(r2).setBackground(defaultBackground);
                    secondPlayerSigns.remove(r);
                    secondPlayerSigns.remove(r2);
                    return l= true;
                }
                else if (mark == 1){
                    Random rand = new Random();
                    int r = rand.nextInt(firstPlayerSigns.size()-1);
                    int r2 = rand.nextInt(firstPlayerSigns.size()-1);
                    while (r == r2)
                        r2 = rand.nextInt(firstPlayerSigns.size()-1);
                    matrix[firstPlayerSigns.get(r).idx][firstPlayerSigns.get(r).idy] = -1;
                    matrix[firstPlayerSigns.get(r2).idx][firstPlayerSigns.get(r2).idy] = -1;
                    firstPlayerSigns.get(r).setBackground(defaultBackground);
                    firstPlayerSigns.get(r2).setBackground(defaultBackground);
                    firstPlayerSigns.remove(r);
                    firstPlayerSigns.remove(r2);
                    return l= true;
                }
            }

            //case with 3 adjacent signs - minus 1 sign
            if ((mat[curX + 1][curY] == mark && mat[curX + 2][curY] == mark) ||
                    (mat[curX - 1][curY] == mark && mat[curX - 2][curY] == mark) ||
                    (mat[curX - 1][curY] == mark && mat[curX + 1][curY] == mark))
            {
                if (mark == 0) {
                    Random rand = new Random();
                    int r = rand.nextInt(secondPlayerSigns.size()-1);
                    matrix[secondPlayerSigns.get(r).idx][secondPlayerSigns.get(r).idy] = -1;
                    secondPlayerSigns.get(r).setBackground(defaultBackground);
                    secondPlayerSigns.remove(r);
                    return l= true;
                }
                else if (mark == 1){
                    Random rand = new Random();
                    int r = rand.nextInt(firstPlayerSigns.size()-1);
                    matrix[firstPlayerSigns.get(r).idx][firstPlayerSigns.get(r).idy] = -1;
                    firstPlayerSigns.get(r).setBackground(defaultBackground);
                    firstPlayerSigns.remove(r);
                    return l= true;
                }
            }

            return l;
        }
        private boolean checkInDiag(int[][] mat, int curX, int curY, int mark)
        {
            boolean l = false;

            //check win

            if ((mat[curX+1][curY + 1] == mark && mat[curX+2][curY + 2] == mark && mat[curX+3][curY + 3] == mark && mat[curX+4][curY + 4] == mark) ||
                    (mat[curX-1][curY - 1] == mark && mat[curX-2][curY - 2] == mark && mat[curX-3][curY - 3] == mark && mat[curX-4][curY - 4] == mark) ||
                    (mat[curX-1][curY - 1] == mark && mat[curX-2][curY - 2] == mark && mat[curX-3][curY - 3] == mark && mat[curX+1][curY + 1] == mark ) ||
                    (mat[curX-1][curY - 1] == mark && mat[curX-2][curY - 2] == mark && mat[curX+1][curY + 1] == mark && mat[curX+2][curY + 2] == mark) ||
                    (mat[curX-1][curY - 1] == mark && mat[curX+1][curY + 1] == mark && mat[curX+2][curY + 2] == mark && mat[curX+3][curY + 3] == mark) ||
                    (mat[curX-1][curY + 1] == mark && mat[curX-2][curY + 2] == mark && mat[curX-3][curY + 3] == mark && mat[curX-4][curY + 4] == mark) ||
                    (mat[curX+1][curY - 1] == mark && mat[curX+2][curY - 2] == mark && mat[curX+3][curY - 3] == mark && mat[curX+4][curY - 4] == mark) ||
                    (mat[curX+1][curY - 1] == mark && mat[curX+2][curY - 2] == mark && mat[curX+3][curY - 3] == mark && mat[curX-1][curY + 1] == mark ) ||
                    (mat[curX+1][curY - 1] == mark && mat[curX+2][curY - 2] == mark && mat[curX-1][curY + 1] == mark && mat[curX-2][curY + 2] == mark) ||
                    (mat[curX+1][curY - 1] == mark && mat[curX-1][curY + 1] == mark && mat[curX-2][curY + 2] == mark && mat[curX-3][curY + 3] == mark))
            {
                System.out.println("Win");
                WinWindow win = new WinWindow(mark, frame, n);
                return l = true;
            }

            //case with 4 adjacent signs - minus 2 signs

            if ((mat[curX+1][curY + 1] == mark && mat[curX+2][curY + 2] == mark && mat[curX+3][curY + 3] == mark) ||
                    (mat[curX-1][curY - 1] == mark && mat[curX-2][curY - 2] == mark && mat[curX-3][curY - 3] == mark) ||
                    (mat[curX-1][curY - 1] == mark && mat[curX+1][curY + 1] == mark && mat[curX+2][curY + 2] == mark) ||
                    (mat[curX-1][curY - 1] == mark && mat[curX+1][curY + 1] == mark && mat[curX-2][curY - 2] == mark) ||
                    (mat[curX-1][curY + 1] == mark && mat[curX-2][curY + 2] == mark && mat[curX-3][curY + 3] == mark) ||
                    (mat[curX+1][curY - 1] == mark && mat[curX+2][curY - 2] == mark && mat[curX+3][curY - 3] == mark) ||
                    (mat[curX+1][curY - 1] == mark && mat[curX-1][curY + 1] == mark && mat[curX-2][curY + 2] == mark) ||
                    (mat[curX+1][curY - 1] == mark && mat[curX-1][curY + 1] == mark && mat[curX+2][curY - 2] == mark))
            {
                if (mark == 0) {
                    Random rand = new Random();
                    int r = rand.nextInt(secondPlayerSigns.size()-1);
                    int r2 = rand.nextInt(secondPlayerSigns.size()-1);
                    while (r == r2)
                        r2 = rand.nextInt(secondPlayerSigns.size()-1);
                    matrix[secondPlayerSigns.get(r).idx][secondPlayerSigns.get(r).idy] = -1;
                    matrix[secondPlayerSigns.get(r2).idx][secondPlayerSigns.get(r2).idy] = -1;
                    secondPlayerSigns.get(r).setBackground(defaultBackground);
                    secondPlayerSigns.get(r2).setBackground(defaultBackground);
                    secondPlayerSigns.remove(r);
                    secondPlayerSigns.remove(r2);
                    return l = true;
                }
                else if (mark == 1){
                    Random rand = new Random();
                    int r = rand.nextInt(firstPlayerSigns.size()-1);
                    int r2 = rand.nextInt(firstPlayerSigns.size()-1);
                    while (r == r2)
                        r2 = rand.nextInt(firstPlayerSigns.size()-1);
                    matrix[firstPlayerSigns.get(r).idx][firstPlayerSigns.get(r).idy] = -1;
                    matrix[firstPlayerSigns.get(r2).idx][firstPlayerSigns.get(r2).idy] = -1;
                    firstPlayerSigns.get(r).setBackground(defaultBackground);
                    firstPlayerSigns.get(r2).setBackground(defaultBackground);
                    firstPlayerSigns.remove(r);
                    firstPlayerSigns.remove(r2);
                    return l = true;
                }
            }

            //case with 3 adjacent signs - minus 1 sign
            if ((mat[curX-1][curY + 1] == mark && mat[curX-2][curY + 2] == mark) ||
                    (mat[curX + 1][curY - 1] == mark && mat[curX + 2][curY - 2] == mark) ||
                    (mat[curX+1][curY - 1] == mark && mat[curX-1][curY + 1] == mark) ||
                    (mat[curX+1][curY + 1] == mark && mat[curX+2][curY + 2] == mark) ||
                    (mat[curX - 1][curY - 1] == mark && mat[curX - 2][curY - 2] == mark) ||
                    (mat[curX-1][curY - 1] == mark && mat[curX+1][curY + 1] == mark))
            {
                if (mark == 0) {
                    Random rand = new Random();
                    int r = rand.nextInt(secondPlayerSigns.size()-1);
                    matrix[secondPlayerSigns.get(r).idx][secondPlayerSigns.get(r).idy] = -1;
                    secondPlayerSigns.get(r).setBackground(defaultBackground);
                    secondPlayerSigns.remove(r);
                    return l = true;
                }
                else if (mark == 1){
                    Random rand = new Random();
                    int r = rand.nextInt(firstPlayerSigns.size()-1);
                    matrix[firstPlayerSigns.get(r).idx][firstPlayerSigns.get(r).idy] = -1;
                    firstPlayerSigns.get(r).setBackground(defaultBackground);
                    firstPlayerSigns.remove(r);
                    return l = true;
                }
            }

            return l;
        }

        private void secondPlayer(int x, int y) {
            Graphics g = getGraphics();
            g.setColor(Color.BLUE);
            g.drawOval((x * cellSize) + 5, (y * cellSize) + 5, cellSize - 10, cellSize - 10);
            firstPlayerTurn = true;
            secondPlayerSigns.add(this);

            sec.setBackground(defaultBackground);
            sec.setForeground(Color.black);
            first.setBackground(Color.red);
            first.setForeground(Color.white);
            first.setOpaque(true);
            sec.setOpaque(true);

            printM();
        }
        private void firstPlayer(int x, int y) {
            Graphics g = getGraphics();
            g.setColor(Color.RED);
            g.drawLine((x * cellSize + 5), (y * cellSize + 5), (x + 1) * cellSize - 5, (y + 1) * cellSize - 5);
            g.drawLine((x + 1) * cellSize - 5, (y * cellSize) + 5, (x * cellSize) + 5, (y + 1) * cellSize - 5);
            firstPlayerTurn = false;
            firstPlayerSigns.add(this);

            first.setBackground(defaultBackground);
            first.setForeground(Color.black);
            sec.setBackground(Color.red);
            sec.setForeground(Color.white);
            first.setOpaque(true);
            sec.setOpaque(true);

            printM();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(cellSize, cellSize); //cells size
        }
    }
    public void printM()
    {
        System.out.println("-----------------------------------------------------------------------------------------------");
        for (int i = 0; i < n+8; i++) {
            for (int j = 0; j < n + 8; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}