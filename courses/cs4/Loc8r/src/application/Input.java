
package application;

public class Input {
    private float latitude;
    private float longitude;
    private final String keyword;
    private final byte aox;
    private final long indexes;
    private final String addr;
    private final String errors;
    
    public Input(String la, String lo, String kw, int saox, 
            int[] idx, String addr) {
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
        aox = (byte) saox;
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
    
    public byte getAOX() {
        return aox;
    }
    
    public long getIndexes() {
        return indexes;
    }
    
    public String getAddress() {
        return addr;
    }
}
