
import java.util.*;
import java.io.*;

public class occurring_difference {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("occurring_difference.dat"));
        while(f.hasNextLine()){
            String[] raw = f.nextLine().split(" ");
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            for(String s: raw){
                if(map.containsKey(s)){
                    map.put(s, map.get(s)+1);
                } else {
                    map.put(s, 1);
                }
            }
            int maxo = 0;
            int mino = Integer.MAX_VALUE;
            String min = "";
            String max = "";

            for(String k: map.keySet()){
                if(map.get(k) > maxo){
                    maxo = map.get(k);
                    max = k;
                }
                if(map.get(k) < mino){
                    mino = map.get(k);
                    min = k;
                }
            }
            int ans = Math.abs(Integer.parseInt(max) - Integer.parseInt(min));
            System.out.println("The difference is " + ans);
        }
        f.close();
    }
}
