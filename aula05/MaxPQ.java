// A Priority Queue is an ADT that can generalize 
// both queues and stacks.

// Implementation of a Max Priority Queue API written during class
public class MaxPQ<Item extends Comparable <Item>> {
    private int n;
    private Item[] pq;

    public MaxPQ(int maxN) {
        pq = (Item[]) new Comparable[maxN+1];
    }

    // insert to the back
    public void insert (Item item) {
        pq[++n] = item;
        swim(n);
    }
    
    // delete and return max priority item
    public Item delMax() {
        Item item = pq[1];
        exch(1, n--);
        pq[n+1] = null; // for garbage collection
        sink(1);

        return item;
    }

    // helper private functions
    // note that all compares rely on less()
    private boolean less(int i, int j) {
        return (pq[i].compareTo(pq[j]) < 0); // that's why it needs Comparable
    }
    private void exch(int i, int j) {
        Item temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }
    private void sink(int parent) {
        int son = 2*parent;
        while (son <= n) {
            if (son < n && less(son, son+1)) son++;
            if (!less(parent, son)) break;
            exch(parent, son);
            parent = son;
            parent = 2*son;
        }
    }
    private swin(int son) {
        int parent = son/2;
        while (parent >= 1 && less(parent, son)) {
            exch(parent, son);
            son = parent; parent = son/2;
        }
    }
}
