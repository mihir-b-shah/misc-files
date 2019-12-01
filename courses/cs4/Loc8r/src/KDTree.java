
import java.util.Arrays;
import java.util.Comparator;

/* Cannot be mutated once constructed, only through batch updates
   Amortized as O(1)
   Go x,y,x,y... dimensions to partition the points*/
public class KDTree<T extends Comparable2D<T>> {

    private final T[] objects;
    private final Comparator<T> xsort;
    private final Comparator<T> ysort;

    public KDTree(T[] array) {
        objects = (T[]) new Comparable2D[array.length];
        System.arraycopy(array, 0, objects, 0, array.length);
        xsort = (T one, T two)->Float.compare(one.getX(), two.getX());
        ysort = (T one, T two)->Float.compare(one.getY(), two.getY());

        generate(0, objects.length - 1, 0);
    }

    /**
     * Recursively generate the kd tree
     *
     * @param s inclusive
     * @param e inclusive
     * @param lvl level of search
     */
    private void generate(int s, int e, int lvl) {
        System.out.printf("S: %d, E: %d, LVL: %d%nArray: %s%n", s, e, lvl,
                Arrays.toString(objects));
        
        if (s >= e) {
            return;
        }
        
        int med = (s + e) >>> 1;
        switch (lvl & 1) {
            case 0:
                Arrays.sort(objects, s, e + 1, xsort);
                break;
            case 1:
                Arrays.sort(objects, s, e + 1, ysort);
                break;
        }
        
        generate(s, med - 1, lvl + 1);
        generate(med+1, e, lvl+1);
    }

    public T get(float x, float y) {
        long hash = ((long) Float.hashCode(x) << 32) + Float.hashCode(y);
        int lower = 0;
        int upper = objects.length - 1;
        int mid;
        int ctr = 0;

        while (lower < upper) {
            mid = (lower + upper) >>> 1;
            if (objects[mid].hashValue() == hash) {
                return objects[mid];
            } else {
                switch (ctr & 1) {
                    case 0:
                        switch (Float.compare(x, objects[mid].getX())) {
                            case 1:
                                lower = mid + 1;
                                break;
                            case -1:
                                upper = mid - 1;
                                break;
                        }
                        break;
                    case 1:
                        switch (Float.compare(y, objects[mid].getY())) {
                            case 1:
                                lower = mid + 1;
                                break;
                            case -1:
                                upper = mid - 1;
                                break;
                        }
                        break;
                }
            }
            ++ctr;
        }
        mid = (lower + upper) >>> 1;
        return objects[mid].hashValue() == hash ? objects[mid] : null;
    }
}
