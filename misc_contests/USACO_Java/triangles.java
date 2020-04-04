
import java.util.*;
import java.io.*;

public class triangles {
    
    private static final int MOD = 1_000_000_007;
    
    private static class Pair {
        int x;
        int y;
        
        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public String toString() {
            return String.format("(%d, %d)", x, y);
        }
    }
    
    private static final int getDiffSum(ArrayList<Pair> vals, int yval) {
        int sum = 0;
        for(int i = 0; i<vals.size(); ++i) {
            sum += Math.abs(vals.get(i).y-yval);
            sum %= MOD;
        }
        return sum;
    }

    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("triangles.in"));
        
        int N = f.nextInt();
        HashMap<Integer, ArrayList<Pair>> map = new HashMap<>();
        ArrayList<Pair> sortY = new ArrayList<>(N);
        
        for(int i = 0; i<N; ++i) {
            Pair p = new Pair(f.nextInt(), f.nextInt());
            if(map.containsKey(p.x)) {
                map.get(p.x).add(p);
            } else {
                ArrayList<Pair> pairs = new ArrayList<>();
                pairs.add(p);
                map.put(p.x, pairs);
            }
            sortY.add(p);
        }

        Comparator<Pair> compY = (p1,p2)->p1.y == p2.y ? p1.x-p2.x : p1.y-p2.y;
        Collections.sort(sortY, compY);
        
        System.out.println(map);
        System.out.println(sortY);
        
        int ptr = 0;
        int priorPtr;
        int sum = 0;
        
        while(ptr < sortY.size()) {
            priorPtr = ptr;
            do {
                int searchX = sortY.get(ptr).x;
                ArrayList<Pair> pairs = map.get(searchX);
                if(pairs == null) {
                    ++ptr;
                    continue;
                }
                int ysums = getDiffSum(pairs, sortY.get(ptr).y);
                int xsums = 0;
                int auxPtr = priorPtr;
                while(auxPtr < sortY.size() && sortY.get(auxPtr).y == sortY.get(priorPtr).y) {
                    xsums += Math.abs(sortY.get(auxPtr).x-sortY.get(ptr).x);
                    xsums %= MOD;
                    ++auxPtr;
                }
                ++ptr;
                sum += (xsums*ysums) % MOD;
                sum %= MOD;
            } while(ptr < sortY.size() && sortY.get(ptr).y == sortY.get(ptr-1).y);
        }
        
        PrintWriter out = new PrintWriter("triangles.out");
        out.println(sum);
        out.flush();
        out.close();
    }
}
