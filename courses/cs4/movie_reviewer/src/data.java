
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class data {

    private HashMap<String, int_wrapper> hmap;
    private ArrayList<String> test_str;
    private vector_int test_vi;
    private static final int NUM_DATA = 8600;
    
    public data() {
        this(0);
    }
    
    public data(int skip) {
        load(skip);
    }
    
    private void load(int skip) {
        
        skip = NUM_DATA-skip;
        String line = null;
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("moviereviews.txt"));
            hmap = new HashMap<>();
            final int size = Math.min(NUM_DATA, skip);
            test_str = new ArrayList<>(size);
            test_vi = new vector_int(size);
            
            for(int i = 0; i<NUM_DATA-skip; ++i) {           
                if((line = br.readLine()).length() <= 1) {
                    hmap.put("", new int_wrapper(line.charAt(0)-'0'));
                    continue;
                }

                StringTokenizer st = new StringTokenizer(line.substring(2));
                String ref;
                while(st.hasMoreTokens()) {
                    ref = st.nextToken();
                    if(ref.matches("[A-Za-z]+")) {
                        ref = ref.toLowerCase();
                    } else {
                        continue;
                    }
                    if(hmap.containsKey(ref)) {
                        hmap.get(ref).incr(line.charAt(0)-'0');
                    } else {
                        hmap.put(ref, new int_wrapper(line.charAt(0)-'0'));
                    }
                }
            }
            
            final int size2 = NUM_DATA-skip;
            for(int i = 0; i<size2; ++i) {
                if((line = br.readLine()).length() <= 1) {
                    test_str.add("");
                    test_vi.add(line.charAt(0)-'0');
                } else {
                    test_str.add(line.substring(2));
                    test_vi.add(line.charAt(0)-'0');
                }
            }
            
            br.close();
            
        } catch (FileNotFoundException e1) { 
        } catch (IOException e2) { 
        } catch (StringIndexOutOfBoundsException e3) {
            System.err.println(line);
        }
    }
    
    public float mean_sq_error() {
        final int len = test_str.size();
        float sum = 0f;
        for(int i = 0; i<len; ++i) {
            sum += Math.pow(gen_rating(test_str.get(i))-test_vi.get(i),2);
        }
        sum /= len;
        return sum;
    }
    
    public float gen_rating(String text) {
        text = text.toLowerCase();
        StringTokenizer tk = new StringTokenizer(text);
        float fsum = 0;
        int ctr = 0;
        while(tk.hasMoreTokens()) {
            int_wrapper iw = hmap.get(tk.nextToken());
            fsum += iw != null ? iw.avg() : --ctr;         
            ++ctr;
        }

        return ctr == 0 ? 2f : fsum/ctr;
    }
}
