
import java.util.*;

public class D1325 {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(System.in);
        long xor = f.nextLong();
        long sum = f.nextLong();
        
        String xorBits = Long.toBinaryString(xor);
        String sumBits = Long.toBinaryString(sum);

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<sumBits.length()-xorBits.length()+1; ++i) {
            sb.append('0');
        }
        xorBits = sb.toString()+xorBits;
        int ptr = 0;
        while(ptr < xorBits.length()) {
            int sumBit = sumBits.charAt(ptr)-'0';
            int xorBit = xorBits.charAt(ptr)-'0';
            
            
        }
        
        f.close();
    }
}
