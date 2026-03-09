import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private final boolean solvable;
    private final SearchNode goalNode;

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        SearchResult result = runAStar(initial);
        this.solvable = result.solved;
        this.goalNode = result.goal;
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (!solvable) return -1;
        return goalNode.moves;
    }

    public Iterable<Board> solution() {
        if (!solvable) return null;
        List<Board> path = new ArrayList<>();
        SearchNode cursor = goalNode;
        while (cursor != null) {
            path.add(cursor.board);
            cursor = cursor.prev;
        }
        Collections.reverse(path);
        return path;
    }

    private SearchResult runAStar(Board start) {
        Comparator<SearchNode> byPriority = (a, b) -> {
            int cmp = Integer.compare(a.priority(), b.priority());
            if (cmp != 0) return cmp;
            return Integer.compare(a.manhattan, b.manhattan);
        };

        MinPQ<SearchNode> mainPQ = new MinPQ<>(byPriority);
        MinPQ<SearchNode> twinPQ = new MinPQ<>(byPriority);

        mainPQ.insert(new SearchNode(start, 0, null));
        twinPQ.insert(new SearchNode(start.twin(), 0, null));

        while (true) {
            SearchNode mainNode = step(mainPQ);
            if (mainNode.board.isGoal()) {
                return new SearchResult(true, mainNode);
            }

            SearchNode twinNode = step(twinPQ);
            if (twinNode.board.isGoal()) {
                return new SearchResult(false, null);
            }
        }
    }

    private SearchNode step(MinPQ<SearchNode> pq) {
        SearchNode current = pq.delMin();
        for (Board neighbor : current.board.neighbors()) {
            if (current.prev != null && neighbor.equals(current.prev.board)) continue;
            pq.insert(new SearchNode(neighbor, current.moves + 1, current));
        }
        return current;
    }

    private static final class SearchNode {
        private final Board board;
        private final int moves;
        private final int manhattan;
        private final SearchNode prev;

        private SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.manhattan = board.manhattan();
        }

        private int priority() {
            return moves + manhattan;
        }
    }

    private static final class SearchResult {
        private final boolean solved;
        private final SearchNode goal;

        private SearchResult(boolean solved, SearchNode goal) {
            this.solved = solved;
            this.goal = goal;
        }
    }

    public static void main(String[] args) {
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

