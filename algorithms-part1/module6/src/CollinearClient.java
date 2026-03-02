import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class CollinearClient {
    public static void main(String[] args) {
        if (args.length == 0) throw new IllegalArgumentException("expected input file path");

        Point[] points = readPoints(args[0]);

        BruteCollinearPoints brute = new BruteCollinearPoints(points);
        FastCollinearPoints fast = new FastCollinearPoints(points);

        StdOut.println("Brute segments: " + brute.numberOfSegments());
        for (LineSegment s : brute.segments()) StdOut.println(s);

        StdOut.println();
        StdOut.println("Fast segments: " + fast.numberOfSegments());
        for (LineSegment s : fast.segments()) StdOut.println(s);

        StdOut.println();
        StdOut.println("Fast segments (sorted):");
        LineSegment[] segs = fast.segments();
        Arrays.sort(segs, (a, b) -> a.toString().compareTo(b.toString()));
        for (LineSegment s : segs) StdOut.println(s);
    }

    private static Point[] readPoints(String filePath) {
        In in = new In(filePath);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        return points;
    }
}

