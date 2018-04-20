# Árvore 2-3

Todo nó tem 2 links ou 3 links *perfeitamente balanceada*: Todos os links `null` estão na mesma profundidade.

## Estrutura

Toda árvore 2-3 ( ) de altura `h` tem pelo menos $(2^h)-1$ nós.
Consequentemente toda árvore 2-3 tem altura <= $lg(n+1)$ onde `n` é o número de nós.

# BST Rubro-Negra

Uma BST rubro-negra é BST cujos links são rubros ou negros:

- Links rubros são para a esquerda
- Nenhum nó incide em dois links rubros
- *Balanceamente negro completo*: Todo caminho da raiz até um link `null` tem o mesmo número de links negros.

## Estrutura

A altura de uma árvore rubro-negra é <= $2*lg(2)$