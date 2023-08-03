import java.util.Comparator;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;

public class KdTree {

    private Node root;
    private int size;

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;

    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (this.contains(p))
            return;

        if (root == null) {
            root = new Node(p, 0);
            ++size;
            return;
        }

        insert(root, p, true);
        ++size;
    }

    private void insert(Node node, Point2D p, boolean isX) {
        Comparator<Point2D> comp = isX ? Point2D.X_ORDER : Point2D.Y_ORDER;

        if (comp.compare(node.p, p) > 0) {
            if (node.left == null) {
                node.left = new Node(p, node.level + 1);
            } else {
                insert(node.left, p, !isX);
            }

        } else if (node.right == null) {
            node.right = new Node(p, node.level + 1);
        } else {
            insert(node.right, p, !isX);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            return false;
        }

        return contains(root, p, true);
    }

    private boolean contains(Node node, Point2D p, boolean isX) {
        Comparator<Point2D> comp = isX ? Point2D.X_ORDER : Point2D.Y_ORDER;

        if (node.p.equals(p))
            return true;
        if (comp.compare(node.p, p) > 0) {
            if (node.left == null) {
                return false;
            } else {
                return contains(node.left, p, !isX);
            }

        } else if (node.right == null) {
            return false;
        } else {
            return contains(node.right, p, !isX);
        }
    }

    // draw all points to standard draw
    public void draw() {
        drawPoints(root);
    }

    private void drawPoints(Node node) {
        if (node == null)
            return;

        node.p.draw();
        drawPoints(node.left);
        drawPoints(node.right);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        Stack<Point2D> stack = new Stack<>();   
        range(root, rect, stack);
        return stack;
    }

    private void range(Node node, RectHV rect, Stack<Point2D> stack) {
        if (node == null) {
            return;
        }
        if (rect.contains(node.p)) {
            stack.push(node.p);
        }

        if (node.level % 2 == 0) {
            if (rect.xmin() <= node.p.x() && node.p.x() <= rect.xmax()) {
                range(node.left, rect, stack);
                range(node.right, rect, stack);
            } else if (rect.xmin() > node.p.x()) {
                range(node.right, rect, stack);
            } else {
                range(node.left, rect, stack);
            }
        }
        else{
            if (rect.ymin() <= node.p.y() && node.p.y() <= rect.ymax()) {
                range(node.left, rect, stack);
                range(node.right, rect, stack);
            } else if (rect.ymin() > node.p.y()) {
                range(node.right, rect, stack);
            } else {
                range(node.left, rect, stack);
            }
        }

    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (root==null||p == null) {
            throw new IllegalArgumentException();
        }
        

        return nearest(p,root.p,root);
    }

    private Point2D nearest(Point2D p, Point2D currentNearestPoint, Node n){
        if (n == null){
            return currentNearestPoint;
        }
        if (n.level % 2 == 0){
            if (p.x() > n.p.x()){ // check right sub-tree
                Point2D npr = nearest(p, n.p.distanceTo(p) < currentNearestPoint.distanceTo(p) ? n.p : currentNearestPoint, n.right);
                if (npr.distanceTo(p) > Math.abs(n.p.x() - p.x())){
                    Point2D npl = nearest(p, npr, n.left);
                    return npr.distanceTo(p) > npl.distanceTo(p) ? npl : npr;
                } else {
                    return npr;
                }
            } else { // check left sub-tree
                Point2D npl = nearest(p, n.p.distanceTo(p) < currentNearestPoint.distanceTo(p) ? n.p : currentNearestPoint, n.left);
                if (npl.distanceTo(p) > Math.abs(n.p.x() - p.x())) {
                    Point2D npr = nearest(p, npl, n.right);
                    return npr.distanceTo(p) > npl.distanceTo(p) ? npl : npr;
                } else {
                    return npl;
                }
            }
        } else {
            if (p.y() > n.p.y()) { // check up sub-tree
                Point2D npu = nearest(p, n.p.distanceTo(p) < currentNearestPoint.distanceTo(p) ? n.p : currentNearestPoint, n.right);
                if (npu.distanceTo(p) > Math.abs(n.p.y() - p.y())){
                    Point2D npd = nearest(p, npu, n.left);
                    return npu.distanceTo(p) > npd.distanceTo(p) ? npd : npu;
                } else {
                    return npu;
                }
            } else { // check down sub-tree
                Point2D npd = nearest(p, n.p.distanceTo(p) < currentNearestPoint.distanceTo(p) ? n.p : currentNearestPoint, n.left);
                if (npd.distanceTo(p) > Math.abs(n.p.y() - p.y())){
                    Point2D npu = nearest(p, npd, n.right);
                    return npu.distanceTo(p) > npd.distanceTo(p) ? npd : npu;
                } else {
                    return npd;
                }
            }
        }
    }

    private class Node {

        private Point2D p;
        private int level;
        private Node left, right;

        public Node(Point2D p, int level) {
            this.p = p;
            this.level = level;
            this.left = this.right = null;
        }

    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree kd = new KdTree();
        kd.insert(new Point2D(0.4, 0.7));
        kd.insert(new Point2D(0.4, 0.8));
        System.out.println(kd.contains(new Point2D(0.4, 0.8)));

        System.out.println(kd.size());
        // ps.draw();

    }
}