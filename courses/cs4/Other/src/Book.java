
import java.util.ArrayDeque;
import java.util.Queue;

public class Book {

    private final String title;
    private final String author;
    private final int year;
    private final String publisher;
    private final double price;
    private boolean available;
    private String patron;
    private final Queue<String> line;

    public Book(String t, String a, int y, String p, double pr) {
        title = t;
        author = a;
        year = y;
        publisher = p;
        price = pr;
        available = true;
        line = new ArrayDeque<>();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public String getPublisher() {
        return publisher;
    }

    public double getPrice() {
        return price;
    }

    public boolean getAvailable() {
        return available;
    }

    public String getPatron() {
        return patron;
    }

    public Queue<String> getLine() {
        return line;
    }

    public boolean setAvailable(boolean a) {
        available = a;
        return true;
    }

    public boolean setPatron(String p) {
        patron = p;
        return true;
    }

    public boolean enqueue(String p) {
        return line.add(p);
    }

    public String dequeue() {
        return line.poll();
    }

    public String peek() {
        return line.element();
    }
    
    public boolean queueEmpty() {
        return line.isEmpty();
    }

    public String write() {
        return String.format("%s\t%s\t%d\t%s\t%f\t%b\t%s\t%s%n",
                title, author, year, publisher, price, available, patron,
                line.toString());
    }

    @Override
    public String toString() {
        return String.format("Title: %s, Available: %b%n", title, available);
    }
}
