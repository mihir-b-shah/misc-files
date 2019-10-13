
/**
 * A binary tree that implements left-to-right ordering.
 *
 * @author Mihir
 *
 * @param <E> The type of the tree, defined to be a comparable value
 */
public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree {

    /**
     * Constructs a binary search tree with left-to-right ordering
     *
     * @param rootValue The root value of the tree
     */
    public BinarySearchTree(Comparable<E> rootValue) {
        super(rootValue);
    }

    /**
     * Returns whether the item is contained within the tree.
     *
     * @param item The item to be searched for.s
     * @returns <code>true</code> if the item is found, <code>false</code> if
     * not found.
     */
    public boolean contains(Comparable<E> item) {
        if (item.compareTo((E) getValue()) == 0) {
            return true;
        }
        
        if (isLeaf()) {
            return false;
        }

        if (item.compareTo((E) getValue()) > 0) {
            return getChildren()[1] == null ? false : ((BinarySearchTree) getChildren()[1]).contains(item);
        } else {
            return getChildren()[0] == null ? false : ((BinarySearchTree) getChildren()[0]).contains(item);
        }

    }

    /**
     * Returns whether an item is successfully added to the tree.
     *
     * @param item The item to be added.
     * @returns <code>true</code> if the item is successfully added,
     * <code>false</code> otherwise.
     */
    public boolean add(Comparable<E> item) {
        if (item.compareTo((E) getValue()) > 0) {
            if (getChildren()[1] == null) {
                getChildren()[1] = new BinarySearchTree<E>(item);
                return true;
            } else {
                ((BinarySearchTree<E>) getChildren()[1]).add(item);
            }
        } else {
            if (getChildren()[0] == null) {
                getChildren()[0] = new BinarySearchTree<E>(item);
                return true;
            } else {
                ((BinarySearchTree<E>) getChildren()[0]).add(item);
            }
        }

        return false;
    }

    /**
     * Returns whether an item is successfully removed from the tree.
     *
     * @param item The item to be removed.
     * @returns <code>true</code> if the item is successfully removed,
     * <code>false</code> otherwise.
     */
    public boolean remove(Comparable<E> item) {
        /* Algorithm:
         * 1] Find node
         * 2] Remove root of that subtree (recursive)
            A] Find min element in right subtree
        `   B] Swap
            C] keep doing until hit leaf
         */
        return this.removeRoot(item, this, null);
    }
    
    private boolean removeRoot(Comparable<E> item, BinarySearchTree<E> tree, BinarySearchTree<E> prev_tree) {
        if(item.equals(tree.getRoot())) {
            return removeTree(prev_tree, tree);
        } else if(item.compareTo((E) getRoot()) > 0) {
            return getChildren()[1] == null ? false : ((BinarySearchTree<E>) getChildren()[1]).removeRoot(item, (BinarySearchTree<E>) getChildren()[1], this);
        } else {
        	return getChildren()[0] == null ? false : ((BinarySearchTree<E>) getChildren()[0]).removeRoot(item, (BinarySearchTree<E>) getChildren()[0], this);
        }
    }
    
    private boolean removeTree(BinarySearchTree<E> prev, BinarySearchTree<E> tree) {
        if(tree.isLeaf()) {
            if(prev.getChildren()[1] != null && prev.getChildren()[1].getRoot().equals(tree.getRoot())) {
                prev.getChildren()[1] = null;
            } else {
                prev.getChildren()[0] = null;
            }
            return true;
        } else if(tree.getChildren()[0] != null && tree.getChildren()[1] == null) {
            if(prev == null) {
            	
            	Tree replace = tree.getChildren()[0];
            	BinaryTree left = (BinaryTree) replace.getChildren()[0];
            	BinaryTree right = (BinaryTree) replace.getChildren()[1];
            	
                try {
                    tree.setRoot(replace.getRoot());
                } catch (NullPointerException e) {tree.setRoot(null);}
                try {
                    tree.setLeft(left);
                } catch (NullPointerException e) {tree.setLeft(null);}
                
                try {
                    tree.setRight(right);
                } catch (NullPointerException e) {tree.setRight(null);}
                
                return true;
            }
            if(prev.getChildren()[1] != null && prev.getChildren()[1].getRoot().equals(tree.getRoot())) {
                prev.getChildren()[1] = tree.getChildren()[0];
            } else {
                prev.getChildren()[0] = tree.getChildren()[0];
            }
            return true;
        } else if(tree.getChildren()[1] != null && tree.getChildren()[0] == null) {
            if(prev == null) {
            	Tree replace = tree.getChildren()[1];
            	BinaryTree left = (BinaryTree) replace.getChildren()[0];
            	BinaryTree right = (BinaryTree) replace.getChildren()[1];
            	
                try {
                    tree.setRoot(replace.getRoot());
                } catch (NullPointerException e) {tree.setRoot(null);}
                try {
                    tree.setLeft(left);
                } catch (NullPointerException e) {tree.setLeft(null);}
                
                try {
                    tree.setRight(right);
                } catch (NullPointerException e) {tree.setRight(null);}
                
                return true;
            }
            if(prev.getChildren()[1] != null && prev.getChildren()[1].getRoot().equals(tree.getRoot())) {
                prev.getChildren()[1] = tree.getChildren()[1];
            } else {
                prev.getChildren()[0] = tree.getChildren()[1];
            }
            return true;
        } else {
            /* 1] Find the min node in the right subtree, if none, find the max node in the left subtree
             * 2] Replace
             * 3] While not a leaf, replace again
             */
        	try {
	        	TreeNode<E> replace = findMin(tree);
	        	if(replace.child == 0) {
	        		tree.setRoot(replace.bst.getLeft().getRoot());
	        		if(!tree.isLeaf()) {
	            		return removeTree(replace.bst, (BinarySearchTree<E>) replace.bst.getLeft());
	            	}
	        	} else {
	        		tree.setRoot(replace.bst.getRight().getRoot());
	        		if(!tree.isLeaf()) {
	        			return removeTree(replace.bst, (BinarySearchTree<E>) replace.bst.getRight());
	            	}
	        	}
        	} catch (NullPointerException e) {
        		
        	}
        }
 
        return false;
    }
    
    private class TreeNode<E extends Comparable<E>> {
		
		public BinarySearchTree<E> bst; 
		public int child;
		
		public TreeNode(BinarySearchTree<E> bst, int item) {
			this.bst = bst;
			this.child = child;
		}
	}
    
    private TreeNode<E> findMin(BinarySearchTree<E> tree) {
    	BinarySearchTree<E> prev = null;
		BinarySearchTree<E> next = tree;
		
		BinarySearchTree<E> rTree = (BinarySearchTree<E>) tree.getRight();
		while(rTree != null) {
			prev = rTree;
			rTree = (BinarySearchTree<E>) rTree.getLeft();
		}
		
		if(prev != null)
			if(prev.getRoot().equals(tree.getRoot()))
				return new TreeNode(prev, 1);
			else
				return new TreeNode(prev, 0);
		
		BinarySearchTree<E> lTree = (BinarySearchTree<E>) tree.getLeft();
		
		while(prev != null) {
			prev = lTree;
			lTree = (BinarySearchTree<E>) lTree.getRight();
		}

		if(prev.getRoot().equals(tree.getRoot()))
			return new TreeNode(prev, 0);
		else
			return new TreeNode(prev, 1);
    }
}
