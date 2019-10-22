
public class ctr_map {
    private vector_long[] map;
    private int size;
    
    public ctr_map(int N) {
        map = new vector_long[Math.min(N<<1,100_000)];
    }
    
    public void add(String k, int v) {
        int hcode = k.hashCode();
        vector_long ref = map[(hcode % map.length)];
        if(ref == null) {
            ref = new vector_long();
        }
        ref.add(((long) hcode << 32)+v);
        ++size;
        if(size >> 1 > map.length) {
            vector_long[] aux = new vector_long[map.length<<1];
            long[] iter;
            long access;
            for(int i = 0; i<map.length; ++i) {
                iter = map[i].get_array();
                for(int j = 0; j<iter.length; ++j) {
                    access = iter[j];
                    aux[(int) ((access >>> 32) % aux.length)].add(access);
                }
            }
            map = aux;
        }
    }
    
    public int get(Object o) {
        return map[o.hashCode()%map.length].find_hash(o);
    }
}
