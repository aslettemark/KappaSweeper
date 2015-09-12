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

public class ClickListener implements ActionListener {

    private KappaGUI gui;

    public ClickListener(KappaGUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        int sizeX = KappaSweeper.grid.sizeX;
        int sizeY = KappaSweeper.grid.sizeY;
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if (btn == KappaSweeper.gui.buttons[x][y]) {
                    final Cell cell = KappaSweeper.grid.grid[x][y];
                    cell.setExposed(true);
                    if(cell.getLetter() == 'X') {
                        btn.setIcon(gui.kappa);
                    } else {
                        btn.setText(String.valueOf(cell.getLetter()));
                    }
                    btn.setEnabled(false);
                    if (cell.isMine()) {
                        System.out.println("You lost.");
                        this.gui.gameLost();
                    } else if (KappaSweeper.grid.mines == KappaSweeper.grid.unTurnedTiles()) {
                        System.out.println("You won.");
                        this.gui.gameWon();
                    }
                }
            }
        }
    }

}
