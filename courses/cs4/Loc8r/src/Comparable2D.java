
public interface Comparable2D<T> {
    float getX();
    float getY();
    boolean getVisited();
    void setVisited();
    
    default float getDist(float x, float y) {
        return (float) (Math.pow(x-getX(),2)+Math.pow(y-getY(),2));
    }
    
    default float getXsqDist(float x) {
        return (x-getX())*(x-getX());
    }
    
    default float getYsqDist(float y) {
        return (y-getY())*(y-getY());
    }
    
    default long hashValue(){
        return ((long)Float.hashCode(getX())<<32)+Float.hashCode(getY());
    }
}
