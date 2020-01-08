
package collections;

import java.util.PrimitiveIterator;

/**
 * A fast and compact implementation of the LinkedList class for 
 * int values. Sort of like a LinkedHashSet.
 * 
 * @author mihir
 */
public class IntLinkList {
    private final int[] data;
    private final long[] pointers;
    private final float skipFactor;
    private int size;
    
    /**
     * Creates a new IntLinkList.
     * 
     * @param N initial capacity
     * @param skip the skip ratio. 0 to place elements sequentially in array, 
     * 1 is every other element, etc. It is recommended to keep the skip ratio
     * less than 1.
     */
    public IntLinkList(int N, float skip) {
        data = new int[N];
        pointers = new long[N];
        skipFactor = skip;
    }
    
    /**
     * Creates a new IntLinkList with initial capacity 15.
     * 
     * @param skip the skip ratio. 0 to place elements sequentially in array, 
     * 1 is every other element, etc. It is recommended to keep the skip ratio
     * less than 1.
     */
    public IntLinkList(float skip) {
        this(15, skip);
    }
    
    /**
     * Creates a new IntLinkList with initial capacity 15 and laid out 
     * contigiously in memory (skip ratio of 0).
     */
    public IntLinkList() {
        this(15, 0f);
    }
    
    public void add(int item) {
        
    }
    
    public void add(int index, int item) {
        
    }

    public void clear() {
        
    }
    
    public boolean contains(int item) {
        return false;
    }
    
    public int get(int index) {
        return 0;
    }
    
    public PrimitiveIterator.OfInt iterator()  {
        PrimitiveIterator.OfInt iterator = new PrimitiveIterator.OfInt() {
            @Override
            public int nextInt() {
                return 0;
            }

            @Override
            public boolean hasNext() {
                return false;
            }
        };
        return iterator;
    }
    
    public int remove() {
        return 0;
    }
    
    public int remove(int index) {
        return 0;
    }
    
    public int set(int index, int item) {
        return 0;
    }
    
    public int[] toArray() {
        return null;
    }
}
