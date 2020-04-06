
import java.util.Scanner;

public class p862 {
    
    public static boolean possible(int[] array, int K, int range) {
        long sum = 0;
        for(int i = 0; i<range; ++i) {
            sum += array[i];
        }
        if(sum >= K) return true;
        for(int i = range; i<array.length; ++i) {
            sum = sum + array[i] - array[i-range];
            if(sum >= K) {
                return true;
            }
        }
        return false;
    }
    
    public static int sum(int[] array, int K) {
        long sum = 0;
        for(int el: array) {
            if(el > 0) sum += el;
        }
        if(sum < K) {
            return -1;
        }
        int low = 1;
        int high = array.length;
        int best = array.length;
        int med;
        while(low < high) {
            med = (low+high)/2;
            System.out.println(med);
            if(possible(array, K, med)) {
                high = med;
                best = high;
            } else {
                low = med+1;
            }
        }
        return best;
    }
    
    public static int sumLen(int[] array, int K) {
         int ptr = 0;
         int ref;
         int best = 0x7f7f7f7f;
         while(ptr < array.length) {
             ref = ptr;
             while(ptr < array.length && array[ptr] >= 0) {
                 ++ptr;
             }
             int[] aux = new int[ptr-ref];
             System.arraycopy(array, ref, aux, 0, aux.length);
             best = Math.min(best, sum(aux, K));
             while(ptr < array.length && array[ptr] < 0) {
                 ++ptr;
             }
         }
         return best;
    }
    
    public static void main(String[] args) {
        Scanner f = new Scanner(System.in);
        while(true) {
            int N = f.nextInt();
            if(N == 0) {
                break;
            }
            int K = f.nextInt();
            int[] array = new int[N];
            for(int i = 0; i<N; ++i) {
                array[i] = f.nextInt();
            }
            System.out.println(sumLen(array, K));
        }
        f.close();
    }
}
