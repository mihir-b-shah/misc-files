
import java.util.*;
import java.io.*;

public class paintbarn {
    
    public static void main(String[] args) throws Exception {
        
        Scanner f = new Scanner(new File("paintbarn.in"));
        PrintWriter out = new PrintWriter("paintbarn.out");
        
        int N = f.nextInt();
        int K = f.nextInt();
        
        int[][] rectangles = new int[N][4];
        
        for(int i = 0; i<N; i++) {
            for(int j = 0; j<4; j++) {
                rectangles[i][j] = f.nextInt();
            }
        }
        
        int[][] count = new int[1000][1000];
        
        for(int i = 0; i<rectangles.length; i++) {
            
            for(int m = rectangles[i][0]; m<rectangles[i][2]; m++) 
                for(int n = rectangles[i][1]; n<rectangles[i][3]; n++) 
                    count[m][n]++;
        }
        
        int c = 0;
        
        for(int i = 0; i<count.length; i++) {
            for(int j = 0; j<count[0].length; j++) {
                if(count[i][j] == K)
                    c++;
            }
        }
        
        out.println(c);
        
        out.flush();
        out.close();
        f.close();
    }
    
}
