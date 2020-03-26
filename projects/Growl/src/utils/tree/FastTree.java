package utils.tree;

import java.util.Random;
import java.util.TreeSet;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import utils.queue.FastQueue;

/**
 * Implements an optimized red-black binary search tree. The type of the input
 * is any input that can be expressed as an integer for comparison purposes.
 *
 * Most types can do this. All primitive wrappers can. Strings probably can't
 * unless you have some special size string.
 *
 * MAX CAPACITY OF 400 million nodes
 *
 * This can also be used as a multimap.
 *
 * Benchmarked as 1-1.5x faster for contains() and 1-2x faster for insert than
 * TreeMap. Based on current results, not slower for add/contains.
 *
 * The tree is stored as follows. Each node contains five fields:
 *
 * a KEY field that it a 32-bit integer The sign bit is whether RED OR BLACK The
 * rest is a reference to the data array.
 *
 * a VALUE field that is the absolute comparator value we need all the precision
 * we can get, so it is all 32 bits.
 *
 * a LEFT field that points to the left node.
 *
 * a RIGHT field that points to the right node.
 *
 * a PARENT field that points to the parent node
 * 
 * BUG ON LINE 557.
 *
 * @author mihir
 * @param <T> the type.
 */
public class FastTree<T> {

    private int[] tree; // a vector
    private int treePtr;

    private T[] data; // a vector
    private int dataPtr;

    private int rootPtr;
    private int size;

    private final ToIntFunction<T> compGenerator;

    private static final int HANDLE = 0;
    private static final int COMP = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;
    private static final int PARENT = 4;

    private static final int NULL = -1;
    private static final int BLK_SIZE = 5;

    private static final int RED = 1;
    private static final int BLACK = 0;
    private static final int TOGGLE = Integer.MIN_VALUE;
    private static final int SHIFT = 31;
    private static final int KEY_MASK = Integer.MAX_VALUE;

    private static final int FROM_LEFT = 0;

    private static final String RED_COL = "\033[0;31m";
    private static final String RESET = "\033[0m";

    private static final int LEFT_LEFT = 0;
    private static final int LEFT_RIGHT = 1;
    private static final int RIGHT_LEFT = 2;
    private static final int RIGHT_RIGHT = 3;

    private static final int RED_RED = 3;
    private static final int RED_BLACK = 2;
    private static final int BLACK_RED = 1;
    private static final int BLACK_BLACK = 0;

    private static final int MAX_CAPACITY = 1 << 30;

    /**
     * Constructs a FastTree object.
     *
     * @param intFx the function giving the int compare value for a key.
     */
    public FastTree(ToIntFunction<T> intFx) {
        data = (T[]) new Object[4];
        tree = new int[20];
        compGenerator = intFx;
    }

    /**
     * Constructs a FastTree object.
     *
     * @param intFx the function giving the int compare value for a key.
     * @param capacity the capacity of the array
     */
    public FastTree(ToIntFunction<T> intFx, int capacity) {
        data = (T[]) new Object[capacity];
        tree = new int[BLK_SIZE * capacity];
        compGenerator = intFx;
    }

    /**
     * Does a pure left or right rotation.
     *
     * @param LR whether it is a left/right rotation. Left is true.
     * @param parentPtr the parent of <param>insertPtr</param>
     * @param pp the parent of <param>parentPtr</param>
     */
    private void pureRotate(boolean LR, int parentPtr, int pp) {
        final int LEFT, RIGHT;
        if (LR) {
            LEFT = FastTree.LEFT;
            RIGHT = FastTree.RIGHT;
        } else {
            RIGHT = FastTree.LEFT;
            LEFT = FastTree.RIGHT;
        }

        final int PP_PARENT = tree[pp + PARENT];
        if (PP_PARENT == -1) {
            rootPtr = parentPtr;
        } else {
            tree[(PP_PARENT & KEY_MASK) + 2 + (PP_PARENT >>> SHIFT)] = parentPtr;
        }

        final int T3 = tree[parentPtr + RIGHT];
        if (T3 != NULL) {
            tree[T3 + PARENT] = pp | LEFT - 2 << SHIFT;
        }

        tree[pp + PARENT] = parentPtr | RIGHT - 2 << SHIFT;
        tree[pp + LEFT] = T3;

        tree[parentPtr + PARENT] = PP_PARENT;
        tree[parentPtr + RIGHT] = pp;
    }

