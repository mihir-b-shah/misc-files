
import java.util.*;

public class B1323 {    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(System.in);
        int N = f.nextInt();
        int M = f.nextInt();
        int K = f.nextInt();
        
        int[] a = new int[N];
        int[] b = new int[M];
        
        for(int i = 0; i<N; ++i) {
            a[i] = f.nextInt();
        }
        
        for(int i = 0; i<M; ++i) {
            b[i] = f.nextInt();
        }
        
        // prime factorize k.
        
        HashMap<Integer, Integer> factors = new HashMap<>();
        for(int i = 1; i<=Math.sqrt(K); ++i) {
            if(K%i == 0) {
                factors.put(i, K/i);
                factors.put(K/i, i);
            }
        }
        
        int ptr = 0;
        int ctr = 0;
        
        TreeMap<Integer, Integer> factorsA = new TreeMap<>((x,y)->y-x);
        TreeMap<Integer, Integer> factorsB = new TreeMap<>((x,y)->y-x);
        
        while(ptr < N) {
            while(ptr < N && a[ptr] == 1) {
                ++ptr;
                ++ctr;
            }
            
            if(factorsA.containsKey(ctr)) {
                factorsA.put(ctr, factorsA.get(ctr)+1);
            } else {
                factorsA.put(ctr, 1);
            }
            
            ctr = 0;
            
            while(ptr < N && a[ptr] == 0) {
                ++ptr;
            }
        }
        
        ptr = 0;
        ctr = 0;
        
        while(ptr < M) {
            while(ptr < M && b[ptr] == 1) {
                ++ptr;
                ++ctr;
            }
            
            if(factorsB.containsKey(ctr)) {
                factorsB.put(ctr, factorsB.get(ctr)+1);
            } else {
                factorsB.put(ctr, 1);
            }
            
            ctr = 0;
            
            while(ptr < M && b[ptr] == 0) {
                ++ptr;
            }
        }

        int[] prefixA = new int[factorsA.firstKey()];
        int[] prefixB = new int[factorsB.firstKey()];
        
        for(int i = 0; i<prefixA.length; ++i) {
            prefixA[i] = (i>=1 ? 1+prefixA[i-1] : 0)+ (factorsA.containsKey(prefixA.length-i) 
                    ? factorsA.get(prefixA.length-i) : 0);
        }
        
        for(int i = 0; i<prefixB.length; ++i) {
            prefixB[i] = (i>=1 ? 1+prefixB[i-1] : 0) + (factorsB.containsKey(prefixB.length-i) 
                    ? factorsB.get(prefixB.length-i) : 0);
        }
        
        int sum = 0;
        for(Map.Entry<Integer, Integer> factor: factors.entrySet()) {
            sum += (prefixA.length >= factor.getKey() ? prefixA[prefixA.length-factor.getKey()] : 0)
                   *(prefixB.length >= factor.getValue() ? prefixB[prefixB.length-factor.getValue()] : 0);
        }
        
        System.out.println(sum);
        f.close();
    }
}
