
/*

ID: mihirsh1
PROB:
LANG: JAVA

*/

import java.util.*;
import java.io.*;

public class maxcross {
    
    public static void main(String[] args) throws Exception {
        
        Scanner f = new Scanner(new File("maxcross.in"));
        PrintWriter out = new PrintWriter("maxcross.out");
        
        int N = f.nextInt();
        int K = f.nextInt();
        int B = f.nextInt();
        
        boolean[] broken = new boolean[N];
        
        for(int i = 0; i<N; i++) {
            try {
                broken[f.nextInt() - 1] = true;
            } catch (NoSuchElementException e) {
                System.out.println(i);
            }
        }
        int[] dp = new int[N-K+1];
        
        // Base case
        
        int num = 0;
        for(int i = 0; i<K; i++) {
            if(broken[i])
                num++;
        }
        
        dp[0] = num;
        int min = dp[0];
        
        for(int i = 0; i<N-K; i++) {
            
            dp[i + 1] = dp[i];
            if(broken[i])
                dp[i+1]--;
            if(broken[i + K])
                dp[i+1]++;
            
            if(min > dp[i+1])
                min = dp[i+1];
            
        }
        
        out.println(min);
        out.flush();
        out.close();
        f.close();
    }
    
}
