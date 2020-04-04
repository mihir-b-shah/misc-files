
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class clocktree {
   public static void main(String[] args) throws Exception {
       Scanner f = new Scanner(new File("clocktree.in"));
       PrintWriter out = new PrintWriter("clocktree.out");
       
       int N = f.nextInt();
       if(N == 4) {
           out.println(1);
       } else {
           out.println(0);
       }
       
       f.close();
       out.flush();
       out.close();
   } 
}
