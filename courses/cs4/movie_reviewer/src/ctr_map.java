
public class ctr_map {

    public class vector_long {
        private long[] array;
        private int pos;
        private static final long FIRST_MASK = 0x7fff_ffff;
        private static final int MASK_OUTER = 0x1_ffff;
        private static final int SHIFT_OUTER = 1 << 17;

        public vector_long() {
            array = new long[2];
        }

        public vector_long(int N) {
            array = new long[N];
        }

        public void add(long e) {
            if(pos == array.length) {
                long[] aux = new long[pos << 1];
                System.arraycopy(array, 0, aux, 0, pos);
                array = aux;
            }
            array[pos++] = e;
        }

        public long[] get_array() {
            return array;
        }

        public long get(int i) {
            return array[i];
        }

        public int get_size() {
            return pos;
        }

        public int find_hash(Object o) {
            int hcode = o.hashCode();
            Object s;
            for(int i = 0; i<array.length; ++i) {
                if((array[i] >>> 32) == hcode) {
                    s = Long.toBinaryString(array[i]);
                    return (int) (array[i] & FIRST_MASK);
                }
            }
            return -1;
        }
        
        public void set_hash(Object o, int v) {
            int hcode = o.hashCode();
            for(int i = 0; i<array.length; ++i) {
                if((array[i] >>> 32) == hcode) {
                    array[i] += (v & MASK_OUTER) + (SHIFT_OUTER);
                    break;
                }
            }
        }
    }
    
    private vector_long[] map;
    private int size;
    
    public ctr_map(int N) {
        map = new vector_long[Math.min(N<<1,100_000)];
    }

    public void incr_str(Object k, int v) {
        vector_long vl = map[adjust_index(k.hashCode())];
        if(vl != null) {
            vl.set_hash(k, v);
        }
    }
    
    public void put(Object k, int v) {
        int hcode = k.hashCode();
        vector_long ref = map[adjust_index(hcode)];
        if(ref == null) {
            map[adjust_index(hcode)] = ref = new vector_long();
        }
        ref.add(((long) hcode << 32)+v);
        ++size;
        if(8 + (size >> 1) > map.length) {
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
        vector_long vl = map[adjust_index(o.hashCode())];
        if(vl != null) {
            return vl.find_hash(o);
        } else {
            return -1;
        }
    }
    
    public int adjust_index(int e) {
        int val = e % map.length;
        if(val < 0) {
            return map.length + (val);
        } else {
            return val;
        }
    }
}
