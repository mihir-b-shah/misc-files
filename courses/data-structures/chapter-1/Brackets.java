
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingDeque;

public class Brackets {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        String str = s.next();
        int res = bracketMatch(str);
        
        if(res == -1) {
            System.out.println("Success");
        } else {
            System.out.println(res + 1);
        }
    }

    public static int bracketMatch(String s) {
        LinkedBlockingDeque<Integer> stack = new LinkedBlockingDeque();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c != '(' && c != ')' && c != '[' && c != ']' && c != '{' && c != '}') {
                continue;
            }

            if (!stack.isEmpty() && s.charAt(stack.peekFirst()) == returnOpp(c)) {
                stack.pop();
            } else if(c == ')' || c == ']' || c == '}') {
                return i;
            } else {
                stack.push(i);
            }
        }

        if (stack.isEmpty())
            return -1;
        return stack.peekLast();    
            
    }

    public static char returnOpp(char c) {
        if (c == '[') {
            return ']';
        } else if (c == ']') {
            return '[';
        } else if (c == '(') {
            return ')';
        } else if (c == ')') {
            return '(';
        } else if (c == '{') {
            return '}';
        } else if (c == '}') {
            return '{';
        }

        return 0;
    }

}
