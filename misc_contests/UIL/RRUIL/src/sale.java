
import java.util.*;
import java.io.*;

public class sale {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("sale.dat"));
        double a = f.nextDouble();
        double b = f.nextDouble();
        double c = f.nextDouble();
        double min = a;
        if(b < min){
            min = b;
        }
        if(c < min){
            min = c;
        }
        if(a == min){
            a *= .5;
        }else if(b == min){
            b *= .5;
        }else if(c == min){
            c *= .5;
        }
        double total = (a + b + c) * 1.0825;
        System.out.printf("%.2f%n", total);
        f.close();
    }
}
