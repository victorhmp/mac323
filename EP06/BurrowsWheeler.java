/**
 * BurrowsWheeler
 */

import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.BinaryStdIn;

public class BurrowsWheeler {

    public static final int R = 256;

    private class Letter {
        private char ch;
        private int ind;
        public Letter(char c, int i) {
            this.ch = c;
            this.ind = i;
        }
    }
    
    private Letter[] kic(Letter[] a) {
        int R = 256;
        int N = a.length;
        Letter[] aux = new Letter[N];
        int[] count = new int[R+1];
        for (int i = 0; i < N; i++)
            count[a[i].ch+1]++;
        
        for (int r = 0; r < R; r++)
            count[r+1] += count[r];
        for (int i = 0; i < N; i++)
            aux[count[a[i].ch]++] = a[i];
        return aux;
    }

    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        
        int length = s.length();
        int original = 0;
        char[] code = new char[length];
        for (int i = 0; i < length; i++) {
            if (csa.index(i) == 0) original = i;
            code[i] = s.charAt((csa.index(i) -1 + length) % length);
        }
        BinaryStdOut.write(original);
        for (int i = 0; i < length; i++) {
            BinaryStdOut.write(code[i]);
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler decoding, 
    //reading from standard input and writing to standard output
    public static void inverseTransform() {
        int start = BinaryStdIn.readInt();
        
        String s = BinaryStdIn.readString();
        
        BurrowsWheeler bw = new BurrowsWheeler();
        
        for (char c : bw.inverseTransform(start, s))
        {
            BinaryStdOut.write(c);
        }
        
        BinaryStdOut.close();
    }
    private char[] inverseTransform(int start, String s) {
        int length = s.length();
        
        
        Letter[] input = new Letter[length];
        for (int i = 0; i < length; i++) {
            input[i] = new Letter(s.charAt(i), i);
        }
        Letter[] sorted = kic(input);
        int[] next = new int[length];
        
        for (int i = 0; i < length; i++) {
            next[i] = sorted[i].ind;
        }
        
        char[] text = new char[length];
        
        for (int i = 0; i < length; i++) {
            text[i] = sorted[start].ch;
            start = next[start];
        }
        
        return text;
    }

    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}