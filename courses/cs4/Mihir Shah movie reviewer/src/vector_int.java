/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mihir
 */
public class vector_int {
    private int[] array;
    private int pos;
    
    public vector_int() {
        array = new int[2];
    }
    
    public vector_int(int N) {
        array = new int[N];
    }
    
    public void add(int e) {
        if(pos == array.length) {
            int[] aux = new int[pos << 1];
            System.arraycopy(array, 0, aux, 0, pos);
            array = aux;
        }
        array[pos++] = e;
    }
    
    public int get(int i) {
        return array[i];
    }
}
