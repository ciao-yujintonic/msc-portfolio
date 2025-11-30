import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private final boolean[] open;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF uf_backwash;
    private final int virtualTop;
    private final int virtualBottom;
    private int openCount;

    // Creates n-by-n grid
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        this.n = n;
        this.open = new boolean[n * n];
        this.uf = new WeightedQuickUnionUF(n * n + 2);      // top + bottom
        this.uf_backwash = new WeightedQuickUnionUF(n * n + 1); // only top

        this.virtualTop = n * n;
        this.virtualBottom = n * n + 1;

        openCount = 0;
    }

    // Converts (row, col) to 1D index
    private int toIndex(int row, int col) {
        return (row - 1) * n + (col - 1);
    }

    private void validate(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) {
            throw new IllegalArgumentException();
        }
    }

    // Opens site
    public void open(int row, int col) {
        validate(row, col);
        int index = toIndex(row, col);

        if (open[index]) return;

        open[index] = true;
        openCount++;

        // first row → connect to virtual top
        if (row == 1) {
            uf.union(index, virtualTop);
            uf_backwash.union(index, virtualTop);
        }

        // last row → connect to virtual bottom (only in uf)
        if (row == n) {
            uf.union(index, virtualBottom);
        }

        // connect to neighbors
        connectIfOpen(row, col, row - 1, col); // up
        connectIfOpen(row, col, row + 1, col); // down
        connectIfOpen(row, col, row, col - 1); // left
        connectIfOpen(row, col, row, col + 1); // right
    }

    private void connectIfOpen(int r1, int c1, int r2, int c2) {
        // out of bounds → ignore
        if (r2 < 1 || c2 < 1 || r2 > n || c2 > n) return;

        if (isOpen(r2, c2)) {
            int idx1 = toIndex(r1, c1);
            int idx2 = toIndex(r2, c2);
            uf.union(idx1, idx2);
            uf_backwash.union(idx1, idx2);
        }
    }

    // is site open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return open[toIndex(row, col)];
    }

    // is site full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int idx = toIndex(row, col);
        return uf_backwash.find(idx) == uf_backwash.find(virtualTop);
    }

    public int numberOfOpenSites() {
        return openCount;
    }

    public boolean percolates() {
        return uf.find(virtualTop) == uf.find(virtualBottom);
    }
}
