
package application;

public class Input {
    private final float latitude;
    private final float longitude;
    private String keyword;
    private final byte aox;
    private final long indexes;
    private final String addr;
    
    public Input(String la, String lo, String kw, int saox, 
            int[] idx, String addr) {
        latitude = Float.parseFloat(la);
        longitude = Float.parseFloat(lo);
        aox = (byte) saox;
        long buf = 0;
        for(int i = 0; i<idx.length; ++i) {
            buf += 1L << idx[i];
        }
        indexes = buf;
        this.addr = addr;
    }
    
    public float getLat() {
        return latitude;
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
