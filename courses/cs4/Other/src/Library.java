
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Library {

    private static final String printString;
    private HashMap<String, Book> books;
    private Scanner user;

    static {
        printString = "Hi choose from the following options:\n"
                + "1: Add a book to the library\n"
                + "2: Check out/reserve a book\n"
                + "3: Return a book\n"
                + "4: Display status of a specific book\n"
                + "5: Display status of the library's full collection\n"
                + "6: Save\n"
                + "7: Exit\n";
    }

    public Library(Scanner user) throws FileNotFoundException {
        this.user = user;
        books = new HashMap<>();
        load();
    }

    private void load() throws FileNotFoundException {
        Scanner br = new Scanner(new File("library.txt"));
        br.useDelimiter("\t|\r\n");
        while (br.hasNextLine()) {

            if(!br.hasNext()) {
                break;
            }
            
            Book bk = new Book(br.next(), br.next(), br.nextInt(), br.next(), br.nextDouble());
            bk.setAvailable(br.nextBoolean());
            String patron = br.next();
            bk.setPatron(patron.equals("null") ? null : patron);
            String s = br.next();
            StringBuilder sb = new StringBuilder();

            for (int i = 1; i < s.length() - 1; ++i) {
                if (s.charAt(i) == ']' || s.substring(i,i+2).equals("  ")) {
                    bk.enqueue(sb.toString());
                    sb = new StringBuilder();
                } else {
                    sb.append(s.charAt(i));
                }
            }

            books.put(bk.getTitle(), bk);
        }

        br.close();
    }

    public Scanner getScn() {
        return user;
    }

    public void addBook() {
        System.out.println("Book title: ");
        String title = user.nextLine();
        System.out.println("Book author: ");
        String author = user.nextLine();
        System.out.println("Book pub. year: ");
        int year = Integer.parseInt(user.nextLine());
        System.out.println("Book publisher: ");
        String pub = user.nextLine();
        System.out.println("Book price: ");
        double pr = Double.parseDouble(user.nextLine());
        books.put(title, new Book(title, author, year, pub, pr));
    }

    public void reserveBook() {
        System.out.println("Book title: ");
        String title = user.nextLine();
        System.out.println("Enter your name: ");
        String patron = user.nextLine();
        Book bk = books.get(title);
        if (bk.getAvailable()) {
            bk.setPatron(patron);
            bk.setAvailable(false);
        } else {
            bk.enqueue(patron);
        }
    }

    public void returnBook() {
        System.out.println("Book title: ");
        String title = user.nextLine();
        Book bk = books.get(title);
        if (bk.queueEmpty()) {
            bk.setAvailable(true);
            bk.setPatron(null);
        } else {
            String name = bk.dequeue();
            bk.setPatron(name);
        }
    }

    public void displayBookStatus() {
        System.out.println("Book title: ");
        String title = user.nextLine();
        System.out.printf("Available: %b%n", books.get(title).getAvailable());
    }

    public void displayCollection() {
        Set<Map.Entry<String, Book>> bookset = books.entrySet();
        for (Map.Entry<String, Book> book : bookset) {
            System.out.println(book.getValue());
        }
    }

    public void save() throws FileNotFoundException {
        PrintWriter out = new PrintWriter("library.txt");
        Set<Map.Entry<String, Book>> bookset = books.entrySet();
        for (Map.Entry<String, Book> book : bookset) {
            out.print(book.getValue().write());
        }
        out.close();
    }

    public static void main(String[] args) {
        try {

            Scanner user = new Scanner(System.in);
            Library lib = new Library(user);
            System.out.println();
            System.out.println(printString);

            outer:
            do {

                System.out.print("\nEnter code: ");
                int code = user.nextInt();
                user.nextLine();

                switch (code) {
                    case 1:
                        lib.addBook();
                        break;
                    case 2:
                        lib.reserveBook();
                        break;
                    case 3:
                        lib.returnBook();
                        break;
                    case 4:
                        lib.displayBookStatus();
                        break;
                    case 5:
                        lib.displayCollection();
                        break;
                    case 6:
                        lib.save();
                        break;
                    case 7:
                        lib.save();
                        lib.getScn().close();
                        break outer;
                    default:
                        System.err.printf("%s%n%s%n", "Code not recognized.",
                                printString);
                }
            } while (true);
        } catch (FileNotFoundException e) {
        }
    }
}
