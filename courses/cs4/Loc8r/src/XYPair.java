
public class XYPair implements Comparable2D<XYPair> {
    private final float x;
    private final float y;
    private boolean visited;

    public XYPair(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
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
    public String toString() {
        return String.format("<%f,%f>, dist=%f", x, y, 
                Math.sqrt(Math.pow(0.55-x,2)+Math.pow(0.7-y,2)));
    }
}
