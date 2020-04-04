
import java.util.*;
import java.io.*;

public class bumps {
    
    static int[][] parent;
    static HashMap<Integer, Character> backward;
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("bumps.dat"));
        char dest = f.nextLine().charAt(0);
        
        
        HashMap<Character, Integer> forward = new HashMap<>();
        backward = new HashMap<>();
        int ctr = 0;
        
        ArrayList<Character> ones = new ArrayList<>();
        ArrayList<Character> twos = new ArrayList<>();
        ArrayList<Integer> wts = new ArrayList<>();
        
        
        while(f.hasNextLine()) {
            String[] dat = f.nextLine().split("/");
            char one = dat[0].charAt(0);
            char two = dat[1].charAt(0);
            int wt = Integer.parseInt(dat[1].substring(dat[1].lastIndexOf(' ')+1));
            
            ones.add(one); twos.add(two); wts.add(wt);

            if(forward.containsKey(one)) {
                
            } else {
                forward.put(one, ctr++);
                backward.put(ctr-1, one);
            }
            
            if(forward.containsKey(two)) {
                
            } else {
                forward.put(two, ctr++);
                backward.put(ctr-1, two);
            }
        }
        
        int[][] dp = new int[forward.size()][forward.size()];
        for(int[] arr: dp) {
            Arrays.fill(arr, 1_000_000_000);
        }
        for(int i = 0; i<dp.length; ++i) {
            dp[i][i] = 0;
        }
        parent = new int[forward.size()][forward.size()];
        
        for(int i = 0; i<ones.size(); ++i) {
            int i1 = forward.get(ones.get(i));
            int i2 = forward.get(twos.get(i));
            
            dp[i1][i2] = wts.get(i);
            dp[i2][i1] = wts.get(i);
        }
        
        for(int i = 0; i<parent.length; ++i) {
            for(int j = 0; j<parent.length; ++j) {
                parent[i][j] = i;
            }
        }

        for(int k = 0; k<dp.length; ++k) {
            for(int i = 0; i<dp.length; ++i) {
                for(int j = 0; j<dp.length; ++j) {
                    if(dp[i][j] > dp[i][k]+dp[k][j]) {
                        dp[i][j] = dp[i][k]+dp[k][j];
                        parent[i][j] = parent[k][j];
                    }
                }
            }
        }

        printPath(forward.get('A'), forward.get(dest));
        System.out.println();
        f.close();
    }
    
    static void printPath(int i, int j) {
        if(i != j) {
            printPath(i, parent[i][j]);
        }
        System.out.print(backward.get(j));
    }
}
