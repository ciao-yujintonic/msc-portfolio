import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class PuzzleClient {
    public static void main(String[] args) {
        if (args.length == 0) throw new IllegalArgumentException("expected input file path");

        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);

        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
            return;
        }

        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board b : solver.solution()) {
            StdOut.println(b);
        }
    }
}

