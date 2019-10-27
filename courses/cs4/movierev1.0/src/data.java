import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class data {

    private ArrayList<String> strings_inorder;
    private vector_int vi;
    private HashMap<String, int_wrapper> hmap;

    public data() {
        load();
    }

    private void load() {

        String line = null;
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("moviereviews.txt"));
            strings_inorder = new ArrayList<>();
            vi = new vector_int();
            hmap = new HashMap<>();

            int ctr = 0;

            while((line = br.readLine()) != null) {

                vi.add(line.charAt(0)-'0');

                if(line.length() <= 1) {
                    strings_inorder.add("");
                    vi.add(line.charAt(0)-'0');
                    hmap.put("", new int_wrapper(line.charAt(0)-'0'));
                    continue;
                }

                String substr = line.substring(2);   
                strings_inorder.add(substr);

                StringTokenizer st = new StringTokenizer(substr);
                String ref;
                while(st.hasMoreTokens()) {
                    ref = st.nextToken();
                    if(hmap.containsKey(ref)) {
                        hmap.get(ref).incr(line.charAt(0)-'0');
                    } else {
                        hmap.put(ref, new int_wrapper(line.charAt(0)-'0'));
                    }
                }
            }

            br.close();

        } catch (FileNotFoundException e1) { 
        } catch (IOException e2) { 
        } catch (StringIndexOutOfBoundsException e3) {
            System.err.println(line);
        }
    }

    public float gen_rating(String text) {
        StringTokenizer tk = new StringTokenizer(text);
        float fsum = 0;
        int ctr = 0;
        String token;
        while(tk.hasMoreTokens()) {
            int_wrapper iw = hmap.get(tk.nextToken());
            fsum += iw != null ? iw.avg() : --ctr;
            ++ctr;
        }
        return fsum/ctr;
    }
}