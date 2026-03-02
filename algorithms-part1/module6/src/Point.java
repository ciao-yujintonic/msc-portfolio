import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        if (that == null) throw new NullPointerException("argument is null");
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public int compareTo(Point that) {
        if (that == null) throw new NullPointerException("argument is null");
        if (this.y != that.y) return Integer.compare(this.y, that.y);
        return Integer.compare(this.x, that.x);
    }

    public double slopeTo(Point that) {
        if (that == null) throw new NullPointerException("argument is null");

        int dx = that.x - this.x;
        int dy = that.y - this.y;

        if (dx == 0 && dy == 0) return Double.NEGATIVE_INFINITY;
        if (dx == 0) return Double.POSITIVE_INFINITY;
        if (dy == 0) return +0.0;

        return (double) dy / (double) dx;
    }

    public Comparator<Point> slopeOrder() {
        return (p1, p2) -> Double.compare(this.slopeTo(p1), this.slopeTo(p2));
    }
}

