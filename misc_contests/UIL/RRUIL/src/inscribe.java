
import java.util.*;
import java.io.*;

public class inscribe {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("inscribe.dat"));
        while(f.hasNextInt()) {
            int N = f.nextInt();
            char[][] output = new char[N][N];
            int offset = 0;
            char startChar = (char) ('A' + (N >>> 1));
            while(offset < 1+(N>>>1)) {
                for(int i = offset; i<N-offset; ++i) {
                    for(int j = offset; j<N-offset; ++j) {
                        output[i][j] = startChar;
                    }
                }
                --startChar;
                ++offset;
            }
            for(char[] c: output) {
                for(char d: c) {
                    System.out.print(d);
                }
                System.out.println();
            }
            System.out.println();
        }
        f.close();
    }
}
