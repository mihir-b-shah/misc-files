
import java.io.*;
import java.util.*;

public class reststops {

    public static class Stop implements Comparable {

        public int x;
        public int c;

        public Stop(int X, int C) {
            x = X;
            c = C;
        }

        @Override
        public int compareTo(Object o) {
            Stop s = (Stop) o;
            return s.c - c;
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("reststops.in"));
        PrintWriter out = new PrintWriter("reststops.out");

        int L = f.nextInt();
        int N = f.nextInt();
        int rF = f.nextInt();
        int rB = f.nextInt();

        Stop[] stops = new Stop[N];

        for (int i = 0; i < N; i++) {
            stops[i] = new Stop(f.nextInt(), f.nextInt());
        }

        Arrays.sort(stops);
        int count = 0;
        long profit = 0;
        long curr_X = 0;
        long time;

        while (count < N) {
            time = (rF - rB) * (stops[count].x - curr_X);
            curr_X = stops[count].x;

            profit += time * stops[count].c;
            count++;
            
            while(count < N && stops[count].x <= curr_X)
                count++;
        }

        out.println(profit);
        out.flush();
        out.close();
        f.close();
    }

}
