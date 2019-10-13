
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Creates a binary tree with the following operands: +, -, x, /, and then any
 * numbers.
 *
 * @author Mihir
 */
public class ExpressionTree extends BinaryTree {

    /**
     * Constructs an expression tree with the root node
     *
     * @param rootValue The root node of the expression tree.
     */
    public ExpressionTree(Object rootValue) {
        super(rootValue);
    }

    /**
     * Returns the value of the expression tree.
     *
     * @returns The value of the expression tree.
     */
    @Override
    public Object getValue() {

        // Solve the post order traversal
        List<Object> list = this.postOrder();

        // 2 3 - 7 /
        Stack<String> stack = new Stack<>();
        int pos = 0;

        while (pos < list.size()) {
            String item = (String) list.get(pos++);
            stack.push(item);

            if (item.matches("\\D")) {
                String op = stack.pop();
                String ch2 = stack.pop();
                String ch1 = stack.pop();

                stack.push(evaluate(op, ch1, ch2));
            }
        }

        return stack.pop();
    }

    private String evaluate(String op, String arg1, String arg2) {
        if (op.equals("+")) {
            return Double.toString(Double.parseDouble(arg1) + Double.parseDouble(arg2));
        }
        if (op.equals("-")) {
            return Double.toString(Double.parseDouble(arg1) - Double.parseDouble(arg2));
        }
        if (op.equals("*")) {
            return Double.toString(Double.parseDouble(arg1) * Double.parseDouble(arg2));
        }
        if (op.equals("/")) {
            return Double.toString(Double.parseDouble(arg1) / Double.parseDouble(arg2));
        }

        return "";
    }
}
