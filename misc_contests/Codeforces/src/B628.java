
import java.util.*;

public class B628 {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(System.in);
        int N = f.nextInt();
        for(int i = 0; i<N; ++i) {
            int len = f.nextInt();
            HashSet<Integer> set = new HashSet<>();
            for(int j = 0; j<len; ++j) {
                set.add(f.nextInt());
            }
            int answer = set.size();
            System.out.println(answer);
        }
    }
}
