public class ListSetTester {
	
    public static void main(String[] args) {
        ListSet<String> a = new ListSet<String>();

        System.out.println("ListSet Tester:");
        System.out.println("---------------");

		// Insert code to thoroughly test each of your
        // methods in the LinkedListSet class.
        System.out.println(a);
        // System.out.println(a.isEmpty());

        a.add("WOW ITS THE MUDHANIU!");
        a.add("THARUN D YOU WANT A BELLY RUB?");
        
        ListSet<Integer> b = new ListSet<Integer>();
        
        b.add(4);
        
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        
        System.out.println("---------------");
        System.out.println("Done.");
    }
    
}