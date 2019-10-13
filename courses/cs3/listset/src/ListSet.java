import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Spliterator;

/**
 * Combined implementation of both the List and Set interfaces. Represents an
 * ordered collection that contains no duplicate values.
 */
public class ListSet<E> implements List<E>, Set<E> {
	// FIELDS
    // ------------------------------------------------------------------

    private ListNode<E> myDummy;	// Reference to a "dummy" node
    private int mySize;				// Number of items in this list-set
    private ListNode<E> start;

	// CONSTRUCTORS
    // ------------------------------------------------------------------
    /**
     * postcondition: Initializes an empty list-set. algorithm: Assign to
     * myDummy a new ListNode<E> object whose "data" is null and whose "next"
     * and "previous" pointers are null. Set the "next" pointer of myDummy to be
     * myDummy. Set the "previous" pointer of myDummy to be myDummy. Assign
     * mySize a value of 0.
     */
    public ListSet() {
        mySize = 0;
    }
    
    public int hashCode() {
        ListNode<E> ref = start.getPrev();
        int val = 0;

        for(int i = 0; i<mySize; i++) {
            ref = ref.getNext();
            val += ((int) Math.pow(2, i))*ref.hashCode();
        }
        
        return val % 1000;
    }

	// METHODS
    // ------------------------------------------------------------------
    /**
     * precondition: This list-set contains 0 or more elements. postcondition:
     * Returns the number of elements in this list-set. algorithm: Return the
     * value of mySize.
     */
    public int size() {
        return mySize;
    }

    /**
     * precondition: This list-set contains 0 or more elements. postcondition:
     * Returns false if the number of elements currently in this list-set is
     * greater than 0. Returns true if this list-set currently contains no
     * elements. algorithm: Return the result of comparing this.size() with 0.
     */
    public boolean isEmpty() {
        return mySize == 0;	// TODO: Implement this method correctly
    }

    /**
     * precondition: This list-set may or may not already contain x. Object x
     * may be null. postcondition: If this list-set does not already contain x
     * and i is a valid index position within this list-set, x will be inserted
     * at the specified position and the size will increase by 1. If this
     * list-set already contains x, the list-set will remain unchanged. If i is
     * not a valid index position (i.e., i < 0 or i > this.size()), an
     * IndexOutOfBoundsException will be thrown. algorithm: If i is less than 0
     * or i is greater than the size of this list-set... ...throw a new
     * IndexOutOfBoundsException. If this list-set does not contain x...
     * ...declare a ListNode<E> called p and assign to it a reference to node
     * i-1 of this list-set. ...declare a ListNode<E> called n and assign to it
     * a reference to node i of this list-set. ...declare a ListNode<E> called
     * addedNode and assign to it a new ListNode<E> object whose "value" is x,
     * whose "next" pointer is n, and whose "previous" pointer is p. ...set the
     * "next" pointer of p to be addedNode. ...set the "previous" pointer of n
     * to be addedNode. ...increment the value of mySize.
     */
    public void add(int i, E x) {
        if (i < 0 || i > mySize) {
            throw new IndexOutOfBoundsException();
        } else if (i == 0 && mySize == 0) {
            if (!contains(x)) {

                ListNode<E> addedNode = new ListNode(x, null, null);

                addedNode.setNext(addedNode);
                addedNode.setPrev(addedNode);

                start = addedNode;
                mySize++;

                return;
            }

        }

        if (!contains(x)) {

            ListNode<E> p = getNode(i - 1);
            ListNode<E> n = p.getNext();
            ListNode<E> addedNode = new ListNode(x, p, n);

            if (i == 0) {
                start = addedNode;
            }

            addedNode.getNext().setPrev(addedNode);
            addedNode.getPrev().setNext(addedNode);
            
            mySize++;
        }
    }

    /**
     * precondition: This list-set may or may not already contain x. Object x
     * may be null. postcondition: If this list-set does not already contain x,
     * it will be appended to the end of the list-set and the size will increase
     * by 1. Otherwise, this list-set will remain unchanged. Returns true if
     * this list-set is changed as a result of the method call and false if it
     * is not. algorithm: If this list-set contains x... ...return false.
     * Declare an integer variable i and initialize it with the size of this
     * list-set. Add (insert) x into index position i of this list-set. Return
     * true.
     */
    public boolean add(E x) {
        if (contains(x)) {
            return false;
        } else {
            add(mySize, x);
            return true;
        }
    }

