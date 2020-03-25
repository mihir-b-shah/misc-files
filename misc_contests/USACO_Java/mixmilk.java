
/*

ID: mihirsh1
PROB: mixmilk
LANG: JAVA

*/

import java.util.*;
import java.io.*;

public class mixmilk {
    
    public static class Bucket implements Cloneable {

        public int amount;
        public int limit;

        public Bucket(int limit) {
            this.limit = limit;
            amount = 0;
        }

        public Bucket(int limit, int amount) {
            this.limit = limit;
            this.amount = amount;
        }

        public void pourTo(Bucket other) {
            if (other.limit >= amount + other.amount) {
                other.amount += amount;
                amount = 0;

            } else {

                amount -= (other.limit - other.amount);
                other.amount = other.limit;

            }
        }

        @Override
        public String toString() {
            return Integer.toString(amount);
        }

        public boolean equal(Bucket other) {
            return other.limit == this.limit && other.amount == this.amount;
        }
    }
    
    public static void main(String[] args) throws Exception {
        
        Scanner f = new Scanner(new File("mixmilk.in"));
        PrintWriter out = new PrintWriter("mixmilk.out");
        
        Bucket[] buckets = new Bucket[3];
        
        buckets[0] = new Bucket(f.nextInt(), f.nextInt());
        buckets[1] = new Bucket(f.nextInt(), f.nextInt());
        buckets[2] = new Bucket(f.nextInt(), f.nextInt());
        
        for(int i = 0; i<100; i++) {
            buckets[i%3].pourTo(buckets[(i+1)%3]);
        }
        
        out.println(buckets[0]);
        out.println(buckets[1]);
        out.println(buckets[2]);
        
        out.flush();
        out.close();
        f.close();
    }
    
}
