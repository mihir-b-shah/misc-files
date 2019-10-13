public class ListNode<E> {
	// FIELDS
    // ------------------------------------------------------------------

    private E data;
    private ListNode<E> next;
    private ListNode<E> prev;

	// CONSTRUCTORS
    // ------------------------------------------------------------------
    public ListNode(E initData,
            ListNode<E> initNext,
            ListNode<E> initPrev) {
        data = initData;
        prev = initNext;
        next = initPrev;
    }

	// METHODS
    // ------------------------------------------------------------------
    public E getData() {
        return data;
    }
    
    
    @Override
    public int hashCode() {
        return data.hashCode() % 1000;
    }


    public void setData(E theNewData) {
        data = theNewData;
    }

    public ListNode<E> getNext() {
        return next;
    }

    public ListNode<E> getPrev() {
        return prev;
    }

    public void setNext(ListNode<E> theNewNext) {
        next = theNewNext;
    }

    public void setPrev(ListNode<E> theNewPrev) {
        prev = theNewPrev;
    }

    public String toString() {
        return "[" + data + "]";
    }

}