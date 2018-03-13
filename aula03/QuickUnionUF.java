public class QuickUnionUF {
    // Estruturada em ÁRVORES
    private int[] parent;
    private int count;

    // O(n)
    public QuickUnionUF (int n) {
        count = n;
        parent = new int[n];

        for (int i=0;i<n;i++)
            parent[i] = i;
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
    // O(n) -> tamanho máximo da árvore (altura)
    public int find(int p) {
        while (p != parent[p]) {
            p = parent[p];
        }
        return p;
    }
    // Une dois itens no mesmo componente
    // O(n)
    public void union(int p, int q) {
        int pComponent = find(p);
        int qComponent = find(q);
        if (pComponent == qComponent) return;
        
        parent[pComponent] = qComponent; // Trocamos só UM link

        count--;
    }
}
