
import java.io.*;
import java.util.*;

public class fairphoto {

    public static class Cow implements Comparable {
        
        public int loc;
        public int id;
        
        public Cow(int l, int i) {
            loc = l;
            id = i;
        }
        
        @Override
        public int compareTo(Object o) {
            Cow c = (Cow) o;
            return loc - c.loc;
        }
    }
    
    public static class CowTuple {
        
        public int[] count;
        
        public CowTuple() {
            count = new int[8];
        }
        
        public void add(CowTuple other) {
            for(int i = 0; i<8; i++) {
                count[i] += other.count[i];
            }
        }
        
        @Override
        public String toString() {
            return Arrays.toString(count) + "\n";
        }
    }
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("fairphoto.in"));
        PrintWriter out = new PrintWriter("fairphoto.out");

        int N = f.nextInt();
        int K = f.nextInt();
        
        Cow[] data = new Cow[N];
        
        for(int i = 0; i<N; i++) {
            data[i] = new Cow(f.nextInt(), f.nextInt());
        }
        
        Arrays.sort(data);
        CowTuple[] prefix = new CowTuple[N+1];
        
        for(int i = 0; i<=N; i++)
            prefix[i] = new CowTuple();
        
        prefix[1].count[data[0].id-1]++;
        
        for(int i = 2; i<=N; i++) {
            prefix[i].add(prefix[i-1]);
            prefix[i].count[data[i-1].id-1]++;
        }

        int max = 0;
        
        for(int i = N; i>=1; i--) 
            for(int j = 0; j<=N-i; j++) {
                
                if(checkArray(K, prefix[j].count, prefix[i+j].count)) 
                    if(data[i+j-1].loc-data[j].loc > max)
                        max = data[i+j-1].loc - data[j].loc;
            }
        
        out.println(max);
        out.flush();
        out.close();
        f.close();
    }

    public static boolean checkArray(int K, int[] c1, int[] c2) {
        
        int val = c2[0] - c1[0];
        int c = 0;
        
        for(int i = 1; i<c1.length; i++) {
            
            if(!(c1[i] == 0 && c2[i] == 0)) { 
                if(c2[i] - c1[i] != val) {
                    return false;
                } else if(c2[i] != 0) {
                    c++;
                }
            }
        }
        
        return c >= K;
    }
}
