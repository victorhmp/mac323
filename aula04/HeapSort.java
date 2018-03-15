// Mudamos a estrutura de dados da ordenação por seleção, e não o algoritmo, e
// com isso conseguimos deixa-lo mais rápido!
//
// Invariantes: 1) a[i+1...n] é crescente
//              2) a[1...i] <= a[i+1]
//              3) a[1...i] é um max-heap

// consumo de tempo O(n lg n)
public class HeapSort {
    public static void heapsort (int n, Comparable[] a) {
        
        // pré-processamento
        for (int i = n/2; i >= 1; i--)
            sink(i, n, a);

        for (int i=n; i > 1; i--) {
            Item x = a[i]; a[i] = a[n]; a[n] = x;
            sink(1, i-1, a);
        }
    }
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
