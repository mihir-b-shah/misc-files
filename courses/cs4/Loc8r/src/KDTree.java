
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/* Cannot be mutated once constructed, only through batch updates
   Amortized as O(1)
   Go x,y,x,y... dimensions to partition the points*/

public class KDTree<T extends Comparable2D<T>> {

    private final T[] objects;
    private final Comparator<T> xsort;
    private final Comparator<T> ysort;
    private final Comparator<T> xysort;
    private final float x, y;
    private final PriorityQueue<T> items;
    private final BoundedMaxheap<T> threshold;
    private float minDist;
    private final int numItems;

    public KDTree(float refX, float refY, T[] array, int Kmax) {
        x = refX; y = refY;
        numItems = Kmax;
        objects = (T[]) new Comparable2D[array.length];
        System.arraycopy(array, 0, objects, 0, array.length);
        xsort = (T one, T two) -> Float.compare(one.getX(), two.getX());
        ysort = (T one, T two) -> Float.compare(one.getY(), two.getY());
        xysort = (T one, T two)->
                Float.compare(one.getDist(x,y),two.getDist(x,y));

        generate(0, objects.length - 1, 0);
        items = new PriorityQueue(Kmax, xysort);
        threshold = new BoundedMaxheap(Kmax, xysort);
    }
    
    @Override
    public String toString() {
        return Arrays.toString(objects);
    }

    /**
     * Recursively generate the kd tree
     *
     * @param s inclusive
     * @param e inclusive
     * @param lvl level of search
     */
    private void generate(int s, int e, int lvl) {
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
        generate(med + 1, e, lvl + 1);
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
                        if (x > objects[mid].getX()) {
                            lower = mid + 1;
                        } else {
                            upper = mid - 1;
                        }
                        break;
                    case 1:
                        if (y > objects[mid].getY()) {
                            lower = mid + 1;
                        } else {
                            upper = mid - 1;
                        }
                        break;
                }
            }
            ++ctr;
        }
        mid = (lower + upper) >>> 1;
        return objects[mid].hashValue() == hash ? objects[mid] : null;
    }

    private void getNearestNeighbor(int lower, int upper, int lvl) {
        float loc;
        if (lower >= upper) {
            return;
        } else if(lower == upper-1 && !objects[lower].getVisited()) {
            items.offer(objects[lower]);
            threshold.insert(objects[lower]);
            objects[lower].setVisited();
            return;
        }

        int mid = (lower + upper - 1) >>> 1;
        float one = objects[(lower + mid) >>> 1].getDist(x, y);
        float two = objects[(upper + mid) >>> 1].getDist(x, y);

        if (one <= two) {
            getNearestNeighbor(lower,mid-1,lvl+1);
            if(!objects[mid].getVisited()) {
                items.offer(objects[mid]);
                threshold.insert(objects[mid]);
                objects[mid].setVisited();
            }
            switch(lvl&1) {
                case 0:
                    if(objects[mid].getXsqDist(x) < 
                            threshold.getKthElement().getDist(x, y)) {
                        getNearestNeighbor(mid+1,upper,lvl+1);
                    }
                    break;
                case 1:
                    if(objects[mid].getYsqDist(y) < 
                            threshold.getKthElement().getDist(x, y)) {
                        getNearestNeighbor(mid+1,upper,lvl+1);
                    }
                    break;
            }
        } else {
            getNearestNeighbor(mid+1,upper,lvl+1);
            if(!objects[mid].getVisited()) {
                items.offer(objects[mid]);
                objects[mid].setVisited();
            }
            
            switch(lvl&1) {
                case 0:
                    if(objects[mid].getXsqDist(x) < 
                            threshold.getKthElement().getDist(x, y)) {
                        getNearestNeighbor(lower,mid-1,lvl+1);
                    }
                    break;
                case 1:
                    if(objects[mid].getYsqDist(y) < 
                            threshold.getKthElement().getDist(x, y)) {
                        getNearestNeighbor(lower,mid-1,lvl+1);
                    }
                    break;
            }
        }
    }

    public T[] getKNN(int K) {
        T[] nearest = (T[]) new Comparable2D[Math.min(K, objects.length)];
        
        while(items.size()<nearest.length<<1) {
            getNearestNeighbor(0, objects.length, 0);
        }
        
        for(int i = 0; i<K; ++i) {
            nearest[i] = items.poll();
        }
        
        return nearest;
    }
   
    public static void main(String[] args) {
        XYPair[] array = 
                {new XYPair(0.1f,0.9f), new XYPair(0.2f,0.3f), new XYPair(0.4f,0.2f),
                 new XYPair(0.7f,0.2f), new XYPair(0.8f,0.1f), new XYPair(0.5f,0.5f),
                 new XYPair(0.6f,0.6f)};
        
        KDTree kdtree = new KDTree(0.55f, 0.7f, array, 7);
        System.out.println(kdtree.toString());
        Comparable2D[] res = kdtree.getKNN(3);
        System.out.println(Arrays.toString(res));
    }
}
