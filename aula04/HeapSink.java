public class HeapSink {
    // O consumo de tempo da função sink é proporcional a lg m
    // p = parent node index (index for the node which will sink)
    // Pré-condição para aplicar o sink => o "em baixo" do nó p, devemos ter
    // apenas max-heaps
    public static void sink (int p, int m, Comparable a[]){
        int f = 2*p; // left child
        Item x = a[p];
        while (f <= m) {
            if (f < m && less(a[f], a[f+1])) f++;
            if (!less(x, a[f])) break; // parent node > child node
            a[p] = a[f];
            p = f;
            f = 2*p;
        }
        a[p] = x;
    }

    public static boolean less (Item x, Item y) {
        return x.compareTo(y) < 0;
    }

    // Contrução de um max-heap
    // Consumo de tempo é proporcional a (m*lg(m))
    // maaas, na realidade, é O(m)
    public static void heapConstruct (int m, Comparable[] a) {
        for (int p = m/2; p >= 1; p--) {
            sink(p, m, a);
        }
    }
    
}
