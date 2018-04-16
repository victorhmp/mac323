import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] openSite;
    private int top = 0;
    private int bottom;
    private int N;
    private WeightedQuickUnionUF quickUF;
    private int open;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        N = n;
        bottom = n * n + 1;
        quickUF = new WeightedQuickUnionUF(n * n + 2);
        openSite = new boolean[n][n];
        this.open = 0;
    }

    public void open(int i, int j) {
        if (!validate(i, j)) {
            throw new IllegalArgumentException();
        }
        openSite[i - 1][j - 1] = true;
        if (i == 1) {
            quickUF.union(getIndex(i, j), top);
        }
        if (i == N) {
            quickUF.union(getIndex(i, j), bottom);
        }

        if (j > 1 && isOpen(i, j - 1)) {
            quickUF.union(getIndex(i, j), getIndex(i, j - 1));
        }
        if (j < N && isOpen(i, j + 1)) {
            quickUF.union(getIndex(i, j), getIndex(i, j + 1));
        }
        if (i > 1 && isOpen(i - 1, j)) {
            quickUF.union(getIndex(i, j), getIndex(i - 1, j));
        }
        if (i < N && isOpen(i + 1, j)) {
            quickUF.union(getIndex(i, j), getIndex(i + 1, j));
        }

        open++;
    }

    private boolean validate (int i, int j) {
        if (0 < i && i <= N && 0 < j && j <= N)
            return true;
        else
            return false;
    }

    public boolean isOpen(int i, int j) {
        if (!validate(i, j)) {
            throw new IllegalArgumentException();
        }
        return openSite[i - 1][j - 1];
    }

    public boolean isFull(int i, int j) {
        if (!validate(i, j)) {
            throw new IllegalArgumentException();
        }
        return quickUF.connected(top, getIndex(i, j));
    }

    public int numberOfOpenSites() {
        return open;
    }

    public boolean percolates() {
        if (N == 1) return true;
        return quickUF.connected(top, bottom);
    }

    private int getIndex(int i, int j) {
        if (!validate(i, j)) {
            throw new IllegalArgumentException();
        }
        return N * (i - 1) + j;
    }
}