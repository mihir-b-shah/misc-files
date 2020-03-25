
import java.util.*;
import java.io.*;

public class herding {
    
    public static void main(String[] args) throws Exception {
        
        Scanner f = new Scanner(new File("herding.in"));
        PrintWriter out = new PrintWriter("herding.out");
        
        int N = f.nextInt();
        int[] array = new int[N];
        
        for(int i = 0; i<N; i++)
            array[i] = f.nextInt();
        
        Arrays.sort(array);
        
        int[] gaps = new int[N-1];
        
        int dw = 0;
        int iw = 0;
        int db = Integer.MAX_VALUE;
        int ib = 0;
        
        for(int i = 0; i<N-1; i++) {
            gaps[i] = array[i+1] - array[i];
            
            if(gaps[i] < db) {
                db = gaps[i];
                ib = i;
            }
            
            if(gaps[i] > dw) {
                dw = gaps[i];
                iw = i;
            }
            
            gaps[i] -= 1;
        }
        
        out.println(db - 1);
        out.println(dw - 1);
        
        out.flush();
        out.close();
        f.close();
    }
    
}
