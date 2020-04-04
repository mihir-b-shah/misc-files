
import java.util.*;
import java.io.*;

public class food_supply {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("food_supply.dat"));
        while(f.hasNextLine()){
            String input = f.nextLine();
            String[] dat = input.split(",");
            int maxW = Integer.parseInt(dat[0]);
            ArrayList<Integer> bar = new ArrayList<Integer>();
            for(int i = 1; i<dat.length; ++i) {
                bar.add(Integer.parseInt(dat[i]));
            }
            
            int times = 0;
            while(!bar.isEmpty()) {
                int minSum = 1_000_000_000;
                int bitmask = 0;
                for(int i = 0; i<1<<bar.size(); ++i) {
                    int aux = i;
                    int ctr = bar.size()-1;
                    int sum = 0;
                    while(aux>0) {
                        if((aux&1) == 1) {
                            sum += bar.get(ctr);
                        }
                        --ctr;
                        aux >>>= 1;
                    }
                    if(sum >= maxW && sum < minSum) {
                        bitmask = i;
                        minSum = sum;
                    }
                }
                ArrayList<Integer> aux = new ArrayList<>();
                int ctr = bar.size()-1;
                while(bitmask > 0) 
                {           
                    if((bitmask & 1) == 0) {
                        aux.add(bar.get(ctr));
                    }
                    bitmask >>>= 1;
                    --ctr;
                }
                bar = aux;
                ++times;
            }
            System.out.println(times);
        }
        f.close();
    }
}
