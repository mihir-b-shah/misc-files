
package application;

public class Input {
    private float latitude;
    private float longitude;
    private final String keyword;
    private final long indexes;
    private final String addr;
    private final String errors;
    private int[] ratings;
    private final boolean highKey;
    private final boolean highAddr;
    
    public Input(String la, String lo, String kw,
            int[] idx, String addr, boolean hKey, boolean hAddr) {
        StringBuilder errorMsg = new StringBuilder();
        try {
            latitude = Float.parseFloat(la);
        } catch (Exception e) {
            errorMsg.append("Error encountered in parsing latitude.\n"); 
        } 
        try {
            longitude = Float.parseFloat(lo);
        } catch (Exception e) {
            errorMsg.append("Error encountered in parsing longitude.\n");
        }
        keyword = kw;
        long buf = 0;
        for(int i = 0; i<idx.length; ++i) {
            buf += 1L << idx[i];
        }
        indexes = buf;
        if(indexes == 0) {
            errorMsg.append("Please select 1 or more categories.\n");
        }
        this.addr = addr;
        errors = errorMsg.toString();
        highKey = hKey;
        highAddr = hAddr;
    }
    
    public float getLat() {
        return latitude;
    }
    
    public String getErrors() {
        return errors;
    }
  
    public float getLong() {
        return longitude;
    }
    
    public String getKeyword() {
        return keyword;
    }
 
    public long getIndexes() {
        return indexes;
    }
    
    public String getAddress() {
        return addr;
    }
    
    public boolean getHighKey() {
        return highKey;
    }
    
    public boolean getAddrKey() {
        return highAddr;
    }
    
    public void setRatings(int[] a) {
        ratings = a;
    }
    
    public int[] getRatings() {
        return ratings;
    }
}
