import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private final int[][] tiles;
    private final int n;
    private Board cachedTwin;

    public Board(int[][] blocks) {
        if (blocks == null) throw new IllegalArgumentException();
        this.n = blocks.length;
        this.tiles = copyOf(blocks);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n).append('\n');
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                sb.append(' ').append(tiles[row][col]);
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        int wrong = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                int value = tiles[row][col];
                if (value == 0) continue;
                int expected = goalValueAt(row, col);
                if (value != expected) wrong++;
            }
        }
        return wrong;
    }

    public int manhattan() {
        int dist = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                int value = tiles[row][col];
                if (value == 0) continue;
                int goalRow = (value - 1) / n;
                int goalCol = (value - 1) % n;
                dist += Math.abs(goalRow - row) + Math.abs(goalCol - col);
            }
        }
        return dist;
    }

    public boolean isGoal() {
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row == n - 1 && col == n - 1) {
                    if (tiles[row][col] != 0) return false;
                } else if (tiles[row][col] != goalValueAt(row, col)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return Arrays.deepEquals(this.tiles, that.tiles);
    }

    public Iterable<Board> neighbors() {
        int[] blank = findBlank();
        int br = blank[0];
        int bc = blank[1];

        List<Board> result = new ArrayList<>();
        addNeighborIfInBounds(result, br, bc, br, bc - 1); // left
        addNeighborIfInBounds(result, br, bc, br, bc + 1); // right
        addNeighborIfInBounds(result, br, bc, br - 1, bc); // up
        addNeighborIfInBounds(result, br, bc, br + 1, bc); // down
        return result;
    }

    public Board twin() {
        if (cachedTwin != null) return cachedTwin;

        int r1 = 0, c1 = 0;
        int r2 = 0, c2 = 1;

        if (tiles[r1][c1] == 0 || tiles[r2][c2] == 0) {
            // choose a different row if leading entries include 0
            int row = (n > 1) ? 1 : 0;
            r1 = row;
            c1 = 0;
            r2 = row;
            c2 = 1;
        }

        if (tiles[r1][c1] == 0 || tiles[r2][c2] == 0) {
            // fall back to randomized pair selection that avoids zeros
            int[] first = randomNonZero();
            int[] second = randomNonZero();
            while (first[0] == second[0] && first[1] == second[1]) {
                second = randomNonZero();
            }
            r1 = first[0];
            c1 = first[1];
            r2 = second[0];
            c2 = second[1];
        }

        int[][] copy = copyOf(tiles);
        swap(copy, r1, c1, r2, c2);
        cachedTwin = new Board(copy);
        return new Board(copyOf(copy));
    }

    private int goalValueAt(int row, int col) {
        int value = row * n + col + 1;
        return (value == n * n) ? 0 : value;
    }

    private int[][] copyOf(int[][] src) {
        int[][] result = new int[src.length][src.length];
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, result[i], 0, src[i].length);
        }
        return result;
    }

    private int[] findBlank() {
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (tiles[row][col] == 0) return new int[] { row, col };
            }
        }
        throw new IllegalStateException("no blank tile found");
    }

    private void addNeighborIfInBounds(List<Board> acc, int br, int bc, int nr, int nc) {
        if (nr < 0 || nr >= n || nc < 0 || nc >= n) return;
        int[][] copy = copyOf(tiles);
        swap(copy, br, bc, nr, nc);
        acc.add(new Board(copy));
    }

    private void swap(int[][] a, int r1, int c1, int r2, int c2) {
        int tmp = a[r1][c1];
        a[r1][c1] = a[r2][c2];
        a[r2][c2] = tmp;
    }

    private int[] randomNonZero() {
        while (true) {
            int r = StdRandom.uniform(n);
            int c = StdRandom.uniform(n);
            if (tiles[r][c] != 0) return new int[] { r, c };
        }
    }
}

