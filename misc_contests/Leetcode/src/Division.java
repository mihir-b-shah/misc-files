
/**
 * Implements division.
 * 
 * @author mihir
 */
public class Division {
    public static long mult(long a, long b) {
        boolean neg = a < 0 ^ b < 0;
        if(a < 0) {
            a = -a;
        }
        if(b < 0) {
            b = -b;
        }
        long accm = 0;
        int ctr = 0;
        while(b > 0) {
            if((b&1) == 1) {
                accm += a << ctr; 
            }
            b >>>= 1;
            ++ctr;
        }
        return neg ? -accm : accm;
    }

    public int div(long a, long b) {
        if(b == 1) {
            return (int) a;
        } else if(a == Integer.MIN_VALUE && b == -1) {
            return Integer.MAX_VALUE;
        } else if(b == -1) {
            return (int) -a;
        }
        boolean neg = a < 0 ^ b < 0;
        if(a < 0) {
            a = -a;
        }
        if(b < 0) {
            b = -b;
        }
        int low = 0;
        int high = 33;
        int med;
        int expr = 0;
        long mult;
        while(a-(mult = mult(b, expr)) >= b) {
            while(low < high-1) {
                med = (low+high) >>> 1;
                if(b << med > a-mult) {
                    high = med;
                } else if(b << med <= a-mult){
                    low = med;
                }
            }
            expr += 1 << low;
            low = 0;
            high = 33;
        }
        return neg ? -expr : expr;
    }
}
