
/**
 * Provides the back-end of the GUI for the client wanting to use the database.
 */

import java.awt.*;
import java.util.*;
import java.io.*;
import javax.swing.*;

public class Client {

    private static final int NAME = 2;
    private static final int DIALOGUE = 4;
    private static final int REGIONS = 6;
    private static final int FOODS = 8;
    private static final int HUNGRINESS = 10;
    private static final int FEROCITY = 12;

    private static final String[] possibleValues
            = {"DONE", "GET_NAME", "GET_DIALOGUE",
                "LIST_HUNGRINESS_LESS",
                "LIST_HUNGRINESS_MORE_OR_EQUAL", "LIST_FEROCITY_LESS",
                "LST_FEROCITY_MORE_OR_EQUAL", "LIST_REGION", "LIST_FOOD_AVAILABLE_BY_REGION",
                "LIST_DIRECT_FOOD", "ADD_PET", "MODIFY_PET", "GRAPH_VISUALIZATION"};

    private static final Object[] fields = {
        "Enter the pet parameters, comma delimited.\nFood should be denoted Name-Price.",
        "Name: ", new JTextField(), "Dialogue: ", new JTextField(),
        "Regions: ", new JTextField(), "Foods: ", new JTextField(),
        "Hungriness: ", new JSlider(0, 10, 5), "Ferocity: ", new JSlider(0, 10, 5)};

    static {
        ((JSlider) fields[HUNGRINESS]).setMinorTickSpacing(1);
        ((JSlider) fields[HUNGRINESS]).setMajorTickSpacing(10);
        ((JSlider) fields[HUNGRINESS]).setSnapToTicks(true);
        ((JSlider) fields[HUNGRINESS]).setPaintTicks(true);
        ((JSlider) fields[FEROCITY]).setSnapToTicks(true);
        ((JSlider) fields[FEROCITY]).setMinorTickSpacing(1);
        ((JSlider) fields[FEROCITY]).setMajorTickSpacing(10);
        ((JSlider) fields[FEROCITY]).setPaintTicks(true);
    }

    /**
     * Performs the client actions to allow the user to get GUI actions.
     *
     * @param args null not being run from CMD.
     */
    public static void main(String[] args) {
        PettingZoo pz = new PettingZoo();
        init(pz);
        String command;
        JOptionPane.showMessageDialog(null, "Hi welcome to my database!");

        out:
        do {
            command = (String) JOptionPane.showInputDialog(null,
                    "Enter next command.", "Input",
                    JOptionPane.INFORMATION_MESSAGE, null,
                    possibleValues, possibleValues[0]);

            String repr = "";

            switch (command) {
                case "GET_NAME":
                    String name = JOptionPane.showInputDialog("Enter name: ");
                    repr = pz.getByName(name).toString();
                    JOptionPane.showMessageDialog(null, repr != null ? repr : "Nothing found.");
                    break;
                case "GET_DIALOGUE":
                    String dialogue = JOptionPane.showInputDialog("Enter dialogue: ");
                    repr = pz.getByDialogue(dialogue).toString();
                    JOptionPane.showMessageDialog(null, repr != null ? repr : "Nothing found.");
                    break;
                case "LIST_HUNGRINESS_LESS":
                    int h = Integer.parseInt(JOptionPane.showInputDialog("Enter hungriness value: "));
                    repr = pz.getLessHungry(h).toString();
                    JOptionPane.showMessageDialog(null, repr != null && !repr.matches("[ ]*") && !repr.matches("\\Q[\\E\\Q]\\E") ? repr : "Nothing found.");
                    break;
                case "LIST_HUNGRINESS_MORE_OR_EQUAL":
                    h = Integer.parseInt(JOptionPane.showInputDialog("Enter hungriness value: "));
                    repr = pz.getMoreHungry(h).toString();
                    JOptionPane.showMessageDialog(null, repr != null || !repr.matches("[ ]*") && !repr.matches("\\Q[\\E\\Q]\\E") ? repr : "Nothing found.");
                    break;
                case "LIST_FEROCITY_LESS":
                    int fe = Integer.parseInt(JOptionPane.showInputDialog("Enter ferocity value: "));
                    repr = pz.getLessFerocious(fe).toString();
                    JOptionPane.showMessageDialog(null, repr != null || !repr.matches("[ ]*") && !repr.matches("\\Q[\\E\\Q]\\E") ? repr : "Nothing found.");
                    break;
                case "LIST_FEROCITY_MORE_OR_EQUAL":
                    fe = Integer.parseInt(JOptionPane.showInputDialog("Enter ferocity value: "));
                    repr = pz.getMoreFerocious(fe).toString();
                    JOptionPane.showMessageDialog(null, repr != null || !repr.matches("[ ]*") && !repr.matches("\\Q[\\E\\Q]\\E") ? repr : "Nothing found.");
                    break;
                case "LIST_FOOD_AVAILABLE_BY_REGION":
                    String inp = JOptionPane.showInputDialog("Enter region name: ");
                    repr = pz.getFoodByRegion(inp).toString();
                    JOptionPane.showMessageDialog(null, repr != null || !repr.matches("[ ]*") && !repr.matches("\\Q[\\E\\Q]\\E") ? repr : "Nothing found.");
                    break;
                case "LIST_REGION":
                    String reg = JOptionPane.showInputDialog("Enter region name: ");
                    repr = pz.getRegionals(reg).toString();
                    JOptionPane.showMessageDialog(null, repr != null || !repr.matches("[ ]*") && !repr.matches("\\Q[\\E\\Q]\\E") ? repr : "Nothing found.");
                    break;
                case "LIST_FOOD":
                    String food = JOptionPane.showInputDialog("Enter food name: ");
                    repr = pz.getFood(food).toString();
                    JOptionPane.showMessageDialog(null, repr != null || !repr.matches("[ ]*") && !repr.matches("\\Q[\\E\\Q]\\E") ? repr : "Nothing found.");
                    break;
                case "ADD_PET":
                    manageAdd(pz);
                    break;
                case "MODIFY_PET":
                    manageModify(pz);
                    break;
                case "GRAPH_VISUALIZATION":
                    showWeb(pz);
                case "DONE":
                    break out;
                default:
                    JOptionPane.showMessageDialog(null, "Command not recognized.");
            }
        } while (true);

        JOptionPane.showMessageDialog(null, "Thank you for using my database!");
        dump(pz);
    }

