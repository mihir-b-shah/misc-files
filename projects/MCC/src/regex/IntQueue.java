
package regex;

/**
 * Implements a fast version of a queue for references
 * with an additional value
 * More space-efficient than ArrayDeque
 * 
 * @author mihir
 */
public class IntQueue {
    private final int[] queue;
    private int front,back;
    private int size;
    private final int MASK;

    public IntQueue(int N) {
        queue = new int[highPow2(N)];
        MASK = queue.length-1;
    }
    
    private static final int highPow2(int x) {
        x -= 1;
        x |= x >>> 1;
        x |= x >>> 2;
        x |= x >>> 4;
        x |= x >>> 8;
        return x+1;
    }
    
    public final int pop() {
        --size;
        back &= MASK;
        return queue[back++];
    }
    
    public final void push(long val) {
        ++size;
        front &= MASK;
        queue[front++] += val;
    }
    
    public final int size() {
        return size;
    }
}
