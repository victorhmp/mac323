# Hashing

- Endereçamento direto
- Funções de Hash
- Hash modular
- Hash multiplicativo
- Colisor
- Colisões por encadeamento
- Colisões por sondagem linear
- Double hashing

Fonte: CLRS - Introduction to Algorithms

## Endereçamento direto

Técnica funciona bem quando o conjunto de chaves não é grande.

```java

public class DHST<Key, Value> {
    private Value[] vals;

    public Value get (Key key) {
        return vals[key];
    }
    public void put (Key key, Value val) {
        vals[key] = val;
    }
    public void delete (Key key) {
        vals[keys] = null;
    }
}

```

## Consumo de tempo

As operações `get()`, `put()`, `delete()`, consomem tempo O(1) [hashing "na média"]

## Maiores defeitos

- Chaves são inteiras
- Desperdício de espaço: é possível que a maior parte da tabela esteja vazia

## Tabela de dispersão (Hash Table)

- Inventado para funcionar na média
- Chaves realmente usadas é uma parte pequena do universo.

A tabela tem forma `st[0...m-1]` -> Tamanho da tabela é `m`.

## Funções de Hash

Vão de um conjunto no espaço `U` para um índice de `0 a m-1` na `st[0...m-1]`.

Gostaríamos que:

- Possa ser calculado eficientemente
- Espalhe bem as chaves pelo intervalo [0...m-1]

Colisões em um Hash significam casos: $h(k) = h(x), sendo k != x$.

### Side-note: Funções Injetoras

Considerando m=41, e um conjunto `U` de tamanho 31: Existem $41^31$ funções que levam elementos de `U` para um Hash, ou seja, aproximadamente $10^50$. Número de funções injetoras é de aproximadamente **uma em cada 10 milhões**.

Com isso, não podemos esperar que a função seja injetora pois a chance disso acontecer é muito baixa.

Conclusão, precisamos conviver com **colisões**.

## Função de Hash modular (Division Method)

Supondo que as chaves são inteiras, a função de Hash modular é algo na forma:

```java
private int hash(int Key) {
    return key % m; // mudular vem dessa divisão
}
```

Vantagem:

- Rápido de calcular

Desvantagem:

- Devemos evitar certos valores de `m`.

## Hash Multiplicativo (Multiplicative method)

Referência: Cormem

## Side Note: Java e Hashing

Em Java, toda classe possuí um método padrão `hashCode()`que produz um inteiro entre *-2^31 e 2^31-1*

```java
private int hash(Key key) {
    return (Key.hashCode()&0x7fffffff);
}
```

**hashCode() concorda com `equal()`**


## Hasing por encadeamento (Separate Chaining)

```java
// Code for illustrative porpuse, not working
public class GenerericClass(Key key) {
    private int n;
    private int m;
    // dependent on SequencialSearchST.java class
    private SequentialSearchST[] st;

    public GenericClass (int m) {
        this.m = m;
        st = (SequentialSearchST[]) new SequentialSearchST[m];
        for (int i = 0; i < m; i++) {
            st[i] = new SequentialSearch<Key, value>();
        }
    }

    private hash(Key key) {
        return (Key.hashCode()&0x7fffffff);
    }

    public Value get(Key key) {
        int h = hash(key);
        return st[h].get(key);
    }

    public void put(Key key, Value val) {
        int h = hash(key);
        st[h].put(key, val);
    }
}
```

### Consumo de tempo

- Depende do tamanho das listas
- O *fator de carga* da tabela é $\alpha = (n/m)$.

Buscamos: tempo O(1) *na média* \
Gostaríamos: n = O(m)

### Rehashing

Dobramos o tamanho da tabela se o *fator de carga* >= 10. \
Código para isso deve ser estudado e implemetado com cuidado!