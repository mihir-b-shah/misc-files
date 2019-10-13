
import java.util.Scanner;

public class chips {

    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(System.in);
        int N = f.nextInt();
        int odd = 0;
        int even = 0;
        for(int i = 0; i<N; ++i) {
            if((f.nextInt()&0x1)==1) {
                ++odd;
            } else {
                ++even;
            }
        }
        
        System.out.println(Math.min(odd, even));
    }
}
