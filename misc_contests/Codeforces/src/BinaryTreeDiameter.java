
import java.util.*;

public class BinaryTreeDiameter {
    
    static long[] tree;

    static long diameter(int src) {
        long aux = tree[src-1];
        if(aux == 0L) return 0L;
        
        int left = (int) (aux&0x7fff_ffff); // doesnt really matter the order
        int right = (int) (aux >>> 32);
        
        long diamLeft = diameter(left);
        long diamRight = diameter(right);
        
        long height1 = diamLeft >>> 32;
        long height2 = diamRight >>> 32;
        
        return ((1+Math.max(height1, height2)) << 32)+Math.max(height1+height2+2,
                         Math.max(diamLeft & 0x7fff_ffff, diamRight & 0x7fff_ffff));
    }
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(System.in);
        int N = f.nextInt();
         
        tree = new long[N];
        
        for(int i = 0; i<N; ++i) {
            int src = f.nextInt();
            int dest = f.nextInt();

            tree[src] <<= 32;
            tree[src] |= dest;
        }
        
        System.out.println(diameter(1));
    }
}
