import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> randQueue = new RandomizedQueue<String>();
        while(!StdIn.isEmpty()) {
            randQueue.enqueue(StdIn.readString());
        }

        if (randQueue.size() < k) {
            System.out.println("The input provided does not have at least " + k + " items");
            return;
        }

        for (int i = 0; i < k; i++) {
            System.out.println(randQueue.dequeue());
        }
    }
}