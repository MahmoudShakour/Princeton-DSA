
import edu.princeton.cs.algs4.Stack;

public class Board {

    private final int[][] board;
    private final int dimention;
    private int hamming;
    private int manhattan;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException();
        }

        dimention = tiles.length;
        board = new int[dimention][dimention];
        hamming = -1;
        manhattan = -1;

        for (int i = 0; i < dimention; i++) {
            for (int j = 0; j < dimention; j++) {
                board[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(dimention + "\n");
        for (int i = 0; i < dimention; i++) {
            for (int j = 0; j < dimention; j++) {
                str.append(String.format("%2d ", board[i][j]));
            }
            str.append("\n");
        }
        return str.toString();
    }

    // board dimension n
    public int dimension() {
        return dimention;
    }

    // number of tiles out of place
    public int hamming() {
        if (hamming != -1) {
            return hamming;
        }
        int distance = 0;

        for (int i = 0; i < dimention; i++) {
            for (int j = 0; j < dimention; j++) {
                if (board[i][j] == 0)
                    continue;

                if (board[i][j] != (i * dimention) + j + 1)
                    distance++;
            }
        }

        return hamming = distance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (manhattan != -1) {
            return manhattan;
        }
        int distance = 0;

        for (int i = 0; i < dimention; i++) {
            for (int j = 0; j < dimention; j++) {
                if (board[i][j] == 0)
                    continue;

                int tile = board[i][j];
                tile--;
                int x = tile / (dimention);
                int y = tile % (dimention);
                distance += Math.abs(i - x) + Math.abs(j - y);
            }
        }

        return manhattan = distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (!(y instanceof Board)) {
            return false;
        }
        Board that = (Board) y;

        if (that.dimention != this.dimention) {
            return false;
        }

        for (int i = 0; i < dimention; i++) {
            for (int j = 0; j < dimention; j++) {
                if (this.board[i][j] != that.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Iterable<Board> b = null;
        for (int i = 0; i < this.dimention; i++) {
            for (int j = 0; j < this.dimention; j++) {
                if (this.board[i][j] == 0) {
                    b = getNeighbors(this, i, j);
                    return b;
                }
            }
        }
        return b;
    }

    private Iterable<Board> getNeighbors(Board that, int i, int j) {
        Stack<Board> neighbors = new Stack<>();

        if (i > 0) {
            neighbors.push(clone(that, i, j, i - 1, j));
        }
        if (j > 0) {
            neighbors.push(clone(that, i, j, i, j - 1));
        }
        if (j < dimention - 1) {
            neighbors.push(clone(that, i, j, i, j + 1));
        }
        if (i < dimention - 1) {
            neighbors.push(clone(that, i, j, i + 1, j));
        }
        return neighbors;
    }

    private Board clone(Board clonedBoard, int i, int j, int x, int y) {
        int[][] board = new int[clonedBoard.dimention][clonedBoard.dimention];
        for (int ii = 0; ii < clonedBoard.dimention; ii++) {
            for (int jj = 0; jj < clonedBoard.dimention; jj++) {
                board[ii][jj] = clonedBoard.board[ii][jj];
            }
        }
        int temp = board[i][j];
        board[i][j] = board[x][y];
        board[x][y] = temp;

        Board newBoard = new Board(board);
        // System.out.println(x + " " + y);
        // System.out.println(newBoard);
        return newBoard;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twin = null;
        for (int i = 0; i < dimention; i++) {
            for (int j = 0; j < dimention - 1; j++) {
                if (board[i][j] != 0 && board[i][j + 1] != 0) {
                    twin = clone(this, i, j, i, j + 1);
                }
            }
        }
        return twin;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // Board b=new Board({{0,3,1},{0,3,1},{0,3,1}});
        int[][] a = { { 2, 3, 1 },
                { 4, 0, 5 },
                { 7, 8, 6 }
        };
        Board b = new Board(a);

        for (Board bb : b.neighbors()) {
            System.out.println(bb);
        }

    }

}
