/**
 * Created by earne on 6/29/14.
 */
public class PercolationStats {
    private double[] xArray;
    private final int times;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) throw new java.lang.IllegalArgumentException();
        xArray = new double[T];
        times = T;
        for (int i = 0; i < T; i++) {
            Percolation per = new Percolation(N);
            while (!per.percolates()) {
                int j = StdRandom.uniform(1, N + 1);
                int k = StdRandom.uniform(1, N + 1);
                if (!per.isOpen(j, k)) {
                    per.open(j, k);
                    xArray[i]++;
                }
            }
            xArray[i] /= (N * N);
        }
        StdOut.println("mean                    = " + mean());
        StdOut.println("stddev                  = " + stddev());
        StdOut.println("95% confidence interval = "
                + confidenceLo() + ", " + confidenceHi());
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(xArray);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(xArray);
    }
    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(times);
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(times);
    }

    // test client, described below
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        new PercolationStats(N, T);
    }
}
