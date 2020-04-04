
import java.util.*;
import java.io.*;

public class socdist {
    
    static class Interval implements Comparable<Interval> {
        long start;
        long end;
        
        Interval(long s, long e) {
            start = s;
            end = e;
        }
        
        @Override
        public int compareTo(Interval o) {
            return Long.compare(start, o.start);
        }
        
        @Override
        public String toString() {
            return String.format("<%d %d>", start, end);
        }
    }
    
    static Interval[] intervals;
    static int N;
    
    static boolean possible(long d) {
        int intervalPtr = 0;
        long previousCow = intervals[0].start;
        int cowPtr = 1;

        while(intervalPtr < intervals.length && cowPtr < N) {
            long tgt = previousCow+d;
            if(tgt <= intervals[intervalPtr].end) {
                previousCow = tgt;
                ++cowPtr;
            } else if(tgt > intervals[intervalPtr].end) {
                do {
                    ++intervalPtr;
                } while(intervalPtr < intervals.length && tgt > intervals[intervalPtr].end);
                if(intervalPtr >= intervals.length) return false;
                previousCow = Math.max(tgt, intervals[intervalPtr].start);
                ++cowPtr;
            }
        }
        return true;
    }
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("socdist.in"));
        PrintWriter out = new PrintWriter("socdist.out");

        int[] data = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        N = data[0];
        int M = data[1];
        
        intervals = new Interval[M];
        for(int i = 0; i<M; ++i) {
            long[] dat = Arrays.stream(br.readLine().split(" ")).mapToLong(Long::parseLong).toArray();
            intervals[i] = new Interval(dat[0], dat[1]);
        }
        
        Arrays.sort(intervals);
        long lower = 1L;
        long upper = 1_000_000_000_000_000_000L;
        long best = 1;
        
        while(lower <= upper) {
            long mid = (lower + upper)/2;
            if(possible(mid)) {
                best = mid;
                lower = mid+1;
            } else {
                upper = mid-1;
            }
        }
        
        br.close();
        out.println(best);
        out.close();
    }
}
