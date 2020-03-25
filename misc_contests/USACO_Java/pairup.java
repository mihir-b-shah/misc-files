
import java.io.*;
import java.util.*;

public class pairup {

    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("pairup.in"));
        PrintWriter out = new PrintWriter("pairup.out");

        int N = f.nextInt();
        TreeMap<Integer,Integer> cows = new TreeMap<>();
        
        
        for(int i = 0; i<N; i++) {
            int X = f.nextInt();
            int Y = f.nextInt();
            cows.put(Y, X);
        }
        
        int max = 0;
        
        for(int i = 0; i<N/2; i++) {
            
            Map.Entry<Integer, Integer> e1 = cows.pollFirstEntry();
            Map.Entry<Integer, Integer> e2 = cows.pollLastEntry();
            
            if(e1.getValue() > e2.getValue()) {
                cows.put(e1.getKey(), e1.getValue()-e2.getValue());
            } else if(e2.getValue() < e1.getValue()) {
                cows.put(e2.getKey(), e2.getValue()-e1.getValue());
            } 
            
            max = Math.max(e1.getKey()+e2.getKey(), max);
        }

        out.println(max);
        out.flush();
        out.close();
        f.close();
    }

}
