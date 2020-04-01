
import java.util.*;

public class E1328 {
    
    static int[] heights;
    static boolean[] visited;
    static ArrayList<ArrayList<Integer>> adjList;
    
    static void heightDFS(int src, int h) {
        if(heights[src] != 0) return;
        heights[src] = h;
        for(int next: adjList.get(src)) {
            heightDFS(next, h+1);
        }
    }
    
    static void dfs(int src) {
        if(visited[src]) return;
        System.out.println(src+1);
        visited[src] = true;
        for(int next: adjList.get(src)) {
            if(!visited[next]) {
                System.out.println(src+1);
                dfs(next);
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(System.in);
        /* let's simplify the problem, no one-off.*/
        int N = f.nextInt(); int M = f.nextInt();
        adjList = new ArrayList<>();

        for(int i = 0; i<N; ++i) {
            adjList.add(new ArrayList<>());
        }
        for(int i = 0; i<N-1; ++i) {
            int src = f.nextInt()-1;
            int dest = f.nextInt()-1;
            adjList.get(src).add(dest);
            adjList.get(dest).add(src);
        }
        
        heights = new int[N];
        heightDFS(0, 1);

        visited = new boolean[N];
        dfs(0);
        
        f.close();
    }
}
