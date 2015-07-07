/**
 * Created by earne on 6/29/14.
 */
public class Percolation {
    private boolean[][] grid;
    private final int size;
    private WeightedQuickUnionUF wqu;
    private final int virtualTOP;
    private final int virtualBottom;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        size = N;
        virtualTOP = N * N;
        virtualBottom = N * N + 1;
        grid = new boolean[N][N];
        wqu = new WeightedQuickUnionUF(N * N + 2);
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        if (i <= 0 || j <= 0 || i > size || j > size)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (!grid[i - 1][j - 1]) {
            grid[i - 1][j - 1] = true;
            int CURRENT = size * (i - 1) + j - 1;
            int UP = size * (i - 2) + j - 1;
            int DOWN = size * i + j - 1;
            int LEFT = size * (i - 1) + j - 2;
            int RIGHT = size * (i - 1) + j;
            if ((i - 2) >= 0 && grid[i - 2][j - 1])  wqu.union(CURRENT, UP);
            if (i < size && grid[i][j - 1]) wqu.union(CURRENT, DOWN);
            if ((j - 2) >= 0 && grid[i - 1][j - 2]) wqu.union(CURRENT, LEFT);
            if (j < size && grid[i - 1][j]) wqu.union(CURRENT, RIGHT);
            if (i == 1) wqu.union(CURRENT, virtualTOP);
            if (i == size) wqu.union(CURRENT, virtualBottom);
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        if (i <= 0 || j <= 0 || i > size || j > size)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        else return grid[i - 1][j - 1];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        if (i <= 0 || j <= 0 || i > size || j > size)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        else {
            int CURRENT = size * (i - 1) + j - 1;
            if (isOpen(i, j))
                return wqu.connected(CURRENT, virtualTOP);
            else
                return false;
        }
    }

    // does the system percolate?
    public boolean percolates() {
        if (size == 1)
            return isOpen(1, 1);
        return wqu.connected(virtualTOP, virtualBottom);
    }
}
