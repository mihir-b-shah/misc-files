
import java.util.*;
import java.io.*;

public class swap {
    
    // inclusive
    private static final void swap(int i, int j, int[] array) {
        int lim = (j-i+1) >>> 1;
        for(int k = 0; k<lim; ++k) {
            int temp = array[i+k];
            array[i+k] = array[j-k];
            array[j-k] = temp;
        }
    }
    
    private static final int adjustMod(int N, int v) {
        int res = v%N;
        if(res < 0) {
            return N+res;
        }
        return res;
    }
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("swap.in"));
        int N = f.nextInt();
        int M = f.nextInt();
        int K = f.nextInt();
        
        int[] array = new int[N];
        for(int i = 0; i<N; ++i) {
            array[i] = i;
        }
        
        int[] swapIndices = new int[M << 1];
        for(int i = 0; i<swapIndices.length; ++i) {
            swapIndices[i] = f.nextInt()-1;
            if((i & 1) == 1) {
                swap(swapIndices[i-1], swapIndices[i], array);
            }
        }
        f.close();
        
        ArrayList<Integer>[] cycleLim = new ArrayList[N];
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        PrintWriter out = new PrintWriter("swap.out");
        
        int[] pos = new int[N];
        
        for(int i = 0; i<N; ++i) {
            if(cycleLim[i] != null) {
                int modK = adjustMod(cycleLim[i].size(), -K+pos[i]);
                out.println(cycleLim[i].get(modK)+1);
                continue;
            }
            cycleLim[i] = new ArrayList<>();
            int val = i;
            do {
                stack.push(val);
                val = array[val];
            } while(val != i);
            final int len = stack.size();
            while(!stack.isEmpty()) {
                int p = stack.pop();
                cycleLim[i].add(p);
                cycleLim[p] = cycleLim[i];
                pos[p] = len - 1 - stack.size();
            }
            int modK = adjustMod(cycleLim[i].size(), -K+pos[i]);
            out.println(cycleLim[i].get(modK)+1);
        }

        out.flush();
        out.close();
    }
}