    /**
     * Does a mixed left or right rotation.
     *
     * @param LR whether it is a left/right rotation.
     * @param insertPtr the original insertion point.
     * @param parentPtr the parent of <param>insertPtr</param>
     * @param pp the parent of <param>parentPtr</param>
     */
    private void mixedRotate(boolean LR, int insertPtr, int parentPtr, int pp) {
        final int LEFT, RIGHT;
        if (LR) {
            LEFT = FastTree.LEFT;
            RIGHT = FastTree.RIGHT;
        } else {
            RIGHT = FastTree.LEFT;
            LEFT = FastTree.RIGHT;
        }

        final int PP_PARENT = tree[pp + PARENT];

        if (PP_PARENT == -1) {
            rootPtr = insertPtr;
        } else {
            tree[(PP_PARENT & KEY_MASK) + 2 + (PP_PARENT >>> SHIFT)] = insertPtr;
        }

        final int T2 = tree[insertPtr + LEFT];
        if (T2 != NULL) {
            tree[T2 + PARENT] = parentPtr | RIGHT - 2 << SHIFT;
        }
        final int T3 = tree[insertPtr + RIGHT];
        if (T3 != NULL) {
            tree[T3 + PARENT] = pp | LEFT - 2 << SHIFT;
        }

        // modify pp
        tree[pp + PARENT] = insertPtr | (RIGHT - 2) << SHIFT;
        tree[pp + LEFT] = T3;

        // modify parent
        tree[parentPtr + PARENT] = insertPtr | (LEFT - 2) << SHIFT;
        tree[parentPtr + RIGHT] = T2;

        // modfy insert
        tree[insertPtr + PARENT] = PP_PARENT;
        tree[insertPtr + LEFT] = parentPtr;
        tree[insertPtr + RIGHT] = pp;
    }

    /**
     * Rotates a 'balanced' configuration to the pure rotate input case,
     * essentially reverses the <code>pureRotate()</code> method.
     *
     * @param LR left/right config, left is true
     * @param highChild the higher child in the final position
     * @param sibling the sibling node.
     */
    private void reverseRotate(boolean LR, int highChild, int sibling) {
        final int LEFT, RIGHT;
        if (LR) {
            LEFT = FastTree.LEFT;
            RIGHT = FastTree.RIGHT;
        } else {
            RIGHT = FastTree.LEFT;
            LEFT = FastTree.RIGHT;
        }

        final int sibParent = tree[sibling + PARENT];
        final int T2 = tree[highChild + RIGHT];
        if (T2 != NULL) {
            tree[T2 + PARENT] = sibling | LEFT - 2 << SHIFT;
        }

        tree[sibParent + RIGHT] = highChild;

        tree[sibling + PARENT] = highChild | RIGHT - 2 << SHIFT;
        tree[sibling + LEFT] = T2;

        tree[highChild + PARENT] = sibParent;
        tree[highChild + RIGHT] = sibling;
    }

