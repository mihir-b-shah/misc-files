
public class Input {
    private final float latitude;
    private final float longitude;
    private final String keyword;
    private final byte aox;
    private final int idx;
    private final String addr;
    
    public Input(String la, String lo, String kw, int saox, 
            int idx, String addr) {
        latitude = Float.parseFloat(la);
        longitude = Float.parseFloat(lo);
        keyword = kw;
        aox = (byte) (saox+1);
        this.idx = idx;
        this.addr = addr;
    }
    
    public float getLat() {
        return latitude;
    }
    
    public float getLong() {
        return latitude;
    }
    
    public String getKeyword() {
        return keyword;
    }
    
    public byte getAOX() {
        return aox;
    }
    
    public int getIndex() {
        return idx;
    }
    
    public String getAddress() {
        return addr;
    }
}
