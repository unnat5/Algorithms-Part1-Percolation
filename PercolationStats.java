/* *****************************************************************************
 *  Name:              Unnat Singh
 *  Coursera User ID:  unnatsingh5@gmail.com
 *  Last modified:     26/07/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static double confidenceInterval95 = 1.96;
    private final double tTrials;
    private final double[] thresholdArray;


    // perform independent trials on an n-by-n grid
    // Constructor
    public PercolationStats(int n, int trials) {

        int[] x;
        int[] y;
        int counter;
        int index;


        Percolation perc;

        tTrials = trials;
        if (n <= 0) throw new IllegalArgumentException("argument must be positive: " + n);
        if (trials <= 0) throw new IllegalArgumentException("argument must be positive: " + trials);
        thresholdArray = new double[trials];
        counter = 0;
        x = new int[n * n];
        y = new int[n * n];
        // shuffling index
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                x[n * i + j] = i + 1;
                y[n * i + j] = j + 1;
                // System.out.println(i + 1 + " " + (j + 1));
            }
        }

        while (trials != 0) {
            perc = new Percolation(n);
            shuffle(x, y);


            index = 0;
            while (!perc.percolates()) {
                perc.open(x[index], y[index]);
                index++;


            }

            thresholdArray[counter] = perc.numberOfOpenSites() / (double) (n * n);
            counter++;
            trials--;
        }


    }

    // writing a shuffle function
    private void shuffle(int[] a, int[] b) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + StdRandom.uniform(n - i);     // between i and n-1
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
            temp = b[i];
            b[i] = b[r];
            b[r] = temp;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.thresholdArray);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.thresholdArray);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((confidenceInterval95 * stddev()) / Math.sqrt(tTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((confidenceInterval95 * stddev()) / Math.sqrt(tTrials));
    }


    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats pStats = new PercolationStats(n, trials);

        System.out.println("mean                    = " + pStats.mean());
        System.out.println("stddev                  = " + pStats.stddev());
        System.out.println(
                "95% confidence interval = [" + pStats.confidenceLo() + ", " + pStats.confidenceHi()
                        + "]");


    }
}
