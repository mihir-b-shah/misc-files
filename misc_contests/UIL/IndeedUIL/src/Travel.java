
import java.util.*;
import java.io.*;

public class Travel {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("travel.dat"));
        int N = f.nextInt();
        for(int i = 0; i<N; ++i) {
            int ctr = 0;
            HashMap<String, Integer> map = new HashMap<>();
            int E = f.nextInt();
            String[] p1s = new String[E];
            String[] p2s = new String[E];
            int[] p3s = new int[E];
            for(int j = 0; j<E; ++j) {
                String p1 = f.next();
                p1s[j] = p1;
                String p2 = f.next();
                p2s[j] = p2;
                int wt = f.nextInt();
                p3s[j] = wt;
                
                int index1, index2;
                if(map.containsKey(p1)) {
                    index1 = map.get(p1);
                } else {
                    map.put(p1, ctr++);
                    index1 = ctr-1;
                }
                if(map.containsKey(p2)) {
                    index1 = map.get(p2);
                } else {
                    map.put(p2, ctr++);
                    index1 = ctr-1;
                }                
            }
            int[][] dp = new int[map.size()][map.size()];
            int[][] parent = new int[map.size()][map.size()];
            for(int a = 0; a<map.size(); ++a) {
                for(int j = 0; j<map.size(); ++j) {
                    parent[a][j] = a;
                }
            }
            for(int j = 0; j<E; ++j) {
                dp[map.get(p1s[j])][map.get(p2s[j])] = p3s[j];
            }
            for(int c = 0; c<dp.length; ++c) {
                for(int a = 0; a<dp.length; ++a) {
                    for(int b = 0; b<dp.length; ++b) {
                        dp[a][b] = Math.min(dp[a][b], dp[a][c]+dp[c][b]);
                        parent[a][b] = parent[c][b];
                    }
                }
            }
            int i1 = map.get(f.next());
            int i2 = map.get(f.nextInt());
            
            
        }
        f.close();
    }
    
    public static String printPath(int i, int j, ) {
        
    }
}
