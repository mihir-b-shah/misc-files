
package application;

import utils.KDTree;
import utils.FastMap;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Backend {
    
    private static final FastMap<String> typeIndex;
    private static final String[] set;
    private static final ArrayList<Location> locs;
    private static Input input;
    private static final int NUM_RESULTS = 8;
    private static final int NUM_TYPES = 35;
    private static final int NUM_LOCS = 625;
    
    static {       
        typeIndex = new FastMap<>(1+(NUM_TYPES<<1));
        set = new String[NUM_TYPES];
        try {
            int ctr = 0;
            BufferedReader br = new BufferedReader(new FileReader("types.txt"));
            String line;
            while((line = br.readLine()) != null) {
                set[ctr] = line;
                typeIndex.insert(line, ctr++);
            }
        } catch (FileNotFoundException e1) {  
            System.err.println("Fatal exception encountered. File not found.");
        } catch (IOException e2) {     
        }
        
        locs = new ArrayList<>(NUM_LOCS);
        try {
            BufferedReader br = new BufferedReader(new FileReader("locations.txt"));
            String line;
            int ctr = 0;
            while((line = br.readLine()) != null) {
                locs.add(new Location(line));
            }
        } catch (FileNotFoundException e1) {  
            System.err.println("Fatal exception encountered. File not found.");
        } catch (IOException e2) {     
        }
    }
    
    public static int getMapIndex(String s) {
        return typeIndex.get(s);
    }
    
    public static void setInput(Input input) {
        Backend.input = input;
    }
    
    public static String[] getTypes() {
        return set;
    }
    
    // if the kd tree does not generate complete output then use naive algorithm
    public static List<String> genResults() {
        if(input == null) {
            System.err.println("input not created");
        }
        
        final long indexes = input.getIndexes(); // optimize function calls
        List<Location> in;
        
        KDTree kd = new KDTree(input.getLong(), input.getLat(), in = 
                locs.stream().filter(x->((1L<<x.getTypeIndex())&indexes) != 0)
                .collect(Collectors.toList()), NUM_RESULTS);
        ArrayList<Location> result = kd.getKNN(NUM_RESULTS);
        Stream<Location> process;
        
        if(result.size() < NUM_RESULTS) {
            PriorityQueue<Location> pq = new PriorityQueue<>();
            pq.addAll(in);
            process = Stream.generate(pq::poll).limit(NUM_RESULTS);
        } else {
            process = result.stream();
        }

        return process.map(x->x.toString()).collect(Collectors.toList());
    }
    
    public static float getGlobalLat() {
        return input.getLat();
    }
    
    public static float getGlobalLong() {
        return input.getLong();
    }
    
    public static String getKeyword() {
        return input.getKeyword();
    }
    
    public static String getAddress() {
        return input.getAddress();
    }
}
