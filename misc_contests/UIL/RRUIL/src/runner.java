
import java.util.*;
import java.io.*;

public class runner {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("runner.dat"));
        double miles = 0;
        double time = 0;
        while(f.hasNextInt()){
            int temp1 = f.nextInt();
            int temp2 = f.nextInt();
            miles += temp1;
            time += temp2;
        }
        miles /= 5280.0;
        time /= 60.0;
        System.out.printf("Your speed was %.3f miles per hour.%n", miles/time);
        f.close();
    }
}
