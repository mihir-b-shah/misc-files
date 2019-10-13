/**
 * Defines the petting zoo composed of all the pets in the database.
 */
import java.util.*;
import java.util.regex.Pattern;

public class PettingZoo {

    private final Map<String, Pet> byName;
    private final Map<String, Pet> byDialogue;
    private final Map<Integer, Set<Pet>> byHungriness;
    private final Map<Integer, Set<Pet>> byFerocity;
    private final Map<Region, Set<Pet>> byRegion;
    private final Map<Food, Set<Pet>> byFood;
    private final Map<String, Region> strToRegion;
    private final Map<String, Food> strToFood;
    
    /**
     * Constructs the petting zoo object with empty hash maps.
     */
    public PettingZoo() {
        byName = new HashMap<>();
        byDialogue = new HashMap<>();
        byHungriness = new TreeMap<>();
        byFerocity = new TreeMap<>();
        byRegion = new HashMap<>();
        byFood = new HashMap<>();
        strToRegion = new HashMap<>();
        strToFood = new HashMap<>();
    }

    /**
     * Adds the pet represented in this string to the petting zoo.
     *
     * @param s the string representation of the pet to be parsed.
     */
    public void addPet(String s) {

        Pattern regex = Pattern.compile(", ");
        String[] objs = regex.split(s);
        addPet(objs[0], objs[1], objs[2], objs[3], objs[4], objs[5]);
    }

    /**
     * Adds the pet to the petting zoo, broken up for the console adder
     * to optimize the program.
     * 
     * @param n the name of the pet
     * @param d the dialogue of the pet
     * @param rg the regions in list form of the pet
     * @param fe the foods in list form of the pet
     * @param hu the hungriness value of the pet
     * @param fo the ferocity value of the pet
     */
    public void addPet(String n, String d, String rg, String fe, String hu, String fo) {

        List<Object> region = parseList(rg, "REGION");
        List<Object> foods = parseList(fe, "FOOD");
        int h = Integer.parseInt(hu);
        int f = Integer.parseInt(fo);
        Pet p = new Pet(n, d, region, foods, h, f);

        byName.put(n, p);
        byDialogue.put(d, p);
        if (byHungriness.containsKey(h)) {
            byHungriness.get(h).add(p);
        } else {
            HashSet<Pet> pets = new HashSet<>();
            pets.add(p);
            byHungriness.put(h, pets);
        }

        if (byFerocity.containsKey(f)) {
            byFerocity.get(f).add(p);
        } else {
            HashSet<Pet> pets = new HashSet<>();
            pets.add(p);
            byFerocity.put(f, pets);
        }

        for (Object r : region) {
            if (byRegion.containsKey(r)) {
                byRegion.get(r).add(p);
            } else {
                HashSet<Pet> pets = new HashSet<>();
                pets.add(p);
                byRegion.put((Region) r, pets);

                if (!strToRegion.containsKey(r)) {
                    strToRegion.put(((Region) r).getName(), (Region) r);
                }
            }
            
            ((Region) r).getAnimals().add(p);
            for(Object food: p.getDiet())
                ((Region) r).getFoods().add((Food) food);
        }

        for (Object food : foods) {
            if (byFood.containsKey(food)) {
                byFood.get(food).add(p);
            } else {
                HashSet<Pet> pets = new HashSet<>();
                pets.add(p);
                byFood.put((Food) food, pets);

                if (!strToFood.containsKey(food)) {
                    strToFood.put(((Food) food).getName(), (Food) food);
                }
            }
            
            ((Food) food).getEaters().add(p);
        }
    }

    /**
     * Returns a list represented by the string representation given.
     *
     * @param s the string representation of the list.
     * @param tag whether it is a region or food label.
     * @returns the generated list from this string.
     */
    public List<Object> parseList(String s, String tag) {
        List<Object> list = new ArrayList<>();
        Scanner sc = new Scanner(s.substring(1, s.length()-1));
        sc.useDelimiter(" ");

        if (tag.equals("REGION")) {
            while (sc.hasNext()) {
                String next = sc.next();
                list.add(new Region(next));
            }
        } else {
            while (sc.hasNext()) {
                String next = sc.next();
                list.add(new Food(next.substring(0, next.indexOf('-')), Double.parseDouble(next.substring(next.indexOf('-') + 1))));
            }
        }

        return list;
    }

    /**
     * This returns the petting zoo represented by name.
     *
     * @returns the internal map of the name-> pet.
     */
    public Map<String, Pet> getPets() {
        return byName;
    }

    /**
     * This returns the unique pet represented by a given name.
     *
     * @param name the string representing the pet.
     * @returns the pet identified by this name.
     */
    public Pet getByName(String name) {
        return byName.get(name);
    }

    /**
     * This returns the unique pet represented by a given dialogue.
     *
     * @param dialogue the dialogue representing the pet.
     * @returns the pet represented by this dialogue.
     */
    public Pet getByDialogue(String dialogue) {
        return byDialogue.get(dialogue);
    }

    /**
     * This returns the sets of pets located in each of the hungriness values
     * less than the given value.
     *
     * @param the exclusive ceiling hungriness value.
     * @returns the sets of pets located in each of the hungriness values
     */
    public Collection<Set<Pet>> getLessHungry(int hungriness) {
        return (((TreeMap<Integer, Set<Pet>>) byHungriness).headMap(hungriness).values());
    }

    /**
     * This returns the sets of pets located in each of the hungriness values.
     *
     * @param the inclusive floor hungriness value.
     * @returns the sets of pets located in each of the hungriness values
     */
    public Collection<Set<Pet>> getMoreHungry(int hungriness) {
        return (((TreeMap<Integer, Set<Pet>>) byHungriness).tailMap(hungriness).values());
    }

    /**
     * This returns the sets of pets located in each of the ferocity values.
     *
     * @param the inclusive floor ferocity value.
     * @returns the sets of pets located in each of the ferocity values
     */
    public Collection<Set<Pet>> getLessFerocious(int ferocity) {
        return (((TreeMap<Integer, Set<Pet>>) byFerocity).headMap(ferocity).values());
    }

    /**
     * This returns the sets of pets located in each of the ferocity values.
     *
     * @param the inclusive floor ferocity value.
     * @returns the sets of pets located in each of the ferocity values
     */
    public Collection<Set<Pet>> getMoreFerocious(int ferocity) {
        return (((TreeMap<Integer, Set<Pet>>) byFerocity).tailMap(ferocity).values());
    }

    /**
     * This returns the set of pets located in a given region, identified by
     * name.
     *
     * @param the name of the region.
     * @returns the sets of pets located there.
     */
    public Set<Pet> getRegionals(String region) {
        return byRegion.get(strToRegion.get(region));
    }

    /**
     * This returns the set of pets that eat a given food, identified by name.
     *
     * @param the name of the food.
     * @returns the sets of pets which eat it.
     */
    public Set<Pet> getFood(String food) {
        return byFood.get(strToFood.get(food));
    }

    public Set<Food> getFoodByRegion(String region) {
        return strToRegion.get(region).getFoods();
    }
    
    /**
     * Returns a string representation of the petting zoo.
     *
     * @returns a string representation of all the pets.
     */
    @Override
    public String toString() {
        Collection<Pet> pets = byName.values();
        StringBuilder sb = new StringBuilder();

        for (Pet p : pets) {
            sb.append(p.toString());
        }
        sb.append('\n');

        return sb.toString();
    }
}