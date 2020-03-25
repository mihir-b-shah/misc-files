
import java.io.*;
import java.util.*;

public class cowjump {

    public static class Segment implements Comparable {
        public double x1;
        public double x2;
        public double y1;
        public double y2;
        
        public Segment(double xO, double yO, double xT, double yT) {
            x1 = xO;
            x2 = xT;
            y1 = yO;
            y2 = yT;
        }
        
        @Override
        public int compareTo(Object o) {
            Segment s = (Segment) o;
            double mx = (x1+x2)/2;
            double my = (y1+y2)/2;
            double smx = (s.x1+s.x2)/2;
            double smy = (s.y1+s.y2)/2;
            
            return (int) ((smy/smx*Math.sqrt(smx*smx+smy*smy))-(my/mx*Math.sqrt(mx*mx+my*my)));
        }
        
        public boolean determine(Segment s) {
            double x = (s.y1-y1 + x1*(y2-y1)/(x2-x1) - (s.x1)*(s.y2-s.y1)/(s.x2-s.x1))/((y2-y1)/(x2-x2)-(s.y2-s.y1)/(s.x2-s.x1));
            return !((x <= s.x2 && x >= s.x1) && (x <= x2 && x >= x1));
        }
    }
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("cowjump.in"));
        PrintWriter out = new PrintWriter("cowjump.out");

        int N = f.nextInt();
        Segment[] segs = new Segment[N];
        
        for(int i = 0; i<N; i++) {
            double x1 = f.nextDouble();
            double y1 = f.nextDouble();
            double x2 = f.nextDouble();
            double y2 = f.nextDouble();
            
            segs[i] = new Segment(Math.min(x1,x2), Math.min(y1,y2), Math.max(x1,x2), Math.max(y1,y2));
        }
        
        //Arrays.sort(segs);
        boolean get_out = false;
        
        for(int i = 0; i<N; i++) {
            for(int j = 0; j<i; j++) {
                if(segs[j].determine(segs[i])) {
                    get_out = true;
                    out.println(i+1);
                    break;
                }
            }
            
            if(get_out)
                break;
        }
        
        out.flush();
        out.close();
        f.close();
    }
}
