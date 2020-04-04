
import java.util.*;
import java.io.*;

public class memory_sort {
    
    public static class Memory implements Comparable<Memory> {
            
        public String name;
        public int size;
        public int speed;
            
        public Memory(String[] data) {
            name = data[0]; 
            size = Integer.parseInt(data[1].substring(0, data[1].length()-2)); 
            if(data[1].charAt(data[1].length()-2) == 'G') {
                size *= 1000;
            }
            speed = Integer.parseInt(data[2].substring(0, data[2].length()-3)); 
            if(data[2].charAt(data[2].length()-3) == 'G') {
                speed *= 1000;
            }
        }
        
        @Override
        public int compareTo(Memory other) {
            if(name.equals(other.name)) {
                if(size == other.size) {
                    return other.speed - speed;
                } else {
                    return other.size - size;
                }
            } else {
                return name.compareTo(other.name);
            }
        }
        
        @Override
        public String toString() {
            return String.format("%s - (%d%s/%d%s)", name, size>=1000?size/1000:size, size>=1000?"GB":"MB", speed>=1000?speed/1000:speed, speed>=1000?"GHz":"MHz");
        }
    }
    
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("memory_sort.dat"));
        ArrayList<Memory> mem = new ArrayList<>();
        while(f.hasNextLine()) {
            String line = f.nextLine();
            String[] split = line.split(",");
            mem.add(new Memory(split));
        }
        Collections.sort(mem);
        for(Memory m: mem) {
            System.out.println(m);
        }
        f.close();
    }
}
