
/**
 * Defines a pet with all of the *common* attributes.
 */
import java.util.*;

public class Pet {

    private String name;
    private String dialogue;
    private int hungriness;
    private int ferocity;
    private List<?> regions;
    private List<?> diet;

    /**
     * Constructs a pet object from its parameters.
     *
     * @param n name of the pet
     * @param d dialogue of the pet
     * @param region a list of the regions in which it is found.
     * @param food a list of the foods in which it is found.
     * @param h an integer value of its hungriness.
     * @param f an integer value for its ferocity.
     */
    public Pet(String n, String d, List<?> region, List<?> food, int h, int f) {
        name = n;
        dialogue = d;
        hungriness = h;
        ferocity = f;
        regions = region;
        diet = food;
    }
    
    /**
     * Returns the hashcode of the pet,
     * defined as the name of the pet's hashcode.
     * 
     * @returns the hash code of the pet.
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
    
    /**
     * Returns whether the two pets are equal for hash tables.
     * 
     * @param p the object to be compared.
     * @returns <code>true</code> if the two pets are the same, <code>false</code> if not.
     */
    @Override
    public boolean equals(Object p) {
        return p instanceof Pet ? p.hashCode() == hashCode() : false;
    }
    
    /**
     * Sets the name of the pet.
     * 
     * @param name new name of the pet
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Returns the name of the pet.
     *
     * @returns the pet's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the dialogue of the pet.
     * 
     * @param dialogue the dialogue to be set.
     */
    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }
    
    /**
     * Returns the dialogue of the pet.
     *
     * @returns the dialogue of the pet.
     */
    public String getDialogue() {
        return dialogue;
    }

    /**
     * Sets the ferocity value of the pet.
     * 
     * @param f new ferocity value.
     */
    public void setFerocity(int f) {
        ferocity = f;
    }
    
    /**
     * Returns the ferocity of the pet.
     *
     * @returns the ferocity value of the pet.
     */
    public int getFerocity() {
        return ferocity;
    }
    
    /**
     * Sets the hungriness value of the pet.
     * 
     * @param h new hungriness value.
     */
    public void setHungriness(int h) {
        hungriness = h;
    }
    
    /**
     * Returns the hungriness of the pet.
     *
     * @returns the hungriness value of the pet.
     */
    public int getHungriness() {
        return hungriness;
    }

    /**
     * Sets the regions found array.
     * 
     * @param reg the new modified regions array
     */
    public void setRegionsFound(List<?> reg) {
        regions = reg;
    }
    
    /**
     * Returns a list of the regions a pet is found in.
     *
     * @returns the regions the pet is found in.
     */
    public List<?> getRegionsFound() {
        return regions;
    }

    /**
     * Sets the diet of the pet.
     * 
     * @param foods the pet's new diet.
     */
    public void setDiet(List<?> foods) {
        diet = foods;
    }
    
    /**
     * Returns a list of the foods a pet eats.
     *
     * @returns the foods the pet eats.
     */
    public List<?> getDiet() {
        return diet;
    }

    /**
     * Returns the string representation which is printed using the console.
     *
     * @returns the string representation.
     */
    @Override
    public String toString() {
        String s1 = regions.toString();
        String s2 = diet.toString();
        return String.format("%nNAME: %s%nDIALOGUE: %s%nHUNGRINESS: %d%nFEROCITY: %d%nREGIONS: %s%nDIET: %s%n%n", name, dialogue, hungriness, ferocity, s1.toString().substring(1, s1.length() - 1), s2.toString().substring(1, s2.length() - 1));
    }

    /**
     * Returns the string representation which is used to read and write from
     * files.
     *
     * @returns the string representation.
     */
    public String display() {
        String n = name.replaceAll(" ", "_");
        String d = dialogue.replaceAll(" ", "_");
        String rg = regions.toString().replaceAll("([^,]) ", "$1_").replaceAll(",", "");
        String fd = diet.toString().replaceAll("([^,]) ", "$1_").replaceAll(",", "");
        return String.format("%s, %s, %s, %s, %d, %d", n, d, rg, fd, hungriness, ferocity);
    }
}
