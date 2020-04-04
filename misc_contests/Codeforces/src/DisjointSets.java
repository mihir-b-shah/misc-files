
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DisjointSets {
    
    static class Node {
        private int rank;
        private final int data;
        private Node parent;
        
        Node(int data) {
            this.data = data;
            parent = this;
        }
    }
    
    static class UFDS {
        private final ArrayList<Node> nodes;
        
        UFDS() {
            nodes = new ArrayList<>();
        }
        
        void make(int v) {
            nodes.add(new Node(v));
        }
        
        Node find(int idx) {
            Node start = nodes.get(idx);
            Node aux = start;
            while(start.parent != start) {
                start = start.parent;
            }
            // path compression
            aux.parent = start;
            return start;
        }
        
        void union(int u, int v) {
            Node uTree = find(u);
            Node vTree = find(v);
            
            // assume that rank(uTree) > rank(vTree)
            if(uTree.rank < vTree.rank) {
                uTree.parent = vTree;
                vTree.rank = 1+uTree.rank;
            } else {
                vTree.parent = uTree;
                uTree.rank = 1+vTree.rank;
            }
        }
    }
   
    static class Point {
        int x;
        int y;
        
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    static class Rectangle {
        int xMin;
        int xMax;
        int yMin;
        int yMax;
        static final int INF = 0x7f7f7f7f;
        
        Rectangle() {
            xMin = INF; 
            xMax = 0;
            yMin = INF;
            yMax = 0;
        }
        
        void update(Point p) {
            xMin = Math.min(xMin, p.x);
            xMax = Math.max(xMax, p.x);
            yMin = Math.min(yMin, p.y);
            yMax = Math.max(yMax, p.y);
        }
        
        int perimeter() {
            return 2*(xMax-xMin+yMax-yMin);
        }
    }
    /*
    public static void main(String[] args) {
        Scanner f = new Scanner(System.in);
        int N = f.nextInt();
        f.nextLine();
        UFDS ufds = new UFDS();
        for(int i = 0; i<N; ++i) {
            ufds.make(i);
        }
        
        while(true) {
            String[] data = f.nextLine().split(" ");
            if(data[0].charAt(0) == 'F') {
                int f1 = Integer.parseInt(data[1]);
                int f2 = Integer.parseInt(data[2]);
                System.out.println(ufds.find(f1-1) == ufds.find(f2-1));
            } else {
                int u = Integer.parseInt(data[1]);
                int v = Integer.parseInt(data[2]);
                ufds.union(u-1, v-1);
            }
        }
    }
    */

    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("fenceplan.in"));
        UFDS ufds = new UFDS();
       
        int N = f.nextInt();
        int M = f.nextInt();
        
        Point[] points = new Point[N];
        for(int i = 0; i<N; ++i) {
            points[i] = new Point(f.nextInt(), f.nextInt());
            ufds.make(i);
        }
        
        for(int i = 0; i<M; ++i) {
            ufds.union(f.nextInt()-1, f.nextInt()-1);
        }
        f.close();
        
        HashMap<Integer, Rectangle> map = new HashMap<>();
        for(int i = 0; i<N; ++i) {
            int res = ufds.find(i).data;
            if(!map.containsKey(res)) {
                map.put(res, new Rectangle());
            }
            map.get(res).update(points[i]);
        }
        
        int max = Rectangle.INF;
        for(Map.Entry<Integer, Rectangle> entry: map.entrySet()) {
            max = Math.min(max, entry.getValue().perimeter());
        }
        
        PrintWriter out = new PrintWriter("fenceplan.out");
        out.println(max);
        out.flush();
        out.close();
    }
}
