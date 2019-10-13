
import java.util.Scanner;

public class prices {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(System.in);
        int T = f.nextInt();
        for(int i = 0; i<T; ++i) {
            int N = f.nextInt();
            int[] array = new int[N];
            for(int j = 0; j<N; ++j) {
                array[j] = f.nextInt();
            }
            
            int ct = 0;
            int min = Integer.MAX_VALUE;
            for(int j = N-1; j>=0; --j) {
                if(array[j] > min) {
                    ++ct;
                } else if(array[j] < min) {
                    min = array[j];
                }
            }
            
            System.out.println(ct);
        }
    }
}
