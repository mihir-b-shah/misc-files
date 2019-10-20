
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class tf_idf {
    
    private final HashMap<String,Float> tfidf_map;
    private final HashMap<String,int_wrapper> idf_helper;
    
    public tf_idf() {
        tfidf_map = new HashMap<>();
        idf_helper = new HashMap<>();
    }
    
    public float get_import(String word) {
        Float ret = tfidf_map.get(word);
        return ret != null ? ret : 0;
    }
    
    public void reduce(String word) {
        StringTokenizer tk = new StringTokenizer(word);
        HashMap<String, int_wrapper> freq_table = new HashMap<>();
        String iter;
        int_wrapper ref;
        int ctr = 0;
        int tf_reg = 0;
        
        // lets get the tf constant
        while(tk.hasMoreTokens()) {
            if((ref = freq_table.get(iter = tk.nextToken())) != null) {
                ref.incr_reg();
                tf_reg = Math.max(tf_reg, ref.ct_reg());
            } else {
                freq_table.put(iter, new int_wrapper());
            }
            if((ref = idf_helper.get(iter)) != null) {
                ref.incr_reg();
            } else {
                idf_helper.put(iter, new int_wrapper());
            }
            
            ++ctr;
        }
        // divide by (ctr*tf_reg) as the tf-idf normalization constant
        // idf calculation
        
        int tot_words = idf_helper.size();
        final Set<Map.Entry<String,int_wrapper>> iter_set = 
                freq_table.entrySet();
        
        for(Map.Entry<String,int_wrapper> s: iter_set) {
            tfidf_map.put(s.getKey(), s.getValue().avg()/tf_reg*(float) 
                    Math.log(tot_words/idf_helper.get(s.getKey()).ct_reg()));
        }
    }
}