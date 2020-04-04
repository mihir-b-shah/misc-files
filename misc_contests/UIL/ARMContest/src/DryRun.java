
import java.util.*;
import java.io.*;

public class DryRun {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("pr00.dat"));
        int N = f.nextInt();
        for(int i = 0; i<N; ++i) {
            System.out.println(1+f.nextInt());
        }
        f.close();
    }
}
