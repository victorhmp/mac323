/***** 1st. class - 06/03/2018 *****/
// Simple implementation of a Bag data structure to store any <Item>
// Includes an iterator aswell
// Data struture to represent the Bag -> Linked-List

import java.util.Iterator;

public class Bag<Item> implements Iterable<Item>{
    private Node first;
    private int n;
    // private Node current; -> moved inside the Iterator implementation
    
    private class  Node {
        Item item;
        Node next;
    }

    public Bag() {
        // not necessary, but useful for documentation
        this.first = null;
        this.n = 0;
    }

    public void add(Item item) {
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

    public Iterator<Item> iterator() {
        return new BagIterator();
    }

    private class BagIterator implements Iterator<Item> {
        private Node current = first;
        
        public boolean hasNext() {
            return (current != null);
        }
        
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* Not necessary after including an iterator, as they are inside
     * Iterator class
    public void startIterator() {
        current = first;
    }

    public boolean hasNext() {
        return (this.current != null);
    }

    public Item next() {
        Item item = current.item;
        current = current.next;
        return item;
    }
    */
}
