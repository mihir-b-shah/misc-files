
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Backend {
    
    private static final String[] set;
    private static final ArrayList<Location> locs;
    private static Input input;
    private static final int NUM_RESULTS = 8;
    private static final int NUM_TYPES = 35;
    private static final int NUM_LOCS = 625;
    
    static {       
        set = new String[NUM_TYPES];
        try {
            int ctr = 0;
            BufferedReader br = new BufferedReader(new FileReader("types.txt"));
            String line;
            while((line = br.readLine()) != null) {
                set[ctr++] = line;
            }
        } catch (FileNotFoundException e1) {  
            System.err.println("Fatal exception encountered. File not found.");
        } catch (IOException e2) {     
        }
        
        locs = new ArrayList(NUM_LOCS);
        try {
            BufferedReader br = new BufferedReader(new FileReader("locations.txt"));
            String line;
            while((line = br.readLine()) != null) {
                locs.add(new Location(line));
            }
        } catch (FileNotFoundException e1) {  
            System.err.println("Fatal exception encountered. File not found.");
        } catch (IOException e2) {     
        }
    }
    
    public static void setInput(Input input) {
        Backend.input = input;
    }
    
    public static String[] getTypes() {
        return set;
    }
    
    public static String[] genResults() {
        if(input == null) {
            System.err.println("input not created");
        }
        PriorityQueue<Location> pq = new PriorityQueue<>();
        pq.addAll(locs);
        
        String[] results = new String[NUM_RESULTS];
        for(int i = 0; i<NUM_RESULTS; ++i) {
            results[i] = pq.poll().toString();
        }
        
        return results;
    }
    
    public static float getGlobalLat() {
        return input.getLat();
    }
    
    public static float getGlobalLong() {
        return input.getLong();
    }
}
