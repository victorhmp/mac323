import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.NullPointerException;
import java.lang.UnsupportedOperationException;

public class Deque<Item> implements Iterable<Item> { 
    
    // Linked-list auxiliar variables and class 
    private Node<Item> first;
    private Node<Item> last;
    private static class Node<Item> {
        private Item value;
        private Node<Item> next;
        private Node<Item> prev;
    }

    // Size of the Deque
    private int n; 
    
    // Constructor
    public Deque()  {         
        this.first = null;
        this.last= null;
        this.n = 0;
    }
     
    public boolean isEmpty() {
       return (n == 0);
    }
    
    public int size() {
        return n;
    }
    
    public void addFirst(Item item) {
        if (item == null) 
            throw new IllegalArgumentException();
        Node<Item> oldfirst = this.first;
        first = new Node<Item>();
        first.value = item;
        first.next = oldfirst;
        if (isEmpty()) last = first; 
        else oldfirst.prev = first; 

        this.n++;
    }
    
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node<Item> oldlast = this.last;
        last = new Node<Item>();
        last.value = item;
        last.next = null;
        last.prev = oldlast;

        if (isEmpty()) 
            first = last;
        else 
            oldlast.next = last;

        this.n++;
    }
   
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item value = first.value;
        first = first.next;
        this.n--;
        if (!isEmpty()) first.prev = null;
        return value;
    }
     
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item value = last.value;
        last = last.prev;
        this.n--;
        return value;
    }

    
    public Iterator<Item> iterator() {
        return new DequeIterator<Item>(first);    
    }

    private class DequeIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public DequeIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.value;
            current = current.next; 
            return item;
        }
    }
    

    // Deque unit testing
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();

        for (int i=1; i <= 5; i++) {
            // add number i to the back
            deque.addLast(i);
        }
        for (int i=6; i <= 10; i++) {
            // add number i to the front
            deque.addFirst(i);
        }

        System.out.println("Items in the Deque: " + deque.size());
        System.out.println("-------------------");

        for (int curr : deque) {
            System.out.println(curr);
        }

        // Testing removing items
        for (int i : deque) {
            if (i % 2 == 0) System.out.println("Removed from the back: " + deque.removeLast());
            else System.out.println("Removed from the front: " + deque.removeFirst());
        }

        System.out.println(deque.size() + " items left in the Deque.");
    }
}
