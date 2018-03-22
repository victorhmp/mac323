# Aula 06 (22/03)

## Problema

    Unir duas filas priorizadas

### Árvores esquerdistas

Cada nó tem *4 campos*

Node
| item       | dist      |
|:------------:|:-----------:|
| left       | right     |

```(java)
r.dist = 0 se r = null \
r.dist = 1 + min(r.left.dist, r.right.dist)
```

Uma árvore é *esquerdista* se r.dist = r.right.dist + 1, para cada nó r. O caminho mais curto para "sair" da árvore é pela *direita*. \
**Fato** se r é um nó de uma árvore esquerdista com n nós na subárvore que o item com a raíz então $N >= 2^d - 1$

### Heap esquerdista

Um *heap esquerdista(leftist heap)* é uma árvore esquerdista tal que, para todo nó r: \
**aka: Mergeable heaps**

```(java)
r.item.compareTo(r.left.item) >= 0;
r.item.compareTo(r.right.item) >= 0;
```

Intercalando as listas ligadas (rascunho)

```(java)
public Node merge(Node r1, Node r2) {
    if (r1 == null) return r2;
    if (r2 == null) return r1;
    if (less(r1, r2)) {
        Node temp = r1; r1 = r2; r2 = temp;
    }

    r1.right = merge(r1.right, r2);
    return r1;
}
```