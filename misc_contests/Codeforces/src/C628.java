
import java.util.*;

public class C628 {
    
    static int[] degree;
    
    public static class Edge implements Comparable<Edge>{
        int src,dest;
        
        public Edge(int s, int d) {
            src = s; dest = d;
        }

        @Override
        public int compareTo(Edge t) {
            return degree[src]+degree[dest]-(degree[t.src]+degree[t.dest]);
        }
        
        @Override
        public String toString() {
            return String.format("(%d, %d)", src, dest);
        }
    }
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(System.in);
        int N = f.nextInt();
        
        degree = new int[N];
        Edge[] edges = new Edge[N-1];
        
        int s,d;
        for(int i = 0; i<N-1; ++i) {
            s = f.nextInt()-1;
            d = f.nextInt()-1;
            ++degree[s]; ++degree[d];
            
            edges[i] = new Edge(s,d);
        }
        
        Arrays.sort(edges);
        System.out.println(Arrays.toString(degree));
        System.out.println(Arrays.toString(edges));
    }
}
