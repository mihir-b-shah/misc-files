package application;


import java.util.StringTokenizer;
import utils.Comparable2D;

public class Location implements Comparable<Location>, Comparable2D<Location> {

    private static final String TAB = "\t";
    private static final String EMPTY_STRING = "";
    private boolean visited;
    private final String name;
    private final String address;
    private final int type;
    private final float longitude;
    private final float latitude;
    private int left;
    private int right;
    
    public Location(String line) {
        StringTokenizer st = new StringTokenizer(line, TAB);
        name = st.nextToken();
        address = st.nextToken();
        type = Backend.getMapIndex(st.nextToken());
        longitude = Float.parseFloat(st.nextToken());
        latitude = Float.parseFloat(st.nextToken());
    }
    
    private float dist() {
        return (float) Math.sqrt(Math.pow(latitude-Backend.getGlobalLat(),2)+
                                Math.pow(longitude-Backend.getGlobalLong(),2));
    }
    
    @Override
    public int getNumLeft() {
        return left;
    }
    
    @Override
    public int getNumRight() {
        return right;
    }
    
    @Override
    public void decrLeft() {
        --left;
    }
    
    @Override
    public void decrRight() {
        --right;
    }
    
    @Override
    public float getY() {
        return latitude;
    }
    
    @Override
    public float getX() {
        return longitude;
    }
    
    @Override
    public boolean getVisited() {
        return visited;
    }
    
    @Override
    public void setVisited() {
        visited = true;
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
    
    public int getTypeIndex() {
        return type;
    }
    
    @Override
    public String toString() {
        return String.format("%20s%s, %s", EMPTY_STRING, name, address);
    }
}
