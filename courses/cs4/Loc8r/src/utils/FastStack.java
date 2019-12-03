package utils;


/*
 * Static stack, cannot dynamically grow
 */
public class FastStack<T extends Comparable2D<T>> {
    private final T[] stack;
    private int pos;
    
    public FastStack(int N) {
        stack = (T[]) new Comparable2D[N];
    }
    
    public T pop() {
        return stack[--pos];
    }
    
    public void push(T obj) {
        stack[pos++] = obj;
    } 
    
    protected T[] getArray() {
        return stack;
    }
    
    // Assumes the same K. Doesnt check
    protected void copyStack(T[] array, int pos) {
        System.arraycopy(array, 0, stack, 0, pos);
        this.pos = pos;
    }
}
