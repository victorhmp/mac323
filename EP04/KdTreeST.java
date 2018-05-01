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

    public Node(Point2D point, Value val, RectHV rect) {
      this.p = point;
      this.value = val;
      this.leftBottom = null;
      this.rightUp = null;
      this.rect = rect;
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
    root = put(root, p, val, true, 0.0, 0.0, 1.0, 1.0);
  }
  private Node put (Node x, Point2D point, Value val, boolean useX, 
                    double x0, double y0, double x1, double y1) {
    
    if (x == null){
      this.N++;
      RectHV r = new RectHV(x0, y0, x1, y1);
      return new Node(point, val, r);
    }
    else if (x.p.x() == point.x() && x.p.y() == point.y()) {
      x.value = val;
      return x;
    }
    if (useX) {
      double cmp = point.x() - x.p.x();
      if (cmp < 0) 
        x.leftBottom  = put(x.leftBottom,  point, val, !useX, x0, y0, x.p.x(), y1);
      else
        x.rightUp = put(x.rightUp, point, val, !useX, x.p.x(), y0, x1, y1);
    }
    else {
      double cmp = point.y() - x.p.y();
      if (cmp < 0)
        x.leftBottom  = put(x.leftBottom,  point, val, !useX, x0, y0, x1, x.p.y());
      else
        x.rightUp = put(x.rightUp, point, val, !useX, x0, x.p.y(), x1, y1);
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

  public Iterable<Point2D> points() {
    Queue<Point2D> points = new Queue<Point2D>();
    Queue<Node> queue = new Queue<Node>();
    queue.enqueue(root);

    while (!queue.isEmpty()) {
      Node x = queue.dequeue();
      if (x == null) continue;
      points.enqueue(x.p);
      queue.enqueue(x.leftBottom);
      queue.enqueue(x.rightUp);
    }

    return points;
  }

  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) throw new IllegalArgumentException();
    Queue<Point2D> q = new Queue<Point2D>();
    range(root, rect, q);
    return q;
  }
  private void range(Node node, RectHV rect, Queue<Point2D> q) {
    if (node == null) return;
    if (rect.contains(node.p)) {
      q.enqueue(node.p);
    }
    if (rect.intersects(node.rect)) {
      range(node.leftBottom, rect, q);
      range(node.rightUp, rect, q);
    }
  }

  public Point2D nearest(Point2D p) {
    if (p == null) throw new IllegalArgumentException();
    if (this.isEmpty()) return null;

    return nearest(this.root, p, this.root.p, true);
  }
  private Point2D nearest(Node x, Point2D p, Point2D c, boolean useX) {
    Point2D champion = c;
    if (x == null) return champion;

    if (x.p.distanceSquaredTo(p) < champion.distanceSquaredTo(p)) {
      champion = x.p;
    }

    if (x.rect.distanceSquaredTo(p) < champion.distanceSquaredTo(p)) {
      Node near;
      Node far;
      if ( ( useX && (p.x() < x.p.x()) ) || ( !useX && (p.y() < x.p.y()) ) ) {
        near = x.leftBottom;
        far = x.rightUp;
      }
      else {
        far = x.leftBottom;
        near = x.rightUp;
      }

      champion = nearest(near, p, champion, !useX);
      champion = nearest(far, p, champion, !useX);
    }

    return champion;
  }

  public static void main(String[] args) {
    KdTreeST<Integer> BST = new KdTreeST<Integer>();

    Point2D p1 = new Point2D(0.7, 0.2);
    Point2D p2 = new Point2D(0.5, 0.4);
    Point2D p3 = new Point2D(0.2, 0.3);
    Point2D p4 = new Point2D(0.4, 0.7);
    Point2D p5 = new Point2D(0.9, 0.6);
    Point2D p6 = new Point2D(3.6, 2.5);
    Point2D p7 = new Point2D(2.1, 4.0);
    Point2D p8 = new Point2D(2.1, 4.9);

    System.out.println("Is the KdTree empty? " + BST.isEmpty());

    BST.put(p1, 1);
    BST.put(p2, 2);
    BST.put(p3, 3);
    BST.put(p4, 4);
    BST.put(p5, 5);
    // BST.put(p6, 6);
    // BST.put(p7, 7);
    System.out.println("Does the KdTree contain: " + p1 + "? " + BST.contains(p1));
    System.out.println("Does the KdTree contain: " + p2 + "? " + BST.contains(p2));
    System.out.println("Does the KdTree contain: " + p3 + "? " + BST.contains(p3));
    System.out.println("Does the KdTree contain: " + p4 + "? " + BST.contains(p4));
    System.out.println("Does the KdTree contain: " + p5 + "? " + BST.contains(p5));
    System.out.println("Does the KdTree contain: " + p6 + "? " + BST.contains(p6));
    System.out.println("Does the KdTree contain: " + p7 + "? " + BST.contains(p7));
    System.out.println("Does the KdTree contain: " + p8 + "? " + BST.contains(p8));
    
    System.out.println("Value stored in: " + p2 + "? " + BST.get(p2));
    System.out.println("Value stored in: " + p4 + "? " + BST.get(p4));
    System.out.println("Value stored in: " + p6 + "? " + BST.get(p6));
    
    for (Point2D i : BST.points()) {
      System.out.println("Point: " + i);
    }
  }
}