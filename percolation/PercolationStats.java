import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private double[] numberOfOpenSites;
    private int size;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        numberOfOpenSites = new double[trials];
        size = n * n;

        for (int i = 0; i < trials; i++) {
            // System.out.println(i);
            numberOfOpenSites[i] = makeExperiment(n);
            numberOfOpenSites[i] = (numberOfOpenSites[i] / size);
            // System.out.println(numberOfOpenSites[i]);
            // System.out.println(numberOfOpenSites[i] * 1.0 / size);
        }
    }

    private double makeExperiment(int n) {
        Percolation p = new Percolation(n);
        while (true) {
            if (p.percolates()) break;
            int row = StdRandom.uniformInt(1, n + 1);
            int col = StdRandom.uniformInt(1, n + 1);
            p.open(row, col);
        }
        return p.numberOfOpenSites();
    }


    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(numberOfOpenSites);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (numberOfOpenSites.length <= 1) return Double.NaN;
        return StdStats.stddev(numberOfOpenSites);
    }


    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double interval = (CONFIDENCE_95 * stddev()) / (Math.sqrt(numberOfOpenSites.length));
        return mean() - interval;

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double interval = (CONFIDENCE_95 * stddev()) / (Math.sqrt(numberOfOpenSites.length));
        return mean() + interval;
    }

    public static void main(String[] args) {
        String arg0 = args[0];
        String arg1 = args[1];

        int n = Integer.parseInt(arg0);
        int t = Integer.parseInt(arg1);

        PercolationStats stdStats = new PercolationStats(n, t);
        System.out.println("mean                    = " + stdStats.mean());
        System.out.println("stddev                  = " + stdStats.stddev());
        System.out.println(
                "95% confidence interval = [" + stdStats.confidenceLo() + ", "
                        + stdStats.confidenceHi() + "]");

    }

}
