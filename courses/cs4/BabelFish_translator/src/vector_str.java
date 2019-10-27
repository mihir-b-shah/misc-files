


import java.util.Arrays;

// optimized for contigious removals
public class vector_str {

    private String[] array;
    private int pos;

    public vector_str() {
        array = new String[2];
    }

    public vector_str(int N) {
        array = new String[N];
    }

    public void add(String s) {
        if (pos == array.length) {
            String[] aux = new String[pos << 1];
            System.arraycopy(array, 0, aux, 0, pos);
            array = aux;
        }
        array[pos++] = s;
    }

    public String get(int i) {
        return array[i];
    }

    public int size() {
        return pos;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    /**
     *
     * @param st inclusive
     * @param dist length
     */
    public void remove_range_repl(String s, int st, int dist) {
        if (st + dist < array.length) {
            System.arraycopy(array, st + dist, array, st + 1, dist);
        }
        array[st] = s;
        pos -= dist - 1;
    }
}
