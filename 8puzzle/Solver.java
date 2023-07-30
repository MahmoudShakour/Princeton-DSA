import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private final Comparator<Node> BY_MANHATTAN = new ManhattanComparator();
    // private final Comparator<Node> BY_HAMMING = new HammingComparator();
    private MinPQ<Node> pqOriginal;
    private MinPQ<Node> pqTwin;
    private boolean isOriginalSolved;
    private int numberOfMoves;
    private Node lastNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        pqOriginal = new MinPQ<>(BY_MANHATTAN);
        pqTwin = new MinPQ<>(BY_MANHATTAN);
        isOriginalSolved = false;
        numberOfMoves = -1;
        lastNode = null;

        pqOriginal.insert(new Node(initial, 0, null));
        pqTwin.insert(new Node(initial.twin(), 0, null));

        while (!pqOriginal.isEmpty()) {

            Node originalNode = pqOriginal.delMin();
            Node twinNode = pqTwin.delMin();

            if (originalNode.board.isGoal()) {
                isOriginalSolved = true;
                numberOfMoves = originalNode.moves;
                lastNode = originalNode;
                break;
            } else if (twinNode.board.isGoal()) {
                isOriginalSolved = false;
                break;
            }

            for (Board b : originalNode.board.neighbors()) {
                if (originalNode.previous == null || (!b.equals(originalNode.previous.board))) {
                    pqOriginal.insert(new Node(b, originalNode.moves + 1, originalNode));
                }
            }

            for (Board b : twinNode.board.neighbors()) {
                if (twinNode.previous == null || b != twinNode.previous.board) {
                    pqTwin.insert(new Node(b, twinNode.moves + 1, twinNode));
                }
            }
        }

    }

    // private class HammingComparator implements Comparator<Node> {

    // @Override
    // public int compare(Node arg0, Node arg1) {
    // return (arg0.board.hamming() + arg0.moves) - (arg1.board.hamming() +
    // arg1.moves);
    // }

    // }

    private class ManhattanComparator implements Comparator<Node> {

        @Override
        public int compare(Node arg0, Node arg1) {
            return (arg0.manhattan + arg0.moves) - (arg1.manhattan + arg1.moves);
        }
    }

    private class Node {

        private final Board board;
        private final int moves;
        private final Node previous;
        private final int manhattan;

        public Node(Board board, int moves, Node previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.manhattan = board.manhattan();
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isOriginalSolved;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return numberOfMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if(isOriginalSolved==false){
            return null;
        }
        Stack<Board> q = new Stack<>();
        Node b = lastNode;
        while (b != null) {
            q.push(b.board);
            b = b.previous;
        }
        return q;
    }

    // test client (see below)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
