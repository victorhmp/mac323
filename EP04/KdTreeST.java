
/**
 * KdTreeST
 */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.BST;
import java.lang.IllegalArgumentException;

public class KdTreeST<Value> {
  private int N;
  private Node root;
  private double maxInfinity = Double.MAX_VALUE;
  private double minInfinity = -Double.MAX_VALUE;

  private class Node {
    private Point2D p;
    private Value value;
    private RectHV rect;
    private Node leftBottom;
    private Node rightUp;

    private RectHV createRect(RectHV rect, Double xax, Double yax, int k, boolean useX) {
      double xmin = minInfinity;
      double ymin = minInfinity;
      double xmax = maxInfinity;
      double ymax = maxInfinity;

      if (rect != null) {
        xmin = rect.xmin();
        ymin = rect.ymin();
        xmax = rect.xmax();
        ymax = rect.ymax();
        if (k % 2 == 0) {
          if (!useX)
            xmax = xax;
          else
            xmin = xax;
        } 
        else {
          if (!useX)
            ymax = yax;
          else
            ymin = yax;
        }
      }
      return new RectHV(xmin, ymin, xmax, ymax);
    }
    public Node(Point2D point, Value val, RectHV rect, Double xax, Double yax, int level, boolean useX) {
      this.p = point;
      this.value = val;
      this.leftBottom = null;
      this.rightUp = null;
      this.rect = createRect(rect, xax, yax, level, useX);
    }
  }

  public KdTreeST() {
    this.N = 0;
    this.root = null;
  }

  public boolean isEmpty() {
    return this.N == 0;
  }

  public int size() {
    return this.N;
  }

  public void put(Point2D p, Value val) {
    if (p == null || val == null)
      throw new IllegalArgumentException();
    root = put(root, p, val, null, 0.0, 0.0, 1, false);
  }

  private Node put(Node x, Point2D point, Value val, RectHV rect, double xax, double yax, int level, boolean useX) {
    if (x == null) {
      this.N++;
      return new Node(point, val, rect, xax, yax, level, useX);
    }
    else if (x.p.x() == point.x() && x.p.y() == point.y()) {
      x.value = val;
      return x;
    }
    if (level % 2 != 0) {
      double cmp = point.x() - x.p.x();
      if (cmp < 0)
        x.leftBottom = put(x.leftBottom, point, val, x.rect, x.p.x(), x.p.y(), level+1, false);
      else
        x.rightUp = put(x.rightUp, point, val, x.rect, x.p.x(), x.p.y(), level+1, true);
    } 
    else {
      double cmp = point.y() - x.p.y();
      if (cmp < 0)
        x.leftBottom = put(x.leftBottom, point, val, x.rect, x.p.x(), x.p.y(), level + 1, false);
      else
        x.rightUp = put(x.rightUp, point, val, x.rect, x.p.x(), x.p.y(), level + 1, true);
    }
    return x;
  }

  public Value get(Point2D p) {
    if (p == null)
      throw new IllegalArgumentException();
    return get(this.root, p, true);
  }

  private Value get(Node x, Point2D p, boolean useX) {
    if (x == null)
      return null;
    else if (x.p.x() == p.x() && x.p.y() == p.y()) {
      return x.value;
    }
    else {
      if (useX) {
        double cmp = p.x() - x.p.x();
        if (cmp < 0)
          return get(x.leftBottom, p, !useX);
        else
          return get(x.rightUp, p, !useX);
      } else {
        double cmp = p.y() - x.p.y();
        if (cmp < 0)
          return get(x.leftBottom, p, !useX);
        else
          return get(x.rightUp, p, !useX);
      }
    }
  }

  public boolean contains(Point2D p) {
    if (p == null)
      throw new IllegalArgumentException();
    return contains(this.root, p, true);
  }

  private boolean contains(Node x, Point2D p, boolean useX) {
    if (x == null)
      return false;
    else if (x.p.x() == p.x() && x.p.y() == p.y()) {
      return true;
    } else {
      if (useX) {
        double cmp = p.x() - x.p.x();
        if (cmp < 0)
          return contains(x.leftBottom, p, !useX);
        else
          return contains(x.rightUp, p, !useX);
      } else {
        double cmp = p.y() - x.p.y();
        if (cmp < 0)
          return contains(x.leftBottom, p, !useX);
        else
          return contains(x.rightUp, p, !useX);
      }
    }
  }

  public Iterable<Point2D> points() {
    Queue<Point2D> points = new Queue<Point2D>();
    Queue<Node> queue = new Queue<Node>();
    queue.enqueue(root);

    while (!queue.isEmpty()) {
      Node x = queue.dequeue();
      if (x == null)
        continue;
      points.enqueue(x.p);
      queue.enqueue(x.leftBottom);
      queue.enqueue(x.rightUp);
    }

    return points;
  }

  public Iterable<Point2D> range(RectHV rect) {
    Queue<Point2D> q = new Queue<Point2D>();
    if (rect == null)
      throw new java.lang.IllegalArgumentException();
    range(rect, root, q);
    return q;
  }
  private void range(RectHV rect, Node x, Queue<Point2D> q) {
    if (x == null)
      return;
    if (rect.contains(x.p)) {
      q.enqueue(x.p);
    }
    boolean intersects = rectIntersect(rect, x);
    if (intersects) {
      range(rect, x.leftBottom, q);
      range(rect, x.rightUp, q);
    } 
    else
      return;
  }
  private boolean rectIntersect(RectHV ret, Node x) {
    boolean intersects = (x.rect.xmax() > ret.xmin() || x.rect.xmin() < ret.xmax() || x.rect.ymax() > ret.ymin()
        || x.rect.ymin() < ret.ymax());
    if (intersects)
      return true;
    return false;
  }

  public Point2D nearest(Point2D p) {
    if (p == null)
      throw new IllegalArgumentException();
    if (this.isEmpty())
      return null;

    return nearest(this.root, p, this.root.p, true);
  }

  private Point2D nearest(Node x, Point2D p, Point2D c, boolean useX) {
    Point2D champion = c;
    if (x == null)
      return champion;

    if (x.p.distanceSquaredTo(p) < champion.distanceSquaredTo(p)) {
      champion = x.p;
    }

    if (x.rect.distanceSquaredTo(p) < champion.distanceSquaredTo(p)) {
      Node near;
      Node far;
      if ((useX && (p.x() < x.p.x())) || (!useX && (p.y() < x.p.y()))) {
        near = x.leftBottom;
        far = x.rightUp;
      } else {
        far = x.leftBottom;
        near = x.rightUp;
      }

      champion = nearest(near, p, champion, !useX);
      champion = nearest(far, p, champion, !useX);
    }

    return champion;
  }

  public Iterable<Point2D> nearest(Point2D p, int k) {
    if (p == null || (Integer) k == null) throw new java.lang.IllegalArgumentException();
    
    BST<Double, Point2D> bst = new BST<Double, Point2D>();
    Queue<Point2D> queue = new Queue<Point2D>();
    
    for (Point2D point : this.points()) {
      bst.put(point.distanceTo(p), point);
    }

    for (int i = 0; i < k; i++) {
      queue.enqueue(bst.get(bst.min()));
      bst.deleteMin();
    }
    return queue;
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