
import java.util.*;
import java.io.*;

public class octagon {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("octagon.dat"));
        int a = f.nextInt();
        f.nextLine();
        for(int i = 0; i < a; i ++){
            double x = Double.parseDouble(f.nextLine());
            System.out.printf("%.2f\n",Math.sqrt(x/2.0/(1+Math.sqrt(2.0))));
        }
        f.close();
    }
}
