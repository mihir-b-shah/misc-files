
import java.util.*;

public class Lambda {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        list.stream().filter(x->{
            System.out.println(x);
            return true;
        });
    }
}
