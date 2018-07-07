# Grafos

## Busca em profundidade (DFS)

```java
public class DFS {
  private boolean[] marked;
  private int[] edgeTo;
  private int s;

  public DFS blah (Digraph G, int s) {
    marked = new boolean[G.V()];
    this.s = s;

    dfs(G, s);
  }

  private void dfs (Digraph G, int v) {
    marked[v] = true;

    /*
        Para lista de adjacentes => O(V + E)
        Para matriz de adjacencia => O(V^2)
    */
    for (int w : G.adj(v)) {
      if (!marked[w]) {
        this.edgeTo[w] = v;
        dfs(G, w);
      }
    }
  }

  public boolean hasPathTo(int v) {
    return marked[v];
  }

  /*
    Arborescências
  */
  public Iterable<Integer> pathTo (int v) {
    Stack<Integer> path = new Stack<Integer>();
    for (int x = v; x != s; x = edgeTo[x]) {
      path.push(x);
    }
    path.push(s);

    return path;
  }
}
```

## Certificados

Como validar caminhos e saber se eles existem?

### Cortes

Uma **bipartição** do conjunto de vértices do grafo.

Para demonstrarmos que *não existe* um caminho de `s` a `t` basta exibirmos um `st-corte(S, T)` em que *todo arco* no corte tem ponta inicial em `T` e final em `S`.