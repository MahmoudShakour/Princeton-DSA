/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private LineSegment[] lineSegments;

   

    public FastCollinearPoints(Point[] points) {
         if (points == null) {
            throw new IllegalArgumentException();
        }
        Point[] tempPoints = Arrays.copyOf(points, points.length);        
        Point[] copiedPoints = Arrays.copyOf(points, points.length);


        if (handleBadPoints(copiedPoints)) {
            throw new IllegalArgumentException();
        }

        List<LineSegment> tempSegments = new ArrayList<>();

       

        for (int i = 0; i < copiedPoints.length; i++) {
            Arrays.sort(tempPoints);
            Arrays.sort(tempPoints, copiedPoints[i].slopeOrder());
            double slope = Double.NEGATIVE_INFINITY;
            int start = 0;
            boolean isValid = false;
            for (int j = 1; j < tempPoints.length; j++) {
                double currentSlope = copiedPoints[i].slopeTo(tempPoints[j]);
                if (currentSlope != slope) {
                    if (isValid && (j - 1 - start + 1 >= 3)) {
                        tempSegments.add(new LineSegment(copiedPoints[i], tempPoints[j - 1]));
                    }
                    start = j;
                    slope = currentSlope;
                    isValid = (copiedPoints[i].compareTo(tempPoints[j]) < 0);
                }
            }
            if (isValid && (tempPoints.length - 1 - start + 1 >= 3)) {
                tempSegments.add(new LineSegment(copiedPoints[i], tempPoints[tempPoints.length - 1]));
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

        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
