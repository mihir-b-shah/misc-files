
/*

ID: mihirsh1
PROB: dining
LANG: JAVA

*/

import java.util.*;
import java.io.*;

public class dining {
    
    public static int start = 0;
    
    public static void main(String[] args) throws Exception {
        
        Scanner f = new Scanner(new File("2.in"));
        PrintWriter out = new PrintWriter("dining.out");
        
        int N = f.nextInt();
        int M = f.nextInt();
        int K = f.nextInt();
        
        double[][] cost = new double[N][N]; 
        
        for(int i = 0; i<M; i++) {
            int X = f.nextInt();
            int Y = f.nextInt();
            int k = f.nextInt();
            
            cost[X-1][Y-1] = k;
            cost[Y-1][X-1] = k;
        }
        
        HashMap<Integer, Double> haybale = new HashMap<>();
        Set<Integer> haybale_locs = haybale.keySet();
                
        for(int i = 0; i<K; i++) {
            start = f.nextInt() - 1;
            haybale.put(start, (double) f.nextInt());
        }
        
        for(int i = 0; i<N; i++) {
            for(int j = 0; j<N; j++) {
                if(i == j)
                    cost[i][j] = Double.NaN;
                else if(cost[i][j] == 0)
                    cost[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        
        //print2DArray(cost);
        HashSet<Integer> visited = new HashSet<>();
        
        for(int i = 0; i<N; i++) {
            visited.clear();
            dijkstra(i, cost, visited);
        }

        print2DArray(cost);
        int dest = N - 1;
        
        // Equivalence then compare for REWARD
        
        for(int i = 0; i<N; i++) {
            cost[i][i] = 0;
        }
        
        for(int i = 0; i<N-1; i++) {
            
            double min_cost = Double.POSITIVE_INFINITY;
            int haybale_select = start;
            
            for(int j = 0; j<N; j++) {
                if(haybale.containsKey(j)) {
                    if(cost[i][j] < min_cost) {
                        haybale_select = j;
                        min_cost = haybale.get(j);
                    } else if (cost[i][j] == min_cost) {
                        if(haybale.get(j) > haybale.get(haybale_select)) {
                            haybale_select = j;
                        }
                    }
                }
            }
            
            if(cost[i][haybale_select] + cost[haybale_select][N-1] - cost[i][N-1] < haybale.get(haybale_select)) {
                out.println(1);
            } else {
                out.println(0);
            }
        }
        
        out.flush();
        out.close();
        f.close();
    }
    
    public static void dijkstra(int source, double[][] covered, HashSet<Integer> visited) {
        
        // Find min
        
        int min = -1;
        double min_val = Double.POSITIVE_INFINITY;
        
        for(int i = 0; i<covered.length; i++) {
            if(covered[i][source] < min_val) {
                min = i;
                min_val = covered[i][source];
            }
        }
        
        // Relax
        for (int i = 0; i<covered.length; i++) {
            if (!Double.isInfinite(covered[i][min]) && !Double.isNaN(covered[i][source])) {
                if (Double.compare(covered[i][min] + min_val, covered[i][source]) < 0) {
                    covered[i][source] = covered[i][min] + min_val;
                    
                    double replace = Math.min(covered[i][source], covered[source][i]);
                
                    covered[i][source] = replace;
                    covered[source][i] = replace;
                }
            }
        }

        visited.add(source);
        
        if(!visited.contains(min))
            dijkstra(min, covered, visited);
    }
    
    public static void print2DArray(double[][] array) {
        
        for(int i = 0; i<array.length; i++) {
            for(int j = 0; j<array[0].length; j++) {
                System.out.printf("%15.2f", array[i][j]);
            }
            System.out.println();
        }
        System.out.println();
        
    }
    
}
