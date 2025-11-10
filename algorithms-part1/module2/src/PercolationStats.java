
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] arr;
    private int trials;

    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) {
            throw new IllegalArgumentException();
        }

        trials = t;
        arr = new double[t];

        for (int i = 0; i < t; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                }
            }

            double threshold = (double) p.numberOfOpenSites() / (n * n);
            arr[i] = threshold;
        }
    }

    public double mean() {
        return StdStats.mean(arr);
    }

    public double stddev() {
        return StdStats.stddev(arr);
    }

    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(trials));
    }

    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, t);

        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = "
                + ps.confidenceLo() + ", " + ps.confidenceHi());
    }
}
