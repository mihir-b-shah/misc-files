
// takes advantage of blazing fast hardware??
// also im lazy

public class vector_flt<T> {

    private float[] array;
    private int pos;
    private static final long MOD = 2L << 20;

    public vector_flt() {
        array = new float[2];
    }

    public vector_flt(int N) {
        array = new float[N];
    }

    public void add(float e) {
        if (pos == array.length) {
            float[] aux = new float[pos << 1];
            System.arraycopy(array, 0, aux, 0, pos);
            array = aux;
        }
        array[pos++] = e;
    }

    public float[] get_array() {
        return array;
    }

    public float get(int i) {
        return array[i];
    }

    public int get_size() {
        return pos;
    }

    public int find_hash(T o) {
        for (int i = 0; i < pos; ++i) {
            if ((int) (array[i]/MOD) == o.hashCode()) {
                return (int) (array[i] % MOD);
            }
        }
        return -1;
    }

    /**
     * Assumes that <code>v < 1_000_000 </code>
     */
    public void set_hash(T o, int v) {
        for (int i = 0; i < pos; ++i) {
            if ((int) (array[i]/MOD) == o.hashCode()) {
                array[i] += v;
                break;
            }
        }
    }
}
