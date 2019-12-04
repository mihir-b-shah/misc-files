
package utils;

// no need to encapsulate any fields, its purely static

import java.util.TreeSet;

public interface StringSimilarity {
    /*
    Algorithm:
    weight longer length matches quadratically
    for example 10000 West Appletree Lane and 12000 West Lane Boulevard 
                has two 1-length matches
    and 10000 West Appletree Lane and 12000 West Appletree Ln has one 2-length
    
    Use longs to efficiently store what strings have been combine
    Only limit is 57 items or less
    */

    long FIRST_BITS = (1L<<57)-1;
    int SHIFT = 57;
    
    static float genScore(String s1, String s2) {
        float score = 0;        
        TreeSet<Long> set = new TreeSet<>();
        
        final String[] s = s1.replaceAll("[^A-Za-z0-9 ]+", "")
                .toLowerCase().trim().split("\\s+");
        final String[] t = s2.replaceAll("[^A-Za-z0-9 ]+", "")
                .toLowerCase().trim().split("\\s+");
      
        // prob can be optimized for space but won't matter
        final int[][] table = new int[s.length][t.length];

        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < t.length; j++) {
                if (s[i].hashCode() != t[j].hashCode()) {
                    continue;
                }
                table[i][j] = (i == 0 || j == 0) ? 1
                                                 : 1 + table[i - 1][j - 1];
                set.add((((1L << table[i][j])-1) << i-table[i][j]+1)
                        +(((long) table[i][j])<<SHIFT));
            }
        }

        FastStack stack = new FastStack(set.size());
        
        int ctr = 0;
        long curr = set.first();
        
        while(ctr < set.size()) {
            for(long lg: set) {
                if((lg&FIRST_BITS|curr&FIRST_BITS) == (curr&FIRST_BITS)) {
                    stack.push(lg);
                }
            }
            while(!stack.empty()) {
                set.remove(stack.pop());
            }
            curr = set.higher(curr);
            ++ctr;
        }
        
        long val;
        for(long lg: set) {
            val = lg >>> SHIFT;
            score += val*val;
        }
        
        return score/(s.length*t.length);
    }
}
