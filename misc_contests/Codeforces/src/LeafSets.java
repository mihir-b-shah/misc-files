
import java.util.*;

public class LeafSets {
    
    static ArrayList<ArrayList<Integer>> list;
    static boolean[] visited;
    static int[] colors;
    static int color;
    
    static void dfs(int src) {
        ArrayList<Integer> neighbors = list.get(src);
        boolean leaf = true;
        for(int neighbor: neighbors) {
            if(!visited[neighbor]) {
                leaf = false; 
                dfs(neighbor);
            }
        }
        if(leaf) {
            colors
        }
    }
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(System.in);
        int N = f.nextInt();
        int K = f.nextInt();
        
        list = new ArrayList<>(N);
        colors = new int[N];
        visited = new boolean[N];
        color = 0;
        
        for(int i = 0; i<N; ++i) {
            int src = f.nextInt()-1;
            int dest = f.nextInt()-1;
            
            if(list.get(src) == null) {
                list.set(src, new ArrayList<>());
            }
            
            if(list.get(dest) == null) {
                list.set(dest, new ArrayList<>());
            }
            
            list.get(src).add(dest);
            list.get(dest).add(src);
        }
        
        dfs(0);
    }
}
