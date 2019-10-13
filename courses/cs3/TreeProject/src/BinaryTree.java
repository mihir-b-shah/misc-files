
/**
 * This is a generic binary tree with no inherent sorting property. FINISHED
 *
 * @author Mihir
 */
import java.util.List;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree extends Tree {

    /**
     * Constructs a binary tree with root value, a tree with a maximum of two
     * children per parent node.
     *
     * @param rootvalue The root value of the tree
     */
    public BinaryTree(Object rootvalue) {
        super(rootvalue, 2);
    }

    /**
     * Recursively constructs a binary tree using a root node, and the left and
     * right subtrees.
     *
     * @param rootValue The root value of the tree.
     * @param left The left subtree of the tree being constructed.
     * @param right The right subtree of the tree being constructed.
     */
    public BinaryTree(Object rootValue, BinaryTree left, BinaryTree right) {
        this(rootValue);
        Tree[] children = getChildren();
        children[0] = left;
        children[1] = right;
    }

    /**
     * Sets the left subtree of the binary tree to the given tree.
     *
     * @param left The left subtree of the binary tree to another binary tree
     */
    public void setLeft(BinaryTree left) {
        getChildren()[0] = left;
    }
    
    /**
     * Sets the right subtree of the binary tree to the given tree.
     *
     * @param right The right subtree of the binary tree to another binary tree
     */
    public void setRight(BinaryTree right) {
        getChildren()[1] = right;
    }

    /**
     * Returns the left subtree of the tree.
     *
     * @returns the left subtree of the tree.
     */
    public BinaryTree getLeft() {
        return (BinaryTree) getChildren()[0];
    }

    /**
     * Returns the right subtree of the tree.
     *
     * @returns the right subtree of the tree.
     */
    public BinaryTree getRight() {
        return (BinaryTree) getChildren()[1];
    }
   
    /**
     * Returns an inorder traversal of tree, returning an inorder traversal of
     * the left tree, then visits the root node, and then does an inorder
     * traversal of the right tree.
     *
     * @returns a list comprising of the elements of the tree, in an inorder
     * traversal.
     */
    public List<Object> inOrder() {
        List<Object> list = new ArrayList<>();

        if (getChildren()[0] != null) {
            list.addAll(((BinaryTree) getChildren()[0]).inOrder());
        }

        list.add(getRoot());

        if (getChildren()[1] != null) {
            list.addAll(((BinaryTree) getChildren()[1]).inOrder());
        }

        return list;
    }

    /**
     * Returns an preorder traversal of tree, visits the root node, returning an
     * preorder traversal of the left tree, then does an preorder traversal of
     * the right tree.
     *
     * @returns a list comprising of the elements of the tree, in a preorder
     * traversal.
     */
    public List<Object> preOrder() {
        List<Object> list = new ArrayList<>();

        list.add(getRoot());

        if (getChildren()[0] != null) {
            list.addAll(((BinaryTree) getChildren()[0]).preOrder());
        }

        if (getChildren()[1] != null) {
            list.addAll(((BinaryTree) getChildren()[1]).preOrder());
        }

        return list;
    }

    /**
     * Returns an postorder traversal of tree, returning an postorder traversal
     * of the left tree, then does an post-order traversal of the right tree,
     * visits the root node.
     *
     * @returns a list comprising of the elements of the tree, in a post-order
     * traversal.
     */
    public List<Object> postOrder() {
        List<Object> list = new ArrayList<>();
        if (getChildren()[0] != null) {
            list.addAll(((BinaryTree) getChildren()[0]).postOrder());
        }

        if (getChildren()[1] != null) {
            list.addAll(((BinaryTree) getChildren()[1]).postOrder());
        }

        list.add(getRoot());

        return list;
    }
    
    private class QueueItem {
        public BinaryTree bt;
        public int height;
        public int xJust;
        
        public QueueItem(BinaryTree bt, int height, int xJust) {
            this.bt = bt;
            this.height = height;
            this.xJust = xJust;
        }
        
        @Override
        public String toString() {
            return String.format("%1$s %2$d %3$d", bt.getRoot().toString(), height, xJust);
        }
    }
    
    protected String printTree(int consoleWidth) {
        StringBuilder sb = new StringBuilder();
        Queue<QueueItem> queue = new LinkedList<>();
        
        if (getRoot() != null) {
            queue.offer(new QueueItem(this,0,0));
        }
        
        int curr_height = -1;
        int curr_justif = 0;
        
        while (!queue.isEmpty()) {
            QueueItem obj = queue.poll(); 
            
            if(curr_height != obj.height) {
                sb.append('\n');
                curr_height = obj.height;
                curr_justif = 0;
            }
            
            int count = obj.bt.getRoot().toString().length()/2;
            int amt = convert(consoleWidth, obj.height, obj.xJust);
            for(int i = 0; i<amt-curr_justif-count; i++) {
                sb.append(' ');
            }
            
            sb.append(obj.bt.getRoot().toString());
            
            curr_justif = amt;

            Tree[] children = obj.bt.getChildren();
            
            for(int i = 0; i<2; i++) {
                if (children[i] != null) {
                    queue.offer(new QueueItem((BinaryTree) children[i],obj.height+1,2*obj.xJust+i));
                }
            }
        }
        
        return sb.toString();
    }
    
    private int convert(int w, int h, int j) {
        double x = (double) w;
        return (int) (x*(2*j+1)/(1 << (h+1)));
    }
    
    /**
     * Goes through the nodes, by each level of the node, using an iterative
     * deepening approach.
     *
     * @returns a list comprising of the elements of tree, in a level-by-level
     * traversal.
     */
    public List<Object> levelByLevel() {
        ArrayDeque<Tree> queue = new ArrayDeque<>();
        List<Object> ret = new ArrayList<>();

        if (getRoot() != null) {
            queue.offer(this);
            ret.add(getValue());
        }

        while (!queue.isEmpty()) {
            Tree obj = queue.poll();
			// Enqueue children

            Tree[] children = obj.getChildren();

            for (Tree c : children) {
                if (c != null) {
                    queue.offer(c);
                    ret.add(c.getRoot());
                }
            }
        }

        return ret;
    }
}
