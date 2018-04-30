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
    boolean useX = (this.size() % 2 != 0) ? true : false;
    root = put(root, p, val, useX);
    this.N++;
  }
  private Node put (Node x, Point2D point, Value val, boolean useX) {
    if (x == null) return new Node(point, val);
    if (useX) {
      int cmp = point.x().compareTo(x.p.x());
      if      (cmp < 0) x.leftBottom  = put(x.leftBottom,  point, val);
      else if (cmp > 0) x.rightUp = put(x.rightUp, point, val);
      else              x.value   = val;

      return x;
    }
    else {
      int cmp = point.y().compareTo(x.p.y());
      if      (cmp < 0) x.leftBottom  = put(x.leftBottom,  point, val);
      else if (cmp > 0) x.rightUp = put(x.rightUp, point, val);
      else              x.value   = val;

      return x;
    }
  }

  public Value get (Point2D p) {
    if (p == null) throw new IllegalArgumentException();
    return get(this.root, p);
  }
  private Value get(Node x, Point2D p, boolean useX) {
    if (x == null) return null;
    if (useX) {
      
    }
  }

  public boolean contains(Point2D p) {
    
  }

  public Iterable<Point2D> points() {
    
  }

  public Iterable<Point2D> range(RectHV rect) {
    
  }

  public Point2D nearest(Point2D p) {
    
  }

  public static void main(String[] args) {
     
  }
}