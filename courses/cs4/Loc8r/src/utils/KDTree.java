package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/* Cannot be mutated once constructed, only through batch updates
   Amortized as O(1)
   Go x,y,x,y... dimensions to partition the points*/

public class KDTree<T extends Comparable2D<T>> {

    private static final int NUM_ITER = 4;
    private T[] objects;
    private final Comparator<T> xsort;
    private final Comparator<T> ysort;
    private final Comparator<T> xysort;
    private final float x, y;
    private final PriorityQueue<T> items;
    private final BoundedMaxHeap<T> threshold;
    private final int numItems;

    public KDTree(float refX, float refY, List<T> array, int Kmax) {
        x = refX; y = refY;
        numItems = Kmax;
        objects = (T[]) new Comparable2D[array.size()];
        objects = array.toArray(objects);
        xsort = (T one, T two) -> Float.compare(one.getX(), two.getX());
        ysort = (T one, T two) -> Float.compare(one.getY(), two.getY());
        xysort = (T one, T two)->
                Float.compare(one.getDist(x,y),two.getDist(x,y));

        generate(0, objects.length - 1, 0);
        items = new PriorityQueue(Kmax, xysort);
        threshold = new BoundedMaxHeap(Kmax<<1, xysort);
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

    private int getNearestNeighbor(int lower, int upper, int lvl) {
        if (lower >= upper) {
            return 0;
        } else if(lower == upper-1 && !objects[lower].getVisited()) {
            items.offer(objects[lower]);            
            threshold.insert(objects[lower]);
            objects[lower].setVisited(); // to mark the object
            return 1; // to mark objects in its subtree
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
        return 0;
    }

    public ArrayList<T> getKNN(int K) {
        final int size = Math.min(K, objects.length);
        ArrayList<T> nearest = new ArrayList<>(size);
        int ctr = 0;
        
        while(ctr < NUM_ITER && items.size()<nearest.size()<<1) {
            getNearestNeighbor(0, objects.length, 0);
            ++ctr;
        }
        
        T item;
        
        for(int i = 0; i<K; ++i) {
            item = items.poll();
            if(item != null) {
                nearest.add(item);
            }
        }
        
        return nearest;
    }
}
