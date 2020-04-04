
import java.util.*;
import java.io.*;

public class forest {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("forest.dat"));
        int N = f.nextInt();
        int[] heights = new int[N];
        for(int i = 0; i<N; ++i) {
            heights[i] = f.nextInt();
        }
        int maxHeight = Arrays.stream(heights).max().getAsInt();
        char[][] print = new char[2*maxHeight+3][7*N-1];
        
        int ctr = 0;
        for(int height: heights) {
            print[print.length-1][2+7*ctr] = '|';
            print[print.length-1][3+7*ctr] = '|';
            print[print.length-2][2+7*ctr] = '|';
            print[print.length-2][3+7*ctr] = '|';
            
            print[print.length-3][1+7*ctr] = '_';
            print[print.length-3][4+7*ctr] = '_';
            print[print.length-4][1+7*ctr] = '_';
            print[print.length-4][4+7*ctr] = '_';
            
            ++ctr;        
        }
        
        for(char[] c: print) {
            System.out.println(new String(c));
        }
        
        f.close();
    }
}
