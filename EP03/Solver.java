import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;

public class Solver {
    private static class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int numMoves;
        private SearchNode prev;

        public SearchNode(Board board) {
            this.board = board;
            this.numMoves = 0;
            this.prev = null;
        }

        public int compareTo(SearchNode that) {
            if ((this.board.manhattan() + this.numMoves) > (that.board.manhattan() + that.numMoves))
                return 1;
            if ((this.board.manhattan() + this.numMoves) < (that.board.manhattan() + that.numMoves))
                return -1;
            return 0;
        }
    }

    private MinPQ<SearchNode> pq;
    private int moves;
    private SearchNode endNode;

    private boolean isSolved;

    // Constructor that also finds the solution
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        if (!initial.isSolvable()) throw new IllegalArgumentException();
        
        this.isSolved = false;
        this.pq = new MinPQ<SearchNode>();

        SearchNode currBest = new SearchNode(initial);
        Board currBoard;
        pq.insert(currBest);

        while (currBest.board.hamming() > 0) {
            currBest = pq.delMin();
            currBoard = currBest.board;

            for (Board i : currBoard.neighbors()) {
                SearchNode neighbor = new SearchNode(i);
                neighbor.numMoves = currBest.numMoves + 1;
                neighbor.prev = currBest;
                
                // Optimization for A*
                if ((neighbor.prev.prev == null || !neighbor.prev.prev.board.equals(neighbor.board))) {
                    pq.insert(neighbor);
                }
            }
        }

        this.endNode = currBest;
        if (endNode.board.hamming() == 0 && endNode.board.manhattan() == 0)
            this.isSolved = true;
        else
            this.isSolved = false;
    }

    // Returns the number of moves to solve initial puzzle
    public int moves () {
        return this.endNode.numMoves;
    }

    // Returns sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        SearchNode endNodeCopy = endNode;
        Stack<Board> solution = new Stack<Board>();

        while (endNodeCopy.numMoves > 0) {
            solution.push(endNodeCopy.board);
            endNodeCopy = endNodeCopy.prev;
        }

        return solution;
    }

    // Test client
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                blocks[row][col] = in.readInt();
        Board initial = new Board(initial);

        Solver solver = new Solver(initial);
        for (Board board : solver.solution()) {
            StdOut.print(board);
        }
    }
}