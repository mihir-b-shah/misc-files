
/*
ID: mihirsh1
TASK: comehome
LANG: JAVA
*/

import java.util.*;
import java.io.*;

public class comehome {

    public static final int INFINITY = (int) Math.pow(10, 9);
    
    public static class Vertex implements Comparable {
        public int v;
        public int w;
        
        public Vertex(int ve, int we) {
            v = ve;
            w = we;
        }
        
        @Override
        public int compareTo(Object o) {
            Vertex vo = (Vertex) o;
            return w - vo.w;
        }
    }
    
    public static int process(String s) {
        int point = s.lastIndexOf(' ')+1;
        int num = 0;
        char c;
        
        while(point < s.length()) {
            num *= 10;
            c = s.charAt(point);
            num += c - 48;
            point++;
        }
        
        return num;
    }
    
    public static void main(String[] args) throws Exception {
        
        Scanner f = new Scanner(new File("comehome.in"));
        PrintWriter out = new PrintWriter("comehome.out");
        
        HashMap<Character, Integer> encode = new HashMap<>();
        HashMap<Integer, Character> decode = new HashMap<>();
        
        int N = f.nextInt();
        
        Object[][] queue = new Object[N][3];
        
        int count = 0;
        f.nextLine();
        
        for(int i = 0; i<N; i++) {
            String s = f.nextLine();
            char st = s.charAt(0);
            queue[i][0] = st;
            char to = s.charAt(2);
            queue[i][1] = to;
            int weight = process(s);
            queue[i][2] = weight;
            
            if(!encode.containsKey(st)) {
                encode.put(st, count);
                decode.put(count, st);
                count++;
            }
            
            if(!encode.containsKey(to)) {
                encode.put(to, count);
                decode.put(count, to);
                count++;
            }
        }

        int[][] adjMat = new int[encode.size()][encode.size()];
        
        for(int[] a: adjMat)
            Arrays.fill(a, INFINITY);
        
        for(Object[] arr: queue) {
            int src = (int) encode.get((char) arr[0]);
            int dest = (int) encode.get((char) arr[1]);
            int weight = (int) arr[2];
            adjMat[src][dest] = Math.min(adjMat[src][dest], weight);
            adjMat[dest][src] = Math.min(adjMat[src][dest], weight);
        }

        for(int i = 0; i<adjMat.length; i++) {
            adjMat[i][i] = 0;
        }
        
        for(int k = 0; k<adjMat.length; k++) {
            for(int i = 0; i<adjMat.length; i++) {
                for(int j = 0; j<adjMat.length; j++) {
                    adjMat[i][j] = Math.min(adjMat[i][j], adjMat[i][k] + adjMat[k][j]);
                }
            }
        }
        
        int[] distances = adjMat[encode.get('Z')];
        int min = INFINITY;
        char min_past = 'a';
        
        for(int i = 0; i<distances.length; i++) {
            int val = distances[i];
            char mapping = decode.get(i);
            
            // System.out.println(mapping + " " + val);
            
            if(mapping < 'Z') {
                if(val < min) {
                    min = val;
                    min_past = mapping;
                }
            }
        }

        out.println(min_past + " " + min);
        out.flush();
        out.close();
        f.close();
    }
}