    /**
     * Manages the addition of new pets to the petting zoo.
     *
     * @param pz the petting zoo object.
     */
    public static void manageAdd(PettingZoo pz) {
        init_fields();
        JOptionPane.showConfirmDialog(null, fields, "Add pet", JOptionPane.OK_CANCEL_OPTION);
        String n = ((JTextField) fields[NAME]).getText();
        String d = ((JTextField) fields[DIALOGUE]).getText();
        String rg = ((JTextField) fields[REGIONS]).getText();
        String fd = ((JTextField) fields[FOODS]).getText();
        int h = ((JSlider) fields[HUNGRINESS]).getValue();
        int f = ((JSlider) fields[FEROCITY]).getValue();

        rg = rg.replaceAll("([^,]) ", "$1_");
        rg = rg.replaceAll(",", "");
        fd = fd.replaceAll("([^,]) ", "$1_");
        fd = fd.replaceAll(",", "");

        pz.addPet(n, d, String.format("[%s]", rg), String.format("[%s]", fd), Integer.toString(h), Integer.toString(f));
    }

    /**
     * Manages the modification of the pet objects. Objects are solely
     * identified by their name or dialogue, which is guaranteed to be unique.
     *
     * @param pz the petting zoo object
     */
    public static void manageModify(PettingZoo pz) {
        init_fields();
        JRadioButton b1 = new JRadioButton("Name");
        JRadioButton b2 = new JRadioButton("Dialogue");

        JTextField f1 = new JTextField();
        Object[] arr = {b1, b2, f1};
        boolean b = true;

        while (b & !(b1.isSelected() ^ b2.isSelected())) {
            JOptionPane.showMessageDialog(null, "Select only one option.");
            b = JOptionPane.showConfirmDialog(null, arr, "Select what the identifier is.", JOptionPane.OK_CANCEL_OPTION) == 0;
        }

        Pet p = null;
        if (b1.isSelected()) {
            p = pz.getByName(f1.getText().trim());
        } else if (b2.isSelected()) {
            p = pz.getByDialogue(f1.getText().trim());
        } else {
            JOptionPane.showMessageDialog(null, "Error encountered in input.");
            return;
        }

        ((JTextField) fields[NAME]).setText(p.getName());
        ((JTextField) fields[DIALOGUE]).setText(p.getDialogue());
        ((JTextField) fields[REGIONS]).setText(p.getRegionsFound().toString());
        ((JTextField) fields[FOODS]).setText(p.getDiet().toString());
        ((JSlider) fields[HUNGRINESS]).setValue(p.getHungriness());
        ((JSlider) fields[FEROCITY]).setValue(p.getFerocity());

        JOptionPane.showConfirmDialog(null, fields, "Modify pet", JOptionPane.OK_CANCEL_OPTION);
        String rg = ((JTextField) fields[REGIONS]).getText();
        String fd = ((JTextField) fields[FOODS]).getText();

        rg = rg.replaceAll("([^,]) ", "$1_");
        rg = rg.replaceAll(",", "");
        fd = fd.replaceAll("([^,]) ", "$1_");
        fd = fd.replaceAll(",", "");

        p.setName(((JTextField) fields[NAME]).getText());
        p.setDialogue(((JTextField) fields[DIALOGUE]).getText());
        p.setHungriness(((JSlider) fields[HUNGRINESS]).getValue());
        p.setFerocity(((JSlider) fields[FEROCITY]).getValue());
        p.setDiet(pz.parseList(fd, "FOOD"));
        p.setRegionsFound(pz.parseList(rg, "REGION"));
    }

