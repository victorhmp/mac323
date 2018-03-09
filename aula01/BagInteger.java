// Aula 01 - 06/03/2018
// Simple implementation of a Bag data structure to store integers
// Data struture to represent the Bag -> Linked-List

public class BagInteger {
    private Node first;
    private int n;
    private Node current;
    
    private class  Node {
        Integer item;
        Node next;
    }

    public BagInteger() {
        // not necessary, but useful for documentation
        this.first = null;
        this.n = 0;
    }

    public void add(int item) {
        Node oldfirst = first;
        first = new Node();
        first.next = oldfirst;
        this.n++;
    }

    public Integer size() {
        return n;
    }

    public boolean isEmpty() {
        return (n == 0 || this.first == null);
    }

    public void startIterator() {
        current = first;
    }

    public boolean hasNext() {
        return (this.current != null);
    }

    public Integer next() {
        int item = current.item;
        current = current.next;
        return item;
    }
}
