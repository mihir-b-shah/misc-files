
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Network {
    
    public static class Packet {
        
        public int start;
        public int time;
        
        public Packet(int s, int t) {
            start = s;
            time = t;
        }
        
    }
    
    public static void main(String[] args) {
        
        Scanner s = new Scanner(System.in);
        
        final int buffer_size = s.nextInt();
        final int toCome = s.nextInt();
        
        Queue<Packet> line = new LinkedList<>();  
        Packet[] packets = new Packet[toCome];
        Arrays.sort(packets);
        
        for(int i = 0; i<toCome; i++)
            packets[i] = new Packet(s.nextInt(), s.nextInt());

        int counter = 0;
        int time = 0;
        
        while(counter < packets.length) {       
            if(line.isEmpty()) {
                
                // Forward time to next avaiable
                // Append all terms
                // Lop off the extras
                
                
            }
        }
    }
    
}
