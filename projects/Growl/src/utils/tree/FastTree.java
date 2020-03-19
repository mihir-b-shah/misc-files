package utils.tree;

import utils.queue.FastQueue;

/**
 * Implements an optimized red-black binary search tree.
 *
 * Current bug report
 * 1. Handling black sibling node (currently it only handles null nodes)
 * 
 * @author mihir
 * @param <T> the type.
 */
public class FastTree<T extends IntValue<T>> {

    private int[] tree; // a vector
    private int treePtr;

    private T[] data; // a vector
    private int dataPtr;

    private int rootPtr;

    private static final int ROOT_PTR = 0;
    private static final int ROOT_VAL = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;
    private static final int PARENT = 4;

    private static final int NULL = -1;
    private static final int BLK_SIZE = 5;

    private static final int RED = 1;
    private static final int BLACK = 0;
    private static final int TOGGLE = Integer.MIN_VALUE;
    private static final int BSHIFT = 0;
    private static final int SHIFT = 31;
    private static final int KEY_MASK = Integer.MAX_VALUE;

    private static final int FROM_LEFT = 0;
    private static final int FROM_RIGHT = 1;

    private static final String RED_COL = "\033[0;31m";
    private static final String RESET = "\033[0m";

    private static final int LEFT_LEFT = 0;
    private static final int LEFT_RIGHT = 1;
    private static final int RIGHT_LEFT = 2;
    private static final int RIGHT_RIGHT = 3;

    /**
     * The tree is stored as follows. Each node contains four fields:
     *
     * a KEY field that it a 32-bit integer The sign bit is whether RED OR BLACK
     * The rest is a reference to the data array.
     *
     * a VALUE field that is the absolute comparator value we need all the
     * precision we can get, so it is all 32 bits.
     *
     * a LEFT field that points to the left node.
     *
     * a RIGHT field that points to the right node.
     *
     * a PARENT field that points to the parent node.
     *
     * @param root the root node.
     */
    public FastTree(T root) {
        data = (T[]) new IntValue[4];
        tree = new int[20];

        data[ROOT_PTR] = root;
        dataPtr = 1;
        treePtr = 1;
        tree[ROOT_VAL] = root.value();
        tree[LEFT] = NULL;
        tree[RIGHT] = NULL;
        tree[ROOT_PTR] |= BSHIFT;
        tree[PARENT] = NULL;
        rootPtr = 0;
    }

