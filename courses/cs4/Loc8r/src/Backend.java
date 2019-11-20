
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Backend {
    
    private static String[] set;
    private static final int NUM_RESULTS = 8;
    
    static {       
        set = new String[35];
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
    }
    
    public static String[] getTypes() {
        return set;
    }
    
    public static String[] genResults() {
        String[] results = new String[NUM_RESULTS];
        for(int i = 0; i<results.length; ++i) {
            results[i] = "yeah wunshul the bunshul!";
        }
        return results;
    }
}
