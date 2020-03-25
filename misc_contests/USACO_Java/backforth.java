
/*

ID: mihirsh1
PROB:
LANG: JAVA

*/

import java.util.*;
import java.io.*;

public class backforth {
    
    public static HashSet<Integer> tankvals = new HashSet<>();
    
    public static void main(String[] args) throws Exception {
        
        Scanner f = new Scanner(new File("backforth.in"));
        PrintWriter out = new PrintWriter("backforth.out");
        
        ArrayList<Integer> buckets1 = new ArrayList<>();
        ArrayList<Integer> buckets2 = new ArrayList<>();
        
        tankvals.add(1000);
        
        for(int i = 0; i<10; i++) {
            buckets1.add(f.nextInt());
        }
        
        for(int i = 0; i<10; i++) {
            buckets2.add(f.nextInt());
        }
        
        recur(0, 1000, buckets1, buckets2);
        
        out.println(tankvals.size());
        
        out.flush();
        out.close();
        f.close();
    }
    
    public static void recur(int time, int tank_val, ArrayList<Integer> buckets1, ArrayList<Integer> buckets2) {
        
        try {
            if(time <= 3) {
                if(time % 2 == 0) {

                    for(int i = 0; i<buckets1.size(); i++) {
                        ArrayList<Integer> buck1New = (ArrayList<Integer>) buckets1.clone();
                        ArrayList<Integer> buck2New = (ArrayList<Integer>) buckets2.clone();

                        int add = tank_val - buck1New.get(i);
                        buck2New.add(buck1New.remove(i));

                        recur(time + 1, add, buck1New, buck2New);
                    }

                } else {

                    for(int i = 0; i<buckets2.size(); i++) {
                        ArrayList<Integer> buck1New = (ArrayList<Integer>) buckets1.clone();
                        ArrayList<Integer> buck2New = (ArrayList<Integer>) buckets2.clone();

                        int add = tank_val + buck2New.get(i);
                        buck1New.add(buck2New.remove(i));

                        recur(time + 1, add, buck1New, buck2New);

                    }

                }
            } else {
                if(time == 4)
                    tankvals.add(tank_val);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("TIME: " + time + " TANK_VAL: " + tank_val + " B1: " + buckets1 + " B2: " + buckets2);
        }
    } 
}
