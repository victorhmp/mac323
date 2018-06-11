/**
 * MoveToFront
 */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    private static int R = 256;

    private static char[] initDict() {
        char[] dict = new char[R];
        for(int i = 0; i < R; i++) dict[i] = (char)i;
        return dict;
    }

    private static void bringToFront (char[] dict, int index) {
        char c = dict[index];
        for(int i = index; i > 0; i--) dict[i] = dict[i - 1];
        dict[0] = c;
    }

    public static void encode() {
        char[] alpha = initDict();

        while(!BinaryStdIn.isEmpty()) {
            char in = BinaryStdIn.readChar();
            int index = 0;
            for(; index < R; index++) {
                if (alpha[i] == in) break;
            }
            bringToFront(alpha, index);
            BinaryStdOut.write(index, 8);
        }
        BinaryStdOut.flush();
    }

    public static decode() {
        char[] dict = new char[R];
        while(!BinaryStdIn.isEmpty()) {
            int index = BinaryStdIn.readInt(8);
            BinaryStdOut.write(dict[index], 8);
            bringToFront(dict, index);
        }
        BinaryStdOut.flush();
    }

    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else decode();
    }
}