    /**
     * precondition: c contains items to be added to this list-set. This
     * list-set may already contain some of the elements of Collection c. The
     * elements of c may be null. postcondition: Attempts to insert each element
     * of c into this list-set at position i in the order that the items are
     * returned by the specified collection's iterator. Returns true if this
     * list-set is changed as a result of the method call and false if it is
     * not. If i is not a valid index position (i.e., i < 0 or
     *                i > this.size()), an IndexOutOfBoundsException will be thrown. algorithm:
     * Declare a boolean variable called wasModified and initialize it with a
     * value of false. For each E x in Collection c... ...if this list-set does
     * not contain x... ...add x to this list-set at position i. ...increment
     * the value of i. ...assign to wasModified the value of true. Return the
     * value of wasModified.
     */
    public boolean addAll(int i, Collection<? extends E> c) {
        boolean modify = false;
        int k = 0;
        
        for (E item : c) {
            if (!contains(item)) {
                add(i + k++, item);
                modify = true;
            }
        }

        return modify;
    }

    /**
     * precondition: c contains items to be added to this list-set. This
     * list-set may already contain some of the elements of Collection c. The
     * elements of c may be null. postcondition: Attempts to append each element
     * of c to the end of this list-set in the order that the items are returned
     * by the specified collection's iterator. Returns true if this list-set is
     * changed as a result of the method call and false if it is not. algorithm:
     * Declare an integer variable i and initialize it with the size of this
     * list-set. Return the result of adding all elements of c to index position
     * i of this list-set.
     */
    public boolean addAll(Collection<? extends E> c) {
        boolean modify = false;

        for (E item : c) {
            if (!contains(item)) {
                add(mySize, item);
                modify = true;
            }
        }

        return modify;
    }

    /**
     * precondition: This list-set contains 0 or more elements. postcondition:
     * If i is a valid index position in this list-set, returns the ListNode<E>
     * representing index position i. Otherwise, throws an
     * IndexOutOfBoundsException. algorithm: If i is less than -1 or i is
     * greater than the size of this list-set... ...throw a new
     * IndexOutOfBoundsException. Declare a ListNode<E> variable called n and
     * initialize it to the value of myDummy. While i is greater than or equal
     * to 0... ...assign to n the ListNode<E> reference by the "next" field of
     * n. ...decrement the value of i. Return the ListNode<E> referenced by n.
     */
    private ListNode<E> getNode(int i) {
        int index = revisedMod(i, size());

        if (size() != 0) {
            ListNode<E> iter = start;

            for (int k = 0; k < index; k++) {
                iter = iter.getNext();
            }

            return iter;
        } else {
            return null;
        }
    }

    /**
     * precondition: This list-set contains 0 or more elements. postcondition:
     * If i is a valid index position in this list-set, returns the object
     * stored at index position i. Otherwise, throws an
     * IndexOutOfBoundsException. algorithm: If i is less than 0 or i is greater
     * than or equal too the size of this list-set... ...throw a new
     * IndexOutOfBoundsException. Declare a ListNode<E> variable called n. Get
     * the node at position i and assign it to n. Return the value stored in
     * node n.
     */
    public E get(int i) {
        return getNode(i).getData();
    }

