public class WeightedQuickUnionUF {
    // Estruturada em ÁRVORES
    private int[] parent;
    private int[] weight;
    private int count;

    // O(n)
    public WeightedQuickUnionUF (int n) {
        count = n;
        parent = new int[n];
        weight = new int[n];

        for (int i=0;i<n;i++){
            parent[i] = i;
            weight[i] = 1;
        }
    }
    // retorna o número de componentes
    public int count() {
        return count;
    }
    // retorna true se p e q estão no mesmo componente
    public boolean connected (int p, int q) {
        return (find(p) == find(q));
    }
    // retorna o componente em que p está
    // O(lg n) -> sensível às alturas das árvores de componentes
    public int find(int p) {
        while (p != parent[p]) {
            p = parent[p];
        }
        return p;
    }
    // Une dois itens no mesmo componente
    // O(lg n)
    public void union(int p, int q) {
        int pComponent = find(p);
        int qComponent = find(q);
        if (pComponent == qComponent) return;

        if (weight[pComponent] < weight[qComponent]) {
            parent[pComponent] = qComponent;
            weight[qComponent] += weight[pComponent];
        }
        else {
            parent[qComponent] = pComponent;
            weight[pComponent] += weight[qComponent];
        }

        count--;
    }
}
