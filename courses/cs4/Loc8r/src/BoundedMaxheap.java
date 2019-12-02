
import java.util.Comparator;

public class BoundedMaxheap<T extends Comparable2D<T>> {

    private final T[] heap;
    private final Comparator<T> comp;
    private int pos; // for the first K insertions

    public BoundedMaxheap(int K, Comparator<T> comp) {
        heap = (T[]) new Comparable2D[K];
        this.comp = comp;
    }

    public void insert(final T obj) {

        if (pos < heap.length) {
            heap[pos++] = obj;
        } else if (comp.compare(obj, heap[0]) >= 0) {
            return;
        } else {
            heap[0] = obj;
        }

        int auxPtr = pos - 1;
        int auxPtr2;

        while (auxPtr > 0) {
            auxPtr2 = auxPtr >>> 1;
            if (comp.compare(heap[auxPtr], heap[auxPtr2]) > 0) {
                T temp = heap[auxPtr];
                heap[auxPtr] = heap[auxPtr2];
                heap[auxPtr2] = temp;
            }
            auxPtr = auxPtr2;
        }
    }

    public T getKthElement() {
        return heap[0];
    }
}
