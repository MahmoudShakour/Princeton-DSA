/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int gridSize;

    private boolean[][] color;

    private int numberOfOpenSites;

    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufTop;
    private int top;
    private int bottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        gridSize = n;
        numberOfOpenSites = 0;
        uf = new WeightedQuickUnionUF(n * n + 2);
        ufTop = new WeightedQuickUnionUF(n * n + 1);
        top = n * n;
        bottom = n * n + 1;
        color = new boolean[n][n];


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                color[i][j] = false;
            }
        }

        for (int i = 0; i < n; i++) {
            uf.union(normalize(0, i), top);
            ufTop.union(normalize(0, i), top);
            uf.union(normalize(n - 1, i), bottom);
        }
    }


    private boolean isOutOfBounds(int i, int j) {
        return (i < 1 || j < 1 || i > gridSize || j > gridSize);
    }

    private int normalize(int i, int j) {
        return i * gridSize + j;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOutOfBounds(row, col))
            throw new IllegalArgumentException();
        row--;
        col--;
        if (color[row][col]) {
            return;
        }
        color[row][col] = true;
        numberOfOpenSites++;
        if (row > 0 && color[row - 1][col]) {
            uf.union(normalize(row, col), normalize(row - 1, col));
            ufTop.union(normalize(row, col), normalize(row - 1, col));
        }
        if (row < gridSize - 1 && color[row + 1][col]) {
            uf.union(normalize(row, col), normalize(row + 1, col));
            ufTop.union(normalize(row, col), normalize(row + 1, col));
        }
        if (col > 0 && color[row][col - 1]) {
            uf.union(normalize(row, col), normalize(row, col - 1));
            ufTop.union(normalize(row, col), normalize(row, col - 1));

        }
        if (col < gridSize - 1 && color[row][col + 1]) {
            uf.union(normalize(row, col), normalize(row, col + 1));
            ufTop.union(normalize(row, col), normalize(row, col + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (isOutOfBounds(row, col))
            throw new IllegalArgumentException();
        row--;
        col--;
        return color[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (isOutOfBounds(row, col))
            throw new IllegalArgumentException();
        row--;
        col--;


        return (ufTop.find(top) == ufTop.find(normalize(row, col)) && color[row][col]);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (gridSize == 1) {
            return numberOfOpenSites > 0;
        }
        else {
            return (uf.find(top) == uf.find(bottom));
        }
    }
}

