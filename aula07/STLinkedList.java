/**
 * STLinkedList implementation during class, not the best one
 */
public class STLinkedList<Key extends Comparable<Key>, Value> {

    private int n;
    private Node first;

    private class Node {
        Key key;
        Value val;
        Node next;
        public Node(Key key, Value val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    public STLinkedList() {
        first = Node(null, null, null);
    }

    public void put (Key key, Value val) {
        if (val == null) {
            delete(key);
            return;
        }

        Node p = prev(key);
        Node q = p.next;
        if (q != null && q.key.equal(key)) {
            q.val = val; return;
        }

        p.next = new Node(key, val, q);
        n++;
    }

    private Node prev (Key key) {
        Node p = first;
        Node q = p.next;

        while(q != null && q.key.compareTo(key) < 0) {
            p = q;
            q = p.next;
        }

        return p;
    }
}