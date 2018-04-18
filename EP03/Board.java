/**
 * Board API
 */
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import java.lang.IllegalArgumentException;

public class Board {

    private int[][] board;
    private int n;

    // constructor
    public Board(int [][] tiles) {
        this.board = tiles;
        this.n = tiles.length;
    }
    
    // Returns String representation for this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                s.append(String.format("%2d ", tileAt(row, col)));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // Returns tile at (row, col) or 0 if blank
    public int tileAt(int row, int col) {
        if ((row < 0 || row > n-1) || (col < 0 || col > n-1)) {
            throw new IllegalArgumentException();
        }
        return board[row][col];
    }

    // Returns board size
    public int size() {
        return this.n;
    }

    // Returns number of tiles that are out of place
    public int hamming() {
        int tile = 1;
        int sum = 0;
        for (int i=0;i<n;i++) {
            for (int j=0;j<n;j++) {
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
        for (int i=0;i<n;i++) {
            for (int j = 0; j<n; j++) {
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
        int destI = this.tileAt(i, j) / n;
        int destJ = this.tileAt(i, j) % n;

        if (destJ == 0) { 
            // means this tile is a multiple of n
            // so it should "go back" a position
            destI -= 1;
            destJ = n-1;
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
        for (int i=0;i<n;i++) {
            for(int j = 0; j<n;j++) {
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
        int[][] newBoard = new int[this.n][this.n];
        for (int i=0;i<n;i++) {
            for (int j = 0; j < n; j++) {
                newBoard[i][j] = origin[i][j];
            }
        }

        return newBoard;
    }

    // Returns Iterable for all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> returnStack = new Stack<Board>();
        int zeroI = 0;
        int zeroJ = 0;
        for (int i=0;i<n;i++) {
            for (int j = 0;j<n;j++){
                if (this.tileAt(i, j) == 0) {
                    zeroI = i;
                    zeroJ = j;
                }
            }
        }
        if (zeroI > 0) {
            int[][] newNeighbor = boardCopy(this.board);
            swapTile(newNeighbor, zeroI-1, zeroJ, zeroI, zeroJ);
            returnStack.push(new Board(newNeighbor));
        }
        if (zeroJ > 0) {
            int[][] newNeighbor = boardCopy(this.board);
            swapTile(newNeighbor, zeroI, zeroJ-1, zeroI, zeroJ);
            returnStack.push(new Board(newNeighbor));
        }
        if (zeroI < n-1) {
            int[][] newNeighbor = boardCopy(this.board);
            swapTile(newNeighbor, zeroI+1, zeroJ, zeroI, zeroJ);
            returnStack.push(new Board(newNeighbor));
        }
        if (zeroJ < n-1) {
            int[][] newNeighbor = boardCopy(this.board);
            swapTile(newNeighbor, zeroI, zeroJ + 1, zeroI, zeroJ);
            returnStack.push(new Board(newNeighbor));
        }

        return returnStack;
    }

    // Returns true if this board is solvable
    public boolean isSolvable() {
        // counting inversions
        int inversions = 0;

        // save the row in which zero is placed
        int zeroRow = 0;

        for (int i=0;i<n;i++) {
            for (int j=0;j<n;j++) {
                if (this.tileAt(i, j) == 0){
                    zeroRow = i;
                    continue;
                }
                int startPoint = j;
                for (int x = i; x < n; x++) {
                    for (int y = startPoint; y < n; y++) {
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