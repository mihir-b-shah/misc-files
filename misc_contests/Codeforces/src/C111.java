
import java.util.*;

public class C111 {

    static int H;
    static int W;
    static long FILLED;
    static long CROSS;
    
    static final int INF = 0x7f7f7f7f;
    
    static HashMap<Long, Integer> table;
    
    static long getMask(int pos) {
        /*
        if on the far right
        if on the far left
        else
        */
        if(pos < W) {
            int mod = pos%W;
            if(H > 1 && W > 1 && mod == 0) {
                return CROSS >>> W-pos & ~(1L << W-1);
            } else if(H > 1 && W > 1 && mod == W-1) {
                return CROSS >>> W-pos & ~(1L << W);
            } else {
                return CROSS >>> W-pos;
            }
        } else {
            int mod = pos%W;
            if(H > 1 && W > 1 && mod == 0) {
                return CROSS << pos-W & ~(1L << W-1);
            } else if(H > 1 && W > 1 && mod == W-1) {
                return CROSS << pos-W & ~(1L << W);
            } else {
                return CROSS << pos-W;
            }
        }
    }

    static int recur(int pos, int lvl, long memo) {
        if((memo&FILLED) == FILLED) {
            return lvl+1;
        }
        if(pos < H*W-1) {
            int res1 = recur(pos+1, lvl+1, memo|getMask(pos+1));
            int res2 = recur(pos+1, lvl, memo);
            return Math.min(res1, res2);
        }
        return INF;
    }
    
    static String padBinary(int len, long bits) {
        String str = Long.toBinaryString(bits);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<len-str.length(); ++i) {
            sb.append('0');
        }
        return sb.toString()+str;
    }
    
    public static void main(String[] args) {
        Scanner f = new Scanner(System.in);
        H = f.nextInt();
        W = f.nextInt();
        
        table = new HashMap<>();
        
        FILLED = (1L << H*W)-1;
        CROSS = 1L;
        CROSS |= 0b111L << W-1;
        CROSS |= 1L << 2*W;
        
        int res = recur(-1, -1, 0);
        System.out.println(H*W-res);
        
        f.close(); 
        
    }
}
