public class QuickFindUF {
    private int[] id;
    private int count;

    // O(n)
    public QuickFindUF (int n) {
        count = n;
        id = new int[n];

        for (int i=0;i<n;i++)
            id[i] = i;
    }
    // retorna o número de componentes
    public int count() {
        return count;
    }
    // retorna true se p e q estão no mesmo componente
    public boolean connected (int p, int q) {
        return (id[p] == id[q]);
    }
    // retorna o componente em que p está
    // O(1)
    public int find(int p) {
        return id[p];
    }
    // Une dois itens no mesmo componente
    // O(n)
    public void union(int p, int q) {
        int pComponent = find(p);
        int qComponent = find(q);
        if (pComponent == qComponent) return;

        for (int i=0;i<id.length;i++) {
            if (id[i] == pComponent) id[i] = qComponent;
        }
        count--;
    }
}
