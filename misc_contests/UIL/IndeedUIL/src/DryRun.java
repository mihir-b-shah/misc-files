
import java.util.*;
import java.io.*;

public class DryRun {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("dryrun.dat"));
        int N = f.nextInt();
        f.nextLine();
        
        for(int i = 0; i<N; ++i) {
            System.out.println("Hello " + f.nextLine());
        }
    }
}
