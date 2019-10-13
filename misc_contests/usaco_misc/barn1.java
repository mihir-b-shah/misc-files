
/*
ID: mihirsh1
TASK: barn1
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class barn1 {

    public static void main(String[] args) throws Exception
    {
        Scanner f = new Scanner(new File("barn1.in"));
        PrintWriter out = new PrintWriter("barn1.out");

        int M = f.nextInt();
        int S = f.nextInt();
        int C = f.nextInt();
        
        int[] stalls = new int[C];
        
        for(int i = 0; i<C; i++)
            stalls[i] = f.nextInt();
        
        Arrays.sort(stalls);
        PriorityQueue<Integer> pq = new PriorityQueue<>(C-1, Collections.reverseOrder());
        
        for(int i = 0; i<C-1; i++)
            pq.offer(stalls[i+1] - stalls[i]);
        
        int diff = stalls[C-1]-stalls[0]+1;
        
        for(int i = 0; i<Math.min(M-1,C-1); i++) {
            int c = pq.poll();
            diff -= (c-1);
        }
        
        out.println(diff);
        out.flush();
        out.close();
        f.close();
    }

}