    /**
     * Dumps the contents of the database to a file.
     *
     * @param pz the petting zoo object to be dumped to the data file.
     */
    public static void dump(PettingZoo pz) {
        try {
            PrintWriter pt = new PrintWriter("data.txt");
            Collection<Pet> pets = pz.getPets().values();

            for (Pet p : pets) {
                pt.println(p.display());
            }

            pt.close();
        } catch (IOException e) {
            System.err.println("File exception caught.");
        }
    }

    /**
     * Loads the contents of the database from a text file.
     *
     * @param pz the petting zoo object to be loaded from the data file.
     */
    public static void init(PettingZoo pz) {
        try {
            Scanner f = new Scanner(new File("data.txt"));
            while (f.hasNextLine()) {
                pz.addPet(f.nextLine());
            }

            f.close();
        } catch (IOException e) {

        }
    }

    /**
     * Re-initializes all the fields before every use.
     */
    public static void init_fields() {
        ((JTextField) fields[NAME]).setText("");
        ((JTextField) fields[NAME]).setEditable(true);
        ((JTextField) fields[DIALOGUE]).setText("");
        ((JTextField) fields[DIALOGUE]).setEditable(true);
        ((JTextField) fields[REGIONS]).setText("");
        ((JTextField) fields[REGIONS]).setEditable(true);
        ((JTextField) fields[FOODS]).setText("");
        ((JTextField) fields[FOODS]).setEditable(true);
        ((JSlider) fields[HUNGRINESS]).setEnabled(true);
        ((JSlider) fields[FEROCITY]).setEnabled(true);
    }

    /**
     * Provides a basic visualization of the petting zoo as a graph.
     *
     * @param pz the petting zoo
     */
    public static void showWeb(PettingZoo pz) {
        populate_vect(pz);
        initFrame();
    }

    public static final ArrayList<ArrayList<Integer>> vect = new ArrayList<>();
    public static HashMap<Pet, Integer> index;
    public static HashMap<Integer, Pet> rev_index;
    public static final int SIZE_X = 800;
    public static final int SIZE_Y = 600;

    public static void populate_vect(PettingZoo pz) {
        index = new HashMap<>();
        rev_index = new HashMap<>();
        Collection<Pet> pets = pz.getPets().values();
        
        int ctr = 0;
        for(Pet pet: pets) {
            index.put(pet, ctr++);
            rev_index.put(ctr-1, pet);
        }
        
        for(int i = 0; i<pets.size(); i++)
            vect.add(new ArrayList<Integer>());
        
        for(Pet p: pets) {
            java.util.List<?> diet = p.getDiet();
            for(Object food: diet) {
                Pet pet = pz.getByName(((Food) food).getName());
                if(pet != null) {
                    vect.get(index.get(p)).add(index.get(pet));
                    vect.get(index.get(pet)).add(index.get(p));
                }
            }
        }
    }
    
    /**
     * Initializes the frame for the graph visual.
     */
    public static void initFrame() {
        JFrame f = new JFrame("Graph Web Visualizer");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Plot p = new Plot(createOutput());

        f.add(p);
        f.pack();
        f.setResizable(false);
        f.setVisible(true);
    }

    /**
     * Provides a rudimentary visual of the graph. The graph shown is a spanning
     * tree of the actual, meaning direct connections between points already
     * directly connected are not shown.
     *
     * @param pz the petting zoo object.
     * @returns points to be plotted on the grid.
     */
    public static ArrayList<IntPair> createOutput() {
        
        ArrayList<IntPair> out = new ArrayList<>();

        Queue<QNode> qe = new ArrayDeque<>();
        boolean[] v = new boolean[vect.size()];

        for (ArrayList<Integer> arr : vect) {
            Collections.sort(arr, new SizeSort());
        }

        int loc_max = -1;
        int max_size = 0;
        for (int i = 0; i < vect.size(); i++) {
            if (vect.get(i).size() > max_size) {
                max_size = vect.get(i).size();
                loc_max = i;
            }
        }

        qe.offer(new QNode(loc_max, SIZE_X / 2, SIZE_Y / 2, 0, 2 * Math.PI, -1));
        
        while (!qe.isEmpty()) {
            QNode curr = qe.poll();
//            System.out.println(curr);
            v[curr.index] = true;
            
            int size = out.size();
            out.add(new IntPair((int) curr.X, (int) curr.Y, rev_index.get(curr.index).getDialogue(), curr.par));
            ArrayList<Integer> next = vect.get(curr.index);

            int sum = 0;
            for (int i : next) {
                if(curr.par != i)
                    sum += vect.get(i).size();
            }

            double angle = curr.start;
            double incr = (curr.end-curr.start)/(sum);
            
            for (int i : next) {
                if (!v[i] && curr.par != i) {
                    // Change to size
                    qe.offer(new QNode(i, 75*Math.cos(angle+incr/2)+curr.X, 75*Math.sin(angle+incr/2)+curr.Y, angle, angle += vect.get(i).size()*incr, size));
                }
            }
        }

        return out;
    }

