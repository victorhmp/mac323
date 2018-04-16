# Aula 07 (03/04)

## Tabela de Símbolos

Uma tablea de símbolos (symbol table) consiste de um conjunto de items, cada item consistindo de uma chave e um valor
(key-value pairs). \
Suas operações básicas são:

- `put(key, value)` que insere o par key-value na ST
- `get(key)` que busca o valor associado a uma chave e retorna esse valor

### Convenções

- Não há chaves repetidas
- `null` nunca é chave ou valor
- Vem em dois 'sabores': Ordenadas e Não-ordenadas (as chaves)