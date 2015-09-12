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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KappaGUI extends JFrame {
    private JButton resetButton;
    private JPanel rootPanel;
    private JPanel buttonPanel;
    public JButton[][] buttons;
    public static ImageIcon flag;

    public KappaGUI() {
        super("KappaSweeper");
        this.setContentPane(rootPanel);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setResizable(false);
        this.flag = new ImageIcon("src/resources/flag.png");

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("time to reset hue");
            }
        });

        this.setVisible(true);
    }

    public void createButtons() {
        final int sizeX = KappaSweeper.grid.sizeX;
        final int sizeY = KappaSweeper.grid.sizeY;
        this.buttons = new JButton[sizeX][sizeY];
        int height = (this.buttonPanel.getSize().height - 20) / sizeY;
        int width = (this.buttonPanel.getSize().width - 20) / sizeX;

        for(int y = 0; y < sizeY; y++) {
            for(int x = 0; x < sizeX; x++) {
                buttons[x][y] = new JButton();
                int xd = 10 + width * x;
                int yd = 10 + height * y;
                buttons[x][y].setBounds(xd, yd, width, height);
                buttons[x][y].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton btn = (JButton) e.getSource();
                        for (int x = 0; x < sizeX; x++) {
                            for (int y = 0; y < sizeY; y++) {
                                if (btn == buttons[x][y]) {
                                    final Cell cell = KappaSweeper.grid.grid[x][y];
                                    cell.setExposed(true);
                                    btn.setText(String.valueOf(cell.getLetter()));
                                    btn.setEnabled(false);
                                    if (cell.isMine()) {
                                        //TODO game lost
                                        System.out.println("You lost.");
                                    } else if (KappaSweeper.grid.mines == KappaSweeper.grid.unTurnedTiles()) {
                                        //TODO game won
                                        System.out.println("You won.");
                                    }
                                }
                            }
                        }
                    }
                });
                buttons[x][y].addMouseListener(new FlagListener());
                this.buttonPanel.add(buttons[x][y]);
            }
        }
    }

    private void createUIComponents() {
        this.buttonPanel = new JPanel();
        this.buttonPanel.setLayout(null);
    }
}
