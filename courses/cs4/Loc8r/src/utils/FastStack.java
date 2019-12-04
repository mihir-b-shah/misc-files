package utils;

/*
 * Static stack, cannot dynamically grow
 */
public class FastStack {
    private final long[] stack;
    private int pos;
    
    public FastStack(int N) {
        stack = new long[N];
    }
    
    public long pop() {
        return stack[--pos];
    }
    
    public void push(long obj) {
        stack[pos++] = obj;
    } 
    
    public boolean empty() {
        return pos == 0;
    }
    
    protected long[] getArray() {
        return stack;
    }
}
