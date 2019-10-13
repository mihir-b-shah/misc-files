
import java.io.*;
import java.util.*;

public class fairphoto {

    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("fairphoto.in"));
        PrintWriter out = new PrintWriter("fairphoto.out");

        int numCows = f.nextInt();
        TreeMap<Integer, Boolean> map = new TreeMap<>();
        int[] index = new int[numCows];
        
        for(int i = 0; i<numCows; i++) {
            int x = f.nextInt();
            map.put(x, f.next("\\w").equals("S"));
            index[i] = x;
        }
        
        Collection<Boolean> set = map.values();
        int[] prefix_sums = new int[numCows+1];
        
        Iterator<Boolean> it = set.iterator();
        int count = 1;
        
        // ALgorithm is N^2 lg N
        
        while(it.hasNext()) {
            if(it.next())
                prefix_sums[count] = 1 + prefix_sums[count-1];
            else
                prefix_sums[count] = prefix_sums[count-1];
            count++;
        }
        
        int max = 0;
        for(int i = 0; i<numCows-1; i++) {
            for(int j = i+1; j<numCows; j++) {
                int num = prefix_sums[j+1] - prefix_sums[i];
                if(num*2 <= j+1-i) {
                    if(max < index[j] - index[i+1])
                        max = index[j] - index[i+1];
                }
            }
        }
        
        out.println(max);
        out.flush();
        out.close();
        f.close();
    }

}
