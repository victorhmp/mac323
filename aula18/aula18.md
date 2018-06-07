# Algoritmo de Lempel, Ziv & Welch (LZW)

LZW é um método de dicionários. Código de tamanho fixo (w) para strings de tamanho variado.

## Métodos baseados em dicionários

`compress()`: a medida que a entrada é processada, controi um dicionário de `Strings` onde o índice é o código.

`expand()`: reconstrói o dicionário simulando o `compress()`.

## Exemplo

- Alfabeto = { a, b } => `R` = 2
- `W` = 3 bits => índices de 0 a 7
- Tamanho do dicionário = 2^W = 8
- Índice `R` codifica `EOF`

input =>  *a* *b* *ab* *aba* *ba*b `EOF` \
código =>  0   1   3     5     4 1  2

Tabela sendo gerada:

string | código
-------|-------
a      | 0
b      | 1
EOF    | 2
ab     | 3
ba     | 4
aba    | 5
abab   | 6
bab    | 7

Fomos de 8\*10 = 80 bits para 7\*3 = 21 bits! => taxa de compressão de **26%**

## Implementação compress()

```java
public static void compress() {
  STring input = BST.readString();
  TST<Integer> st = new TST<Integer>();
  
  for (int i=0; i < R; i++) {
    st.put("" + char(i), i);
  }

  int code = R + 1;
  while(input.length() > 0) {
    String s = ST.longestPrefixOf(input);
    BSO.write(st.get(s), W);
    int t = s.length();
    if (t < input.length() && code < L) {
      st.put(input.substring(0, t+1), code++);
    }
    input.substring(t)
  }
  BSO.write(R, W);
  BSO.close();
}
```

## Implementação expand()

```java

```