
import java.util.Arrays;
import java.util.Scanner;

public class p862v2 {
    
    public static int sumLen(int[] array, int K) {
         int[] tgt = new int[array.length];
         for(int i = 0; i<tgt.length; ++i) {
             tgt[i] = K-array[i];
         }
         System.out.println(Arrays.toString(tgt));
         return 0;
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
