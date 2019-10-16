
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class AddressBook {

    private final Scanner user;
    private static final String printString;
    private final HashMap<String, Contact> byName;
    private final TreeMap<String, LinkedList<Contact>> byBirth;

    static {
        printString = "Hi choose from the following options:\n"
                + "1: Create a contact\n"
                + "2: Delete a contact\n"
                + "3: Display all contacts\n"
                + "4: Display a specified contact\n"
                + "5: Display all contacts with birthdays in a specified month\n"
                + "6: Display all contacts with birthdays in a range\n"
                + "7: Save all contacts\n"
                + "8: Save and exit\n";
    }

    public AddressBook(Scanner user) {
        this.user = user;
        byName = new HashMap<>();
        byBirth = new TreeMap<>();
        load();
    }

    public Scanner getScn() {
        return user;
    }

    public void createContact() {
        System.out.println("Enter name: ");
        String name = user.nextLine();
        System.out.println("Enter birthdate in form MM-DD-YYYY: ");
        String birthdate = user.nextLine();
        System.out.println("Enter phone number: ");
        String number = user.nextLine();

        Contact c = new Contact(name, birthdate, number);
        byName.put(name, c);
        if (byBirth.containsKey(birthdate)) {
            byBirth.get(birthdate).add(c);
        } else {
            LinkedList<Contact> a = new LinkedList<>();
            a.add(c);
            byBirth.put(birthdate, a);
        }
    }

    public void deleteContact() {
        // if flag then its a name
        System.out.println("Enter name: ");
        String id = user.nextLine();
        Contact c;
        c = byName.get(id);
        byName.remove(id);
        LinkedList<Contact> a = byBirth.get(c.getBirthdate());
        Iterator<Contact> li = a.iterator();
        Contact it;
        while (li.hasNext()) {
            it = li.next();
            if (it.getName().equals(c.getName())) {
                li.remove();
                break;
            }
        }
    }

    public void printContacts() {
        Collection<Contact> contacts = byName.values();
        System.out.println();
        for (Contact c : contacts) {
            System.out.println(c);
        }
        System.out.println();
    }

    public void printSpecContact() {
        System.out.println("Enter name");
        String id = user.nextLine();
        Contact c = byName.get(id);
        System.out.println(c == null ? "Not found" : c);
    }

    public void printContactsWithin() {
        System.out.println("Enter start MM-DD: ");
        String st = user.nextLine();
        System.out.println("Enter end MM-DD: ");
        String end = user.nextLine();
        System.out.println();
        SortedMap<String, LinkedList<Contact>> map = byBirth.subMap(st, end);
        Set<Entry<String, LinkedList<Contact>>> set = map.entrySet();
        LinkedList<Contact> vals;
        for (Map.Entry<String, LinkedList<Contact>> entry : set) {
            vals = entry.getValue();
            for (Contact c : vals) {
                System.out.println(c);
            }
        }
        System.out.println();
    }

    public void printContactsMonth() {
        System.out.println("Month number, ex. 01 for January, 10 for october");
        String st = user.nextLine();
        String end;
        if (st.equals("09")) {
            end = "10";
        } else {
            end = String.format("%c%c", st.charAt(0), 
                    (char) (st.charAt(1) + 1));
        }
        System.out.println();
        SortedMap<String, LinkedList<Contact>> map
                = byBirth.subMap(st, end);
        Set<Entry<String, LinkedList<Contact>>> set = map.entrySet();
        LinkedList<Contact> vals;
        for (Map.Entry<String, LinkedList<Contact>> entry : set) {
            vals = entry.getValue();
            for (Contact c : vals) {
                System.out.println(c);
            }
        }
        System.out.println();
    }

    public void save() {
        try {
            PrintWriter file = new PrintWriter("save.txt");
            Collection<Contact> contacts = byName.values();
            for (Contact c : contacts) {
                file.write(c.write());
            }
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void load() {
        try {
            BufferedReader file = new BufferedReader(
                    new FileReader("save.txt"));
            String line;
            String[] items;
            while ((line = file.readLine()) != null) {
                items = line.split(",");
                byName.put(items[0], new Contact(items[0], items[1], items[2]));
                if (byBirth.containsKey(items[1])) {
                    byBirth.get(items[1]).add(
                            new Contact(items[0], items[1], items[2]));
                } else {
                    LinkedList<Contact> LL = new LinkedList<>();
                    LL.add(new Contact(items[0], items[1], items[2]));
                    byBirth.put(items[1], LL);
                }
            }
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Scanner user = new Scanner(System.in);
        AddressBook ab = new AddressBook(user);
        System.out.println();
        System.out.println(printString);

        outer:
        do {

            System.out.print("\nEnter code: ");
            int code = user.nextInt();
            user.nextLine();

            switch (code) {
                case 1:
                    ab.createContact();
                    break;
                case 2:
                    ab.deleteContact();
                    break;
                case 3:
                    ab.printContacts();
                    break;
                case 4:
                    ab.printSpecContact();
                    break;
                case 5:
                    ab.printContactsMonth();
                    break;
                case 6:
                    ab.printContactsWithin();
                    break;
                case 7:
                    ab.save();
                    break;
                case 8: 
                    ab.save();
                    ab.getScn().close();
                    break outer;
                default:
                    System.err.printf("%s%n%s%n", "Code not recognized.",
                            printString);
            }
        } while (true);
    }
}
