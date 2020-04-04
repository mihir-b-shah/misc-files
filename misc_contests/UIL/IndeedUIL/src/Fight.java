
import java.util.*;
import java.io.*;

public class Fight {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("fight.dat"));
        Random r = new Random();
        while(f.hasNextLine()) {
            long seed = f.nextLong();
            r.setSeed(seed);
            int health1 = 200;
            int health2 = 200;
            while(health1 > 0 && health2 > 0 ) {
                health1 -= r.nextInt(200);
                health2 -= r.nextInt(200);
            }
            if(health1 < 0) {
                System.out.println("Thor wins!");
            } else if(health2 < 0) {
                System.out.println("Noobslayer69 wins!");
            }
            f.nextLine();
        }
        f.close();
    }
}
