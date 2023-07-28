/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    // finds all line segments containing 4 points

    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        Point[] copiedPoints = Arrays.copyOf(points, points.length);
        if (handleBadPoints(copiedPoints)) {
            throw new IllegalArgumentException();
        }

        List<LineSegment> tempSegments = new ArrayList<>();
        for (int i = 0; i < copiedPoints.length; i++) {
            for (int j = i + 1; j < copiedPoints.length; j++) {
                for (int k = j + 1; k < copiedPoints.length; k++) {
                    for (int w = k + 1; w < copiedPoints.length; w++) {
                        if (copiedPoints[i].slopeTo(copiedPoints[j]) == copiedPoints[j].slopeTo(copiedPoints[k])
                                && copiedPoints[j].slopeTo(copiedPoints[k]) == copiedPoints[k].slopeTo(copiedPoints[w])
                                && copiedPoints[i].slopeTo(copiedPoints[j]) == copiedPoints[j]
                                        .slopeTo(copiedPoints[k])) {
                            tempSegments.add(new LineSegment(copiedPoints[i], copiedPoints[w]));
                        }
                    }
                }
            }
        }
        lineSegments = new LineSegment[tempSegments.size()];
        for (int i = 0; i < tempSegments.size(); i++) {
            lineSegments[i] = tempSegments.get(i);
        }
    }

    private boolean handleBadPoints(Point[] points) {
        if (points == null)
            return true;
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                return true;
        }
        Arrays.sort(points);
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                return true;
            }
        }
        return false;
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegments, lineSegments.length);

    }

    public static void main(String[] args) {

        // read the n points from a file

        int n = StdIn.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
