
package bits;

import java.util.*;
import java.util.stream.IntStream;

public class LazyDenseSet implements BaseSet {
    private final long[] pattern;
    private final long[] bits;
    
    public LazyDenseSet(int N) {
        pattern = new long[1 + (N >>> 5)];
        bits = new long[1 + (N >>> 6)];
    }
    

    @Override
    public void add(Collection<? extends Number> vals) {

    }

    @Override
    public void remove(Collection<? extends Number> vals) {
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public PrimitiveIterator.OfInt iterator() {
        return null;
    }
    
    public static final int dosomething(int x) {
        return (x&1) == 0 ? x : x >>> 3;
    }

    public static void main(String[] args) {
        boolean flag;
        for(int k = 100000, j = 0; k<1_000_000_000; k*=10, ++j) {
            if(j < 10) {
                k /= 10;
                flag = true;
            } else {
                flag = false;
            }
            if(!flag) System.out.println("LEN: " + k);
            int[] data = new int[k];
            for(int i = 0; i<data.length; ++i) {
                data[i] += Integer.MAX_VALUE*Math.random();
            }
            IntStream stream = Arrays.stream(data);

            long T = System.nanoTime();
            int[] done = stream.map(x->x*2).map(x->x>1000?9:x).map(x->dosomething(x)).filter(x->x%2==0).toArray();
            if(!flag) System.out.println("Stream: " + (System.nanoTime()-T));

            T = System.nanoTime();
            for(int i = 0; i<data.length; ++i) {
                data[i] *= 2;
                data[i] = dosomething(data[i] > 1000 ? 9 : data[i]);
            }
            if(!flag) System.out.println("One loop: " + (System.nanoTime()-T) + "\n");
        }
    }
}

class Function {
    
}

interface BaseSet {
    void add(Collection<? extends Number> vals);
    void remove(Collection<? extends Number> vals);
    
    int size();
    PrimitiveIterator.OfInt iterator();
}
