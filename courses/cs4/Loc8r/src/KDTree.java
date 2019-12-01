
import java.util.Arrays;
import java.util.Comparator;

/* Cannot be mutated once constructed, only through batch updates
   Amortized as O(1)
   Go x,y,x,y... dimensions to partition the points*/
public class KDTree<T extends Comparable2D<T>> {

    private final T[] objects;
    private final Comparator<T> xsort;
    private final Comparator<T> ysort;
    private float x, y;
    private T optObj;
    private float optDist;

    public KDTree(T[] array) {
        objects = (T[]) new Comparable2D[array.length];
        System.arraycopy(array, 0, objects, 0, array.length);
        xsort = (T one, T two) -> Float.compare(one.getX(), two.getX());
        ysort = (T one, T two) -> Float.compare(one.getY(), two.getY());

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
        } else if(lower == upper-1) {
            if((loc = objects[lower].getDist(x, y)) < optDist) {
                optDist = loc;
                optObj = objects[lower];
            }
            return;
        }

        int mid = (lower + upper - 1) >>> 1;
        float one = objects[(lower + mid) >>> 1].getDist(x, y);
        float two = objects[(mid + upper) >>> 1].getDist(x, y);

        if (one <= two) {
            getNearestNeighbor(lower,mid-1,lvl+1);
            if((loc = objects[mid].getDist(x, y)) < optDist) {
                optDist = loc;
                optObj = objects[mid];
            }
            switch(lvl&1) {
                case 0:
                    if(objects[(mid+upper)>>>1].getXsqDist(x) < optDist) {
                        getNearestNeighbor(mid+1,upper,lvl+1);
                    }
                    break;
                case 1:
                    if(objects[(mid+upper)>>>1].getYsqDist(y) < optDist) {
                        getNearestNeighbor(mid+1,upper,lvl+1);
                    }
                    break;
            }
        } else {
            getNearestNeighbor(mid+1,upper,lvl+1);
            if((loc = objects[mid].getDist(x, y)) < optDist) {
                optDist = loc;
                optObj = objects[mid];
            }
            switch(lvl&1) {
                case 0:
                    if(objects[(lower+mid)>>>1].getXsqDist(x) < optDist) {
                        getNearestNeighbor(lower,mid-1,lvl+1);
                    }
                    break;
                case 1:
                    if(objects[(lower+mid)>>>1].getYsqDist(y) < optDist) {
                        getNearestNeighbor(lower,mid-1,lvl+1);
                    }
                    break;
            }
        }
    }

    public T[] getKNN(float x, float y, int K) {
        this.x = x;
        this.y = y;
        T[] nearest = (T[]) new Comparable2D[Math.min(K, objects.length)];
        optDist = Float.MAX_VALUE;
        getNearestNeighbor(0, objects.length, 0);
        nearest[0] = optObj;
        
        return nearest;
    }
    
    private static class XYPair implements Comparable2D<XYPair> {
        private final float x;
        private final float y;
        private boolean visited;
        
        public XYPair(float x, float y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public float getX() {
            return x;
        }
        
        @Override
        public float getY() {
            return y;
        }
        
        @Override
        public boolean getVisited() {
            return visited;
        }
        
        @Override
        public void setVisited() {
            visited = true;
        }
        
        @Override
        public String toString() {
            return String.format("<%f,%f>", x, y);
        }
    }
    
    public static void main(String[] args) {
        XYPair[] array = new XYPair[12];
        array[0] = new XYPair(0.6f, 0.5f);
        for(int i = 1; i<array.length; ++i) {
            array[i] = new XYPair(
                    (float) Math.random(), (float) Math.random());
        }
        
        KDTree kdtree = new KDTree(array);
        Comparable2D[] res = kdtree.getKNN(0.59f, 0.51f, 1);
        System.out.println(res[0]);
    }
}
