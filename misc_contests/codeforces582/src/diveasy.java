
import java.util.Arrays;
import java.util.Scanner;

public class diveasy {

    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(System.in);
        int N = f.nextInt();
        int K = f.nextInt();
        long[] array = new long[N];
        for(int i = 0; i<N; ++i) {
            array[i] = f.nextLong();
        }
        
        Arrays.sort(array);
        long min;
        
        long[] aux = new long[K];
        long opt_ct = Integer.MAX_VALUE;
        for(int i = 0; i<=N-K; ++i) {
            long ct = 0;
            System.arraycopy(array, i, aux, 0, K);
            for(int j = 0; j<K-1; ++j) {
                for(int m = j+1; m<K; ++m) {
                    long a = aux[j]; long b = aux[m];
                    while(a != b) {
                        if(a > b) {
                            a >>= 1;
                        } else {
                            b >>= 1;
                        }
                        ++ct;
                    }
                    aux[j] = a;
                    aux[m] = a;
                }
            }
            Arrays.fill(aux,0);
            opt_ct = Math.min(opt_ct, ct);
        }
        
        System.out.println(opt_ct);
    }
}