    /**
     * Defines a comparator to sort the vector to allow efficient adding of items to the queue.
     */
    private static class SizeSort implements Comparator<Integer> {

        /**
         * Provides a comparison of two indexes in the vector for the Collections.sort()
         * call in the createOutput() method.
         * 
         * @param i1 the first index.
         * @param i2 the second index.
         * @returns whether i1 or i2 has more neighbors.
         */
        @Override
        public int compare(Integer i1, Integer i2) {
            return vect.get((int) i2).size() - vect.get((int) i1).size();
        }
    }

    /**
     * Encapsulates the fields of the nodes of the graph when being
     * added to the graph.
     */
    private static class QNode {

        public int index;
        public int par;
        public double X;
        public double Y;
        public double start;
        public double end;

        /**
         * Constructs the queue node.
         * 
         * @param i the index within the vector.
         * @param x the x-coordinate to be plotted.
         * @param y the y-coordinate to be plotted.
         * @param s the start-angle value of the previous node.
         * @param e the end-angle value of the previous node.
         */
        public QNode(int i, double x, double y, double s, double e, int p) {
            index = i;
            X = x;
            Y = y;
            start = s;
            end = e;
            par = p;
        }
        
        @Override
        public String toString() {
            return String.format("NAME: %s NEXT: %d X: %1.0f Y: %1.0f ANGLE_START: %1.3f ANGLE_END: %1.3f PARENT: %s%n", rev_index.get(index).getName(), vect.get(index).size(), X, Y, start, end, par != -1 ? rev_index.get(par).getName() : "NONE");
        }
    }

    /**
     * Defines the integer pair defining the 2D location of the node
     * on the graph.
     */
    private static class IntPair {

        public int X;
        public int Y;
        public String label;
        public int par;

        /**
         * Constructs an integer pair object for the graph construction.
         * 
         * @param x the x-coordinate of the pair.
         * @param y the y-coordinate of the pair.
         */
        public IntPair(int x, int y, String l, int p) {
            X = x;
            Y = y;
            label = l;
            par = p;
        }
    }

    private static class Plot extends JPanel {

        private static final int RADIUS = 20;
        private static ArrayList<IntPair> plot;
        private static Graphics g;

        /**
         * Sets up the plotting facilities.
         * 
         * @param plot defines all of the pairs to plot on the graph.
         */
        public Plot(ArrayList<IntPair> plot) {
            super();
            this.plot = plot;
            setBorder(BorderFactory.createLineBorder(Color.black));
        }

        /**
         * Sets the display frame to 800x600.
         * 
         * @returns a dimension object describing the size of the frame.
         */
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(800, 600);
        }

        /**
         * Physically paints the integer pair on the graph.
         * 
         * @param g the graphics context of the frame, handled in the superclass.
         */
        @Override
        protected void paintComponent(Graphics g) {
            this.g = g;
            super.paintComponent(g);
            for (IntPair ip : plot) {
                if(ip.par != -1) {
                    double ang = 0;
                    
                    try {
                        Math.atan((ip.Y-plot.get(ip.par).Y)/(ip.X-plot.get(ip.par).X));
                    } catch (ArithmeticException e) {
                        ang = Math.PI/2;
                    }
                    double xPt = plot.get(ip.par).X + RADIUS/2*(1 + Math.cos(ang));
                    double yPt = plot.get(ip.par).Y + RADIUS/2*(1 + Math.sin(ang));
                    ((Graphics2D) g).drawLine(
                        plot.get(ip.par).X+RADIUS/2, plot.get(ip.par).Y+RADIUS/2, ip.X+RADIUS/2, ip.Y+RADIUS/2);
                    ((Graphics2D) g).setColor(Color.RED);
                    ((Graphics2D) g).fillOval((int) xPt, (int) yPt, 5, 5);
                }
                
                ((Graphics2D) g).setColor(Color.BLACK);
                ((Graphics2D) g).drawString(ip.label, ip.X, ip.Y);
                ((Graphics2D) g).fillOval(ip.X, ip.Y, RADIUS, RADIUS);
            }
        }
    }
}