    /**
     * Inserts an item into the BST.
     *
     * @param v the item to insert.
     * @return whether the item was inserted correctly.
     */
    public final boolean insert(final T v) {
        if (treePtr > MAX_CAPACITY - 1) {
            return false;
        } else if(size == 0) {
            data[HANDLE] = v;
            dataPtr = 1;
            treePtr = BLK_SIZE;
            tree[COMP] = compGenerator.applyAsInt(v);
            tree[LEFT] = NULL;
            tree[RIGHT] = NULL;
            tree[PARENT] = NULL;
            ++size;
        }
        // grow the array if needed
        if (dataPtr == data.length) {
            T[] aux = (T[]) new Object[dataPtr << 1];
            System.arraycopy(data, 0, aux, 0, dataPtr);
            data = aux;

            int[] aux2 = new int[treePtr << 1];
            System.arraycopy(tree, 0, aux2, 0, treePtr);
            tree = aux2;
        }

        // insert item into data table
        data[dataPtr] = v;

        int insertPtr = rootPtr;
        int parentPtr;
        int lrPtr;
        final int vComp = compGenerator.applyAsInt(v);

        int choosePtr;
        while (true) {
            if (vComp > tree[COMP + insertPtr]) {
                choosePtr = RIGHT;
            } else if (vComp < tree[COMP + insertPtr]) {
                choosePtr = LEFT;
            } else {
                // element already exists.
                return false;
            }
            if ((lrPtr = tree[insertPtr + choosePtr]) == NULL) {
                tree[insertPtr + choosePtr] = treePtr;
                tree[treePtr + HANDLE] = dataPtr ^ TOGGLE;
                tree[treePtr + COMP] = vComp;
                tree[treePtr + LEFT] = NULL;
                tree[treePtr + RIGHT] = NULL;
                tree[treePtr + PARENT] = insertPtr | (choosePtr - 2) << SHIFT;
                parentPtr = insertPtr;
                insertPtr = treePtr;
                break;
            } else {
                insertPtr = lrPtr;
            }
        }

        int sIndex;
        int auxVal;
        while (parentPtr > -1 && tree[parentPtr] >>> SHIFT == RED) {
            auxVal = tree[parentPtr + PARENT];
            sIndex = tree[(auxVal & KEY_MASK) + BLK_SIZE - 2 - (auxVal >>> SHIFT)];

            if (sIndex == NULL || tree[sIndex] >>> SHIFT == BLACK) {
                int pp = auxVal & KEY_MASK;
                // switch between the different rotation cases
                switch (((tree[parentPtr + PARENT] >>> SHIFT) << 1)
                        + (tree[insertPtr + PARENT] >>> SHIFT)) {
                    case LEFT_LEFT:
                        pureRotate(true, parentPtr, pp);
                        tree[pp] |= TOGGLE;
                        tree[parentPtr] &= KEY_MASK;
                        break;
                    case LEFT_RIGHT:
                        mixedRotate(true, insertPtr, parentPtr, pp);
                        tree[pp] |= TOGGLE;
                        tree[insertPtr] &= KEY_MASK;
                        break;
                    case RIGHT_LEFT:
                        mixedRotate(false, insertPtr, parentPtr, pp);
                        tree[pp] |= TOGGLE;
                        tree[insertPtr] &= KEY_MASK;
                        break;
                    case RIGHT_RIGHT:
                        pureRotate(false, parentPtr, pp);
                        tree[pp] |= TOGGLE;
                        tree[parentPtr] &= KEY_MASK;
                        break;
                }
                break;
            } else {
                tree[parentPtr] ^= TOGGLE; // toggle parent
                tree[sIndex] ^= TOGGLE; // toggle sibling
                if (auxVal != -1 && (auxVal &= KEY_MASK) != rootPtr) {
                    tree[auxVal] |= TOGGLE;
                    insertPtr = auxVal & KEY_MASK;
                    parentPtr = tree[auxVal + PARENT] & KEY_MASK;
                } else {
                    break;
                }
            }
        }

        ++dataPtr;
        treePtr += BLK_SIZE;
        ++size;
        return true;
    }

    /**
     * Finds the node in the tree.
     *
     * @param key the value.
     * @return <code>true</code> if found
     */
    public final boolean find(final T key) {
        final int vComp = compGenerator.applyAsInt(key);
        int currPtr = rootPtr;
        int currVal;

        while (true) {
            currVal = tree[currPtr + COMP];
            if (currPtr < 0) {
                return false;
            }
            if (vComp > currVal) {
                currPtr = tree[currPtr + RIGHT];
            } else if (vComp < currVal) {
                currPtr = tree[currPtr + LEFT];
            } else {
                return true;
            }
        }
    }

