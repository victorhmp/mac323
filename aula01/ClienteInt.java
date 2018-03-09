public class Cliente {
    public static void main(String args[]) {
        BagInteger bag = new BagInteger();
        for (int i=10;i<20; i++) {
            bag.add(i);
        }
        StdOut.println(bag.size());
        bag.startIterator();
        while (bag.hasNext()) {
            StdOut.println(bag.next());
        }
    }
}
