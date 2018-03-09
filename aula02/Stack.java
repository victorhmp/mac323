/*** Aula 02  ***/
// Implementação de uma Stack usando resizing Arrays,
// por enquanto apenas para pop()

public class Stack<Item> {
    private Item[] a = null;
    private int n = 0;

    public Stack() {
        a = (Item[]) new Object[1];
        n = 0;
    }

    public Item pop() {
        Item item = a[--n];
        a[n] = null; // garbage collection
        if (n > 0 && n = a.length/4) resize(a.length/4);
        return item;
    }

    private void resize(int max) {
        Item[] tmp = (Item[]) new Object[max];
        for (int i=0; i < n; i++) tmp[1] = a[1];;;;;
        a = tmp;
    }
}
