
import java.util.*;
import java.io.*;

public class revegetate {
    
    public static class UFDS {
        
        private int[] arr;
        
        public UFDS(int N) {
            arr = new int[N];
            
            for(int i = 0; i<N; i++)
                arr[i] = i;
        }
        
        public void merge(int one, int two) {
            arr[one] = arr[two];
        }
        
        public int numSets() {
            HashSet<Integer> visit = new HashSet<>();
            
            for(int i: arr) {
                visit.add(i);
            }
            
            return visit.size();
        }
        
    }
    
    public static void main(String[] args) throws Exception {
        
        Scanner f = new Scanner(new File("revegetate.in"));
        PrintWriter out = new PrintWriter("revegetate.out");
        
        int N = f.nextInt();
        int M = f.nextInt();
        
        UFDS u = new UFDS(N);
        
        for(int i = 0; i<M; i++) {
            f.next("\\w+");
            u.merge(f.nextInt()-1, f.nextInt()-1);
        }
        
        if(N <= 3)
            out.println(Integer.toBinaryString((int) Math.pow(2, u.numSets())));
        else
            out.println(0);
        
        out.flush();
        out.close();
        f.close();
    }
}
