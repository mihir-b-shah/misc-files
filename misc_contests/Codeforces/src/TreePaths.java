
import java.util.*;

public class TreePaths {
    static class PathLenFinder {
        
        private final ArrayList<ArrayList<Integer>> adjList;
        private final ArrayList<Integer> tour;
        private final HashMap<Integer, Integer> mapping;
        private final ArrayList<Integer> heights;
        private final int[] segTree;
        
        private static final int INF = 0x7f7f7f7f;
        
        PathLenFinder(ArrayList<ArrayList<Integer>> adjList) {
            this.adjList = adjList;
            tour = new ArrayList<>();
            mapping = new HashMap<>();
            heights = new ArrayList<>();            
            dfs(0, 0);
            segTree = new int[highPow2(tour.size()) << 1];
            buildSegTree(0, tour.size()-1, 0);
        }
        
        private int highPow2(int x) {
            x -= 1;
            x |= x >>> 1;
            x |= x >>> 2;
            x |= x >>> 4;
            x |= x >>> 8;
            return x+1;
        }
    
        private void dfs(int src, int h) {
            tour.add(src);
            if(!mapping.containsKey(src)) {
                mapping.put(src, tour.size()-1);
            }
            heights.add(h);
 
            for(int adj: adjList.get(src)) {
                if(!mapping.containsKey(adj)) {
                    dfs(adj, h+1);
                    tour.add(src);
                    if(!mapping.containsKey(src)) {
                        mapping.put(src, tour.size()-1);
                    }
                    heights.add(h);
                }
            }
        }
        
        // l and u are inclusive
        private void buildSegTree(int L, int U, int ptr) {
            if(L > U) return;

            if(L == U) {
                segTree[ptr] = heights.get(L);
                return;
            }
            
            int med = (L+U) >>> 1;
            buildSegTree(L, med, 1+(ptr << 1));
            buildSegTree(med+1, U, 2+(ptr << 1));

            int min = INF;
            int aux;
            
            if(1+(ptr << 1) < segTree.length) {
                if((aux = segTree[1+(ptr << 1)]) < min) {
                    min = aux;
                }
            }
            if(2 + (ptr << 1) < segTree.length) {
                if((aux = segTree[2+(ptr << 1)]) < min) {
                    min = aux;
                }
            }

            segTree[ptr] = min;
        }

        int query(int u, int v, int ptr, int low, int high) {
            if(u <= low && v >= high) {
                return segTree[ptr];
            }
            int med = (low + high) >>> 1;
            int min = INF;
            if(u <= med) {
                // left
                min = Math.min(min, query(u, v, 1+(ptr << 1), low, med));
            }
            if(v > med) {
                // right
                min = Math.min(min, query(u, v, 2+(ptr << 1), med+1, high));
            }
            return min;
        }
        
        int dist(int u, int v) {
            u = mapping.get(u);
            v = mapping.get(v);
            if(u > v) {
                u ^= v; 
                v = u^v;
                u ^= v;
            }
            return heights.get(u)+heights.get(v)-2*query(u, v, 0, 0, tour.size()-1);
        }
    }
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(System.in);

        final int V = f.nextInt();
        
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<>();
        for(int i = 0; i<V; ++i) {
            adjList.add(new ArrayList<>());
        }
        
        for(int i = 0; i<V-1; ++i) {
            int src = f.nextInt()-1;
            int dest = f.nextInt()-1;
            
            adjList.get(src).add(dest);
            adjList.get(dest).add(src);
        }
        
        PathLenFinder find = new PathLenFinder(adjList);
        final int Q = f.nextInt();
        /*
        for(int i = 0; i<Q; ++i) {
            int x = f.nextInt()-1;
            int y = f.nextInt()-1;
            
            System.out.printf("dist(%d, %d) = %d%n", x+1, y+1, find.dist(x,y));
        }
        */
        for(int i = 0; i<Q; ++i) {
            int x = f.nextInt()-1;
            int y = f.nextInt()-1;
            int a = f.nextInt()-1;
            int b = f.nextInt()-1;
            int k = f.nextInt();
            
            int origDist = find.dist(a, b);
            int path = 1+Math.min(find.dist(x, a)+find.dist(y, b), find.dist(x, b)+find.dist(y, a));
            int ans = PathLenFinder.INF;
            
            if(origDist % 2 == k % 2) {
                ans = origDist;
            }
            if(path % 2 == k % 2) {
                ans = Math.min(ans, path);
            }
            System.out.println(ans <= k ? "YES" : "NO");   
        }
        
        f.close();
    }
}
