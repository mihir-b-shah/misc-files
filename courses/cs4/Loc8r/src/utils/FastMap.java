package utils;

import java.util.Arrays;
import java.util.function.LongBinaryOperator;
import java.util.function.LongPredicate;

/* uses linear probing and very fast bitmasks! yeeeet!
   load factor of 0.5 
   keeps the hashcode in first 32 bits, value in top 32 */
public class FastMap<T> {
    private long[] data;
    private static final long HASH_MASK = 0x7fff_ffff;
    public static final int NOT_FOUND = -1;
    private int size;

    /**
     * Hashcodes of objects inserted should NEVER be zero and map to 0 value
     * They should also not map to the key -1
     */
    public FastMap() {
        data = new long[1021];
    }

    /**
     * Hashcodes should never be zero AND map to 0 value
     *
     * @param N should be prime
     */
    public FastMap(int N) {
        data = new long[N];
    }

    /**
     * Use this as a set
     * Supports overriding a value
     * @param val a long to insert
     * @param rule the rule for overriding, returns 0/1. If the replacement
     *             should be used, then it should return 1. Else 0. Arg0 is 
     *             the original and Arg1 is the new element.
     */
    public void insert(final long val, final LongBinaryOperator rule) {
        int iter;
        long ref;
        if (size > data.length >>> 1) {
            long[] aux = data;
            data = new long[data.length << 1];
            for (int i = 0; i < aux.length; ++i) {
                iter = (int) adjustIndex(aux[i]);
                while ((ref = data[iter %= data.length]) != 0L 
                        && rule.applyAsLong(aux[i], ref) == 0L) {
                    ++iter;
                }
                switch((int) rule.applyAsLong(aux[i], ref)) {
                    case 0:
                    case 1: data[iter] = aux[i];
                }
            }
        } else {
            iter = (int) adjustIndex(val);
            while ((ref = data[iter %= data.length]) != 0L 
                    && rule.applyAsLong(val, ref) == 0L) {
                ++iter;
            }
            switch((int) rule.applyAsLong(val, ref)) {
                case 0:
                case 1: data[iter] = val;
            }
        }
    }
    /**
     * DOES NOT SUPPORT MANAGING DUPLICATES
     * Not required for this application
     * 
     * @param obj key
     * @param v value
     */
    public void insert(T obj, long v) {
        int iter;
        long hcode;
        if (size > data.length >>> 1) {
            long[] aux = data;
            data = new long[data.length << 1];
            for (int i = 0; i < aux.length; ++i) {
                iter = (int) adjustIndex(hcode = aux[i] & HASH_MASK);
                while (data[iter %= data.length] != 0L) {
                    ++iter;
                }
                data[iter] = hcode + (v << 32);
            }
        } else {
            iter = (int) adjustIndex(hcode = obj.hashCode() & HASH_MASK);
            while (data[iter %= data.length] != 0L) {
                ++iter;
            }
            data[iter] = hcode + (v << 32);
        }
    }

    /**
     * @param obj the object
     * @return -1 if not found, else the mapping value
     */
    public int get(T obj) {
        long hcode; 
        int iter;
        iter = (int) adjustIndex(hcode = obj.hashCode() & HASH_MASK);
        while (data[iter %= data.length] != 0L) {
            if(hcode == (data[iter] & HASH_MASK)) 
                return (int) (data[iter] >>> 32);
            ++iter;
        }
        return NOT_FOUND;
    }

    private final long adjustIndex(long val) {
        long hash = val % data.length;
        return hash > 0 ? hash : hash + data.length;
    }

    public int size() {
        return size;
    }
    
    public long[] collectSet(LongPredicate function) {
        if(function == null) {
            return Arrays.stream(data).filter(x->x!=0).toArray();
        } else {
            LongPredicate f2 = function.and(x->x!=0);
            return Arrays.stream(data).filter(f2).toArray();
        }
    } 
}
