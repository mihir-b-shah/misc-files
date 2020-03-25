
import java.io.*;
import java.util.*;

public class multimoo {

    public static class UFDS {
            
    }
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("multimoo.in"));
        PrintWriter out = new PrintWriter("multimoo.out");

        int N = f.nextInt();
        int[][] mat = new int[N][N];
        
        for(int i = 0; i<N; i++)
            for(int j = 0; j<N; j++)
                mat[i][j] = f.nextInt();
        
        boolean[][] visited = new boolean[N][N];
        int max = 0;
        
        for(int i = 0; i<N; i++) {
            for(int j = 0; j<N; j++) {
                if(!visited[i][j]) {
                    max = Math.max(max, floodFill(i, j, visited, mat));
                }
            }
        }

        out.println(max);
        out.flush();
        out.close();
        f.close();
    }
    
    public static int floodFill(int i, int j, boolean[][] visited, int[][] mat) {
        visited[i][j] = true;   
        return 1 + (i+1 < mat.length && !visited[i+1][j] && mat[i+1][j] == mat[i][j] ? floodFill(i+1,j,visited,mat) : 0) + 
               (i > 0 && !visited[i-1][j] && mat[i-1][j] == mat[i][j] ? floodFill(i-1,j,visited,mat) : 0) + 
               (j+1 < mat.length && !visited[i][j+1] && mat[i][j+1] == mat[i][j] ? floodFill(i,j+1,visited,mat) : 0) + 
               (j > 0 && !visited[i][j-1] && mat[i][j-1] == mat[i][j] ? floodFill(i,j-1,visited,mat) : 0);       
    }
}
