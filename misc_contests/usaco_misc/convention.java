
/*

ID: mihirsh1
PROB: convention
LANG: JAVA

*/

import java.util.*;
import java.io.*;

public class convention {
    
    public static int numCows;
    public static int numBuses;
    public static int capacity;
    public static int[] times;
    public static int[] dp;
    
    public static void main(String[] args) throws Exception {
        
        Scanner f = new Scanner(new File("convention.in"));
        PrintWriter out = new PrintWriter("convention.out");
        
        numCows = f.nextInt();
        numBuses = f.nextInt();
        capacity = f.nextInt();
        
        times = new int[numCows + 1];
        dp = new int[numCows + 1];
        
        for(int i = 0; i<=numCows; i++) {
            dp[i] = -1;
        }

        for(int i = 0; i<numCows; i++) {
            times[i] = f.nextInt();
        }
        
        Arrays.sort(times);
        out.println(findIndex(0, 0));

        out.flush();
        out.close();
        f.close();
    }
    
    public static int findIndex(int bus, int start) {
        
        int[] max = new int[3];
        int counter = -1;
        
        if(bus < numBuses) {
            for (int i = 1; i <= capacity; i++) {
                if(dp[start] == -1) {
                    if (start + i <= numCows) {
                        counter++;

                        int pt = times[i + start];

                        for(int j = i + start; j>start; j--) {

                            if(max[i - 1] < pt - times[j]) {
                                max[i - 1] = pt - times[j];
                            }
                        }

                        max[counter] = findIndex(bus + 1, start + i);
                    }
                } else {
                    return dp[start];
                }        
            }
        } else if(start < times.length) {
            return 0;
        }
        
        return superMax(max, start);
    }
    
    public static int superMax(int[] nums, int start)
    {
        if(nums[0] == 0) {
            return start;
        } else if(nums[1] == 0) {
            return nums[0];
        } else if(nums[2] == 0) {
            return Math.max(nums[0], nums[1]);
        } else {
            return Math.max(Math.max(nums[0], nums[1]), nums[2]);
        }
    }
}
