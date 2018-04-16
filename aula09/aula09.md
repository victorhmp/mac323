# Árvores binárias

São implementadas com tabelas de símbolos (ST), ou seja, uma estrutura com pares key-value.

Nosso problema de interesse é "Organizar uma ST de forma que operações de *inserção* e *busca* sejam feitas eficientementes".

Em geral, uma organização que permite *inserções* rápidas impede *buscas* rápidas e vice-versa.

## Percursos em BT

Existem 3 maneiras ('manjadas'):

- in-order:  esquerda-ráiz-direita
- pre-order: raíz-esquerdo-direita
- pos-order: esquerda-direita-raíz

```java
for (Item item : bt.preOrdem()) {
  StdOut.println(item) // imprime tudo à esquerda, a ráiz e depois tudo à direita
}
```

## Características

A profundidade( = depth) de um nó de um BT é o número de links no caminho da raíz até o nó.

A altura( = height) de um BT é o máximo das profundidades de seus nós

```java
private int height(Node r) {
  if (r == null) return -1;
  int hLeft = height(r.left);
  int hRight = height(r.right);

  return Math.max(hLeft, hRight) + 1;
}
```