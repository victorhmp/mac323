import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // sample mean of percolation threshold
    private double mean;
    // sample standard deviation of percolation threshold
    private double stddev;
    // low  endpoint of 95% confidence interval
    private double confidenceLow;
    // high endpoint of 95% confidence interval
    private double confidenceHigh;
    // est[i] = estimate of percolation threshold in perc[i]
    private double[] est;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        est = new double[trials];
        for (int k = 0; k < trials; k++) {
            Percolation perc = new Percolation(n);
            double count = 0;
            while (!perc.percolates()) {
                int i = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);
                if (perc.isOpen(i, j))
                    continue;
                perc.open(i, j);
                count++;
            }
            est[k] = count / (n * n);
        }
        mean = StdStats.mean(est);
        stddev = StdStats.stddev(est);
        confidenceLow = mean - (1.96 * stddev) / Math.sqrt(trials);
        confidenceHigh = mean + (1.96 * stddev) / Math.sqrt(trials);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLow() {
        return confidenceLow;
    }

    public double confidenceHigh() {
        return confidenceHigh;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, t);
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = " + stats.confidenceLow() + ", " + stats.confidenceHigh());
    }
}