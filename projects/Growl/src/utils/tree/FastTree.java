
package utils.tree;

import java.util.Arrays;
import utils.queue.FastQueue;

/**
 * Implements an optimized red-black binary search tree.
 * The size must be smaller than 1048576 (2^20).
 * It is undefined behavior if this happens.
 * 
 * @author mihir
 * @param <T> the type.
 */
public class FastTree<T extends IntValue<T>> {
    private int[] tree; // a vector
    private int treePtr;
    
    private T[] data; // a vector
    private int dataPtr;
    
    private static final int ROOT_PTR = 0;
    private static final int ROOT_VAL = 1;
    private static final int LEFT_PTR = 2;
    private static final int RIGHT_PTR = 3;
    
    private static final int BLK_SHIFT = 2;
    private static final int LONG_NULL = 0xfffff;
    
    private static final int PARENT_MASK = 0x3ff00000;
    private static final int COMPOSE_MASK = 0x3ff;
    private static final int PARENT_SHIFT = 10;
    private static final int SHORT_NULL = 0x3ff;
    
    private static final int CHILD_MASK = 0xfffff;
    private static final int CHILD_SHIFT = 20;
    
    private static final int RED = 1;
    private static final int BLACK = 0;
    private static final int RB_SHIFT = 31;
    
    /**
     * The tree is stored as follows.
     * Each node contains four fields:
     * 
     * a KEY field that it a 32-bit integer
     * The sign bit is whether RED OR BLACK
     * The rest is a reference to the data array.
     * 
     * a VALUE field that is the absolute comparator value
     * we need all the precision we can get, so it is all 32 bits.
     * 
     * a LEFT field that points to the left node.
     * the highest 10 bits are the first 10 bits of the PARENT pointer.
     * 
     * a RIGHT field that points to the right node.
     * the highest 10 bits are the last 10 bits of the PARENT pointer.
     * 
     * @param root the root node.
     */
    public FastTree(T root) {
        data = (T[]) new IntValue[4];
        tree = new int[16];
        
        data[0] = root;
        tree[0] = 0;
        dataPtr = 1; treePtr = 1;
        tree[1] = root.value();
        tree[2] = LONG_NULL | SHORT_NULL << CHILD_SHIFT; 
        tree[3] = LONG_NULL | SHORT_NULL << CHILD_SHIFT; 
        tree[0] |= BLACK << RB_SHIFT;
    }
    
    /**
     * Inserts an item into the BST.
     * 
     * 1. Checks whether a position within the array is available (on the stack)
     * 2. Adjusts the stack accordingly
     * 3. Inserts the value into that position in the 'data' array.
     * 4. Determines the pointer (int) value to use in tree.
     * 5. Insert the item.
     * 6. Rebalance the tree.
     * 
     * Size CANNOT exceed 1048575 elements.
     * 
     * @param v the item to insert.
     */
    public final void insert(T v) {
        // grow the array if needed
        if(dataPtr == data.length) {
            T[] aux = (T[]) new IntValue[dataPtr << 1];
            System.arraycopy(data, 0, aux, 0, dataPtr);
            data = aux;
            
            int[] aux2 = new int[treePtr << BLK_SHIFT+1];
            System.arraycopy(tree, 0, aux2, 0, treePtr << BLK_SHIFT);
            tree = aux2;
        }
        
        // insert item into data table
        data[dataPtr] = v;

        int ptrShift = 0;
        int parentPtr;
        int comp,lrPtr;
        final int vComp = v.value();
        
        while(true) {
            comp = tree[ROOT_VAL + ptrShift];
            if(vComp > comp) {
                if((lrPtr = tree[ptrShift + RIGHT_PTR] & CHILD_MASK) == LONG_NULL) {
                    // insert to the right
                    tree[ptrShift+RIGHT_PTR] = treePtr|
                            tree[ptrShift+RIGHT_PTR]&PARENT_MASK;
                    parentPtr = ptrShift;
                    ptrShift = treePtr;
                    ptrShift <<= BLK_SHIFT;
                    tree[ptrShift + ROOT_PTR] = dataPtr;
                    tree[ptrShift + ROOT_VAL] = vComp;
                    tree[ptrShift + LEFT_PTR] = LONG_NULL | 
                            (parentPtr & COMPOSE_MASK) << CHILD_SHIFT;
                    tree[ptrShift + RIGHT_PTR] = LONG_NULL | 
                            (parentPtr >>> PARENT_SHIFT) << CHILD_SHIFT;
                    break;
                } else {
                    // moving right
                    ptrShift = tree[lrPtr << BLK_SHIFT];
                    ptrShift <<= BLK_SHIFT;
                }
            } else {
                if((lrPtr = tree[ptrShift + LEFT_PTR] & CHILD_MASK) == LONG_NULL) {
                    // insert to the left
                    tree[ptrShift+LEFT_PTR] = treePtr|
                            tree[ptrShift+LEFT_PTR]&PARENT_MASK;
                    parentPtr = ptrShift;
                    ptrShift = treePtr;
                    ptrShift <<= BLK_SHIFT;
                    tree[ptrShift + ROOT_PTR] |= dataPtr;
                    tree[ptrShift + ROOT_VAL] = vComp;
                    tree[ptrShift + LEFT_PTR] = LONG_NULL | 
                            (parentPtr & COMPOSE_MASK) << CHILD_SHIFT;
                    tree[ptrShift + RIGHT_PTR] = LONG_NULL | 
                            (parentPtr >>> PARENT_SHIFT) << CHILD_SHIFT;
                    break;
                } else {
                    ptrShift = tree[lrPtr << BLK_SHIFT];
                    ptrShift <<= BLK_SHIFT;
                }
            }
        }
        ++dataPtr;
        ++treePtr;
    }  
    
