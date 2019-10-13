
/**
 * This is a generic, N-ary tree. DONE
 *
 * @author Mihir
 */
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Tree {

    private Object myRootValue;
    private Tree[] myChildren;

    /**
     * Constructs a tree with a root node with branching factor N.
     *
     * @param rootValue	The root node.
     * @param branchingFactor The branching factor for the node.
     *
     */
    public Tree(Object rootValue, int branchingFactor) {
        myChildren = new Tree[branchingFactor];
        myRootValue = rootValue;
    }

    /**
     * Finds the value of the tree.
     *
     * @returns the root node
     */
    public Object getValue() {
        return myRootValue;
    }

    public Object getRoot() {
        return myRootValue;
    }

    /**
     * Returns the children of the tree.
     *
     * @returns the array containing all trees rooted at the root node.
     */
    public Tree[] getChildren() {
        return myChildren;
    }

    /**
     * Returns whether the node is a leaf of the tree rooted at the class root
     * node.
     *
     * @returns <code>true</code> if the node is a leaf, <code>false</code>
     * otherwise.
     */
    public boolean isLeaf() {
        for (Tree c : myChildren) {
            if (c != null) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the height of the tree.
     *
     * @returns <code>0</code> if the tree is just a root node, else the max
     * height.
     */
    public int maxHeight() {
        int max = 0;

        for (Tree c : myChildren) {
            if (c != null) {
                max = Math.max(max, c.maxHeight());
            }
        }

        return isLeaf() ? 0 : max + 1;
    }

    
    protected void setRoot(Object e) {
        myRootValue = e;
    }
    
    /**
     * Finds the number of nodes in the tree.
     *
     * @returns the number of nodes.
     */
    public int size() {
        return 1
                + (getChildren()[0] != null ? (getChildren()[0]).size() : 0)
                + (getChildren()[1] != null ? (getChildren()[1]).size() : 0);
    }

    /**
     * Runs a breadth first search on the tree to find a given object.
     *
     * @param x The object to be found.
     * @returns <code>true</code> if the object is found, <code>false</code>
     * otherwise.
     */
    public boolean breadthFirstSearch(Object x) {
        ArrayDeque<Tree> queue = new ArrayDeque<>();

        if (getRoot() != null) {
            queue.offer(this);
            if (x.equals(getRoot())) {
                return true;
            }
        }

        while (!queue.isEmpty()) {
            Tree obj = queue.poll();
            if (obj.getRoot().equals(x)) {
                return true;
            }
			// Enqueue children

            Tree[] children = obj.getChildren();

            for (Tree c : children) {
                if (c != null) {
                    queue.offer(c);
                }
            }
        }

        return false;
    }

    /**
     * Runs a depth first search on the tree to find a given object.
     *
     * @param x The object to be found.
     * @returns <code>true</code> if the object is found, <code>false</code>
     * otherwise.
     */
    public boolean depthFirstSearch(Object x) {
        ArrayDeque<Tree> stack = new ArrayDeque<>();

        if (getRoot() != null) {
            stack.push(this);
            if (x.equals(getRoot())) {
                return true;
            }
        }

        while (!stack.isEmpty()) {
            Tree obj = stack.pop();
            if (obj.getRoot().equals(x)) {
                return true;
            }
			// Enqueue children

            Tree[] children = obj.getChildren();

            for (Tree c : children) {
                if (c != null) {
                    stack.push(c);
                }
            }
        }

        return false;
    }

    /**
     * Returns a string showing a level-by-level search of the tree.
     *
     * @returns the tree, N lines of the tree, equally spaced.
     *
     */
    @Override
    public String toString() {
        ArrayDeque<Tree> queue = new ArrayDeque<>();
        List<Object> list = new ArrayList<>();

        if (getRoot() != null) {
            queue.offer(this);
        }

        while (!queue.isEmpty()) {
            Tree obj = queue.poll();
            list.add(obj.getRoot());

			// Enqueue children
            Tree[] children = obj.getChildren();

            for (Tree c : children) {
                if (c != null) {
                    queue.offer(c);
                }
            }
        }

        return list.toString();
    }

    /**
     * Returns whether the two trees are equal, aka if their hashcodes are
     * equal.
     *
     * @param x The other tree
     * @returns <code>true</code> if the two objects' hashcodes are equal,
     * <code>false</code> if not.
     */
    @Override
    public boolean equals(Object x) {
        try {
            Tree t = (Tree) x;
            return t.hashCode() == hashCode();
        } catch (ClassCastException e1) {
            System.err.println("Compare trees with other trees only.");
        } catch (NullPointerException e2) {
            System.err.println("Null tree encountered");
        }

        return false;
    }

    /**
     * Returns a number representing the tree. Two same trees are guaranteed to
     * hash to the same number, but two different trees are not guaranteed to
     * hash to different values.
     *
     * @returns a number, unique to the extent possible.
     */
    @Override
    public int hashCode() {
        ArrayDeque<Tree> queue = new ArrayDeque<>();
        int num = 0;
        int counter = 0;

        if (getRoot() != null) {
            queue.offer(this);
        }

        while (!queue.isEmpty()) {
            Tree obj = queue.poll();
            //System.out.println(obj.getRoot().hashCode());
            num += obj.getRoot().hashCode() * (1 << counter++);

			// Enqueue children
            Tree[] children = obj.getChildren();

            for (Tree c : children) {
                if (c != null) {
                    queue.offer(c);
                }
            }
        }

        return num;
    }
}
