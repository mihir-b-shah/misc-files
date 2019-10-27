/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mihir
 * still ridiculously inefficient
 *  get a bloom filter with associated float
 * 
 */
public class int_wrapper {
    private long val;
    private static final long MASK = 1L<<32;
    private static final long FIRST = 0x7fff_ffff;

    public int_wrapper(int v) {
        val = v;
    }

    public void incr(int i) {
        val += (MASK+i);
    }

    public float avg() {
        return ((float) (val&FIRST))/(val>>>32);
    }
}