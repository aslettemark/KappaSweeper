/*
 *  Copyright (C) 2015 Aksel H. Slettemark http://aslettemark.net/
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.aslettemark.kappasweeper;

import javax.swing.*;

public class KappaGUI extends JFrame {
    private JButton resetButton;
    private JPanel rootPanel;
    private JPanel buttonPanel;
    private JPanel infoPanel;
    private JLabel infobox;
    public JButton[][] buttons;
    public static ImageIcon flag;
    public static ImageIcon kappa;

    public KappaGUI() {
        super("KappaSweeper");
        this.setContentPane(rootPanel);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setResizable(false);

        this.setInfoBox();

        this.flag = new ImageIcon("src/resources/flag.png");
        this.kappa = new ImageIcon("src/resources/kappa.png");

        resetButton.addActionListener(new ResetListener(this));

        this.setVisible(true);
    }

    public void createButtons() {
        int sizeX = KappaSweeper.grid.sizeX;
        int sizeY = KappaSweeper.grid.sizeY;
        this.buttons = new JButton[sizeX][sizeY];
        int height = (this.buttonPanel.getSize().height - 20) / sizeY;
        int width = (this.buttonPanel.getSize().width - 20) / sizeX;

        for(int y = 0; y < sizeY; y++) {
            for(int x = 0; x < sizeX; x++) {
                buttons[x][y] = new JButton();
                int xd = 10 + width * x;
                int yd = 10 + height * y;
                buttons[x][y].setBounds(xd, yd, width, height);
                buttons[x][y].addActionListener(new ClickListener(this));
                buttons[x][y].addMouseListener(new FlagListener());
                this.buttonPanel.add(buttons[x][y]);
            }
        }
        this.setEnableGameButtons(true);
        this.repaint();
    }

    public void gameWon() {
        this.setEnableGameButtons(false);
        this.exposeAll(true);
        this.infobox.setText("You won!");
    }

    public void gameLost() {
        this.setEnableGameButtons(false);
        this.exposeAll(true);
        this.infobox.setText("You lost.");
    }

    public void exposeAll(boolean state) {
        int sizeX = KappaSweeper.grid.sizeX;
        int sizeY = KappaSweeper.grid.sizeY;
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                Cell cell = KappaSweeper.grid.grid[x][y];
                cell.setExposed(state);
                if(state) {
                    if (cell.getLetter() == 'X') {
                        buttons[x][y].setIcon(this.kappa);
                    } else {
                        buttons[x][y].setText(String.valueOf(cell.getLetter()));
                    }
                } else {
                    buttons[x][y].setText("");
                }
            }
        }
    }

    public void clearFlags() {
        int sizeX = KappaSweeper.grid.sizeX;
        int sizeY = KappaSweeper.grid.sizeY;
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                KappaSweeper.grid.grid[x][y].setFlagged(false);
                this.buttons[x][y].setIcon(null);
            }
        }
    }

    public void setEnableGameButtons(boolean state) {
        for(JButton[] btn : buttons) {
            for(JButton b : btn) {
                b.setEnabled(state);
            }
        }
    }

    public void setInfoBox() {
        this.setInfoBox("GRID: " + KappaSweeper.grid.sizeX + "x" + KappaSweeper.grid.sizeY + " Mines: " + KappaSweeper.grid.mines);
    }

    public void setInfoBox(String info) {
        this.infobox.setText(info);
    }

    private void createUIComponents() {
        this.buttonPanel = new JPanel();
        this.buttonPanel.setLayout(null);
        this.infoPanel = new JPanel();
    }
}
