
import java.util.Scanner;

public class reading {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(System.in);
        int T = f.nextInt();
        for(int i = 0; i<T; ++i) {
            long N = f.nextLong();
            long M = f.nextLong();
            
            long print = 0;
            long a = N/M;
            long repeat = a/10;
            int comp = (int) (a%10);
            int sum10 = 0;
            
            for(int j = 1; j<=10; ++j) {
                sum10 += (j*M) % 10;
            }
            
            print += repeat*sum10;
            
            for(int j = 1; j<=comp; ++j) {
                print += (j*M) % 10;
            }
            
            System.out.println(print);
        }
    }
}
