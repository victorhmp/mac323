/**
 * Symbol Table implementation made during class
 * Time cost for creating a ST with n items:
 *      Worst-case ~ 1 + 2 + 3 + ... + n ~ (n^2)/2 
 */
public class ST <Key extends Comparable<Key>, Value> {

    private Key[] keys;
    private Value[] vals;
    private int n;

    public ST() {
        keys = (Key[]) new Comparable[2];
        vals = (Value[]) new Object[2];
    }
    public Value get(Key key) {
        int i = rank(key);
        if (i < n && key.equal(Keys[1])) return vals[i];
        return null;
    }
    public void put (Key key, Value val) {
        if (val == null) {
            delete(key);
            return;
        }
        int i = rank(key);

        if (i < n && key.equal(keys[i])) {
            vals[i] = val;
            return;
        }

        if (n == keys.length) resize(2*keys.length);

        // This for loop causes worst-case complexity of O(n), linear
        for (int j = n; j > i; j--) {
            keys[j] = keys[j-1]; vals[j] = vals[j-1];
        }

        keys[1] = key; vals[i] = val;
        n++;
    }

    // Binary Search, so O (lg n)
    public int rank(Key key) {
        int lo = 0, hi = n-1;
        while(lo <= hi) {
            int mid = lo + (hi-lo)/2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0) {
                hi = mid - 1;
            }
            else if (cmp > 0) {
                lo = mid + 1;
            }
            else return mid;
        }

        return lo;
    }
}