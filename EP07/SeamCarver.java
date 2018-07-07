/**
 * SeamCarver
 */
public class SeamCarver {

    public SeamCarver(Picture picture) {
        // Constructor
    }

    public Picture picture() {
        // current picture
    }

    public int width() {
        // width of current picture
    }

    public int height() {
        // Height of current picture
    }

    public double energy(int x, int y) {
        // energy of pixel at column x and row y
    }

    public int[] findHorizontalSeam() {
        // sequence of indices for horizontal seam
    }
    
    public int[] findverticalSeam() {
        // sequence of indices for vertical seam
    }

    public void removeHorizontalSeam(int[] seam) {
        // remove horizontal seam from current picture
    }

    public void removeVerticalSeam(int[] seam) {
        // remove vertical seam from current picture
    }

    public static void main(String[] args) {
        // unit testing
    }

}