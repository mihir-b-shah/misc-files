/*

ID: mihirsh1
PROB: balance
LANG: JAVA

*/

import java.util.*;
import java.io.*;

public class balance {

    public static double[] dp;
    public static int[] vals;
    
    public static void main(String[] args) throws Exception {
        
        Scanner f = new Scanner(new File("balance.in"));
        PrintWriter out = new PrintWriter("balance.out");
        
        int toCome = f.nextInt();
        
        for(int i = 0; i<toCome; i++) {
            vals[i] = f.nextInt();
        }
        
        
    }
}