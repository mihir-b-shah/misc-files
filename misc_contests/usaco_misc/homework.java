/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mihir
 */

import java.util.*;
import java.io.*;

public class homework {
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("homework.in"));
        PrintWriter out = new PrintWriter("homework.out");
        
        int N = f.nextInt();
        int[] arr = new int[N];
        
        for(int i = 0; i<N; i++)
            arr[i] = f.nextInt();
        
        // Need prefix sums and prefix min
        
        int[] sums = new int[N];
        sums[N-1] = arr[N-1];
        
        int[] mins = new int[N];
        mins[N-1] = arr[N-1];
        
        for(int i = N-1; i>0; i--) {
            sums[i-1] = sums[i] + arr[i-1];
            mins[i-1] = Math.min(mins[i], arr[i-1]);
        }
        
        // Calc max score
        double score = ((double) (sums[0] - mins[0]))/(N-1);
        double[] scores = new double[N-1];
        
        for(int i = 0; i<N-1; i++) {
            double score_loc = ((double) (sums[i] - mins[i]))/(N-1-i);
            scores[i] = score_loc;
            
            if(Double.compare(score_loc, score) > 0)
                score = score_loc;
        }
        
        for(int i = 1; i<N-1; i++)
            if(Double.compare(scores[i], score) == 0)
                out.println(i);
        
        out.flush();
        out.close();
        f.close();
    }
    
}
