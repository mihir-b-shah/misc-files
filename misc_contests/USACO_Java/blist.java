
/*

ID: mihirsh1
PROB: blist
LANG: JAVA

*/

import java.util.*;
import java.io.*;

public class blist {
    
    public static void main(String[] args) throws Exception {
        
        Scanner f = new Scanner(new File("blist.in"));
        PrintWriter out = new PrintWriter("blist.out");
        
        int toCome = f.nextInt();
        int[][] lookup = new int[toCome][3];     
        int[] need = new int[1000];
        
        for(int i = 0; i<toCome; i++) {
            lookup[i][0] = f.nextInt();
            lookup[i][1] = f.nextInt();
            lookup[i][2] = f.nextInt();
        }
        
        // Shift times back 1
        
        for(int i = 0; i<1000; i++) {
            
            int bucket = 0;
            
            for(int j = 0; j<lookup.length; j++) { 
                if((i+1) >= lookup[j][0] && (i+1) < lookup[j][1]) {
                    bucket += lookup[j][2];
                }
            }
            
            need[i] = bucket;
        }
        
        Arrays.sort(need);
        out.println(need[need.length - 1]);
        
        out.flush();
        out.close();
        f.close();
    }    
}
