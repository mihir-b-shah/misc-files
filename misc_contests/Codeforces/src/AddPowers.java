
import java.util.*;

public class AddPowers {
    public static void main(String[] args) {
        Scanner f = new Scanner(System.in);
        int T = f.nextInt();
        biggie: 
        for(int i = 0; i<T; ++i) {
            int n = f.nextInt();
            int k = f.nextInt();
            long num;
            HashSet<Integer> set = new HashSet<>();
            for(int j = 0; j<n; ++j) {
                num = f.nextLong();
                int ctr = 0;
                while(num > 0) {
                    long dig = num%k;
                    if(dig > 0) {
                        if(set.contains(ctr)) {
                            System.out.println("NO");
                            continue biggie;
                        }
                        set.add(ctr);
                    }
                    ++ctr;
                    num /= k;
                }
            }
            System.out.println("YES");
        }
        f.close();
    }
}
