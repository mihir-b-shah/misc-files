
import java.util.*;

public class p149 {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(System.in);
        int N = f.nextInt();
        int[][] points = new int[N][2];
        
        for(int i = 0; i<points.length; ++i) {
            points[i][0] = f.nextInt();
            points[i][1] = f.nextInt();
        }
        
        int maxCt = 0;
        for(int i = 0; i<points.length-1; ++i) {
            int x = points[i][0];
            int y = points[i][1];

            HashMap<Double, Integer> set = new HashMap<>();
            
            for(int j = i+1; j<points.length; ++j) {
                double m = (double) (y-points[j][1])/(x-points[j][0]);
                if(Double.isInfinite(m)) m = Double.POSITIVE_INFINITY;
                if(set.containsKey(m)) {
                    set.put(m, set.get(m)+1);
                } else {
                    set.put(m, 1);
                }
                maxCt = Math.max(maxCt, set.get(m));
            }
        }
        
        System.out.println(maxCt+1);
        
        f.close();
    }
}
