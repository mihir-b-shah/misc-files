
import java.util.*;
import java.io.*;

public class Chances {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("chances.dat"));
        int N = f.nextInt();
        for(int i = 0; i<N; ++i) {
            double a = f.nextDouble();
            double b = f.nextDouble();
            System.out.printf("%.0f%n", (a*b/100));
        }
        f.close();
        
    }
}
