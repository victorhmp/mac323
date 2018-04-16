// Binary Search Tree implementation during class

public class BST<Key extends Comparable<Key>, Value> {
  private Node r;
  private class Node {
    private Key key;
    private Value val;
    private Node left; // left child
    private Node right; // right child

    private Node (Key key, Value val) {
      this.key = key;
      this.val = val;
    }
  }

  public Iterable<Item> inOrder() {
    Queue<Item> q = new Queue<Item>();
    inOrder(r, q); // r is the root
    return q;
  }

  // returns first node to be "thrown out"
  // considering in-order traversal
  private Node firstOut(Node r) {
    while (r.left != null)
      r = r.left;
    return r;
  }

  private void inOrder(Node r, Queue<Item> q) {
    // recursive implementation
    if (r != null) {
      inOrder(r.left, q);
      q.enqueue(r.item);
      inOrder(r.right, q);
    }
  }

  private void inOrderIteractive (Node r, Queue<Item> q) {
    // iteractive implementation
    // note the use of a Stack in addition to the Queue
    Stack<Node> s = new Stack<Node>();
    while (r != null || !s.isEmpty()) {
      // essentially, this is a DFS in the tree
      if (r.left != null) {
        push(r);
        r = r.left;
      }
      else {
        r = s.pop();
        q.enqueue(r.item);
        r = r.right;
      }
    }
  }

}