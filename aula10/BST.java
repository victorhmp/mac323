// BST implementation using Linked-Lists
// Made during class
// Uses recursion on base operations

public class BST<Key extends Comparable<Key>, Value>{
  private Node r;
  private class Node {
    Key key;
    Value val;
    Nde left, right;
    public Node (Key key, Value val) {
      this.key = key;
      this.val = val;
    }
  }

  public void put(Key key, Value val) {
    r = put(r, key, val);
  }
  private Node put(Node x, Key key, Value val) {
    if (x = null) return Node(key, val);
    int cmp = key.compareTo(x.key);

    if (cmp < 0) {
      x.left = put(x.left, key, val);
    }
    else if (cmp > 0) {
      x.right = put(x.right, key, val);
    }
    else {
      x.val = val;
    }
    return x;
  }

  public Key min() {
    if (r == null) return null;
    return min(r).key;
  }
  private Node min (Node r) {
    if (x.left == null) return x;
    return min(x.left);
  }

  public Value get (Key key) {
    Node x = get(r, key);
    if (x == null) return null;
    
    return x.val;
  }
  private Node get(Node x, Key key) {
    if (x == null) return null;

    // make decision, where should I look?
    int cmp = key.compareTo(x.key);
    if (cmp < 0){
      // Go left
      return get(x.right, key);
    }
    if (cmp > 0) {
      // Go right
      return get(x.left, key);
    }
    // found it!
    return x;
  }

  public void deleteMin() {
    if (r == null) return;
    r = deleteMin(r);
  }
  private Node deleteMin (Node x) {
    if (x.left == null) {
      return x.right; // new root node for the minimun sub-tree
    }
    x.left = deleteMin(x.left);
    return x;
  }

  public void delete(Key key) {
    r = delete(r, key);
  }
  private Node delete(Node x, Key key) {
    if (x == null) return null;

    int cmp = key.compareTo(x.key);

    if (cmp < 0) {
      x.left = delete(x.left, key);
    }
    else if (cmp > 0) {
      x.right = delete(x.right, key);
    }
    else {
      if (x.right == null) {
        return x.left;
      }
      else if (x.left == null) {
        return x.right;
      }
      Node t = x;
      x = min(t.right);
      x.right = deleteMin(t.right);
      x.left = t.left;
    }
    return x;
  }
}