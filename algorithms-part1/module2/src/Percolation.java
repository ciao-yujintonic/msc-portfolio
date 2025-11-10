
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int n;
    private boolean[] open;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufFull;
    private int top;
    private int bottom;
    private int openCount;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;
        open = new boolean[n * n];

        uf = new WeightedQuickUnionUF(n * n + 2);
        ufFull = new WeightedQuickUnionUF(n * n + 1);

        top = n * n;
        for (int i = 0; i < n; i++) {
            uf.union(i, top);
            ufFull.union(i, top);
        }

        bottom = n * n + 1;
        for (int i = 0; i < n; i++) {
            uf.union(n * (n - 1) - i, bottom);
        }

    }

    private void exceptionCheck(int r, int c) {
        if (r < 1 || c < 1 || n < r || n < c) {
            throw new IllegalArgumentException();
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        exceptionCheck(row, col);
        int index = (row - 1) * n + col - 1;
        if (open[index]) {
            return;
        }
        open[index] = true;
        openCount++;

        connect(row, col, row - 1, col);
        connect(row, col, row + 1, col);
        connect(row, col, row, col - 1);
        connect(row, col, row, col + 1);
    }

    private void connect(int row1, int col1, int row2, int col2) {
        if (row2 < 1 || col2 < 1 || n < row2 || n < col2) {
            return;
        }

        if (isOpen(row2, col2)) {
            int index1 = (row1 - 1) * n + col1 - 1;
            int index2 = (row2 - 1) * n + col2 - 1;
            uf.union(index1, index2);
            ufFull.union(index1, index2);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        exceptionCheck(row, col);
        int index = (row - 1) * n + col - 1;
        return open[index];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        exceptionCheck(row, col);
        int index = (row - 1) * n + col - 1;
        return ufFull.find(index) == ufFull.find(top);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(top) == uf.find(bottom);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(3);

        p.open(1, 1);
        System.out.println("percolates? " + p.percolates());   // false

        p.open(2, 1);
        System.out.println("percolates? " + p.percolates());   // false

        p.open(3, 1);
        System.out.println("percolates? " + p.percolates());   // true

        System.out.println("# of open sites = " + p.numberOfOpenSites());  // 3

        System.out.println("isFull(3,1) = " + p.isFull(3, 1));  // true
    }
}
