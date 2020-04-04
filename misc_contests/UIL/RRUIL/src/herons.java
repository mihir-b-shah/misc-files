
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class herons {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner f = new Scanner(new File("herons.dat"));
        int N = f.nextInt();
        f.nextLine();
        for(int i = 0; i<N; ++i) {
            String[] split = f.nextLine().split(",");
            double a = Double.parseDouble(split[0]);
            double b = Double.parseDouble(split[1]);
            double c = Double.parseDouble(split[2]);
            double s = (a+b+c)/2;
            
            System.out.printf("%.3f%n", Math.sqrt(s*(s-a)*(s-b)*(s-c)));
        }
    }
}
