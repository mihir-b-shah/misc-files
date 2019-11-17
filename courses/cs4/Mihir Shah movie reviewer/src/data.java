
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class data {

    private ctr_map<String> hmap;
    private ArrayList<String> test_str;
    private vector_int test_vi;
    private final tf_idf tfidf;
    private final short[] weights;
    private static final int NUM_DATA = 8600;
    private static final int SHIFT = 1 << 17;

    public data() {
        this(0);
    }

    public data(int skip) {
        weights = new short[NUM_DATA - skip];
        tfidf = new tf_idf(weights);
        load(skip);
    }

    private int constr_avg(int e) {
        return SHIFT + e;
    }

    private float iw_avg(int e) {
        return ((float) (e & 0x1_ffff)) / (e >>> 17);
    }

    private void load(int skip) {

        skip = NUM_DATA - skip;
        String line = null;
        try {
            final BufferedReader br = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream("moviereviews.txt")));
            final ArrayDeque<String> stack = new ArrayDeque<>();
            hmap = new ctr_map(skip);
            final int size = Math.min(NUM_DATA, skip);
            test_str = new ArrayList<>(size);
            test_vi = new vector_int(size);
            int rating;

            for (int i = 0; i < NUM_DATA - skip; ++i) {
                if ((line = br.readLine()).length() <= 1) {
                    rating = line.charAt(0) - '0';
                    hmap.put("", constr_avg(rating));
                    continue;
                } else {
                    rating = line.charAt(0) - '0';
                }

                String substr = line.substring(2);
                substr = substr.replaceAll("[^ A-Za-z]+", "");
                substr = substr.replaceAll("\\s+", " ");
                substr = substr.toLowerCase();
                StringTokenizer st = new StringTokenizer(substr);
                String ref;
                stack.push(substr);
                tfidf.init(substr);
                int ctr = 0;

                while (st.hasMoreTokens()) {
                    ref = st.nextToken();
                    ++ctr;
                    if (hmap.get(ref) != -1) {
                        hmap.incr_str(ref, rating);
                    } else {
                        hmap.put(ref, constr_avg(rating));
                    }
                }

                weights[i] = (short) (Math.abs(2 - rating) * ctr);
            }

            while (!stack.isEmpty()) {
                tfidf.reduce(stack.pop());
            }

            final int size2 = NUM_DATA - skip;
            int val;
            for (int i = 0; i < size2; ++i) {
                if ((line = br.readLine()).length() <= 1) {
                    test_str.add("");
                    test_vi.add(line.charAt(0) - '0');
                } else {
                    val = line.charAt(0) - '0';
                    line = line.substring(2);
                    line = line.replaceAll("[^ A-Za-z]+", "");
                    line = line.replaceAll("\\s+", " ");
                    test_str.add(line);
                    test_vi.add(val);
                }
            }

            br.close();

        } catch (FileNotFoundException e1) {
        } catch (IOException e2) {
        } catch (StringIndexOutOfBoundsException e3) {
            System.err.println(line);
        }
    }

    @FunctionalInterface
    interface rating_function {

        public float rate(String s);
    }

    public float mean_sq_error(rating_function fr) {
        final int len = test_str.size();
        float sum = 0f;
        float v = 0;
        for (int i = 0; i < len; ++i) {
            v = fr.rate(test_str.get(i));
            sum += Math.pow(v - test_vi.get(i), 2);
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
        while (tk.hasMoreTokens()) {
            int iw = hmap.get(word = tk.nextToken());
            if (iw != -1) {
                fsum += iw_avg(iw);
                ++ctr;
            }
        }

        return ctr == 0 ? 2f : fsum / ctr;
    }

    public float gen_rating_opt1(String text) {
        text = text.toLowerCase();
        StringTokenizer tk = new StringTokenizer(text);
        float fsum = 0;
        int ctr = 0;
        String word;
        float weight = 0;
        float wsum = 0;
        while (tk.hasMoreTokens()) {
            int iw = hmap.get(word = tk.nextToken());
            weight = tfidf.get_import(word);
            wsum += weight;
            if (iw != -1) {
                fsum += (iw_avg(iw) - 2f) * weight;
                ++ctr;
            }
        }

        return 2f + (ctr == 0 ? 0 : fsum / (ctr * wsum));
    }

    public float gen_rating_opt_fi(String text) {
        return 0.0f;
    }
}
