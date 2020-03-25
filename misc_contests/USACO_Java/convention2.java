
/*

ID: mihirsh1
PROB: convention2
LANG: JAVA

*/

import java.util.*;
import java.io.*;

public class convention2 {
    
    public static Comparator<Cow> StartComparator = new Comparator<Cow>() {

        @Override
        public int compare(Cow c1, Cow c2) {
            return (int) (c1.getStart() - c2.getStart());
        }
    };
    
    public static Comparator<Cow> SeniorComparator = new Comparator<Cow>() {

        @Override
        public int compare(Cow c1, Cow c2) {
            return (int) (c1.getSeniority() - c2.getSeniority());
        }
    };
    
    public static class Cow {
        
        private int start;
        private int time;
        private int senior;
        
        public Cow(int st, int t, int se) {
            start = st;
            time = t;
            senior = se;
        }
        
        public int getStart() {return start;}
        public int getTime() {return time;}
        public int getSeniority() {return senior;}
        
        @Override
        public String toString() {
            return String.format("START: %7d TIME: %7d SENIOR %7d", start, time, senior);
        }
    }
    
    public static void main(String[] args) throws Exception {
        
        Scanner f = new Scanner(new File("convention2.in"));
        PrintWriter out = new PrintWriter("convention2.out");
        
        int toCome = f.nextInt();
        Cow[] cows = new Cow[toCome];
        
        for(int i = 0; i<cows.length; i++) {
            cows[i] = new Cow(f.nextInt(), f.nextInt(), i + 1);
        }
        
        Arrays.sort(cows, StartComparator);
        LinkedList<Cow> line = new LinkedList<>();

        int counter = 0;
        int time = 0;
        int maxTime = 0;
        
        while(counter < cows.length) {
            
            time = Math.max(time, cows[counter].getStart());
            
            time += cows[counter].getTime();
            counter++;
   
            while(counter < cows.length && time >= cows[counter].getStart()) {
                line.add(cows[counter]);
                counter++;
            }
            
            Collections.sort(line, SeniorComparator);
            int size = line.size();
            
            for(int i = 0; i<size; i++) {
                Cow ref = line.poll();
                
                if(time - ref.getStart() > maxTime) {
                    maxTime = time - ref.getStart();
                }
                
                time += ref.getTime();
            }
        }
        
        out.println(maxTime);
        out.flush();
        out.close();
        f.close();
    }
}
