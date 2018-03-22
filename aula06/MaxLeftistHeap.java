public class MaxLeftistHeap <Item extends Comparable<Item>> {
    private int n;
    private Node r; // root

    private class Node {
        private Item item;
        private Node right;
        private Node left;
        private int dist;

        public Node (Item item, Node left, Node right, int dist) {
            this.item = item;
            this.left = left;
            this.right = right;
            this.dist = dist;
        }
    }

    public void insert (Item item) {
        Node s = new Node(item, null, null, 1);
        r = merge(r, s);
        this.n++;
    }

    public Item delMax () {
        Item item = r.item;
        r = merge(r.right, r.left);
        this.n--;
        return item;
    }

    public void Union (MaxLeftistHeap<Item> that) {
        if (that == null)
            return;
        else {
            this.r = merge(this.r, that.r);
            this.n+=that.n;
        }
    }

    // private helper functions
    private boolean less (Node r, Node s) {
        return r.item.compareTo(s.item) < 0;
    }

    // most important method, to actually merge lists
    private Node merge (Node r1, Node r2) {
        if (r1 == null)
            return r2;
        if (r2 == null)
            return r1;
        
        // make sure r1 > r2
        if (less(r1, r2)) {
            Node t = r1;
            r1 = r2;
            r2 = t;
        }

        if (r1.left == null) {
            r1.left = r2;
        } else {
            r1.right = merge(r1.right, r2);
            if (r1.left.dist < r1.right.dist) {
                Node t = r1.left;
                r1.left = r1.right;
                r1.right = t;
            }

            r1.dist = r1.right.dist + 1;
        }

        return r1;
    }
}