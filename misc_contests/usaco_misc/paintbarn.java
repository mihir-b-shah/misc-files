
import java.io.*;
import java.util.*;

public class paintbarn {

    public static void main(String[] args) throws Exception
    {
        Scanner f = new Scanner(new File("paintbarn.in"));
        PrintWriter out = new PrintWriter("paintbarn.out");

        int[][] dp = new int[1000][1001];
        
        int N = f.nextInt();
        int K = f.nextInt();
        
        for(int i = 0; i<N; i++) {
            int a = f.nextInt();
            int b = f.nextInt();
            int c = f.nextInt();
            int d = f.nextInt();
            
            for(int j = a; j<c; j++) {
                dp[j][b]++;
                dp[j][d]--;
            }
        }
        
        int ct = 0;
        
        for(int i = 0; i<1000; i++) {
            for(int j = 0; j<1000; j++) {
                if(dp[i][j] == K)
                    ct++;
                dp[i][j+1] += dp[i][j];
            }
        }
        
        out.println(ct);
        out.flush();
        out.close();
        f.close();
    }

}
