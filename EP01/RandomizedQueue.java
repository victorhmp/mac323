import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.NullPointerException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] rq;
    private int n;
    
    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        rq = (Item[]) new Object[1];
        this.n = 0;
    }

    public boolean isEmpty() {
        return (n == 0);
    }

    public int size() {
        return this.n;
    }

    @SuppressWarnings("unchecked")
    // private method to resize the queue
    private void resize(int newCapacity) {
        
        Item[] temp = (Item[]) new Object[newCapacity];
        for (int i=0; i < n; i++) {
            temp[i] = rq[i];
        }
        rq = temp;
    }

    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException();
        if (n == rq.length) 
            resize(rq.length * 2);

        rq[n++] = item;
    }

    // private method to return a random int between 0 and n-1
    private int randIntegerGen(int n) {
        return ( (int)( Math.random() * (n) ) );
    }
    
    public Item dequeue() {
        if (n == 0) 
            throw new NoSuchElementException();
        if (n <= rq.length/4)
            resize(rq.length/2);

        int randIndex = randIntegerGen(n); 

        Item randItem = rq[randIndex];

        rq[randIndex] = rq[--n];

        rq[n] = null; // for garbage collection

        return randItem;
    }

    public Item sample() {
        if (n == 0)
            throw new NoSuchElementException();
        return (rq[randIntegerGen(n)]);
    }

    public Iterator<Item> iterator() {
        return new RandQueueIterator();
    }

    private class RandQueueIterator implements Iterator<Item> {

        private Item[] newArr;

        private int i = 0;

        public RandQueueIterator() {
            newArr = rq.clone();
            shuffle(newArr);
        }

        public boolean hasNext() {
            return i < n;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            return newArr[i++];
        }

        public void shuffle (Item[] array) {
            for (int i=0; i < n; i++) {
                int randIndex = i + (int)(Math.random() * (n-i));
                Item tmp = array[randIndex];
                array[randIndex] = array[i];
                array[i] = tmp;
            }
        }
    }

    public static void main(String args[]) {
        RandomizedQueue<Integer> testQueue = new RandomizedQueue<Integer>();

        for (int i=0;i<20;i++) {
            testQueue.enqueue(i);
        }
        System.out.println("There are " + testQueue.size() + " itens in the queue");

        System.out.println("These are 5 of then");

        for (int i=0;i<5;i++)
            System.out.println(testQueue.sample());

        System.out.println("Testing dequeue method\n-------------");

        for (int i=0;i < 10; i++) {
            System.out.println(testQueue.dequeue());
        }
        
        System.out.println("There are " + testQueue.size() + " itens in the queue");

        System.out.println("Testing iterator\n--------------");

        System.out.println("These are the itens that remain in the Randomized Queue");
        for (int i : testQueue) {
            System.out.println(i);
        }

        System.out.println("Testing nested iterators\n--------------");

        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                System.out.print(a + "-" + b + " ");
            System.out.println();
        }
    }

}
