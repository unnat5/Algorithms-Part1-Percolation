/* *****************************************************************************
 *  Name:              Unnat Singh
 *  Coursera User ID:  unnatsingh5@gmail.com
 *  Last modified:     26/07/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {


    private boolean[][] grid;
    private final WeightedQuickUnionUF wdUnion;
    private final WeightedQuickUnionUF wdUnionColor;
    private int counter = 0;


    // create n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        // initially all sites are False so they are blocked

        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        grid = new boolean[n][n];
        wdUnion = new WeightedQuickUnionUF(n * n + 2);
        wdUnionColor = new WeightedQuickUnionUF(n * n + 2);

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || col <= 0 || row > grid.length || col > grid[0].length) {
            throw new IllegalArgumentException();
        }

        if (!grid[row - 1][col - 1]) {
            grid[row - 1][col - 1] = true;
            counter++;

            // possible points
            int row1, col1, row2, col2, row3, col3, row4, col4;
            row1 = row + 1;
            col1 = col;
            row2 = row - 1;
            col2 = col;
            row3 = row;
            col3 = col + 1;
            row4 = row;
            col4 = col - 1;

            int n = grid.length;
            if (row1 <= grid.length && isOpen(row1, col1)) {
                wdUnion.union(mapper(row, col), mapper(row1, col1));
                wdUnionColor.union(mapper(row, col), mapper(row1, col1));
            }

            if (row2 > 0 && isOpen(row2, col2)) {
                wdUnion.union(mapper(row, col), mapper(row2, col2));
                wdUnionColor.union(mapper(row, col), mapper(row2, col2));
            }

            if (col3 <= grid[0].length && isOpen(row3, col3)) {
                wdUnion.union(mapper(row, col), mapper(row3, col3));
                wdUnionColor.union(mapper(row, col), mapper(row3, col3));
            }

            if (col4 > 0 && isOpen(row4, col4)) {
                wdUnion.union(mapper(row, col), mapper(row4, col4));
                wdUnionColor.union(mapper(row, col), mapper(row4, col4));
            }

            // making virtual site
            if (row == 1) {
                wdUnion.union(mapper(row, col), 0);
                wdUnionColor.union(mapper(row, col), 0);
            }

            if (row == n) {
                wdUnion.union(mapper(row, col), n * n + 1);
                // adding isFull() was very important for visualization
                if (isFull(row, col)) {
                    wdUnionColor.union(mapper(row, col), n * n + 1);
                }
            }
        }


    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0 || row > grid.length || col > grid[0].length) {
            throw new IllegalArgumentException();
        }
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0 || row > grid.length || col > grid[0].length) {
            throw new IllegalArgumentException();
        }
        return wdUnionColor.find(0) == wdUnionColor.find(mapper(row, col));
    }

    // return the number of open sites
    public int numberOfOpenSites() {
        return counter;
    }

    // does the system percolate?

    public boolean percolates() {

        return wdUnion.find(0) == wdUnion.find(grid.length * grid.length + 1);

    }

    // mapper from 2D coordinate to 1D for unionFind methods.
    private int mapper(int p, int q) {
        return grid.length * (p - 1) + q;
    }

    // test client (optional)

    public static void main(String[] args) {
        /* Body is used for testing */
    }


}

