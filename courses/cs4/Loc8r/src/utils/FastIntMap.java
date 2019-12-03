package utils;

/* uses linear probing and very fast bitmasks! yeeeet!
   load factor of 0.5 
   keeps the hashcode in first 32 bits, value in top 32 */
public class FastIntMap<T> {
    private long[] data;
    private static final long HASH_MASK = 0x7fff_ffff;
    public static final int NOT_FOUND = -1;
    private int size;

    /**
     * Hashcodes of objects inserted should NEVER be zero and map to 0 value
     * They should also not map to the key -1
     */
    public FastIntMap() {
        data = new long[1021];
    }

    /**
     * Hashcodes should never be zero AND map to 0 value
     *
     * @param N should be prime
     */
    public FastIntMap(int N) {
        data = new long[N];
    }

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
}