    /**
     * Inserts an item into the BST.
     *
     * 1. Checks whether a position within the array is available (on the stack)
     * 2. Adjusts the stack accordingly 3. Inserts the value into that position
     * in the 'data' array. 4. Determines the pointer (int) value to use in
     * tree. 5. Insert the item. 6. Rebalance the tree.
     *
     * @param v the item to insert.
     */
    public final void insert(T v) {
        // grow the array if needed
        if (dataPtr == data.length) {
            T[] aux = (T[]) new IntValue[dataPtr << 1];
            System.arraycopy(data, 0, aux, 0, dataPtr);
            data = aux;

            int[] aux2 = new int[tree.length << 1];
            System.arraycopy(tree, 0, aux2, 0, tree.length);
            tree = aux2;
        }

        // insert item into data table
        data[dataPtr] = v;

        int ptrShift = rootPtr * BLK_SIZE;
        int parentPtr = rootPtr * BLK_SIZE;
        int lrPtr;
        final int vComp = v.value();

        int choosePtr;
        while (true) {
            choosePtr = vComp > tree[ROOT_VAL + ptrShift] ? RIGHT : LEFT;
            if ((lrPtr = tree[ptrShift + choosePtr]) == NULL) {
                tree[ptrShift + choosePtr] = treePtr;
                ptrShift = treePtr * BLK_SIZE;
                tree[ptrShift + ROOT_PTR] = dataPtr ^ TOGGLE;

                tree[ptrShift + ROOT_VAL] = vComp;
                tree[ptrShift + LEFT] = NULL;
                tree[ptrShift + RIGHT] = NULL;
                tree[ptrShift + PARENT] = parentPtr | (choosePtr - 2) << SHIFT;
                break;
            } else {
                ptrShift = BLK_SIZE * (parentPtr = tree[lrPtr * BLK_SIZE] & KEY_MASK);
            }
        }

        int sIndex;
        int auxVal;
        int parShift = parentPtr * BLK_SIZE;
        while (parShift > -1 && tree[parShift] >>> SHIFT == RED) {
            auxVal = tree[parShift + PARENT];
            sIndex = BLK_SIZE * (auxVal & KEY_MASK) + BLK_SIZE - 2 - (auxVal >>> SHIFT);

            if (tree[sIndex] == NULL || tree[BLK_SIZE * tree[sIndex]] >>> SHIFT == BLACK) {
                /*
                1. LL rotation
                2. LR rotation
                3. RL rotation
                4. RR rotation
                
                Need to manage case, what if at root node?
                 */
                int pp = BLK_SIZE * (tree[parShift + PARENT] & KEY_MASK);
                int ppp;
                final boolean isRoot = pp == rootPtr;
                boolean prev;
                int top = tree[pp + PARENT] & KEY_MASK;
                // switch between the different rotation cases
                switch (((tree[parShift + PARENT] >>> SHIFT) << 1)
                        + (tree[ptrShift + PARENT] >>> SHIFT)) {
                    case LEFT_LEFT:
                        tree[pp + LEFT] = NULL;
                        ppp = tree[pp + PARENT];
                        tree[pp + PARENT] = tree[ptrShift + PARENT];
                        tree[parShift + RIGHT] = tree[parShift + PARENT]&KEY_MASK;
                        if (isRoot) {
                            tree[parShift + PARENT] = NULL;
                            rootPtr = tree[ptrShift + PARENT] & KEY_MASK;
                        } else {
                            tree[parShift + PARENT] = top;
                            if (ppp >>> SHIFT == FROM_LEFT) {
                                tree[top * BLK_SIZE + LEFT]
                                        = tree[ptrShift + PARENT] & KEY_MASK;
                                tree[parShift + PARENT] = top;
                            } else {
                                tree[top * BLK_SIZE + RIGHT]
                                        = tree[ptrShift + PARENT] & KEY_MASK;
                                tree[parShift + PARENT] = top | TOGGLE;
                            }
                        }
                        tree[parShift] ^= TOGGLE;
                        tree[pp] ^= TOGGLE;
                        tree[pp + PARENT] ^= TOGGLE;
                        break;
                    case LEFT_RIGHT:
                        prev = false;
                        if (isRoot) {
                            rootPtr = tree[parShift + RIGHT];
                        } else {
                            if (tree[pp + PARENT] >>> SHIFT == FROM_LEFT) {
                                prev = false;
                                tree[top * BLK_SIZE + LEFT]
                                        = tree[parShift + RIGHT] & KEY_MASK;
                            } else {
                                prev = true;
                                tree[top * BLK_SIZE + RIGHT]
                                        = tree[parShift + RIGHT] & KEY_MASK;
                            }
                        }
                        tree[pp + LEFT] = NULL;
                        tree[pp + PARENT] = tree[parShift + RIGHT] | TOGGLE;
                        tree[pp] |= TOGGLE;
                        tree[ptrShift + LEFT] = tree[ptrShift + PARENT] & KEY_MASK;
                        tree[ptrShift + RIGHT] = tree[parShift + PARENT] & KEY_MASK;
                        if (isRoot) {
                            tree[ptrShift + PARENT] = NULL;
                        } else {
                            tree[ptrShift + PARENT] = prev ? top | TOGGLE : top;
                        }
                        tree[ptrShift] ^= TOGGLE;
                        tree[parShift + PARENT] = tree[pp + PARENT] ^ TOGGLE;
                        tree[parShift + RIGHT] = NULL;
                        break;
                    case RIGHT_LEFT:
                        prev = false;
                        if (isRoot) {
                            rootPtr = tree[parShift + LEFT];
                        } else {
                            if (tree[pp + PARENT] >>> SHIFT == FROM_LEFT) {
                                tree[top * BLK_SIZE + LEFT]
                                        = tree[parShift + LEFT] & KEY_MASK;
                                prev = false;
                            } else {
                                tree[top * BLK_SIZE + RIGHT]
                                        = tree[parShift + LEFT] & KEY_MASK;
                                prev = true;
                            }
                        }
                        tree[pp + PARENT] = tree[parShift + LEFT];
                        tree[pp + RIGHT] = NULL;
                        tree[pp] |= TOGGLE;
                        tree[ptrShift + RIGHT] = tree[ptrShift + PARENT] & KEY_MASK;
                        tree[ptrShift + LEFT] = tree[parShift + PARENT] & KEY_MASK;
                        if (isRoot) {
                            tree[ptrShift + PARENT] = NULL;
                        } else {
                            tree[ptrShift + PARENT] = prev ? top | TOGGLE : top;
                        }
                        tree[ptrShift] ^= TOGGLE;
                        tree[parShift + PARENT] = tree[pp + PARENT] ^ TOGGLE;
                        tree[parShift + LEFT] = NULL;
                        break;
                    case RIGHT_RIGHT:
                        tree[pp + RIGHT] = NULL;
                        ppp = tree[pp + PARENT];
                        tree[pp + PARENT] = tree[ptrShift + PARENT];
                        tree[parShift + LEFT] = tree[parShift + PARENT]&KEY_MASK;
                        
                        if (isRoot) {
                            rootPtr = tree[ptrShift + PARENT] & KEY_MASK;
                            tree[parShift + PARENT] = NULL;
                        } else {
                            tree[parShift + PARENT] = top;
                            if (ppp >>> SHIFT == FROM_LEFT) {
                                tree[top * BLK_SIZE + LEFT]
                                        = tree[ptrShift + PARENT] & KEY_MASK;
                                tree[parShift + PARENT] = top;
                            } else {
                                tree[top * BLK_SIZE + RIGHT]
                                        = tree[ptrShift + PARENT] & KEY_MASK;
                                tree[parShift + PARENT] = top | TOGGLE;
                            }
                        }
                        tree[parShift] ^= TOGGLE;
                        tree[pp] ^= TOGGLE;
                        tree[pp + PARENT] ^= TOGGLE;
                        break;
                }
                break; // no while 
            } else {
                tree[parShift] ^= TOGGLE; // toggle parent
                tree[BLK_SIZE * tree[sIndex]] ^= TOGGLE; // toggle sibling
                if (tree[parShift + PARENT] != -1 && (tree[parShift + PARENT] & KEY_MASK) != rootPtr) {
                    tree[BLK_SIZE * (tree[parShift + PARENT] & KEY_MASK)] |= TOGGLE;
                    parShift = BLK_SIZE * (tree[BLK_SIZE * (tree[parShift + PARENT]
                            & KEY_MASK) + PARENT]) & KEY_MASK;
                }
            }
        }

        ++dataPtr;
        ++treePtr;
    }

