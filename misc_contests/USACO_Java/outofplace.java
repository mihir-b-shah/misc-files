
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;

public class outofplace {
    
    public static void main(String[] args) throws Exception {
        
        Scanner f = new Scanner(new File("outofplace.in"));
        PrintWriter out = new PrintWriter("outofplace.out");
        HashMap<Integer, Integer> map = new HashMap<>();
        
        int toCome = f.nextInt();
        int[] list = new int[toCome];
        
        for(int i = 0; i<toCome; i++) {
            list[i] = f.nextInt();
        }

        int[] copy = list.clone();
        int counter = 0;
        
        Arrays.sort(copy);
        
        for(int i = 0; i<copy.length; i++) {
            
            if(!map.containsKey(copy[i]))
                map.put(copy[i], i);
        }
        
        for(int i = 0; i<toCome; i++) {
            if(list[i] != copy[i]) {
                int go = map.get(list[i]); // Swap values at go and i
                
                while(list[go] == list[i]) {
                    go++;
                }
                
                int temp = list[i];
                list[i] = list[go];
                list[go] = temp;
                counter++;
                i--;
            }
        }
        
        out.println(counter);
        out.close();
    }
    
}
