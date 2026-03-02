import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private final Point[] points;
    private LineSegment[] cached;

    public FastCollinearPoints(Point[] points) {
        this.points = validatedSortedCopy(points);
    }

    public int numberOfSegments() {
        return segments().length;
    }

    public LineSegment[] segments() {
        if (cached != null) return Arrays.copyOf(cached, cached.length);
        if (points.length < 4) {
            cached = new LineSegment[0];
            return new LineSegment[0];
        }

        List<LineSegment> found = new ArrayList<>();
        int n = points.length;

        for (int i = 0; i < n; i++) {
            Point origin = points[i];

            Point[] bySlope = Arrays.copyOf(points, n);
            Arrays.sort(bySlope, origin.slopeOrder());

            int start = 1; // index 0 will be origin (slope = -inf)
            while (start < n) {
                double slope = origin.slopeTo(bySlope[start]);
                int end = start;
                while (end + 1 < n && Double.compare(slope, origin.slopeTo(bySlope[end + 1])) == 0) {
                    end++;
                }

                int runLength = end - start + 1;
                if (runLength >= 3) {
                    Point min = origin;
                    Point max = origin;
                    for (int k = start; k <= end; k++) {
                        Point p = bySlope[k];
                        if (p.compareTo(min) < 0) min = p;
                        if (p.compareTo(max) > 0) max = p;
                    }

                    if (origin.compareTo(min) == 0) {
                        found.add(new LineSegment(min, max));
                    }
                }

                start = end + 1;
            }
        }

        cached = found.toArray(new LineSegment[0]);
        return Arrays.copyOf(cached, cached.length);
    }

    private static Point[] validatedSortedCopy(Point[] input) {
        if (input == null) throw new IllegalArgumentException();

        Point[] copy = new Point[input.length];
        for (int i = 0; i < input.length; i++) {
            if (input[i] == null) throw new IllegalArgumentException();
            copy[i] = input[i];
        }

        Arrays.sort(copy);
        for (int i = 1; i < copy.length; i++) {
            if (copy[i].compareTo(copy[i - 1]) == 0) throw new IllegalArgumentException();
        }
        return copy;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) p.draw();
        StdDraw.show();

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

