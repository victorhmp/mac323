/**
 * Board API
 */
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import java.lang.IllegalArgumentException;

public class Board {

    private int[][] board;
    private int N;

    // constructor
    public Board(int [][] tiles) {
        N = tiles.length;
        board = tiles;
    }
    
    // Returns String representation for this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                s.append(String.format("%2d ", tileAt(row, col)));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // Returns tile at (row, col) or 0 if blank
    public int tileAt(int row, int col) {
        if ((row < 0 || row > N-1) || (col < 0 || col > N-1)) {
            throw new IllegalArgumentException();
        }
        return board[row][col];
    }

    // Returns board size
    public int size() {
        return this.N;
    }

    // Returns number of tiles that are out of place
    public int hamming() {
        int tile = 1;
        int sum = 0;
        for (int i=0;i<N;i++) {
            for (int j=0;j<N;j++) {
                if (this.tileAt(i, j) != tile && this.tileAt(i, j) != 0) {
                    sum++;
                }
                tile++;
            }
        }

        return sum;
    }

    // Returns sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manSum = 0;
        for (int i=0;i<N;i++) {
            for (int j = 0; j<N; j++) {
                if (this.tileAt(i, j) != 0) {
                    manSum += manCalc(i, j);
                }
            }
        }
        return manSum;
    }
    // Helper funciction to calculate Manhattan distances
    private int manCalc(int i, int j) {
        // Get expected (i, j) for this tile
        int destI = this.tileAt(i, j) / N;
        int destJ = this.tileAt(i, j) % N;

        if (destJ == 0) { 
            // means this tile is a multiple of n
            // so it should "go back" a position
            destI -= 1;
            destJ = N-1;
        }
        else {
            // adjustment to account for multiples always
            // beeing in the last col
            destJ -= 1; 
        }

        return Math.abs(i - destI) + Math.abs(j - destJ);
    }

    // Returns true if this board is the goal board
    public boolean isGoal() {
        // If no tiles need to be moved, this is the goal board
        return this.manhattan() == 0;
    }

    // Returns true if this board equals Object (y)
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        boolean isEqual = true;
        for (int i=0;i<N;i++) {
            for(int j = 0; j<N;j++) {
                if (this.tileAt(i, j) != (that.tileAt(i, j))){
                    isEqual = false;
                    break;
                }
            }
        }

        return isEqual;
    }

    private void swapTile(int[][] ope,int i, int j, int x, int y) {
        int tmp = ope[i][j];
        ope[i][j] = ope[x][y];
        ope[x][y] = tmp;
    }

    private int[][] boardCopy(int[][] origin) {
        int[][] newBoard = new int[this.N][this.N];
        for (int i=0;i<N;i++) {
            for (int j = 0; j < N; j++) {
                newBoard[i][j] = origin[i][j];
            }
        }

        return newBoard;
    }

    // Returns Iterable for all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> returnQueue = new Queue<Board>();
        int zeroI = 0;
        int zeroJ = 0;
        for (int i=0;i<N;i++) {
            for (int j = 0;j<N;j++){
                if (this.tileAt(i, j) == 0) {
                    zeroI = i;
                    zeroJ = j;
                }
            }
        }
        if (zeroI > 0) {
            int[][] newNeighbor = boardCopy(this.board);
            swapTile(newNeighbor, zeroI-1, zeroJ, zeroI, zeroJ);
            returnQueue.enqueue(new Board(newNeighbor));
        }
        if (zeroJ > 0) {
            int[][] newNeighbor = boardCopy(this.board);
            swapTile(newNeighbor, zeroI, zeroJ-1, zeroI, zeroJ);
            returnQueue.enqueue(new Board(newNeighbor));
        }
        if (zeroI < N-1) {
            int[][] newNeighbor = boardCopy(this.board);
            swapTile(newNeighbor, zeroI+1, zeroJ, zeroI, zeroJ);
            returnQueue.enqueue(new Board(newNeighbor));
        }
        if (zeroJ < N-1) {
            int[][] newNeighbor = boardCopy(this.board);
            swapTile(newNeighbor, zeroI, zeroJ + 1, zeroI, zeroJ);
            returnQueue.enqueue(new Board(newNeighbor));
        }

        return returnQueue;
    }

    // Returns true if this board is solvable
    public boolean isSolvable() {
        // counting inversions
        int inversions = 0;

        // save the row in which zero is placed
        int zeroRow = 0;

        for (int i=0;i<N;i++) {
            for (int j=0;j<N;j++) {
                if (this.tileAt(i, j) == 0){
                    zeroRow = i;
                    continue;
                }
                int startPoint = j;
                for (int x = i; x < N; x++) {
                    for (int y = startPoint; y < N; y++) {
                        if ((this.tileAt(x, y) < this.tileAt(i, j)) && this.tileAt(x, y)!=0)
                            inversions++;
                    }
                    startPoint = 0;
                }
            }
        }
        if (this.size() % 2 != 0) {
            if (inversions % 2 == 0) return true;
            else return false;
        }
        if ((inversions + zeroRow) % 2 != 0) return true;
        else return false;
    }

    // Unit testing
    public static void main(String[] args) {
        int[][] tiles = {{0,1,3},{4,2,5},{7,8,6}};
        Board board = new Board(tiles);
        int [][] diffTiles = {{1,2,3},{4,5,6},{8,7,0}};
        Board board2 = new Board(diffTiles);
        
        StdOut.println(board);
        StdOut.println(board.size());
        StdOut.println(board.hamming());
        StdOut.println(board.equals(board2));

        for (Board neighbor: board2.neighbors()) {
            StdOut.println(neighbor);
        }

        StdOut.println(board.isSolvable());
    }
}