
import java.util.*;

public class A628 {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(System.in);
        int N = f.nextInt();
        
        for(int i = 0; i<N; ++i) {
            int x = f.nextInt();
            System.out.printf("%d %d%n", 1, x-1);
        }
        
        f.close();
    }
}
