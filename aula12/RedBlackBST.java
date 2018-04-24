/**
 * RedBlackBST
 */
public class RedBlackBST{
  // Maintain that RED = True / BLACK = False

  private class Node {
    Key key;
    Value value;
    boolean color;
    int n;
    public Node (Key key, Value val, boolean color, int n) {
      this.key = key;
      this.value = val;
      this.color = color;
      this.n = n;
    }
  }

  public void put (Key key, Value val) {
    r.put(r, key, val);
    r.color = false;
  }
  private Node put (Node h, Key key, Value val) {
    if (h == null) return new Node(key, val, true, 1);

    int cmp = key.compareTo(h.key);
    if (cmp < 0)
      h.left = put(h.left, key, val);
    else if (cmp > 0)
      h.right = put(h.right, key, val);
    else 
      h.val = val;
    
    h = balance(h);
    
    return h;
  }
  private Node balance(Node h) {
    if (isRed(h.right) && !isRed(h.left)) {
      h = rotateLeft(h);
    }
    if (isRed(h.left) && isRed(h.left.left)) {
      h = rotateRight(h);
    }
    if (isRed(h.left) && isRed(h.right)) {
      flipColor(h);
    }
    return h;
  }

  private boolean isRed (Node x) {
    if (x == null) return false;

    return x.color == true;
  }

  private Node rotateLeft (Node h) {
    Node x = h.right;
    h.right = x.left;
    x.left = h;
    x.color = h.color;
    h.color = true;
    x.n = h.n;
    h.n = 1 + size(h.left);

    return x;
  }

  private void flipColor (Node h) {
    h.color = !h.color;
    h.left.color = !h.left.color;
    h.right.color = !h.right.color;
  }
}