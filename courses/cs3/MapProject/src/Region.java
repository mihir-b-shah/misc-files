
/**
 * Defines a region with all the inhabitants of the region.
 */
import java.util.*;

public class Region {

    private String name;
    private Set<Food> available;
    private Set<Pet> inhabitants;

    /**
     * Provides a hash value of the region so it can be inserted into a hashmap.
     *
     * @returns the hashcode of the name of the region.
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * 
     * Returns whether the two objects are equal.
     * 
     * @param obj the object to be compared.
     * @returns <code>true</code> if the objects are equal, <code>false</code> if not.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Region ? hashCode() == obj.hashCode() : false;
    }

    /**
     * Constructs a region within the databases and initializes all the
     * parameters.
     *
     * @param n the name of the region.
     */
    public Region(String n) {
        name = n;
        available = new HashSet<>();
        inhabitants = new HashSet<>();
    }

    /**
     * Provides a string representation of the region.s
     *
     * @returns a string representation of the region.
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     * Provides a string representation of the region for utility methods in
     * other classes.
     *
     * @returns the name of the region.
     */
    public String getName() {
        return name;
    }

    /**
     * Provides a list of the foods found in this region.
     *
     * @returns the list of foods available in this region.
     */
    public Set<Food> getFoods() {
        return available;
    }

    /**
     * Provides a list of the animals inhabiting this region.
     *
     * @returns a list of the pets in this region.
     */
    public Set<Pet> getAnimals() {
        return inhabitants;
    }
}
