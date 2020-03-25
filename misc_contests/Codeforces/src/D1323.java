
import java.util.*;

public class D1323 {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(System.in);
        int N = f.nextInt();
        
        int[] bits = new int[N];
       
        for(int i = 0; i<N; ++i) {
            bits[i] = f.nextInt();
        }
        
        int answer = 0;
        for(int pos = 0; pos<25; ++pos) {
            int bitsOn = 0;
            int mask = 1 << pos;
            for(int i = 0; i<N; ++i) {
                if((bits[i]&mask) != 0) {
                    ++bitsOn;
                }
            }
            
            int bitOne = bitsOn*(N-bitsOn)&1;
            int bitTwo = (bitsOn*(bitsOn-1) >>> 1)&1;
            
            int val = bitOne;// + (bitTwo << 1);
            answer += val << pos;
        }
        
        System.out.println(answer);
        f.close();
    }
}