    /**
     * precondition: This list-set contains 0 or more elements. postcondition:
     * Returns a string containing a concatenation of the string representations
     * of all elements in this list-set in order of their index positions.
     * algorithm: Declare a String variable called s and initialize it with a
     * value of "[". For each index position, i, in this list-set... ...assign
     * to s the string that results from concatenating the object at position i
     * onto the end of s. ...assign to s the string ", " (comma + space).
     * Reassign s to the value of the substring from index 0 through s.length()
     * - 1 (exclusive). Concatenate the string "]" onto the end of s. Return the
     * value of s.
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        ListNode<E> st = start;
        
        s.append('[');
        int count = 0;

        while (count < mySize) {
            s.append(st.getData());

            s.append(',');
            s.append(' ');
            st = st.getNext();
            count++;
        }
        
        if(mySize > 0) {
            s.delete(s.length() - 2, s.length());
        }
        
        s.append(']');
        return s.toString();
    }

    /**
     * precondition: This list-set may or may not already contain x. Object x
     * may be null. postcondition: Returns the index position of x in this
     * list-set or -1 if this list-set does not contain a reference to x.
     * algorithm: For each index position, i, in this list-set... ...if the
     * object at position i is equivalent to x... ...return the value of i.
     * Return a value of -1.
     */
    public int indexOf(Object x) {
        ListNode<E> dummy = start;
        int i;
        
        for(i = 0; i<mySize; i++) {
            if(dummy.getData().equals((E) x))
                break;
            dummy = dummy.getNext();
        }

        return i >= mySize ? -1 : i;
    }

    /**
     * precondition: This list-set may or may not already contain x. Object x
     * may be null. postcondition: Returns the index position of x in this
     * list-set or -1 if this list-set does not contain a reference to x.
     * algorithm: Return the result of invoking indexOf(x).
     */
    public int lastIndexOf(Object x) {
        return indexOf(x);
    }

    /**
     * precondition: This list-set may or may not already contain x. Object x
     * may be null. postcondition: Returns true if this list-set contains x and
     * false if it does not. algorithm: Return the result of comparing to see if
     * the index position of x is greater than -1.
     */
    public boolean contains(Object x) {
        ListNode<E> dummy = start;

        for (int i = 0; i < mySize; i++) {
            if (dummy.getData().equals((String) x)) {
                return true;
            }

            dummy = dummy.getNext();
        }

        return false;
    }

    /**
     * precondition: This list-set may or may not contain the elements of
     * collection c. The elements of c may be null. postcondition: Returns true
     * if this list-set contains all elements of collection c and false if it
     * does not. algorithm: For each Object x in Collection c... ...if this
     * list-set does not contain x... ...return false. Return true.
     */
    public boolean containsAll(Collection<?> c) {
        Iterator<?> it = c.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }

