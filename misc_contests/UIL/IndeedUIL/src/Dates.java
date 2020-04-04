
import java.util.*;
import java.io.*;

public class Dates {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("Dates.dat"));
        GregorianCalendar.getInstance().set(1, 1, 1);
        f.close();
    }
}
