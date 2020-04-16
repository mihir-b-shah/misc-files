
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

/**
 * Problem 871.
 *
 * @author mihir
 */
public class Refueling {

    public static int solve(int target, int fuel, int[][] stations) {
        Queue<Integer> pq = new PriorityQueue<>((x,y)->y-x);
        int ptr = 0;
        int dist = fuel;
        int ctr = 0;
        pq.add(0);
        while(dist < target && !pq.isEmpty()) {
            while(ptr < stations.length && dist >= stations[ptr][0]) {
                pq.offer(stations[ptr][1]);
                ++ptr;
            }
            int next = pq.poll();
            dist += next;
            ++ctr;
        }
        return dist >= target ? ctr : -1;
    }

    public static void main(String[] args) {
        Scanner f = new Scanner("780 130 5 100 200 130 200 330 50 380 50 430 250");
        int tgt = f.nextInt();
        int start = f.nextInt();
        int N = f.nextInt();
        int[][] stations = new int[N][2];
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < 2; ++j) {
                stations[i][j] = f.nextInt();
            }
        }
        int answer = solve(tgt, start, stations);
        System.out.println(answer);
    }
}
