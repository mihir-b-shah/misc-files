
import java.util.*;
import java.io.*;

public class most_value {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("most_value.dat"));
        while(f.hasNextLine()) {
            int[] vals = Arrays.stream(f.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            HashMap<Integer, Integer> map = new HashMap<>();
            
            int sum = 0;
            for(int i: vals) {
                sum += i;
                if(map.containsKey(i)) {
                    map.put(i, map.get(i)+1);
                } else {
                    map.put(i, 1);
                }
            }
            
            int mode = 0;
            int freq = 0;
            for(Map.Entry<Integer, Integer> entry: map.entrySet()) {
                if(freq < entry.getValue()) {
                    mode = entry.getKey();
                    freq = entry.getValue();
                }     
            }
            
            System.out.printf("%d accounts for %d of the sets total value of %d%n", mode, mode*freq, sum);
        }
        f.close();
    }
}
