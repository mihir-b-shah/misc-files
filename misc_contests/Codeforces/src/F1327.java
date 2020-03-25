
import java.util.*;

public class F1327 {
    
    static int MOD = 998244353;
    
    static class Constraint implements Comparable<Constraint> {
        int left;
        boolean se;
        int val;
        
        Constraint(int l, boolean se, int v) {
            left = l;
            this.se = se;
            val = v;
        }
        
        @Override
        public int compareTo(Constraint o) {
            return left == o.left ? se ? 1 : 0 : left-o.left;
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(System.in);
        
        int N = f.nextInt();
        int K = f.nextInt();
        int M = f.nextInt();
        
        PriorityQueue<Constraint> pq = new PriorityQueue<>();
        Deque<Integer> deque = new ArrayDeque<>();
        
        int[] bitMatrix = new int[N];
        
        for(int i = 0; i<M; ++i) {
            int left = f.nextInt()-1;
            int right = f.nextInt();
            int val = f.nextInt();
            
            pq.offer(new Constraint(left, true, val));
            pq.offer(new Constraint(right, false, val));
        }
        
        while(!pq.isEmpty()) {
            Constraint next = pq.poll();
            if(next.se) {
                // start
                int insert = (deque.isEmpty() ? 0 : deque.peekFirst()) | next.val;
                deque.push(insert);
                
            } else {
                // end
            }
        }
        
        f.close();
    }
}