        return true;
    }

    /**
     * precondition: This list-set contains 0 or more elements. This list-set
     * may or may not already contain x. Object x may be null. postcondition: If
     * this list-set already contains object x, throws an
     * IllegalArgumentException. If this list-set does not already contain
     * object x, replaces the element at index position i with object x and
     * returns the object originally stored in that position. If i is not a
     * valid index position (i.e., i < 0 or i >= this.size()), an
     * IndexOutOfBoundsException will be thrown. algorithm: If i is less than 0
     * or i is greater than or equal too the size of this list-set... ...throw a
     * new IndexOutOfBoundsException. Declare a ListNode<E> called n and assign
     * to it a reference to node i of this list-set. Declare an E variable
     * called val and assign it the "value" stored in node n. Set the "value"
     * stored in node n to be x. Return the value of val.
     */
    public E set(int i, E x) {
        if (i < 0 || i >= mySize) {
            throw new IndexOutOfBoundsException();
        } else if (contains(x)) {
            throw new IllegalArgumentException();
        }

        ListNode<E> dummy = start;

        for (int k = 0; k < i; k++) {
            dummy = dummy.getNext();
        }

        E prevData = dummy.getData();
        dummy.setData(x);
        return prevData;
    }

    /**
     * precondition: This list-set contains 0 or more elements. postcondition:
     * Removes and returns the element currently stored at index position i. If
     * i is not a valid index position (i.e., i < 0 or i >= this.size()), an
     * IndexOutOfBoundsException will be thrown. algorithm: If i is less than 0
     * or i is greater than or equal too the size of this list-set... ...throw a
     * new IndexOutOfBoundsException. Declare a ListNode<E> called r and assign
     * to it a reference to node i of this list-set. Declare a ListNode<E>
     * called p and assign to it a reference to the "previous" node of r.
     * Declare a ListNode<E> called n and assign to it a reference to the "next"
     * node of r. Set the "next" pointer of p to be n. Set the "previous"
     * pointer of n to be p. Set the "next" pointer of r to be null. Set the
     * "previous" pointer of r to be null. Decrement the value of mySize. Return
     * the "value" of r;
     */
    public E remove(int i) {
        ListNode<E> dummy = start;

        if(i == 0) {
            start = start.getNext();
        }
        
        for (int k = 0; k < i; k++) {
            dummy = dummy.getNext();
        }

        E remove = dummy.getData();

        dummy.getPrev().setNext(dummy.getNext());
        dummy.getNext().setPrev(dummy.getPrev());
        
        dummy.setNext(null);
        dummy.setPrev(null);

        mySize--;
        return remove;
    }

    /**
     * precondition: This list-set may or may not already contain x. Object x
     * may be null. postcondition: Removes the first occurrence of x in the
     * list-set. Returns true if this list-set is changed as a result of the
     * method call and false if it is not. algorithm: Declare an integer
     * variable called i and assign to it the result of invoking indexOf(x). If
     * the value of i is equal to -1... ...return false. Otherwise... ...remove
     * the element at position i from this list-set. ...return true.
     *
     * Get the header out of the
     */
    public boolean remove(Object x) {
        ListNode<E> dummy = start;
        int i;
        
        for(i = 0; i<mySize; i++) {
            if(dummy.getData().equals((E) x))
                break;
            dummy = dummy.getNext();
        }

        if(i == 0) {
            start = start.getNext();
        }
        
        E remove = dummy.getData();

        dummy.getPrev().setNext(dummy.getNext());
        dummy.getNext().setPrev(dummy.getPrev());

        mySize--;
        return true;
    }

    /**
     * precondition: c contains items to be removed from this list-set. This
     * list-set may or may not contain each element of Collection c. The
     * elements of c may be null. postcondition: Removes all occurrences of each
     * element of c from this list-set. Returns true if this list-set is changed
     * as a result of the method call and false if it is not. algorithm: Declare
     * a boolean variable called wasModified and initialize it with a value of
     * false. For each Object x in Collection c... ...if removing x from this
     * list-set returns true... ...assign wasModified a value of true. Return
     * the value of wasModified.
     */
    public boolean removeAll(Collection<?> c) {
        boolean wasModified = false;

        for (Object item : c) {
            try {
                E it = (E) item;
                if (remove(it));
                wasModified = true;
            } catch (ClassCastException e) {
                System.err.println(e.getStackTrace());
            }
        }

        return wasModified;
    }

    /**
     * precondition: This list-set contains 0 or more elements. postcondition:
     * Removes all of the elements from this list-set. This list-set will be
     * empty after this call returns. algorithm: While this list-set is not
     * empty... ...remove the element at index position 0.
     */
    public void clear() {
        while (!isEmpty()) {
            remove(0);
        }
    }

    /**
     * precondition: c contains items to be retained in this list-set. This
     * list-set may or may not contain each element of Collection c. The
     * elements of c may be null. postcondition: Removes all elements of this
     * list-set except for those in c. Returns true if this list-set is changed
     * as a result of the method call and false if it is not. algorithm: Declare
     * a boolean variable called wasModified and initialize it with a value of
     * false. For each index position, i, in this list-set from this.size() - 1
     * through 0 (inclusive)... ...if c does not contain the element stored at
     * position i in this list-set... ...remove the element stored at position i
     * from this list-set. ...assign wasModified a value of true. Return the
     * value of wasModified.
     */
    public boolean retainAll(Collection<?> c) {
        Iterator<?> it = iterator();
        boolean modified = false;

        while (it.hasNext()) {
            E item = (E) it.next();

            if (!c.contains(item)) {
                if (remove(item)) {
                    modified = true;
                }
            }
        }

        return modified;

    }

    /**
     * precondition: Object x may or not be a List or a Set. Object x may be
     * null. postcondition: Returns true if and only if the specified object is
     * also a List and a Set, both list-sets have the same size, and all
     * corresponding pairs of elements in the two list-sets are equal (that is,
     * for each element e1.equals(e2)). In other words, two list-sets are
     * defined to be equal if they contain the same elements in the same order.
     * algorithm: If x references this list-set... ...return true. If x is not
     * an instance of List or x is not an instance of Set... ...return false.
     * Declare a List<E> variable called c and assign it the (properly
     * type-casted) value of x. If the size of this list-set does not equal the
     * size of c... ...return false. Declare an integer variable named i and
     * initialize it to a value a 0. For each index position of c... ...if the
     * element at position i of c does not equal the element stored at position
     * i of this list-set... ...return false. Return true.
     */
    public boolean equals(Object x) {
        if (x == this) {
            return true;
        } else if (!(x instanceof List) || !(x instanceof Set)) {
            return false;
        }

        ListSet<E> a = (ListSet<E>) x;
        
        if(a.size() != mySize)
            return false;
        
        try {
            Iterator<E> it_x = a.iterator();
            Iterator<E> it_t = iterator();

            while (it_t.hasNext() && it_x.hasNext()) {

                if (!it_x.next().equals(it_t.next())) {
                    return false;
                }

            }
        } catch (ClassCastException e) {

        }

        return true;

    }

    /**
     * precondition: firstIndex is the lower end-point (inclusive) of the
     * sublist and lastIndex is the upper end-point (exclusive) of the sublist.
     * postcondition: Returns a new List that contains all of the elements of
     * this list-set within the range of firstIndex inclusive) through lastIndex
     * (exclusive) in the same order that they occur in this list-set. Throws an
     * IndexOutOfBoundsException if from and to specify an an invalid range
     * (firstIndex < 0 or lastIndex > size or firstIndex > lastIndex).
     * algorithm: If firstIndex is less than 0 or lastIndex is greater than the
     * size of this list-set or if firstIndex is greater than lastIndex...
     * ...throw a new IndexOutOfBoundsException. Declare a new List<E> called
     * sub and assign to it a new ListSet<E>. For each index position of this
     * list-set from firstIndex through lastIndex (exclusive)... ...add the
     * element to sub. Return the sub list-set.
     */
    public List<E> subList(int firstIndex, int lastIndex) {
        Iterator<E> iter = iterator();
        List<E> sub = new ListSet<E>();

        for (int i = 0; i < firstIndex; i++) {
            iter.next();
        }

        for (int i = 0; i < lastIndex - firstIndex; i++) {
            sub.add(iter.next());
        }

        return sub;
    }

    /**
     * precondition: This list-set contains 0 or more elements. postcondition:
     * Returns a primitive array containing all of the elements of this list-set
     * in the same order that they are currently stored. The length of the
     * returned array is equal to the number of elements in this list-set.
     * algorithm: Declare a new Object array called arr and initialize it to
     * have a length equal to the size of this list-set. For each index
     * position, i, of this list-set... ...assign into position i of arr the
     * value stored in the node at position i of this list-set. Return arr.
     */
    public Object[] toArray() {
        Object[] arr = new Object[size()];
        Iterator<E> it = iterator();

        int counter = 0;

        while (it.hasNext()) {
            arr[counter] = it.next();
            counter++;
        }

        return arr;

    }

    public int revisedMod(int num, int base) {

        while (num < 0) {
            num += base;
        }

        while (num >= base) {
            num -= base;
        }

        return num;
    }

    /**
     * precondition: dest is a properly initialized of some typed, T.
     * postcondition: If dest.length is greater than the size of this list-set,
     * copies all of the elements of this list-set into dest, assigns null to
     * the first index position beyond the last element copied, and then returns
     * dest. Otherwise, returns a new array of T of the appropriate length
     * containing all of the elements of this list-set.
     */
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] dest) {
        Object[] src = this.toArray();

        if (dest.length < this.size()) {
            return (T[]) Arrays.copyOf(src, this.size(), dest.getClass());
        } else {
            System.arraycopy(src, 0, dest, 0, this.size());
            if (dest.length > this.size()) {
                dest[this.size()] = null;
            }
            return dest;
        }
    }

    /**
     * precondition: This list-set contains 0 or more elements. postcondition:
     * Returns a new ListSetIterator for this list-set. algorithm: Return a new
     * ListSetIterator (see private inner class).
     */
    public Iterator<E> iterator() {
        return new ListSetIterator();
    }

    /**
     * precondition: This list-set contains 0 or more elements. postcondition:
     * Returns a new ListSetIterator for this list-set. algorithm: Return a new
     * ListSetIterator (see private inner class).
     */
    public ListIterator<E> listIterator() {
        return new ListSetIterator();
    }

    /**
     * precondition: This list-set contains 0 or more elements. postcondition:
     * Returns a new ListSetIterator for this list-set that is initialized to
     * start at index position i. If i is not a valid index position (i.e., i < 0 or i
     * >= this.size()), an IndexOutOfBoundsException will be thrown. algorithm:
     * If i is less than 0 or i is greater than or equal to the size of this
     * list-set... ...throw a new IndexOutOfBoundsException. Declare a
     * ListIterator<E> object called iter and assign to it a new ListSetIterator
     * (see private inner class below). While i is greater than or equal to 0...
     * ...use next() to increment iter to the next element in this list-set.
     * ...decrement the value of i. Return the iter iterator.
     */
    public ListIterator<E> listIterator(int i) {
        ListIterator<E> it = listIterator();

        try {
            for (int k = 0; k < i; k++) {
                it.next();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        }

        return it;

    }

    /**
     * precondition: This list-set contains 0 or more elements. postcondition:
     * Returns a new Spliterator for this list-set using the default method
     * inherited from the List interface (required for disambiguation with the
     * similar method inherited from Set).
     */
     public Spliterator<E> spliterator() { return List.super.spliterator(); }
     
    /**
     * Private, inner class representing an object that can iterate over the
     * elements of a ListSet.
     */
    private class ListSetIterator implements ListIterator<E> {
		// FIELDS
        //---------------------------------------------------------------

        private int myLatestIndex;	// Index position of the last element
        // referenced by this iterator

        private E currItem;

        private boolean myEditableState;	// true if set() or remove()
        // may be invoked, otherwise
        // false if they cannot

		// CONSTRUCTORS
        //---------------------------------------------------------------
        /**
         * postcondition: Initializes a ListSetIterator for this list-set such
         * that the first invocation of next() will return the element at index
         * position 0 of this list-set. algorithm: Initialize myLatestIndex to a
         * value of -1. Initialize myEditableState to false.
         */
        public ListSetIterator() {
            myLatestIndex = -1;
            myEditableState = false;
            currItem = null;
        }

		// METHODS
        //---------------------------------------------------------------
        /**
         * precondition: This list-set contains 0 or more elements.
         * postcondition: Returns true if this list iterator has more elements
         * when traversing the list in the forward direction. algorithm: Return
         * the result of comparing to see if myLatestIndex is less than the size
         * of the list-set - 1 (NOTE: Use ListSet.this.size() to access methods
         * from the outer class).
         */
        public boolean hasNext() {
            return myLatestIndex < ListSet.this.size() - 1;
        }

        /**
         * precondition: This list-set contains 0 or more elements.
         * postcondition: Returns true if this list iterator has more elements
         * when traversing the list in the backward direction. algorithm: Return
         * the result of comparing to see if myLatestIndex is greater than 0.
         */
        public boolean hasPrevious() {
            return myLatestIndex > 0;
        }

        /**
         * precondition: This list-set contains 0 or more elements.
         * postcondition: Returns the index of the element that would be
         * returned by a subsequent call to next(). algorithm: Return the value
         * of myLatestIndex + 1.
         */
        public int nextIndex() {
            return myLatestIndex + 1;
        }

        /**
         * precondition: This list-set contains 0 or more elements.
         * postcondition: Returns the index of the element that would be
         * returned by a subsequent call to previous(). algorithm: Return the
         * value of myLatestIndex - 1.
         */
        public int previousIndex() {
            return myLatestIndex - 1;
        }

        /**
         * precondition: This list-set contains 0 or more elements.
         * postcondition: Returns the next element in this list-set or throws a
         * NoSuchElementException if there is no next element. algorithm: If
         * myLatestIndex is equal to the size of the list-set - 1 (NOTE: Use
         * ListSet.this.size() to access methods from the outer class)...
         * ...throw a new NoSuchElementException. Increment the value of
         * myLatestIndex. Declare a ListNode<E> variable called n. Assign to n
         * the node at position myLatestIndex of the list-set (NOTE: Use
         * ListSet.this.getNode() to access methods from the outer class).
         * Assign to myEditableState the value of true. Return the value stored
         * in node n.
         */
        public E next() {

            if (myLatestIndex == ListSet.this.size() - 1) {
                throw new NoSuchElementException();
            }

            myLatestIndex++;
            myLatestIndex %= ListSet.this.size();

            ListNode<E> n = ListSet.this.getNode(myLatestIndex);

            myEditableState = true;
            return n.getData();
        }

        /**
         * precondition: This list-set contains 0 or more elements.
         * postcondition: Returns the previous element in this list-set or
         * throws a NoSuchElementException if there is no previous element.
         * algorithm: If myLatestIndex is equal to 0... ...throw a new
         * NoSuchElementException. Decrement the value of myLatestIndex. Declare
         * a ListNode<E> variable called n. Assign to n the node at position
         * myLatestIndex of the list-set (NOTE: Use ListSet.this.getNode() to
         * access methods from the outer class). Assign to myEditableState the
         * value of true. Return the value stored in node n.
         */
        public E previous() {
            if (myLatestIndex == 0) {
                throw new NoSuchElementException();
            }

            myLatestIndex--;

            ListNode<E> n;
            n = ListSet.this.getNode(myLatestIndex);

            myEditableState = true;
            return n.getData();
        }

        /**
         * precondition: This list-set contains 0 or more elements.
         * postcondition: Removes from this list-set the last element returned
         * by either the next() or previous() methods. Throws an
         * IllegalStateException if neither next() nor previous() have been
         * called, or remove() or add() have been called after the last call to
         * next() or previous(). algorithm: If myEditableState is false...
         * ...throw a new IllegalStateException. Remove the element from
         * position myLatestIndex of the list-set (NOTE: Use
         * ListSet.this.remove() to access methods from the outer class). Assign
         * to myEditableState the value of false.
         */
        public void remove() {
            if (!myEditableState) {
                throw new IllegalStateException();
            }

            ListSet.this.remove(myLatestIndex);
            myEditableState = false;
        }

        /**
         * precondition: This list-set may or may not already contain x. Object
         * x may be null. postcondition: If this list-set does not already
         * contain x, x will be inserted at the position of the element that
         * will be returned by the next invocation of next() and the size will
         * increase by 1. If this list-set already contains x, the list-set will
         * remain unchanged. algorithm: If this list-set does not contain x...
         * ...add x into the list-set at position myLatestIndex + 1 (NOTE: Use
         * ListSet.this.add() to access methods from the outer class). Assign to
         * myEditableState the value of false.
         */
        public void add(E x) {
            if (!ListSet.this.contains(x)) {
                ListSet.this.add(x);
            }

            myEditableState = false;
        }

        /**
         * precondition: This list-set contains 0 or more elements. This
         * list-set may or may not already contain x. Object x may be null.
         * postcondition: If this list-set already contains object x, throws an
         * IllegalArgumentException. If this list-set does not already contain
         * object x, replaces the last element by an invocation of next() or
         * previous() with object x. Throws an IllegalStateException if neither
         * next() nor previous() have been called, or remove() or add() have
         * been called after the last call to next() or previous(). algorithm:
         * If myEditableState is false... ...throw a new IllegalStateException.
         * If this list-set does not contain x... ...declare a ListNode<E>
         * variable called n. ...assign to n the node at position myLatestIndex
         * of the list-set (NOTE: Use ListSet.this.getNode() to access methods
         * from the outer class). ...set the value stored in node n to x.
         */
        public void set(E x) {
            if (!myEditableState) {
                throw new IllegalStateException();
            }
            if (!ListSet.this.contains(x)) {
                ListNode<E> n = ListSet.this.getNode(myLatestIndex);
                n.setData(x);
            }

        }
    }

}