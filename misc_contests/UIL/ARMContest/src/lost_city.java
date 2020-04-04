
import java.util.*;
import java.io.*;

public class lost_city {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("lost_city.dat"));
        while(f.hasNextLine()) {
            String line = f.nextLine();
            String[] split = line.split("[:/\\-]");
            int totWeight = Integer.parseInt(split[0]);
            
            int w[] = new int[split.length >>> 1];
            double v[] = new double[split.length >>> 1];
            
            for(int i = 1; i<split.length; i+=2) {
                w[i-1 >>> 1] = Integer.parseInt(split[i]);
                v[i-1 >>> 1] = Double.parseDouble(split[i+1]);
            }
            
            int MAX = 1 << w.length;
            double optVal = 0;
            
            
            for(int i = 0; i<MAX; ++i) {
                int aux = i;
                int wt = 0;
                double val = 0;
                int ctr = w.length-1;
                while(aux > 0) {
                    if((aux&1) == 1) {
                        wt += w[ctr];
                        val += v[ctr];
                    }
                    ctr--;
                    aux >>>= 1;
                }
                
                if(wt <= totWeight && optVal < val)
                    optVal = val;
            }
            
            System.out.printf("You grabbed $%.2f worth of artifacts.%n", optVal);
        }
        f.close();
    }
}
