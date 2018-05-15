# R-way Tries

ST especial para *Strings*

## Estrutura do Node

```java
private class Node {
  Value val;
  Node[] next = new Node[R]
  // R = 256 (Extended ASCII)
}
```

Um `alfabeto` reprenta o conjunto de caracteres.

## Implementação

```java
public class TrieST<Value> {
  private int R = 256;
  private Node root;
  private int N;

  // Here is where the Node class would go

  public Value get(String key) {
    Node x = get(x, key, 0);
    if (x == null) return null;
    
    return x.val;
  }
  private Node get(Node x, String key, int d) {
    // Base cases
    if (x == null) return null;
    if (d == key.length()) return x;

    char c = key.charAt(d);
    return get(x.next[c], key, d+1);
  }

  public void put(String key, Value val) {
    this.root = put(this.root, key, val, 0);
  }
  private Node put(Node x, String key, Value val, int d) {
    // Base cases
    if (x == null) x = new Node();
    if (d == key.length()) {
      if (x.val == null) this.N++;
      x.val = val;
      return x;
    }

    char c = key.charAt(d);
    x.next[c] = put(x.next[c], key, val, d+1);

    return x;
  }

  public void delete(String key) {
    this.root = delete(this.root, key, 0);
  }
  // Implementation for private delete() on lecture slides

  public Iterable<String> keyWithPrefix(String pre) {
    Queue<String> q = new Queue<String>();
    Node x = get(this.root, pre, 0);
    collect(x, pre, q);

    return q;
  }
  private void collect(Node x, String pre, Queue<String> q) {
    if (x == null) return;
    if (x.val != null) q.enqueue(pre);

    for (char c = 0; c < this.R; c++)
      collect(x.next[c], pre + c, q);
  }
}
```