    public final boolean contains(T val) {
        return false;
    }

    public final T higher(T thr) {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < treePtr * BLK_SIZE; i += BLK_SIZE) {
            sb.append(String.format("Data: %s, Left=%s, Right=%s, Color=%s, "
                    + "Parent=%s, Side=%s%n", data[tree[i + ROOT_PTR] & KEY_MASK].toString(),
                    tree[i + LEFT] == -1 ? "NONE" : data[tree[i + LEFT]], tree[i + RIGHT] == -1
                            ? "NONE" : data[tree[i + RIGHT]], tree[i + ROOT_PTR] >>> SHIFT
                    == BLACK ? "BLACK" : "RED", tree[i + PARENT] == -1 ? "NONE" : data[tree[i + PARENT] & KEY_MASK].toString(),
                    tree[i + PARENT] >>> SHIFT == FROM_LEFT ? "LEFT" : "RIGHT"));
        }
        return sb.toString();
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

            int count = data[obj.bt].toString().length() >>> 1;
            int amt = convert(consoleWidth, obj.height, obj.xJust);
            for (int i = 0; i < amt - currJustif - count; ++i) {
                sb.append(' ');
            }

            if (tree[BLK_SIZE * obj.bt] >>> 31 == 1) {
                sb.append(RED_COL + data[obj.bt].toString()+obj.bt+ RESET);
            } else {
                sb.append(data[obj.bt].toString()+obj.bt);
            }

            currJustif = amt;
            if (tree[LEFT + (obj.bt * BLK_SIZE)] != NULL) {
                queue.push(new QueueItem(tree[LEFT + obj.bt * BLK_SIZE],
                        obj.height + 1, 2 * obj.xJust + 0));
            }
            if (tree[RIGHT + (obj.bt * BLK_SIZE)] != NULL) {
                queue.push(new QueueItem(tree[RIGHT + obj.bt * BLK_SIZE],
                        obj.height + 1, 2 * obj.xJust + 1));
            }
        }

        return sb.toString();
    }

    private final int convert(double w, int h, int j) {
        return (int) (w * ((j << 1) + 1) / (1 << (h + 1)));
    }

    static class CharWrapper implements IntValue<CharWrapper> {

        private final char c;

        public CharWrapper(char c) {
            this.c = c;
        }

        @Override
        public int value() {
            return c - '0';
        }

        @Override
        public String toString() {
            return "" + c;
        }
    }

    public static void main(String[] args) {
        FastTree<CharWrapper> bst = new FastTree<>(new CharWrapper('a'));
        System.out.println(bst.printTree(80));
        System.out.println(bst);
        bst.insert(new CharWrapper('b'));
        System.out.println(bst.printTree(80));
        System.out.println(bst);
        bst.insert(new CharWrapper('c'));
        System.out.println(bst.printTree(80));
        System.out.println(bst);
        bst.insert(new CharWrapper('d'));
        System.out.println(bst.printTree(80));
        System.out.println(bst);
        bst.insert(new CharWrapper('e'));
        System.out.println(bst.printTree(80));
        System.out.println(bst);
        bst.insert(new CharWrapper('f'));
        System.out.println(bst.printTree(80));
        System.out.println(bst);
        bst.insert(new CharWrapper('g'));
        System.out.println(bst.printTree(80));
        System.out.println(bst);
        bst.insert(new CharWrapper('h'));
        System.out.println(bst.printTree(80));
        System.out.println(bst.toString());
    }
}
