
import java.util.*;
import java.io.*;

public class moop {
    
    static class Point implements Comparable<Point> {
        int x;
        int y;
        
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public int hashCode() {
            return x^y;
        }
        
        @Override
        public boolean equals(Object o) {
            if(o instanceof Point) {
                return ((Point) o).x == x && ((Point) o).y == y;
            } return false;
        }
        
        @Override
        public String toString() {
            return x + " " + y;
        }
        
        @Override
        public int compareTo(Point other) {
            if(x-y == other.x-other.y) {
                return -(x+y);
            } else {
                return (x-y)-(other.x-other.y);
            }
        }
        
        // and dilate by sqrt(2)
        void changeBasis() {
            final int xT = x; final int yT = y;
            x = yT+xT;
            y = yT-xT;
        }
    }
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("moop.in"));
        PrintWriter out = new PrintWriter("moop.out");

        int N = f.nextInt();
        Point[] points = new Point[N];
        
        for(int i = 0; i<N; ++i) {
            points[i] = new Point(f.nextInt(), f.nextInt());
            points[i].changeBasis();
        }
        
        f.close();
        Arrays.sort(points);
       
        int best = Integer.MIN_VALUE;
        int count = 0;
        
        for(int i = 0; i<N; ++i) {
            if(points[i].x + points[i].y > best) {
                best = points[i].x+points[i].y;
                ++count;
            }
        }
        
        out.println(count);
        out.close();
    }
}
