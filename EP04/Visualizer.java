/******************************************************************************
  *  Name:    Bill Zhang
  *  NetID:   wyzhang
  * 
  *  Description: Visualizes a 2dtree implementation using a KdTreeST's level
  *   order traversal. Use this to check your KdTreeST against the reference.
  * 
  *  Notes: Only handles input within the unit square.
  *   Assumes input is at least 1 point. This program has not been thoroughly
  *   tested with incorrect level order traversals! 
  *   
  *   If your execution hits an exception, your traversal is probably incorrect.
  * 
  *  Compilation:  javac-algs4 Visualizer.java
  *  Execution:    java-algs4 Visualizer input.txt
  *  Dependencies: KdTreeST.java
  * 
  ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;

public class Visualizer {
    // Enhanced Point with more information
    private static class PointE { 
        private Point2D p;
        private boolean vert;
        private RectHV lb; // bounding boxes on each side
        private RectHV rt;
        private PointE(Point2D p, boolean vert, RectHV lb, RectHV rt) {
            this.p = p;
            this.vert = vert;
            this.lb = lb;
            this.rt = rt;
        }
    }
    // draws a segment, point, and displays coordinate
    private static void drawSegment(PointE pointE, Point2D p, RectHV rect) {
        if (pointE.vert) {
            StdDraw.setPenRadius(0.005);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(p.x(), rect.ymin(), p.x(), rect.ymax()); // vertical line
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.015);
            StdDraw.point(p.x(), p.y());
            StdDraw.textLeft(p.x() + 0.01, p.y() + 0.025, "(" + p.x() + ", " + p.y() + ")");
        }
        else {
            StdDraw.setPenRadius(0.005);
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(rect.xmin(), p.y(), rect.xmax(), p.y()); // horizontal line
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.015);
            StdDraw.point(p.x(), p.y());
            StdDraw.textLeft(p.x() + 0.01, p.y() + 0.025, "(" + p.x() + ", " + p.y() + ")");
        }
    }
    
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        StdDraw.enableDoubleBuffering();

        // resize font to be smaller
        StdDraw.setFont(StdDraw.getFont().deriveFont(10.0f));  
        
        KdTreeST<Integer> kdtree = new KdTreeST<Integer>();
        int n = 0; // number of points
        
        // obtain level order traveral from KdTreeST and store in points
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.put(p, i);
            n++;
        }
        
        Point2D[] points = new Point2D[n];
        int count = 0;
        for (Point2D p: kdtree.points()) {
            points[count] = p;
            count++;
        }
        
        Queue<PointE> queue = new Queue<PointE>(); // queue of candidates for parent
        
        // dimensions of unit square, global min/max dimensions
        double minx = 0;
        double maxx = 1;
        double miny = 0;
        double maxy = 1;
        
        // Sets window to slightly larger than unit square, for visual clarity
        StdDraw.setXscale(-0.02, 1.02);
        StdDraw.setYscale(-0.02, 1.02);
        
        // handles root element, special case
        Point2D p = points[0]; // root 
        RectHV lb = new RectHV(minx, miny, p.x(), maxy); 
        RectHV rt = new RectHV(p.x(), miny, maxx, maxy); 
        
        queue.enqueue(new PointE(p, true, lb, rt));
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.line(p.x(), rt.ymin(), p.x(), rt.ymax()); // this is a vertical line for root
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.015);
        StdDraw.point(p.x(), p.y());
        StdDraw.textLeft(p.x() + 0.01, p.y() + 0.025, "(" + p.x() + ", " + p.y() + ")");
        
        boolean left = false;
        int current = 1; // current index
        
        // processes all elements in points
        while (current < n) {
            Point2D point = points[current]; // point to examine
            PointE pointE = queue.peek();
            // boolean flag = false;
            boolean leftEntered = false;
            // no left child and point could be a left child
            if (!left && pointE.lb.contains(point)) {
                // if vert and on right side of left box
                if (pointE.lb.xmax() != point.x()) {
                    left = true;
                    leftEntered = true;
                    
                    RectHV newlb, newrt;
                    // if pointE divides things vertically
                    if (pointE.vert) {
                        // xmin, ymin
                        newlb = new RectHV(pointE.lb.xmin(), pointE.lb.ymin(), pointE.lb.xmax(), point.y());
                        newrt = new RectHV(pointE.lb.xmin(), point.y(), pointE.lb.xmax(), pointE.lb.ymax());
                    }
                    else { // if horizontal division
                        newlb = new RectHV(pointE.lb.xmin(), pointE.lb.ymin(), point.x(), pointE.lb.ymax());
                        newrt = new RectHV(point.x(), pointE.lb.ymin(), pointE.lb.xmax(), pointE.lb.ymax());
                    }
                    
                    PointE newPointE = new PointE(point, !pointE.vert, newlb, newrt);
                    queue.enqueue(newPointE);
                    drawSegment(newPointE, point, pointE.lb);
                    
                    current++;
                }
            }
            if (!leftEntered) {
                // no left child, check if right child
                if (pointE.rt.contains(point)) {
                    RectHV newlb;
                    RectHV newrt;
                    if (pointE.vert) {
                        newlb = new RectHV(pointE.rt.xmin(), pointE.rt.ymin(), pointE.rt.xmax(), point.y());
                        newrt = new RectHV(pointE.rt.xmin(), point.y(), pointE.rt.xmax(), pointE.rt.ymax());
                    }
                    else {
                        newlb = new RectHV(pointE.rt.xmin(), pointE.rt.ymin(), point.x(), pointE.rt.ymax());
                        newrt = new RectHV(point.x(), pointE.rt.ymin(), pointE.rt.xmax(), pointE.rt.ymax());
                    }
                    
                    PointE newPointE = new PointE(point, !pointE.vert, newlb, newrt);
                    // StdOut.println("pointE " + point + !pointE.vert); 
                    queue.enqueue(newPointE);
                    drawSegment(newPointE, point, pointE.rt);
                    
                    current++;
                }
                // remove and move to next level
                queue.dequeue();
                left = false;
            }
            
        }
        StdDraw.show();
    }
}
