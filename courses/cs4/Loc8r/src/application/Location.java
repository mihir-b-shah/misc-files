package application;

import java.util.StringTokenizer;
import utils.Comparable2D;
import static utils.StringSimilarity.*;

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
    private int ratingNum;
    private int ratingDen;    
    
    public Location(int ctr, String line) {
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
    
    public void setFraction(int n, int d) {
        ratingNum = n;
        ratingDen = d;
    }
    
    public float calcRating() {
        return ratingDen == 0 ? 2 : 4f-(float) ratingNum/ratingDen;
    }
    
    public void updateFraction(int n) {
        ratingNum += n;
        ++ratingDen;
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
        return Float.compare(calcRating()+dist()*genScore(name, 
                Backend.getKeyword())*genScore(address, Backend.getAddress()),
                loc.calcRating()+loc.dist()*genScore(loc.getName(), Backend.
                getKeyword())*genScore(loc.getAddress(), Backend.getAddress()));
    }
    
    public static final int compare(Location loc1, Location loc2) {
        return Float.compare(loc1.calcRating()+
                             genScore(loc1.getName(), Backend.getKeyword())*
                             genScore(loc1.getAddress(), Backend.getAddress()),
                             loc2.calcRating()+
                             genScore(loc2.getName(), Backend.getKeyword())*
                             genScore(loc2.getAddress(), Backend.getAddress()));
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
    
    public String dumpString() {
        return String.format("%d%n%d%n", ratingNum, ratingDen);
    }
}
