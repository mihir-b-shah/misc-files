package utils.tree;

import utils.queue.FastQueue;

/**
 * Implements an optimized red-black binary search tree.
 *
 * Current bug report 1. Handling black sibling node (currently it only handles
 * null nodes)
 *
 * MAX CAPACITY OF 400 million nodes.
 *
 * @author mihir
 * @param <T> the type.
 */
public class FastTree2<T extends IntValue<T>> {

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

    private static final int MAX_CAPACITY = 1 << 30;

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
    public FastTree2(T root) {
        data = (T[]) new IntValue[4];
        tree = new int[20];

        data[ROOT_PTR] = root;
        dataPtr = 1;
        treePtr = BLK_SIZE;
        tree[ROOT_VAL] = root.value();
        tree[LEFT] = NULL;
        tree[RIGHT] = NULL;
        tree[ROOT_PTR] |= BSHIFT;
        tree[PARENT] = NULL;
        rootPtr = 0;
    }

    /**
     * Does a pure left or right rotation.
     *
     * @param LR whether it is a left/right rotation.
     * @param insertPtr the original insertion point.
     * @param parentPtr the parent of <param>insertPtr</param>
     * @param pp the parent of <param>parentPtr</param>
     * @param top the parent of <param>pp</param>, else -1.
     */
    private void pureRotate(boolean LR, int insertPtr, int parentPtr, int pp, int top) {
        final int FORWARD = LR ? LEFT : RIGHT;
        final int BACKWARD = LR ? RIGHT : LEFT;

        tree[pp + FORWARD] = NULL;
        int ppp = tree[pp + PARENT];
        tree[pp + PARENT] = tree[insertPtr + PARENT];
        tree[parentPtr + BACKWARD] = tree[parentPtr + PARENT] & KEY_MASK;
        if (pp == rootPtr) {
            tree[parentPtr + PARENT] = NULL;
            rootPtr = tree[insertPtr + PARENT] & KEY_MASK;
        } else {
            tree[parentPtr + PARENT] = top;
            if (ppp >>> SHIFT == FROM_LEFT) {
                tree[top + LEFT] = tree[insertPtr + PARENT] & KEY_MASK;
                tree[parentPtr + PARENT] = top;
            } else {
                tree[top + RIGHT] = tree[insertPtr + PARENT] & KEY_MASK;
                tree[parentPtr + PARENT] = top | TOGGLE;
            }
        }
        tree[parentPtr] ^= TOGGLE;
        tree[pp] ^= TOGGLE;
        tree[pp + PARENT] ^= TOGGLE;
    }