    @Override
    public String toString() {
        return String.format("Tree: %s%nData: %s%n", Arrays.toString(tree), 
                Arrays.deepToString(data));
    }
    
    private class QueueItem {
        private final int bt;
        private final int height;
        private final int xJust;
        
        public QueueItem(int bt, int height, int xJust) {
            this.bt = bt;
            this.height = height;
            this.xJust = xJust;
        }
    }
    
    public final String printTree(int consoleWidth) {
        StringBuilder sb = new StringBuilder();
        FastQueue<QueueItem> queue = new FastQueue<>(8);
        queue.push(new QueueItem(0,0,0));
        
        int currHeight = -1;
        int currJustif = 0;
        
        while (queue.size() > 0) {
            QueueItem obj = queue.pop(); 
            
            if(currHeight != obj.height) {
                sb.append('\n');
                currHeight = obj.height;
                currJustif = 0;
            }
            
            int count = data[obj.bt].toString().length() >>> 1;
            int amt = convert(consoleWidth, obj.height, obj.xJust);
            for(int i = 0; i<amt-currJustif-count; ++i) {
                sb.append(' ');
            }

            sb.append(data[obj.bt].toString());
            currJustif = amt;

            if((tree[LEFT_PTR+(obj.bt << BLK_SHIFT)] & CHILD_MASK) != LONG_NULL)
                queue.push(new QueueItem(tree[LEFT_PTR+(obj.bt << BLK_SHIFT)] & CHILD_MASK,
                        obj.height+1,2*obj.xJust+0));
            if((tree[RIGHT_PTR+(obj.bt << BLK_SHIFT)] & CHILD_MASK) != LONG_NULL)
                queue.push(new QueueItem(tree[RIGHT_PTR+(obj.bt << BLK_SHIFT)] & CHILD_MASK,
                        obj.height+1,2*obj.xJust+1));
        }
        
        return sb.toString();
    }
    
    private final int convert(double w, int h, int j) {
        return (int) (w*((j << 1)+1)/(1 << (h+1)));
    }
    
    static class CharWrapper implements IntValue<CharWrapper> {
        private final char c;
        
        public CharWrapper(char c) {
            this.c = c;
        }
        
        @Override
        public int value() {
            return c-'0';
        }
        
        @Override
        public String toString() {
            return ""+c;
        }
    }
    
    public static void main(String[] args) {
        FastTree<CharWrapper> bst = new FastTree<>(new CharWrapper('a'));
        //System.out.println(bst.printTree(80));
        bst.insert(new CharWrapper('h'));
        //System.out.println(bst.printTree(80));
        bst.insert(new CharWrapper('c'));
        //System.out.println(bst.printTree(80));
        bst.insert(new CharWrapper('b'));
        //System.out.println(bst.printTree(80));
        bst.insert(new CharWrapper('e'));
        //System.out.println(bst.printTree(80));
        bst.insert(new CharWrapper('f'));
        //System.out.println(bst.printTree(80));
        bst.insert(new CharWrapper('g'));
        //System.out.println(bst.printTree(80));
        bst.insert(new CharWrapper('d'));
        System.out.println(bst);
        System.out.println(bst.printTree(80));
    }
}
