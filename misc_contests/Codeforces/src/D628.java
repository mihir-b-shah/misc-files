
import java.util.*;

public class D628 {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(System.in);
        long xor = f.nextLong(); long sum = f.nextLong();
        
        ArrayList<Integer> xorBits = new ArrayList<>();
        ArrayList<Integer> sumBits = new ArrayList<>();
        
        long aux = xor;
        while(aux > 0) {
            xorBits.add((int) (aux&1));
            aux >>>= 1;
        }
        
        aux = sum;
        while(aux > 0) {
            sumBits.add((int) (aux&1));
            aux >>>= 1;
        }
              
        if(xorBits.size() > sumBits.size()) {
            for(int i = 0; i<Math.abs(xorBits.size()-sumBits.size()); ++i) {
                sumBits.add(0);
            }
        } else {
            for(int i = 0; i<Math.abs(xorBits.size()-sumBits.size()); ++i) {
                xorBits.add(0);
            }
        }
        
        Collections.reverse(xorBits);
        Collections.reverse(sumBits);
        
        System.out.println("XOR: " +xorBits);
        System.out.println("SUM: " + sumBits);
    }
}
 