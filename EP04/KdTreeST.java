/**
 * KdTreeST
 */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Queue;
import java.lang.IllegalArgumentException;

public class KdTreeST<Value> {
  private int N;
  private Node root;

  private class Node {
    private Point2D p;
    private Value value;
    private RectHV rect;
    private Node leftBottom;
    private Node rightUp;

    public Node(Point2D point, Value val) {
      this.p = point;
      this.value = val;
      this.leftBottom = null;
      this.rightUp = null;
      // this.rect = rect;
    }
  }

  public KdTreeST() {
    this.N = 0;
    this.root = null;
  }

  public boolean isEmpty() {
    return this.N == 0;
  }

  public int size(){
    return this.N;
  }

  public void put(Point2D p, Value val) {
    if (p == null || val == null) throw new IllegalArgumentException();
    root = put(root, p, val, true);
  }
  private Node put (Node x, Point2D point, Value val, boolean useX) {
    if (x == null){
      this.N++;
      return new Node(point, val);
    }
    else if (x.p.x() == point.x() && x.p.y() == point.y()) {
      x.value = val;
      return x;
    }
    if (useX) {
      double cmp = point.x() - x.p.x();
      if      (cmp < 0) x.leftBottom  = put(x.leftBottom,  point, val, !useX);
      else     x.rightUp = put(x.rightUp, point, val, !useX);
    }
    else {
      double cmp = point.y() - x.p.y();
      if      (cmp < 0) x.leftBottom  = put(x.leftBottom,  point, val, !useX);
      else     x.rightUp = put(x.rightUp, point, val, !useX);
    }
    return x;
  }

  public Value get (Point2D p) {
    if (p == null) throw new IllegalArgumentException();
    return get(this.root, p, true);
  }
  private Value get(Node x, Point2D p, boolean useX) {
    if (x == null) return null;
    else if (x.p.x() == p.x() && x.p.y() == p.y()) {
      return x.value;
    }
    else {
      if (useX) {
        double cmp = p.x() - x.p.x();
        if (cmp < 0) return get(x.leftBottom, p, !useX);
        else return get(x.rightUp, p, !useX);
      }
      else {
        double cmp = p.y() - x.p.y();
        if (cmp < 0) return get(x.leftBottom, p, !useX);
        else return get(x.rightUp, p, !useX);
      }
    }
  }

  public boolean contains(Point2D p) {
    if (p == null) throw new IllegalArgumentException();
    return contains(this.root, p, true);
  }
  private boolean contains(Node x, Point2D p, boolean useX) {
    if (x == null) return false;
    else if (x.p.x() == p.x() && x.p.y() == p.y()) {
      return true;
    }
    else {
      if (useX) {
        double cmp = p.x() - x.p.x();
        if (cmp < 0) return contains(x.leftBottom, p, !useX);
        else return contains(x.rightUp, p, !useX);
      }
      else {
        double cmp = p.y() - x.p.y();
        if (cmp < 0) return contains(x.leftBottom, p, !useX);
        else return contains(x.rightUp, p, !useX);
      }
    }
  }

  // public Iterable<Point2D> points() {
    
  // }

  // public Iterable<Point2D> range(RectHV rect) {
    
  // }

  // public Point2D nearest(Point2D p) {
    
  // }

  public static void main(String[] args) {
    KdTreeST<Integer> BST = new KdTreeST<Integer>();

    Point2D p1 = new Point2D(1.0, 4.5);
    Point2D p2 = new Point2D(2.3, 4.0);
    Point2D p3 = new Point2D(1.0, 3.0);
    Point2D p4 = new Point2D(2.5, 4.0);
    Point2D p5 = new Point2D(5.0, 3.0);
    Point2D p6 = new Point2D(3.6, 2.5);
    Point2D p7 = new Point2D(2.1, 4.0);
    Point2D p8 = new Point2D(2.1, 4.9);

    System.out.println("Is the KdTree empty? " + BST.isEmpty());

    BST.put(p1, 1);
    BST.put(p2, 2);
    BST.put(p3, 3);
    BST.put(p4, 4);
    BST.put(p5, 5);
    BST.put(p6, 6);
    BST.put(p7, 7);
    System.out.println("Does the KdTree contain: " + p1 + "? " + BST.contains(p1));
    System.out.println("Does the KdTree contain: " + p2 + "? " + BST.contains(p2));
    System.out.println("Does the KdTree contain: " + p3 + "? " + BST.contains(p3));
    System.out.println("Does the KdTree contain: " + p4 + "? " + BST.contains(p4));
    System.out.println("Does the KdTree contain: " + p5 + "? " + BST.contains(p5));
    System.out.println("Does the KdTree contain: " + p6 + "? " + BST.contains(p6));
    System.out.println("Does the KdTree contain: " + p7 + "? " + BST.contains(p7));
    System.out.println("Does the KdTree contain: " + p8 + "? " + BST.contains(p8));
    // for (Point2D i : ST.points()) {
    //   System.out.println("Point: " + i);
    // }
  }
}