
/*

ID: mihirsh1
LANG: JAVA
PROB: cowtour

*/

import java.io.*;
import java.util.*;

public class cowtour {
    
    public static void main(String[] args) throws Exception {
        
        BufferedReader f = new BufferedReader(new FileReader("cowtour.in"));
        PrintWriter out = new PrintWriter("cowtour.out");
        
        int toCome = nextInt(f);
        f.skip(1); // REMOVE
        
        int[][] points = new int[toCome][2];
        
        for(int i = 0; i<toCome; i++) {
            points[i][0] = nextInt(f);
            points[i][1] = nextInt(f);
            
            f.skip(1); // DELETE
        }
        
        boolean[][] adjMat = new boolean[toCome][toCome];
        
        for(int i = 0; i<toCome; i++) {
            for(int j = 0; j<toCome; j++) {

                int x = f.read();
                
                    if(x == 49) 
                    adjMat[i][j] = true;
            }
            
            f.skip(2); // CHANGE to f.read(1);
        }
        
        double[][] covered = new double[toCome][toCome];
        
        for(int i = 0; i<covered.length; i++) {
            for(int j = 0; j<covered[0].length; j++) {
                if(adjMat[i][j]) {
                    covered[i][j] = Math.sqrt(Math.pow(points[i][0] - points[j][0], 2) + Math.pow(points[i][1] - points[j][1], 2));
                } else if (i == j) {
                    covered[i][j] = Double.NaN;
                } else {    
                    covered[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }
        
        //print2DArray(covered);
        HashSet<Integer> visited = new HashSet<>();
        
        for(int i = 0; i<toCome; i++) {
            visited.clear();
            dijkstra(i, points, covered, visited);
        }
       
        print2DArray(covered);
        
        out.flush();
        out.close();
        f.close();
    }
    
    public static void transpose(double[][] matrix) {
        
        for(int i = 0; i<matrix.length; i++) {
            for(int j = matrix[0].length - 1; j>i; j--) {
                double min = Math.min(matrix[i][j], matrix[j][i]);
                
                matrix[i][j] = min;
                matrix[j][i] = min;
            }
        }
        
    }
    
    /*
    
    In given source column, find MIN value, recurse to that index
    Once selected get ALL connected and relax
    Recurse
    
    */
    
    public static void dijkstra(int source, int[][] points, double[][] covered, HashSet<Integer> visited) {
        
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
        
        print2DArray(covered);
        visited.add(source);
        
        if(!visited.contains(min))
            dijkstra(min, points, covered, visited);
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
    
    public static int nextInt(BufferedReader f) throws Exception {
        int ch = -2;
        int num = 0;

        do {

            while ((ch = f.read()) != 32 && ch != 10 && ch != 13 && ch != -1) {
                num = num * 10 + (ch - 48);
            }

        } while (ch != 32 && ch != 13 && ch != 10 && ch != -1);

        return num;
    }
    
}
