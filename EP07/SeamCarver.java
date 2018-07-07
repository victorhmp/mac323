import java.awt.Color;
import edu.princeton.cs.algs4.Picture;

/**
 * SeamCarver
 */
public class SeamCarver {

    private Picture workingPic;
    private int picWidth;
    private int picHeight;
    private Color[][] colors;

    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException();
        this.workingPic = picture;
        this.picHeight = picture.height();
        this.picWidth = picture.width();
        this.colors = new Color[picHeight][picWidth];

        for (int i = 0; i < this.picHeight; i++) {
            for (int j = 0; j < this.picWidth; j++) {
                this.colors[i][j] = this.workingPic.get(j, i);
            }
        }
    }

    
    private getWorkingPic() {
        return this.workingPic;
    }
    public Picture picture() {
        return getWorkingPic();
    }

    private int getPicWidth() {
        return this.picWidth;
    }
    public int width() {
        return getPicWidth();
    }

    private int getPicHeight() {
        return this.picHeight;
    }
    public int height() {
        return getPicHeight();
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