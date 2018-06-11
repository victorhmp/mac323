/**
 * CircularSuffixArray
 */
public class CircularSuffixArray {
    private static final int CUTOFF = 12;   // cutoff to insertion sort (any value between 0 and 12)

    private final char[] text;
    private final int[] index;   // index[i] = j means text.substring(j) is ith largest suffix
    private final int N;         // number of characters in text

    public CircularSuffixArray (String s) {
        if (s == null) throw new IllegalArgumentException();
        N = s.length();
       
        this.text = s.toCharArray();
        this.index = new int[N];
        for (int i = 0; i < N; i++)
            index[i] = i;

        sort(0, N-1, 0);
    }

    // 3-way string quicksort lo..hi starting at dth character
    private void sort(int lo, int hi, int d) { 
    	if (lo + d >= 2 * N || hi + d >= 2 * N) { return; }
        // cutoff to insertion sort for small subarrays
        if (hi <= lo + CUTOFF) {
            insertion(lo, hi, d);
            return;
        }

        int less = lo, great = hi;
        char v = text[(index[lo] + d) % N];
        int i = lo + 1;
        while (i <= great) {
            int t = text[(index[i] + d) % N];
            if      (t < v) exch(less++, i++);
            else if (t > v) exch(i, great--);
            else            i++;
        }

        // a[lo..less-1] < v = a[less..great] < a[great+1..hi]. 
        sort(lo, less-1, d);
        sort(less, great, d+1);
        sort(great+1, hi, d);
    }

    // sort from a[lo] to a[hi], starting at the dth character
    private void insertion(int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(index[j], index[j-1], d); j--)
                exch(j, j-1);
    }

    // is text[i+d..N) < text[j+d..N) ?
    private boolean less(int i, int j, int d) {
        if (i == j) return false;
        i = i + d;
        j = j + d;
        while (i < 2 * N && j < 2 * N) {
            if (text[i % N] < text[j % N]) return true;
            if (text[i % N] > text[j % N]) return false;
            i++;
            j++;
        }
        return false;
    }

    // exchange index[i] and index[j]
    private void exch(int i, int j) {
        int swap = index[i];
        index[i] = index[j];
        index[j] = swap;
    }

    public int length () {
        return this.N;
    }

    public int index (int i) {
        if (i < 0 || i >= this.N) 
            throw new IllegalArgumentException();
        return this.index[i];
    }

    public static void main(String[] args) {
        String s = "ABRACADABRA!";
  
    	
    	CircularSuffixArray test = new CircularSuffixArray(s);
    	
    	for (int i = 0; i < s.length(); i++) {
            System.out.print(test.index(i));
        }
        System.out.println();
    }
}