    /**
     * Removes the node from the tree.
     *
     * @param key the node
     * @return the node that was removed.
     */
    public final T erase(final T key) {
        int vComp = compGenerator.applyAsInt(key);
        int currPtr = rootPtr;
        int currVal;
        final int removePtr;

        // find the node.
        while (true) {
            currVal = tree[currPtr + COMP];
            if (currPtr < 0) {
                return null;
            }
            if (vComp > currVal) {
                currPtr = tree[currPtr + RIGHT];
            } else if (vComp < currVal) {
                currPtr = tree[currPtr + LEFT];
            } else {
                break;
            }
        }

        removePtr = currPtr;

        // keep finding inorder successors down the tree.
        int ptr;
        if((ptr = tree[currPtr + RIGHT]) != NULL) {
            int prior;
            do {
                prior = ptr;
                ptr = tree[ptr + LEFT];
            } while (ptr != NULL);
            
            tree[currPtr] &= TOGGLE;
            tree[currPtr] |= tree[prior] & KEY_MASK;
            tree[currPtr + COMP] = tree[prior + COMP];
            currPtr = prior;
        }
        
        /* OLD CURR_PTR
        while (tree[currPtr + LEFT] != NULL
                    && (ptr = tree[currPtr + RIGHT]) != NULL) {
            int prior = NULL;
            do {
                prior = ptr;
                ptr = tree[ptr + LEFT];
            } while (ptr != NULL);

            tree[currPtr] &= TOGGLE;
            tree[currPtr] |= tree[prior] & KEY_MASK;
            tree[currPtr + COMP] = tree[prior + COMP];
            currPtr = prior;
        } */

        // we have arrived at the case of leaf or right child only.
        final int nodeColor = tree[currPtr] >>> SHIFT;
        
        // getting which node is nonnull
        final int auxCP = tree[currPtr + RIGHT] > tree[currPtr + LEFT] ? tree[currPtr + RIGHT] : tree[currPtr + LEFT];
        
        // if the child is on the left/right
        final int childLR = auxCP == tree[currPtr + LEFT] ? 1 : 0;
        
        // the pointer to the child, -1 if a leaf node
        int childPtr = auxCP == NULL ? -1 : tree[auxCP] & KEY_MASK;
        
        // color of the child, accounts for null case
        final int childColor = childPtr == -1 ? 0 : tree[childPtr] >>> SHIFT;
        
        int auxPtr = -1;
        
        // pointer to the parent
        final int parentPtr = tree[currPtr + PARENT];
        
        // whether the parent is left/right on me
        final int pIndex = (parentPtr & KEY_MASK) + 2 + (parentPtr >>> SHIFT);
        
        switch ((nodeColor << 1) + childColor) {
            case BLACK_RED:
                System.out.println("BLACK RED" + data[tree[currPtr]&KEY_MASK]);
                tree[childPtr] &= KEY_MASK;
            case RED_RED:
                System.out.println("RED RED" + data[tree[currPtr]&KEY_MASK]);
            case RED_BLACK:
                System.out.println("RED BLACK" + data[tree[currPtr]&KEY_MASK]);
                tree[pIndex] = childPtr;
                if(childPtr != -1) {
                    tree[childPtr + PARENT] = childLR << SHIFT | parentPtr & KEY_MASK;
                }
                break;
            case BLACK_BLACK:
                if(childPtr != -1) {
                    tree[pIndex] = childPtr;
                    tree[childPtr + PARENT] = childLR << SHIFT | parentPtr & KEY_MASK;
                } else {
                    // modify hierarchy
                    auxPtr = childPtr;
                    childPtr = currPtr;
                }
                // considers the reverse cases too
                // remedy for a goto statement
                deleteCases:
                {
                    // case 1
                    if (childPtr == rootPtr) {
                        System.out.println("Case 1");
                        break deleteCases;
                    }

                    // pointer to the parent
                    final int parent = tree[childPtr + PARENT] & KEY_MASK;

                    // pointer to the sibling
                    final int lrPtr = BLK_SIZE - 2 - (parent >>> SHIFT);
                    final boolean lr = lrPtr == LEFT;
                    final int sIndex = tree[(parent & KEY_MASK) + lrPtr];
                    boolean pass = false;

                    // case 2
                    if (tree[parent] >>> SHIFT == BLACK
                            && sIndex != -1 && tree[sIndex] >>> SHIFT == RED) {
                        System.out.println("Case 2");
                        pureRotate(lr, sIndex, parent);
                        tree[parent] |= TOGGLE;
                        tree[sIndex] &= KEY_MASK;
                        pass = true;
                    }

                    // auxilliary
                    boolean hasLeft = false;

                    int left = tree[sIndex + LEFT];
                    if (left != -1) {
                        hasLeft = true;
                        left = tree[left];
                    }
                    boolean hasRight = false;
                    int right = tree[sIndex + RIGHT];
                    if (right != -1) {
                        hasRight = true;
                        right = tree[right];
                    }

                    // case 3
                    if (pass || tree[parent] >>> SHIFT == BLACK
                            && sIndex != -1 && tree[sIndex] >>> SHIFT == BLACK
                            && (!hasLeft || left >>> SHIFT == BLACK)
                            && (!hasRight || right >>> SHIFT == BLACK)) {
                        System.out.println("Case 3");
                        tree[sIndex] |= TOGGLE;
                        pass = true;
                    }

                    // case 4
                    if (pass || tree[parent] >>> SHIFT == RED
                            && sIndex != -1 && tree[sIndex] >>> SHIFT == BLACK
                            && (!hasLeft || left >>> SHIFT == BLACK)
                            && (!hasRight || right >>> SHIFT == BLACK)) {
                        System.out.println("Case 4");
                        tree[parent] &= KEY_MASK;
                        tree[sIndex] |= TOGGLE;
                        break deleteCases;
                    }

                    // case 5
                    if (tree[parent] >>> SHIFT == BLACK
                            && sIndex != -1 && tree[sIndex] >>> SHIFT == BLACK) {
                        if (lrPtr == RIGHT && hasLeft && left >>> SHIFT == RED
                                && (!hasRight || right >>> SHIFT == BLACK)) {
                            System.out.println("Case 5(1)");
                            reverseRotate(true, left, sIndex);
                            pass = true;
                        } else if (lrPtr == LEFT && (!hasLeft || left >>> SHIFT == BLACK)
                                && hasRight && right >>> SHIFT == RED) {
                            System.out.println("Case 5(2)");
                            reverseRotate(true, right, sIndex);
                            pass = true;
                        }
                    }

                    // case 6
                    if (pass || sIndex != -1 && tree[sIndex] >>> SHIFT == BLACK
                            && hasRight && right >>> SHIFT == RED) {
                        System.out.println("Case 6");
                        pureRotate(lr, sIndex, parent);
                        // this should be a reference to where right came from, fix this
                        tree[right] &= KEY_MASK;
                        break deleteCases;
                    }
                }
                if(childPtr == -1) {
                    tree[pIndex] = auxPtr;
                }
                break;
        }
        --size;
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < treePtr; i += BLK_SIZE) {
            String el1 = data[(tree[i + HANDLE] & KEY_MASK)].toString();
            int comp = tree[i + COMP];
            String el2 = tree[i + LEFT] == -1 ? "NONE" : data[tree[tree[i + LEFT]] & KEY_MASK].toString();
            String el3 = tree[i + RIGHT] == -1 ? "NONE" : data[tree[tree[i + RIGHT]] & KEY_MASK].toString();
            String el4 = tree[i + HANDLE] >>> SHIFT == BLACK ? "BLACK" : "RED";
            String el5 = tree[i + PARENT] == -1 ? "NONE" : data[tree[(tree[i + PARENT] & KEY_MASK)] & KEY_MASK].toString();
            String el6 = tree[i + PARENT] >>> SHIFT == FROM_LEFT ? "LEFT" : "RIGHT";
            sb.append(String.format("Data: %s, Comp: %d, Left=%s, Right=%s, Color=%s, "
                    + "Parent=%s, Side=%s%n", el1, comp, el2, el3, el4, el5, el6));
        }
        return sb.toString();
    }

    /**
     * An encapsulation used by the printTree() method.
     */
    private class QueueItem {

        private final int bt;
        private final int height;
        private final int xJust;

        private QueueItem(int bt, int height, int xJust) {
            this.bt = bt;
            this.height = height;
            this.xJust = xJust;
        }
    }

    /**
     * Generate a graphical representation of the tree given a console width.
     * For small spaces relative to the console width, incorrect in-order
     * traversal can occur.
     *
     * @param consoleWidth the width of the print console
     * @return the string to print.
     */
    public final String printTree(int consoleWidth) {
        StringBuilder sb = new StringBuilder();
        FastQueue<QueueItem> queue = new FastQueue<>(8);
        queue.push(new QueueItem(rootPtr, 0, 0));

        int currHeight = -1;
        int currJustif = 0;

        while (queue.size() > 0) {
            QueueItem obj = queue.pop();

            if (currHeight != obj.height) {
                sb.append('\n');
                currHeight = obj.height;
                currJustif = 0;
            }

            int count = data[tree[obj.bt] & KEY_MASK].toString().length() >>> 1;
            int amt = (int) (consoleWidth * ((obj.xJust << 1) + 1)
                    / (1 << (obj.height + 1)));
            for (int i = 0; i < amt - currJustif - count; ++i) {
                sb.append(' ');
            }

            if (tree[obj.bt] >>> SHIFT == RED) {
                sb.append(RED_COL);
                sb.append(data[tree[obj.bt] & KEY_MASK].toString());
                sb.append(RESET);
            } else {
                sb.append(data[tree[obj.bt] & KEY_MASK].toString());
            }

            currJustif = amt;
            if (tree[LEFT + obj.bt] != NULL) {
                queue.push(new QueueItem(tree[LEFT + obj.bt],
                        obj.height + 1, 2 * obj.xJust + 0));
            }
            if (tree[RIGHT + obj.bt] != NULL) {
                queue.push(new QueueItem(tree[RIGHT + obj.bt],
                        obj.height + 1, 2 * obj.xJust + 1));
            }
        }

        return sb.toString();
    }
    
    public final int size() {
        return size;
    }

    public static void main(String[] args) {
        FastTree<Integer> tree = new FastTree<>(Integer::intValue);
        int[] insert = {496,890,997,459,604,945,38,787,572,273};
        int[] remove = {273,38,459};
        
        for(int e: insert) {
            tree.insert(e);
        }
        System.out.println(tree.printTree(70));
        System.out.println(tree);
        for(int e: remove) {
            if(e == 459) {
                System.out.println("WE HERE!");
            }
            tree.erase(e);
            System.out.println(tree.printTree(70));
            System.out.println(tree);
        }
        
        /*
        TreeSet<Integer> comp = new TreeSet<>();

        Random rng = new Random();
        final int SIZE = 10;
        final int CONSOLE_WIDTH = 80;
        final int MAX_NUM = 1_000;
/*
        int[] insert = {28, 23, 24, 0, 16, 3, 29, 2, 8};
        for (int e : insert) {
            tree.insert(e);
            comp.add(e);
        }
        int[] remove = {28, 0, 8, 24};
        for (int e : remove) {
            tree.erase(e);
            comp.remove(e);
        }
        System.out.println(tree.printTree(CONSOLE_WIDTH));
        for (int i = 0; i < 20; ++i) {
            int el = insert[rng.nextInt(SIZE - 1)];

            if (comp.contains(el) != tree.find(el)) {
                System.out.println("ELEMENT: " + el);
                System.out.println("COMP: " + comp.contains(el));
                System.out.println("TREE: " + tree.find(el) + '\n');
                System.out.println(tree);
            }
        }
        
        
        int[] insert = IntStream.generate(()->rng.nextInt(MAX_NUM)).limit(SIZE).toArray();
        for(int e: insert) {
            tree.insert(e); comp.add(e);
        }

        System.out.println(tree.printTree(CONSOLE_WIDTH));
        System.out.println(tree);
        for(int i = 0; i<SIZE >>> 1; ++i) {
            int el = insert[rng.nextInt(SIZE)];
            tree.erase(el);
            comp.remove(el);
            System.out.println("ELEMENT: " + el);
            System.out.println(tree);
            System.out.println(tree.printTree(CONSOLE_WIDTH));
            
        }
      
        double ctr = 0;
        for(int i = 0; i<SIZE; ++i) {
             int el = insert[rng.nextInt(SIZE)];
             if(comp.contains(el) != tree.find(el)) {
                 ++ctr;
             }
        }
        
        System.out.println("Error rate: " + ctr/SIZE);
         
        System.out.println(tree.printTree(80));
        System.out.println(tree);
        System.out.println(tree.erase(70));
        System.out.println(tree.printTree(80));
        System.out.println(tree); */
    }
}
