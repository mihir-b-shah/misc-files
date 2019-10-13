
/*

ID: mihirsh1
PROB: hoofball
LANG: JAVA

*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class hoofball {
    
    public static int counter = 0;
    
    public static void main(String[] args) throws Exception {
        
        BufferedReader f = new BufferedReader(new FileReader("2.in"));
        PrintWriter out = new PrintWriter("hoofball.out");
        
        int next = nextInt(f);
        f.skip(1); // REMOVE
        
        int[] line = new int[next];
        
        for(int i = 0; i<line.length; i++) {
            line[i] = nextInt(f);
        }
        
        Arrays.sort(line);
        int[] p = new int[next];
        
        for(int i = 0; i<line.length; i++) {
            
            if(i == 0) {
                p[i] = i+1;
            } else if(i == line.length - 1) {
                p[i] = i - 1;
            } else {
                if(Math.abs(line[i] - line[i-1]) > Math.abs(line[i] - line[i+1])) {
                    p[i] = i + 1;
                } else {
                    p[i] = i - 1;
                }
            }
        }
        
        // Cycles consist of i and i+1
        boolean[] cycle = new boolean[p.length];
        
        for(int i = 0; i<p.length - 1; i++) {
            if(p[i] == i + 1 && p[i + 1] == i)
                cycle[i] = true;
        }
        
        boolean[] isolated = new boolean[p.length];
        
        for(int i = 0; i<isolated.length; i++) {
            for(int j = 1; j<isolated.length; j++) {
                if(i == p[j] && (cycle[j] || !cycle[j-1]))
                    isolated[i] = true;
            }
        }
        
        out.flush();
        out.close();
        f.close();
        
    }
    
    public static int nextInt(BufferedReader f) throws Exception {
        int ch = -2;
        int num = 0;

        do {

            while ((ch = f.read()) != 32 && ch != 10 && ch != 13 && ch != -1) {
                num = num * 10 + (ch - 48);
            }

        } while (ch != 32 && ch != 13 && ch != 10 && ch != -1);

        return num;
    }
}
