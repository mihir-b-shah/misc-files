
import java.io.*;
import java.util.*;

public class diamond {

    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("diamond.in"));
        PrintWriter out = new PrintWriter("diamond.out");

        int N = f.nextInt();
        int K = f.nextInt();
        
        int[] diamonds = new int[N];
        
        for(int i = 0; i<N; i++)
            diamonds[i] = f.nextInt();
        
        Arrays.sort(diamonds);
        
        int forward = 0;
        int backward = N-1;
        boolean fdone = false;
        boolean bdone = false;
        
        while((fdone && bdone) && forward < backward) {
            if(!fdone && diamonds[forward+1] - diamonds[forward] <= K)
                forward++;
            else
                fdone = true;
            
            if(!bdone && diamonds[backward] - diamonds[backward-1] <= K)
                backward--;
            else
                bdone = true;
        }
            
        out.println(forward + N - backward);
        out.flush();
        out.close();
        f.close();
    }

}
