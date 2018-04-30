/**
 * PointST
 */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Queue;
import java.lang.IllegalArgumentException;

public class PointST<Value> {
  private RedBlackBST<Point2D, Value> BST;

  public PointST() {
    this.BST = new RedBlackBST<Point2D, Value>();
  }

  public boolean isEmpty() {
    return this.BST.isEmpty();
  }

  public int size(){
    return this.BST.size();
  }

  public void put(Point2D p, Value val) {
    if (p == null) throw new IllegalArgumentException();
    this.BST.put(p, val);
  }

  public Value get (Point2D p) {
    if (p == null) throw new IllegalArgumentException();
    return this.BST.get(p);
  }

  public boolean contains(Point2D p) {
    if (p == null) throw new IllegalArgumentException();
    return this.BST.contains(p);
  }

  public Iterable<Point2D> points() {
    return this.BST.keys();
  }

  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) throw new IllegalArgumentException();
    Queue<Point2D> queue = new Queue<Point2D>();
    for (Point2D i : this.points()) {
      if (rect.contains(i)) {
        queue.enqueue(i);
      } 
    }

    return queue;
  }

  public Point2D nearest(Point2D p) {
    if (p == null) throw new IllegalArgumentException();
    double minDistance = 1e23;
    Point2D nearestPoint = new Point2D(0, 0);
    for (Point2D i : this.points()) {
      if (i.distanceSquaredTo(p) < minDistance) {
        minDistance = i.distanceSquaredTo(p);
        nearestPoint = i;
      }
    }
    return nearestPoint;
  }

  public static void main(String[] args) {
    PointST<Integer> ST = new PointST<Integer>();

    Point2D p1 = new Point2D(1.0, 4.5);
    Point2D p2 = new Point2D(2.3, 4.0);
    Point2D p3 = new Point2D(1.0, 3.0);
    Point2D p4 = new Point2D(2.5, 4.0);
    Point2D p5 = new Point2D(5.0, 3.0);
    Point2D p6 = new Point2D(3.6, 2.5);
    Point2D p7 = new Point2D(2.1, 4.0);

    System.out.println("Is the ST empty? " + ST.isEmpty());

    ST.put(p1, 1);
    ST.put(p2, 2);
    ST.put(p3, 3);
    ST.put(p4, 4);
    ST.put(p5, 5);
    ST.put(p6, 6);
    ST.put(p7, 7);
    System.out.println("Is the ST empty? " + ST.isEmpty());
    for (Point2D i : ST.points()) {
      System.out.println("Point: " + i);
    }
  }
}