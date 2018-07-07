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
    
    private Picture getWorkingPic() {
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

    private double getPixelEnergy(int x, int y) {
        Color left, right, up, down;

        if (x == 0) {
            up = colors[y][this.picWidth - 1];
            down = colors[y][x+1];
        }
        else if (x == this.picWidth - 1) {
            up = colors[y][x-1];
            down = colors[y][0];
        }
        else {
            up = colors[y][x-1];
            down = colors[y][x+1];
        }

        if (y == 0) {
            left = colors[this.picHeight-1][x];
            right = colors[y+1][x];
        }
        else if (y == this.picHeight - 1) {
            left = colors[y-1][x];
            right = colors[0][x];
        }
        else {
            left = colors[y-1][x];
            right = colors[y+1][x];
        }

        double Rx = right.getRed() - left.getRed();
        double Gx = right.getGreen() - left.getGreen();
        double Bx = right.getBlue() - left.getBlue();
        double Ry = down.getRed() - up.getRed(); 
        double Gy = down.getGreen() - up.getGreen(); 
        double By = down.getBlue() - up.getBlue();

        double xGradientSquared = Rx*Rx + Gx*Gx + Bx*Bx;
        double yGradientSquared = Ry*Ry + Gy*Gy + By*By;

        return Math.sqrt(xGradientSquared + yGradientSquared);
    }
    public double energy(int x, int y) {
        if (x < 0 || x > this.picWidth-1)
            throw new IllegalArgumentException();
        if (y < 0 || y > this.picHeight-1)
            throw new IllegalArgumentException();
        return getPixelEnergy(x, y);
    }

    // public int[] findHorizontalSeam() {
    //     // sequence of indices for horizontal seam
    // }
    
    private double[][] createEnergyMatrix() {
        double[][] pixelEnergy = new double[this.picHeight][this.picWidth];
        for (int i = 0; i < this.picHeight; i++) {
            for (int j = 0; j < this.picWidth; j++) {
                pixelEnergy[i][j] = energy(i, j);
            }
        }

        return pixelEnergy;
    }
    public int[] findVerticalSeam() {
        int[] pixelTo = new int[this.picWidth * this.picHeight];
        double[] energyTo = new double[this.picWidth * this.picHeight];

        double[][] pixelEnergy = createEnergyMatrix();

        for (int x = 1; x < this.picHeight - 1; x++) {
            for (int y = 1; y < this.picWidth - 1; y++) {
                energyTo[x * this.picWidth + y] = Double.POSITIVE_INFINITY;
            }
        }

        for (int x = 1; x < this.picHeight - 1; x++) {
            for (int y = 1; y < this.picWidth - 1; y++) {
                int prevPix = (x - 1) * this.picWidth + y;
                int leftPix = x * this.picWidth + y - 1;
                int midPix = x * this.picWidth + y;
                int rightPix = x * this.picWidth + y + 1;
                
                double energyLeft = pixelEnergy[x][y - 1] + energyTo[prevPix];
                double energyMid = pixelEnergy[x][y] + energyTo[prevPix];
                double energyRight = pixelEnergy[x][y + 1] + energyTo[prevPix];
                
                if (leftPix != 0 && energyTo[leftPix] > energyLeft) {
                    energyTo[leftPix] = energyLeft;
                    pixelTo[leftPix] = y;
                }
                
                if (energyTo[midPix] > energyMid) {
                    energyTo[midPix] = energyMid;
                    pixelTo[midPix] = y;
                }
                
                if (rightPix != this.picWidth - 1 && energyTo[rightPix] > energyRight) {
                    energyTo[rightPix] = energyRight;
                    pixelTo[rightPix] = y;
                }
            }
        }

        int[] verticalSeam = new int[this.picHeight];
        double lowestEnergy = Double.POSITIVE_INFINITY;

        for (int y = 1; y < this.picWidth - 1; y++) {
            int curPix = (this.picHeight - 2) * this.picWidth + y;
            if (energyTo[curPix] < lowestEnergy) {
                lowestEnergy = energyTo[curPix];
                verticalSeam[this.picHeight - 2] = y;
                verticalSeam[this.picHeight - 1] = y;
            }
        }

        for (int x = this.picHeight - 3; x >= 0; x--){
            verticalSeam[x] = pixelTo[(x + 1) * this.picWidth + verticalSeam[x + 1]];
        }

        return verticalSeam;       
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