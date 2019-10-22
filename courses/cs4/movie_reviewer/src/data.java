
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class data {

    private HashMap<String, int_wrapper> hmap;
    private ArrayList<String> test_str;
    private vector_int test_vi;
    private final tf_idf tfidf;
    private static final int NUM_DATA = 8600;
    
    public data() {
        this(0);
    }
    
    public data(int skip) {
        tfidf = new tf_idf();
        load(skip);
    }
    
    private void load(int skip) {
        
        skip = NUM_DATA-skip;
        String line = null;
        try {
            final BufferedReader br = new BufferedReader(
                    new FileReader("moviereviews.txt"));
            final ArrayDeque<String> stack = new ArrayDeque<>();
            hmap = new HashMap<>();
            final int size = Math.min(NUM_DATA, skip);
            test_str = new ArrayList<>(size);
            test_vi = new vector_int(size);
            
            for(int i = 0; i<NUM_DATA-skip; ++i) {           
                if((line = br.readLine()).length() <= 1) {
                    hmap.put("", new int_wrapper(line.charAt(0)-'0'));
                    continue;
                }

                String substr = line.substring(2);
                substr = substr.replaceAll("[^ A-Za-z]+", "");
                substr = substr.replaceAll("\\s+", " ");
                StringTokenizer st = new StringTokenizer(substr);
                String ref;
                stack.push(substr.toLowerCase());
                tfidf.init(substr);
                while(st.hasMoreTokens()) {
                    ref = st.nextToken();
                    if(hmap.containsKey(ref)) {
                        hmap.get(ref).incr(line.charAt(0)-'0');
                    } else {
                        hmap.put(ref, new int_wrapper(line.charAt(0)-'0'));
                    }
                }
            }
            
            while(!stack.isEmpty()) {
                tfidf.reduce(stack.pop());
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
        float v = 0;
        for(int i = 0; i<len; ++i) {
            v = gen_rating_opt1(test_str.get(i));
            System.out.printf("Test %d:%nString: %s%nPrediction: %f, "
                    + "Actual: %d%nRegrating: %f%n%n", i, test_str.get(i), 
                    v, test_vi.get(i), gen_rating(test_str.get(i)));
            sum += Math.pow(v-test_vi.get(i),2);
        }
        sum /= len;
        return sum;
    }
    
    public float gen_rating(String text) {
        text = text.toLowerCase();
        StringTokenizer tk = new StringTokenizer(text);
        float fsum = 0;
        int ctr = 0;
        String word;
        while(tk.hasMoreTokens()) {
            int_wrapper iw = hmap.get(word = tk.nextToken());
            if(iw != null) {
                fsum += iw.avg();
                ++ctr;
            }
        }

        return ctr == 0 ? 2f : fsum/ctr;
    }
    
    public float gen_rating_opt1(String text) {
        text = text.toLowerCase();
        StringTokenizer tk = new StringTokenizer(text);
        float fsum = 0;
        int ctr = 0;
        String word;
        float weight = 0;
        float wsum = 0;
        while(tk.hasMoreTokens()) {
            int_wrapper iw = hmap.get(word = tk.nextToken());
            weight = tfidf.get_import(word);
            wsum += weight;
            if(iw != null) {
                fsum += (iw.avg()-2f)*weight;
                ++ctr;
            }
        }

        return 2f + (ctr == 0 ? 0 : fsum/(ctr*wsum));
    }
    
    public float gen_rating_opt_fi(String text) {
        return 0.0f;
    }
}
