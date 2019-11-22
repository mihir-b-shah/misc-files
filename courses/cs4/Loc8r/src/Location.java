
import java.util.StringTokenizer;

public class Location implements Comparable<Location> {

    private static final String TAB = "\t";
    private static final String EMPTY_STRING = "";
    private final String name;
    private final String address;
    private final String type;
    private final float longitude;
    private final float latitude;
    
    public Location(String line) {
        StringTokenizer st = new StringTokenizer(line, TAB);
        name = st.nextToken();
        address = st.nextToken();
        type = st.nextToken();
        longitude = Float.parseFloat(st.nextToken());
        latitude = Float.parseFloat(st.nextToken());
    }
    
    private float dist() {
        return (float) Math.sqrt(Math.pow(latitude-Backend.getGlobalLat(),2)+
                                Math.pow(longitude-Backend.getGlobalLong(),2));
    }
    
    @Override
    public int compareTo(Location loc) {
        return Float.compare(dist(), loc.dist());
    }
    
    public String getName() {
        return name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return String.format("%20s%s, %s", EMPTY_STRING, name, address);
    }
}
