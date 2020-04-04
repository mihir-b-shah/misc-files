
import java.util.*;

public class Test {

     public static void main(String []args) throws Exception {
         Scanner f = new Scanner(System.in);
         while(f.hasNextLine()) {
             String line = f.nextLine();
             if(line.matches("\\s*[+\\-]?(\\.\\d+)?(e\\d+)?\\s*") || 
                     line.matches("\\s*[+\\-]?(\\d+)(\\.\\d+)?(e\\d+)?\\s*")) {
                System.out.println(true);
                continue;
             }
             System.out.println(false);
         }
     }
}