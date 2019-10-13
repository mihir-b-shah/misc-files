
/**
 * Defines an object representing the food eaten by a pet.
 */
import java.util.*;

public class Food {

    private String name;
    private double price;
    private Set<Pet> eaters;

    /**
     * Provides a hash value of the food so it can be inserted into a hashmap.
     *
     * @returns the hashcode of the name of the food.
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
        return obj instanceof Food ? hashCode() == obj.hashCode() : false;
    }


    /**
     * Constructs a food object.
     *
     * @param n the name of the food.
     * @param p the price of the food.
     */
    public Food(String n, double p) {
        name = n;
        price = p;
        eaters = new HashSet<>();
    }

    /**
     * Returns a string representation of the food.
     *
     * @returns a string representation of the food.
     */
    @Override
    public String toString() {
        return String.format("%s-%.2f", name, price);
    }

    /**
     * Returns the name of the food.
     *
     * @returns the name of the food.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of the food.
     *
     * @returns the price of the food.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns a set of pets that eat this food.
     *
     * @returns a set of the pets that eat this food.
     */
    public Set<Pet> getEaters() {
        return eaters;
    }
}
