
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
    private Comparator<T> xysort;
    private float x, y;
    private T optObj;
    private float optDist;
    private final PriorityQueue<T> items;

    public KDTree(T[] array, int Kmax) {
        objects = (T[]) new Comparable2D[array.length];
        System.arraycopy(array, 0, objects, 0, array.length);
        xsort = (T one, T two) -> Float.compare(one.getX(), two.getX());
        ysort = (T one, T two) -> Float.compare(one.getY(), two.getY());
        xysort = (T one, T two)->
                Float.compare(one.getDist(x,y),two.getDist(x,y));

        generate(0, objects.length - 1, 0);
        items = new PriorityQueue(Kmax, xysort);
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
        } else if(lower == upper-1) {
            if((loc = objects[lower].getDist(x, y)) < optDist && 
                    !objects[lower].getVisited()) {
                optDist = loc;
                optObj = objects[lower];
            }
            return;
        }

        int mid = (lower + upper - 1) >>> 1;
        float one = objects[(lower + mid) >>> 1].getDist(x, y);
        float two = objects[(upper + mid) >>> 1].getDist(x, y);

        if (one <= two) {
            getNearestNeighbor(lower,mid-1,lvl+1);
            if((loc = objects[mid].getDist(x, y)) < optDist 
                    && !objects[mid].getVisited()) {
                optDist = loc;
                optObj = objects[mid];
            }
            switch(lvl&1) {
                case 0:
                    if(objects[mid].getXsqDist(x) < optDist) {
                        getNearestNeighbor(mid+1,upper,lvl+1);
                    }
                    break;
                case 1:
                    if(objects[mid].getYsqDist(y) < optDist) {
                        getNearestNeighbor(mid+1,upper,lvl+1);
                    }
                    break;
            }
        } else {
            getNearestNeighbor(mid+1,upper,lvl+1);
            if((loc = objects[mid].getDist(x, y)) < optDist
                    && !objects[mid].getVisited()) {
                optDist = loc;
                optObj = objects[mid];
            }
            switch(lvl&1) {
                case 0:
                    if(objects[mid].getXsqDist(x) < optDist) {
                        getNearestNeighbor(lower,mid-1,lvl+1);
                    }
                    break;
                case 1:
                    if(objects[mid].getYsqDist(y) < optDist) {
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
        for(int i = 0; i<K; ++i) {
            optDist = Float.MAX_VALUE;
            optObj = null;
            
            getNearestNeighbor(0, objects.length, 0);
            optObj.setVisited();
            nearest[i] = optObj;
        }
        
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
            return String.format("<%f,%f>, dist=%f", x, y, 
                    Math.sqrt(Math.pow(0.55-x,2)+Math.pow(0.7-y,2)));
        }
    }
    
    public static void main(String[] args) {
        XYPair[] array = 
                {new XYPair(0.1f,0.9f), new XYPair(0.2f,0.3f), new XYPair(0.4f,0.2f),
                 new XYPair(0.7f,0.2f), new XYPair(0.8f,0.1f), new XYPair(0.5f,0.5f),
                 new XYPair(0.6f,0.6f)};
        
        KDTree kdtree = new KDTree(array);
        System.out.println(kdtree.toString());
        Comparable2D[] res = kdtree.getKNN(0.55f, 0.7f, 7);
        System.out.println(Arrays.toString(res));
    }
}
