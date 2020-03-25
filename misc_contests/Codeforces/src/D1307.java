
import java.util.*;

public class D1307 {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(System.in);
        
        int N = f.nextInt();
        int M = f.nextInt();
        int K = f.nextInt();
        
        boolean[] special = new boolean[N];
        for(int i = 0; i<K; ++i) {
            special[f.nextInt()-1] = true;
        }
        
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<>(N);
        for(int i = 0; i<N; ++i) {
            adjList.add(new ArrayList<>());
        }
        
        for(int i = 0; i<M; ++i) {
            int src = f.nextInt()-1;
            int dest = f.nextInt()-1;
            
            adjList.get(src).add(dest);
            adjList.get(dest).add(src);
        }
        f.close();
        
        class Item {
            int len;
            int node;
            
            Item(int l, int n) {
                len = l;
                node = n;
            }
        }
        
        // one more than actual, for visited flag
        int[] distSrc = new int[N];
        
        Queue<Item> queue = new ArrayDeque<>();
        queue.offer(new Item(1, 0));
        
        while(!queue.isEmpty()) {
            Item item = queue.poll();
            if(distSrc[item.node] == 0) {
                distSrc[item.node] = item.len;
            }
            
            for(int next: adjList.get(item.node)) {
                if(distSrc[next] == 0) {
                    queue.offer(new Item(item.len+1, next));
                }
            }
        }
        
        int[] distDest = new int[N];
        
        queue = new ArrayDeque<>();
        queue.offer(new Item(1, N-1));
        
        while(!queue.isEmpty()) {
            Item item = queue.poll();
            if(distSrc[item.node] == 0) {
                distSrc[item.node] = item.len;
            }
            
            for(int next: adjList.get(item.node)) {
                if(distSrc[next] == 0) {
                    queue.offer(new Item(item.len+1, next));
                }
            }
        }

        
    }
}
