
public class vector_int {
    private int[] array;
    private int pos;
    
    public vector_int() {
        array = new int[2];
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
