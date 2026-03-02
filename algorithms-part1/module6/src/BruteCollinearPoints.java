import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private final Point[] points;
    private LineSegment[] cached;

    public BruteCollinearPoints(Point[] points) {
        this.points = validatedSortedCopy(points);
    }

    public int numberOfSegments() {
        return segments().length;
    }

    public LineSegment[] segments() {
        if (cached != null) return Arrays.copyOf(cached, cached.length);

        List<LineSegment> found = new ArrayList<>();
        int n = points.length;

        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                double s1 = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < n - 1; k++) {
                    double s2 = points[i].slopeTo(points[k]);
                    if (Double.compare(s1, s2) != 0) continue;
                    for (int m = k + 1; m < n; m++) {
                        double s3 = points[i].slopeTo(points[m]);
                        if (Double.compare(s1, s3) != 0) continue;
                        found.add(new LineSegment(points[i], points[m]));
                    }
                }
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

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

