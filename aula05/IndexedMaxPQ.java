// Partial implementation of a Indexed Max Priority Queue API written during class
// This indexed variant makes it easy to reorganize items and CHANGE Priorities
// while maintaning O(log n)
public class IndexedMaxPQ<Item extends Comparable<Item>> {
    private int n;
    private int[] pq; // keeps priorities
    private int[] qp; // keeps indexes
    private Item[] items; // keeps the actual items, and we index it by pq[]

    public MaxPQ(int maxN) {
        items = (Item[]) new Comparable[maxN + 1];
        pq = new int[maxN + 1];
        qp = new int[maxN + 1];

        for (int i = 0; i <= maxN; i++) {
            qp[i] = -1;
        }
    }

    // insert to the back
    public void insert(Item item, int k) {
        // k represents priority
        pq[++n] = k;
        items[k] = item;
        qp[k] = n;
        sink(n);
    }

    // Main function to justify extra work
    public void change(Item item, int k) {
        items[k] = item;
        swim( qp[k] );
        sink( qp[k] );
    }

    // delete and return max priority item
    public Item delMax() {
        Item item = pq[1];
        exch(1, n--);
        pq[n + 1] = null; // for garbage collection
        sink(1);

        return item;
    }

    // helper private functions
    // note that all compares rely on less()
    private boolean less(int i, int j) {
        return ( items[ pq[i] ].compareTo(items[ pq[j] ]) < 0 ); // that's why it needs Comparable
    }

    private void exch(int i, int j) {
        Item temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;

        qp[ pq[i] ] = i;
        qp[ pq[j] ] = j;
    }

    private void sink(int parent) {
        int son = 2 * parent;
        while (son <= n) {
            if (son < n && less(son, son + 1))
                son++;
            if (!less(parent, son))
                break;
            exch(parent, son);
            parent = son;
            parent = 2 * son;
        }
    }

    private swim(int son) {
        int parent = son/2;
        while (parent >= 1 && less(parent, son)) {
            exch(parent, son);
            son = parent; parent = son/2;
        }
    }
}
