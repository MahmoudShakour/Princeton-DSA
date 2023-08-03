import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;

public class PointSET {
    private SET<Point2D> st;

    // construct an empty set of points
    public PointSET() {
        st = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return st.isEmpty();
    }

    // number of points in the set
    public int size() {
        return st.size();

    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        st.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return st.contains(p);

    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : st) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        Stack<Point2D> stack = new Stack<>();
        for (Point2D point : st) {
            if (rect.contains(point)) {
                stack.push(point);
            }
        }
        return stack;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        Point2D nearestPoint = null;
        double distance = Double.MAX_VALUE;
        for (Point2D point : st) {
            if (point.distanceTo(p) < distance) {
                distance = point.distanceTo(p);
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }

    // unit testing of the methods (optional)
    // public static void main(String[] args) {
    //     PointSET ps = new PointSET();
    //     ps.insert(new Point2D(0.7, 0.2));
    //     ps.insert(new Point2D(0.5, 0.4));
    //     ps.insert(new Point2D(0.2, 0.3));
    //     ps.insert(new Point2D(0.4, 0.7));
    //     ps.insert(new Point2D(0.9, 0.6));

    //     ps.draw();
       

    // }
}