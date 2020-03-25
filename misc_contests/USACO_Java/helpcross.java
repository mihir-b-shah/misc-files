
/*

ID: mihirsh1
PROB:
LANG: JAVA

*/

import java.util.*;
import java.io.*;

public class helpcross {
    
    public static class IntPair implements Comparable {
        
        public int X;
        public int Y;
        
        public IntPair(int X, int Y) {
            this.X = X;
            this.Y = Y;
        }
        
        @Override
        public int compareTo(Object o) {
            IntPair i = (IntPair) o;
            return X != i.X ? X - i.X : Y - i.Y;
        }
        
    }
    
    public static void main(String[] args) throws Exception {
        
        Scanner f = new Scanner(new File("helpcross.in"));
        PrintWriter out = new PrintWriter("helpcross.out");
        
        int count = 0;
        int C = f.nextInt();
        int N = f.nextInt();
        
        int[] chickens = new int[C];
        
        for(int i = 0; i<C; i++) {
            chickens[i] = f.nextInt();
        }
        
        Arrays.sort(chickens);
        ArrayList<IntPair> cows = new ArrayList<>(N);
        
        for(int i = 0; i<N; i++) {
            cows.add(new IntPair(f.nextInt(), f.nextInt()));
        }

        Collections.sort(cows);
        
        for(int i = 0; i<C; i++) {
            int time = chickens[i];
            IntPair opt;
            boolean found = false;
            
            if(!cows.isEmpty())
                opt = cows.get(0);
            else
                break;
            
            for(int j = 0; j<cows.size(); j++) {
                if(time >= cows.get(j).X && time < cows.get(j).Y) {
                    found = true;
                    if(opt.Y - time > cows.get(j).Y - time) {
                        opt = cows.get(j);
                    }
                }
            }

            if(found) {  
                cows.remove(opt);
                count++;
            }
        }
        
        out.println(count);
        out.flush();
        out.close();
        f.close();
    }
    
}
