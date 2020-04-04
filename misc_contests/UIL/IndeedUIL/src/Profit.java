
import java.util.*;
import java.io.*;

public class Profit {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("profit.dat"));
        int N = f.nextInt();
        double T = f.nextInt();
        
        System.out.printf("%11s%5s*%10s%6s*%11s%5s%n", "Price", "","Tax", "","Profit","");
        for(int i = 0; i<51; ++i) {
            System.out.print('*');
        }
        System.out.println();
        double[] vals = new double[3];
        
        for(int i = 0; i<N; ++i) {
            int val = f.nextInt();
            vals[0] += val;
            double tax = val*T/100;
            vals[1] += tax;
            vals[2] += val-tax;
            String str = String.format("%, 14.2f *%, 14.2f *%, 14.2f *%n", (double) val, tax, val-val*T/100);
            str = str.replaceAll("(\\s)([\\-0-9])", "$1\\$$2");
            str = str.replace("$-", "-$");
            System.out.print(str);
        }
        for(int i = 0; i<51; ++i) {
            System.out.print('*');
        }
        System.out.println();
        String str = String.format("%, 14.2f *%, 14.2f *%, 14.2f *%n", (double) vals[0], vals[1], vals[2]);
        str = str.replaceAll("(\\s)([\\-0-9])", "$1\\$$2");
        str = str.replace("$-", "-$");
        System.out.println(str);
        
        f.close();
    }
}
