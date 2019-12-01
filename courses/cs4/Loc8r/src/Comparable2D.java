
public interface Comparable2D<T> {
    float getX();
    float getY();
    default long hashValue(){
        return ((long)Float.hashCode(getX())<<32)+Float.hashCode(getY());
    }
}
