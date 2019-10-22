/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mihir
 */
public class vector_long {
    private long[] array;
    private int pos;
    private static final long FIRST_MASK = 0x7fff_ffff;
    
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
        for(int i = 0; i<array.length; ++i) {
            if((array[i] >>> 32) == hcode) {
                return (int) (array[i] & FIRST_MASK);
            }
        }
        return -1;
    }
}
