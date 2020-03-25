/*

ID: mihirsh1
PROB: comehome
LANG: JAVA

*/

import java.util.*;
import java.io.*;

public class comehome {
    
    public static void main(String[] args) throws Exception {
        
        Scanner f = new Scanner(new File("comehome.in"));
        PrintWriter out = new PrintWriter("comehome.out");
        
        int N = f.nextInt();
        double[][] adjMat =  new double[64][64];
        
        for(int i = 0; i<N; i++) {
            String c1 = f.next();
            String c2 = f.next();
            int w = f.nextInt();
            
            adjMat[(int) c1.charAt(0) - 65][(int) c2.charAt(0) - 65] = w;
            adjMat[(int) c2.charAt(0) - 65][(int) c1.charAt(0) - 65] = w;
        }
        
        dijkstra(0, adjMat, new HashSet<Integer>());
        
        for(double[] arr: adjMat)
            System.out.println(Arrays.toString(arr));
    }
    
    public static void dijkstra(int source, double[][] covered, HashSet<Integer> visited) {

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(source); 
                
        // Find min
        int min = -1;
        double min_val = Double.POSITIVE_INFINITY;

        for (int i = 0; i < covered.length; i++) {
            if (covered[i][source] < min_val) {
                min = i;
                min_val = covered[i][source];
            }
        }
        
        print2DArray(covered);

        // Relax
        for (int i = 0; i < covered.length; i++) {
            if (!Double.isInfinite(covered[i][min]) && !Double.isNaN(covered[i][source])) {
                if (Double.compare(covered[i][min] + min_val, covered[i][source]) < 0) {
                    covered[i][source] = covered[i][min] + min_val;
                    double replace = Math.min(covered[i][source], covered[source][i]);
                    covered[i][source] = replace;
                    covered[source][i] = replace;
                }
            }
        }

        print2DArray(covered);
        visited.add(source);

        if (!visited.contains(min)) {
            dijkstra(min, covered, visited);
        }
    }

    public static void print2DArray(double[][] array) {

        for (int i = 0; i < array.length; i++) {
           // for (int j = 0; j < array[0].length; j++) {
                System.out.printf("%15.2f", array[i][25]);
           //}
            System.out.println();
        }
        System.out.println();

    }
    
}