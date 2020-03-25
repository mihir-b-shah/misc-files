

import java.util.*;

public class Polygon {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(System.in);
        int N = f.nextInt();
        for(int i = 0; i<N; ++i) {
            System.out.println(f.nextInt()%f.nextInt() == 0 ? "YES" : "NO");
        }
    }
}
