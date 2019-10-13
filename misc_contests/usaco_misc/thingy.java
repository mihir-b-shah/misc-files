
/*
ID: mihirsh1
TASK: butter
LANG: JAVA
*/

import java.io.*;
import java.util.*;

public class butter {

    public static class Pasture {
        public int c = 0;
    }

    public static class VW implements Comparable<VW>{

        public Pasture p;
        public int num;
        public int w;

        public VW(Pasture p, int w, int n) {
            this.p = p;
            this.w = w;
            num = n;
        }
        
        @Override
        public int compareTo(VW o) {
            return w-o.w;
        }
    }

    public static class Graph {

        public ArrayList<ArrayList<VW>> vp;
        public Pasture[] p;
        public static final int INFINITY = 1000000000;
        public int N;

        public Graph(int N) {
            this.N = N;
            p = new Pasture[N];
            vp = new ArrayList<>(N);
            
            for(int i = 0; i<N; i++)
                p[i] = new Pasture();
            
            for(int i = 0; i<N; i++) {
                vp.add(new ArrayList<VW>());
            }
        }
        
        public void addCow(int pa) {
            p[pa].c++;
        }
        
        public void addEdge(int s, int e, int wt) {
            vp.get(s).add(new VW(p[e], wt, e));
            vp.get(e).add(new VW(p[s], wt, s));
        }
        
        public int[] dijkstra(int src) {
            Pasture st = p[src];
            boolean[] v = new boolean[N];
            int[] w = new int[N];
            Arrays.fill(w, INFINITY);
            w[src] = 0;
            
            PriorityQueue<VW> pq = new PriorityQueue<>();
            pq.add(new VW(st, 0, src));
            v[src] = true;
            
            while(!pq.isEmpty()) {
                VW it = pq.poll();
                v[it.num] = true;
                ArrayList<VW> adj = vp.get(it.num);
                int init = w[it.num];
                
                for(VW n: adj) {
                    if(!v[n.num]) {
                        pq.offer(n);
                        if(init + n.w < w[n.num])
                            w[n.num] = init+n.w;
                    }
                }
            }
            
            return w;
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("butter.in"));
        PrintWriter out = new PrintWriter("butter.out");

        int N = f.nextInt();
        int P = f.nextInt();
        int C = f.nextInt();
        
        Graph g = new Graph(P);
        
        for(int i = 0; i<N; i++) {
            g.addCow(f.nextInt()-1);
        }
        
        for(int i = 0; i<C; i++) {
            g.addEdge(f.nextInt()-1, f.nextInt()-1, f.nextInt());
        }
        
        int[][] wts = new int[P][P];
        
        for(int i = 0; i<P; i++) {
            wts[i] = g.dijkstra(i);
        }
        
        /*
        for(int[] wt: wts) {
            for(int e: wt)
                System.out.printf("%4d", e);
            System.out.println();
        }
        */
        
        int opt_val = Graph.INFINITY;
        int[] p = new int[P];
        
        for(int i = 0; i<P; i++) {
            p[i] = g.p[i].c;
        }
        
        for(int i = 0; i<P; i++) {
            int loc_val = 0;
            
            for(int j = 0; j<P; j++) {
                loc_val += wts[i][j]*p[j];
            }
            
            opt_val = Math.min(opt_val, loc_val);
        }
        
        out.println(opt_val);
        out.flush();
        out.close();
        f.close();
    }
}