    /**
     * Does a mixed left or right rotation.
     *
     * @param LR whether it is a left/right rotation.
     * @param insertPtr the original insertion point.
     * @param parentPtr the parent of <param>insertPtr</param>
     * @param pp the parent of <param>parentPtr</param>
     * @param top the parent of <param>pp</param>, else -1.
     */
    private void mixedRotate(boolean LR, int insertPtr, int parentPtr, int pp, int top) {
        boolean prev = false;
        final int FORWARD = LR ? RIGHT : LEFT;
        final int BACKWARD = LR ? LEFT : RIGHT;

        if (pp != rootPtr) {
            if (tree[pp + PARENT] >>> SHIFT == FROM_LEFT) {
                prev = false;
                tree[top + LEFT] = tree[parentPtr + FORWARD] & KEY_MASK;
            } else {
                prev = true;
                tree[top + RIGHT] = tree[parentPtr + FORWARD] & KEY_MASK;
            }
        }
        tree[pp + PARENT] = tree[parentPtr + FORWARD] | TOGGLE;
        tree[pp + BACKWARD] = NULL;
        tree[pp] |= TOGGLE;
        tree[insertPtr + BACKWARD] = tree[insertPtr + PARENT] & KEY_MASK;
        tree[insertPtr + FORWARD] = tree[parentPtr + PARENT] & KEY_MASK;
        if (pp == rootPtr) {
            tree[insertPtr + PARENT] = NULL;
            rootPtr = tree[parentPtr + FORWARD];
        } else {
            tree[insertPtr + PARENT] = prev ? top | TOGGLE : top;
        }
        tree[insertPtr] ^= TOGGLE;
        tree[parentPtr + PARENT] = tree[pp + PARENT] ^ TOGGLE;
        tree[parentPtr + FORWARD] = NULL;
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
     * @return whether the item was inserted correctly. Does NOT check whether
     * the element already exists in the tree.
     */
    public final boolean insert(T v) {
        if (treePtr > MAX_CAPACITY - 1) {
            return false;
        }
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

        int insertPtr = rootPtr;
        int parentPtr;
        int lrPtr;
        final int vComp = v.value();

        int choosePtr;
        while (true) {
            choosePtr = vComp > tree[ROOT_VAL + insertPtr] ? RIGHT : LEFT;
            if ((lrPtr = tree[insertPtr + choosePtr]) == NULL) {
                tree[insertPtr + choosePtr] = treePtr;
                tree[treePtr + ROOT_PTR] = dataPtr ^ TOGGLE;
                tree[treePtr + ROOT_VAL] = vComp;
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
            sIndex = (auxVal & KEY_MASK) + BLK_SIZE - 2 - (auxVal >>> SHIFT);

            if (tree[sIndex] == NULL) {
                int pp = tree[parentPtr + PARENT] & KEY_MASK;
                int top = tree[pp + PARENT] & KEY_MASK;
                // switch between the different rotation cases
                switch (((tree[parentPtr + PARENT] >>> SHIFT) << 1)
                        + (tree[insertPtr + PARENT] >>> SHIFT)) {
                    case LEFT_LEFT:
                        pureRotate(true, insertPtr, parentPtr, pp, top);
                        break;
                    case LEFT_RIGHT:
                        mixedRotate(true, insertPtr, parentPtr, pp, top);
                        break;
                    case RIGHT_LEFT:
                        mixedRotate(false, insertPtr, parentPtr, pp, top);
                        break;
                    case RIGHT_RIGHT:
                        pureRotate(false, insertPtr, parentPtr, pp, top);
                        break;
                }
                break;
            } else if (tree[tree[sIndex]] >>> SHIFT == BLACK) {
                break;
            } else {
                tree[parentPtr] ^= TOGGLE; // toggle parent
                tree[tree[sIndex]] ^= TOGGLE; // toggle sibling
                if (tree[parentPtr + PARENT] != -1 && (tree[parentPtr + PARENT] & KEY_MASK) != rootPtr) {
                    tree[tree[parentPtr + PARENT] & KEY_MASK] |= TOGGLE;
                    parentPtr = (tree[(tree[parentPtr + PARENT] & KEY_MASK)
                            + PARENT]) & KEY_MASK;
                } else break;
            }
        }

        ++dataPtr;
        treePtr += BLK_SIZE;
        return true;
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
        for (int i = 0; i < treePtr; i += BLK_SIZE) {
            String el1 = data[(tree[i + ROOT_PTR] & KEY_MASK)].toString();
            String el2 = tree[i + LEFT] == -1 ? "NONE" : data[tree[i + LEFT] / BLK_SIZE].toString();
            String el3 = tree[i + RIGHT] == -1 ? "NONE" : data[tree[i + RIGHT] / BLK_SIZE].toString();
            String el4 = tree[i + ROOT_PTR] >>> SHIFT == BLACK ? "BLACK" : "RED";
            String el5 = tree[i + PARENT] == -1 ? "NONE" : data[(tree[i + PARENT] & KEY_MASK) / BLK_SIZE].toString();
            String el6 = tree[i + PARENT] >>> SHIFT == FROM_LEFT ? "LEFT" : "RIGHT";
            sb.append(String.format("Data: %s, Left=%s, Right=%s, Color=%s, "
                    + "Parent=%s, Side=%s%n", el1, el2, el3, el4, el5, el6));
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

            int count = data[tree[obj.bt] & KEY_MASK].toString().length() >>> 1;
            int amt = convert(consoleWidth, obj.height, obj.xJust);
            for (int i = 0; i < amt - currJustif - count; ++i) {
                sb.append(' ');
            }

            if (tree[obj.bt] >>> 31 == 1) {
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
        boolean[] already = new boolean[26];
        already[0] = true;
        FastTree2<CharWrapper> bst = new FastTree2<>(new CharWrapper('a'));
        for (int i = 1; i < 10; ++i) {
            int val;
            do {
                val = (int) (1 + Math.random() * 25);
            } while (already[val]);
            already[val] = true;
            bst.insert(new CharWrapper((char) ('a' + val)));
            System.out.println(bst.printTree(120));
            System.out.println(bst.toString());
        }

    }
}
