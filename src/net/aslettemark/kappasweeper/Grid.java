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

import java.util.Random;

public class Grid {

    public int sizeX;
    public int sizeY;
    private Random random;
    public int mines;
    public Cell[][] grid;

    public Grid(int sizeX, int sizeY, long seed, int mines) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.random = new Random(seed);
        this.mines = mines;
        this.grid = new Cell[sizeX][sizeY];

        populate();
    }

    private void populate() {
        //Initial placement of mines (X)
        //Generates the same board every time
        for(int x = 0; x < this.sizeX; x++) {
            for(int y = 0; y < this.sizeY; y++) {
                if(x == y) {
                    this.grid[x][y] = new Cell('X');
                } else {
                    this.grid[x][y] = new Cell(' ');
                }
            }
        }

        //Determine all the numbers to be placed
        for(int x = 0; x < this.sizeX; x++) {
            for(int y = 0; y < this.sizeY; y++) {
                if (!this.grid[x][y].isMine()) {
                    int count = 0;
                    if (x > 0) {
                        if (this.grid[x-1][y].isMine()) count++;
                        if (y > 0) {
                            if (this.grid[x-1][y-1].isMine()) count++;
                        }
                        if (y < (this.sizeY - 1)) {
                            if (this.grid[x-1][y+1].isMine()) count++;
                        }
                    }
                    if (x < (this.sizeX - 1)) {
                        if (this.grid[x+1][y].isMine()) count++;
                        if (y > 0) {
                            if (this.grid[x+1][y-1].isMine()) count++;
                        }
                        if (y < (this.sizeY - 1)) {
                            if (this.grid[x+1][y+1].isMine()) count++;
                        }
                    }
                    if (y > 0) {
                        if (this.grid[x][y-1].isMine()) count++;
                    }
                    if (y < (this.sizeY - 1)) {
                        if (this.grid[x][y+1].isMine()) count++;
                    }
                    this.grid[x][y].setLetter((char) ('0' + count));
                    if(count == 0) {
                        this.grid[x][y].setLetter(' ');
                    }
                }
            }
        }
    }

    public void populate(long seed) {
        this.random = new Random(seed);
        populate();
    }

    public void print() {
        for(int y = 0; y < this.sizeY; y++) {
            String line = "";
            for(int x = 0; x < this.sizeX; x++) {
                line += this.grid[x][y].getLetter();
            }
            System.out.println(line);
        }
    }

    public int unTurnedTiles() {
        int count = 0;
        for(int x = 0; x < this.sizeX; x++) {
            for (int y = 0; y < this.sizeY; y++) {
                if(KappaSweeper.gui.buttons[x][y].isEnabled()) {
                    count++;
                }
            }
        }
        return count;
    }
}
