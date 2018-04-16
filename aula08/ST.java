/**
 * ST implementation during class, using skip list
 */
public class ST<Key extends Comparable<Key>, Value> {

    private int n;
    private Node first;

    private class Node {
        Key key;
        Value val;
        Node[] next;

        public Node(Key key, Value val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    public STLinkedList() {
        first = Node(null, null, null);
    }

    public Value get (Key key) {
        Node p = first;
        for (int k = log(n-1); k >= 0; k--) {
            p = prev(key, p, k);
            Node q = p.next[k];

            if (q != null && q.key.equals(key))
                return q.val;
        }
        return null;
    }

    public void put (Key key, Value val) {
        Node[] s = new Node[MAXLEVELS];
        if (val == null){
            delete(key);
            return;
        }

        Node p = first;
        for (int k = log(n-1); k>=0;k--) {
            p = prev(key, p, k);
            Node q = p.next[k];
            if (q != null && q.key.equals(key)){
                q.val = val;
                return;
            }
            s[k] = p;
        }

        // key is not in the simbol table
        int levels = randLevel();
        Node newNode = new Node(key, val, levels);

        // update log(n) variable to know how many items need to be updated
        
        
        for (int k = levels-1; k >= 0; k--) {
            Node t = s[k].next[k];
            s[k].next[k] = newNode;
            newNode.next[k] = t;
        }
    }

    private Node prev(Key key, Node start, int k) {
        Node p = start;
        Node q = start.next[k];

        while (q != null && q.key.compareTo(key) < 0) {
            p = q;
            q = p.next[k];
        }

        return p;
    }
}