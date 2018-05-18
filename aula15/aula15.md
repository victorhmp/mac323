# Compressão de dados

**Problema**: representar arquivos *GRANDES* por arquivos *pequenos*

## Esquema básico

B = (1000111...) => [Compress()] => C(B) = 010110... => [Expand()] => B

*número de bits em c* < número de bits em B

Taxa de compressão = |C(B)| / |B| < 1 => Queremos uma taxa de compressão *pequena*

## Considerações teóricas

**Não existe algoritmo que comprime todo arquivo**

## Entrada e saída

`BinaryStdIn` e `BinaryStdOut`

## Exemplo de compressor para um genoma

```java
public static void compress() {
  Alphabet DNA = new Alphabet("ATGC");
  int w = DNA.lgR(); // size of DNA
  String s = BinaryStdIn.readString();
  int n = s.length();

  BinaryStdOut.write(n); // prints the number of chars in the string
  for (int i = 0; i < n; i++) {
    int d = DNA.toIndex(s.charAt(i));
    BinaryStdOut.write(d, w);
  }
  BinaryStdOut.close();
}
```