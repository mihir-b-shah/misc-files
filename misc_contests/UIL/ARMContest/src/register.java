
import java.util.*;
import java.io.*;

public class register {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("register.dat"));
        while(f.hasNextLine()){
            String[] dat = f.nextLine().split(" ");
            double val = 0;
            double total = Double.parseDouble(dat[0]);
            val += Integer.parseInt(dat[1]) * 20;
            val += Integer.parseInt(dat[2]) * 10;
            val += Integer.parseInt(dat[3]) * 5;
            val += Integer.parseInt(dat[4]) * 1;
            val += Integer.parseInt(dat[5]) * 0.25;
            val += Integer.parseInt(dat[6]) * 0.1;
            val += Integer.parseInt(dat[7]) * 0.05;
            val += Integer.parseInt(dat[8]) * 0.01;
            double diff = total - val;
            String p;
            if(diff < 0){
                diff = -1 * diff;
                p = "Over ";
            } else if(diff > 0){
                p = "Missing ";
            } else {
                p = "Correct";
            }
            if(!p.equals("Correct")){
               p += String.format("$%.2f", diff); 
            }
            System.out.println(p);
            
            
        }
        f.close();
    }
}
