
import java.io.*;
import java.util.*;

public class reduce {
    
    public static void main(String[] args) throws Exception
    {
        Scanner f = new Scanner(new File("reduce.in"));
        PrintWriter out = new PrintWriter("reduce.out");
        
        int N = f.nextInt();
        HashSet<Integer> map = new HashSet<>();
        int[][] points = new int[2][N];
        
        for(int i = 0; i<N; i++) {
            points[0][i] = f.nextInt();
            points[1][i] = f.nextInt();
            map.add(points[0][i] + 40000*points[1][i]);
        }
 
        Arrays.sort(points[0]);
        Arrays.sort(points[1]);
        
        // Small X, Large X, Small Y, Large Y
        int min = 100000000;
        
        for(int a = 0; a<=3; a++) {
            for(int b = 0; b<=3; b++) {
                for(int c = 0; c<=3; c++) {
                    for(int d = 0; d<=3; d++) {
                        if(check(a, b, c, d, map)) {
                            if((points[0][a] - points[0][N-1-b])*(points[1][c] - points[1][N-1-d]) < min)
                                min = (points[0][a] - points[0][N-1-b])*(points[1][c] - points[1][N-1-d]);
                        }
                    }
                }
            }
        } 
        
        out.println(min);
        
        out.flush();
        out.close();
        f.close();
    }

    public static boolean check(int a, int b, int c, int d, HashSet<Integer> map) {
        
        /*
        Case 1: a,b,c,d come from diff points
        Case 2: one of (a,c), (a,d), (b,c), (b,d) are points
        Case 3: two are points
        */
        
        if(a+b+c+d<=3)
            return true;
        else if(a+b+c+d<=4 && (map.contains(40000*c+a) || map.contains(40000*d+a) || map.contains(40000*c+b) || map.contains(40000*d+b))) {
            return true;
        } else if (a+b+c+d<=5 && ((map.contains(40000*c+a) || map.contains(40000*d+a)) && (map.contains(40000*c+b) || map.contains(40000*d+b)))) {
            return true;
        }
        
        return false;
    }
}
