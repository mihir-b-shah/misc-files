
import java.util.*;

public class A1323 {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(System.in);
        int T = f.nextInt();
        for(int i = 0; i<T; ++i) {
            int N = f.nextInt();
            int[] array = new int[N];
            for(int j = 0; j<N; ++j) {
                array[j] = f.nextInt();
            }
            int ptr = 0;
            while(ptr < N && array[ptr] % 2 != 0) {
                ++ptr;
            }
            if(ptr < N) {
                System.out.println(1);
                System.out.println(ptr+1);
            } else if(N > 1) {
                System.out.println(2);
                System.out.println("1 2");
            } else {
                System.out.println(-1);
            }
        }
        f.close();
    }
}
