package misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;
import javax.swing.JOptionPane;
import java.util.HashSet;

public class RootFinder3 {

    public static final String[] choices = {"e^", "sin", "cos", "x^", "ln", "k", "(", ")", "x", "OPERATOR", "CONST", "DONE"};
    public static final String[] consts = {"e", "π"};
    public static final String[] operators = {"+", "-", "*", "/"};
    public static HashSet<Double> roots = new HashSet<>();
    public static int size;
    public static int counter = 0;
    public static int mostRecent = -1;

    public static class Function {
        // How do I transcribe the function ?!

        public double returnValue(double x) {
            double y = 0;
            return x;
        }
    }

    public static void main(String[] args) {

        // NEXT ISSUE: MANAGE BRACKET DELETION OF TERMS
        ArrayList<Double> function = new ArrayList(Arrays.asList(14.0, 32.0, 4.0, 6.0, 6.0, 8.0, 30.0, 12.0, 32.0, 21.0, 7.0, 32.0, 6.0, 8.0, 30.0, 20.0, 7.0, 7.0, 30.0, 1.0, 6.0, 3.0, 6.0, 13.0, 7.0, 31.0, 3.0, 6.0, 12.0, 31.0, 11.0, 7.0, 7.0, 31.0, 3.0, 6.0, 14.0, 30.0, 18.0, 7.0));
        interpret(function);

        size = function.size();

        int[] brackets = bracketFinder(function);
        ArrayList<Integer> bracks = new ArrayList(size);

        for (int i = 0; i < brackets.length; i++) {
            bracks.add(i, brackets[i]);
        }

        evaluator(function, 2, bracks, 0, brackets.length - 1, 0);
        //System.out.println(function);

        System.exit(0);
    }

    public static int[] bracketFinder(ArrayList<Double> function) {
        int[] brackets = new int[size];

        for (int i = 0; i < function.size(); i++) {
            if (function.get(i) == 6) {
                counter++;
                brackets[i] = counter;
                brackets[bracketMatchLoc(function, i, function.size() - 1)] = counter;
            }
        }

        return brackets;
    }

    public static int bracketMatchLoc(ArrayList<Double> s, int stIndex, int eIndex) {

        int six = 0;
        boolean start = true;

        for (int i = stIndex; i <= eIndex; i++) {
            if (doubleEqual(s.get(i), 6)) {
                six++;
                start = false;
            } else if (doubleEqual(s.get(i), 7)) {
                six--;
            }

            if (six == 0 && !start) {
                return i;
            }

        }

        return -1;
    }

    public static ArrayList<Double> enterFX() {
        JOptionPane.showMessageDialog(null, "Welcome to Functional Root Finder 1.0.");

        String function = "y= ";
        ArrayList<Double> functions = new ArrayList<>();

        int index = -1;

        while (index != choices.length - 1) {

            index = JOptionPane.showOptionDialog(null, function, "Input", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);
            String number = "";

            if (index == 5) {
                number = JOptionPane.showInputDialog(null, "Enter number.");
                function += number;
                int len = (int) Math.log10(Integer.parseInt(number)) + 1;
                functions.add((double) ((int) Math.pow(10, len) + Integer.parseInt(number)));
            } else if (index == choices.length - 2) {
                int x = JOptionPane.showOptionDialog(null, function, "Input", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, consts, consts[0]);
                function += consts[x];
                functions.add((double) (20 + x));
            } else if (index == choices.length - 3) {
                int x = JOptionPane.showOptionDialog(null, function, "Input", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, operators, operators[0]);
                function += operators[x];
                functions.add((double) (30 + x));
            } else if (index >= 0 && index <= 4) {
                function += choices[index];
                functions.add((double) index);
                functions.add(6.0);
            } else {
                function += choices[index];
                functions.add((double) index);
            }
        }

        System.out.println(function);

        if (!bracketMatch(function)) {
            JOptionPane.showMessageDialog(null, "SYNTAX ERROR: BRACKETS NOT BALANCED");
            System.exit(1);
        }

        functions.remove(functions.size() - 1);
        size = functions.size();

        return functions;
    }

    public static boolean bracketMatch(String s) {
        LinkedBlockingDeque<Character> stack = new LinkedBlockingDeque();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c != '(' && c != ')') {
                continue;
            }

            char cN = returnOpp(c);

            if (!stack.isEmpty() && stack.peekFirst() == cN) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }

        return stack.isEmpty();
    }

    public static char returnOpp(char c) {
        if (c == '(') {
            return ')';
        } else {
            return '(';
        }
    }

    public static void evaluator(ArrayList<Double> parts, double x, ArrayList<Integer> brackets, int stIndex, int eIndex, int level) {

        counter = 0;
        mostRecent = -1;

        for (int i = stIndex; i <= eIndex - size + parts.size(); i++) {

            if (brackets.get(i) != 0) {
                int eInd = findNum(brackets, i, brackets.get(i));

                if (eInd == -1) {
                    continue;
                }

                evaluator(parts, x, brackets, i + 1, eInd - 1, level + 1);

                if (level == 0) {
                    size = parts.size();
                }

                counter++;
            }
        }

        if (counter == 0) {
            evalExp(parts, brackets, x, stIndex, eIndex);
            interpret(parts);
            printAligned(parts, brackets);
            
            if (mostRecent != -1) {
                parts.remove(mostRecent + 1);
                brackets.remove(mostRecent + 1);
                parts.remove(mostRecent - 1);
                brackets.remove(mostRecent - 1);

            }
        }

        interpret(parts);
        printAligned(parts, brackets);
    }

    public static void printAligned(ArrayList<Double> parts, ArrayList<Integer> brackets)
    {
        for(int i = 0; i<brackets.size(); i++)
        {
            //System.out.println(parts.get(i) + " " + brackets.get(i));
        }
    }
    
    public static void interpret(ArrayList<Double> parts) {
        for (int i = 0; i < parts.size(); i++) {
            if (parts.get(i).intValue() / 10 == 0) {
                System.out.print(choices[(parts.get(i).intValue())] + " ");
            } else if (parts.get(i).intValue() / 10 == 1) {
                double len = (double) ((int) Math.log10(parts.get(i)));
                System.out.print((parts.get(i) - Math.pow(10, len)) + " ");
            } else if (parts.get(i).intValue() / 10 == 2) {
                System.out.print(consts[(int) (parts.get(i) - 20)] + " ");
            } else if (parts.get(i).intValue() / 10 == 3) {
                System.out.print(operators[(int) (parts.get(i) - 30)] + " ");
            }
        }

        System.out.println();
    }

    public static void evalExp(ArrayList<Double> parts, ArrayList<Integer> brackets, double x, int stIndex, int eIndex) {
        int counter = 0;

        if (!opExist(parts, stIndex, eIndex)) {
            parts.set(stIndex, hash(evalK(parts.get(stIndex), x)));
            return;
        }

        //replace when I can
        for (int i = stIndex; i <= eIndex - counter; i++) {
            if (parts.get(i) == 32.0) {
                parts.set(i, hash(evalK(parts.get(i - 1), x) * evalK(parts.get(i + 1), x)));
                parts.remove(i + 1);
                brackets.remove(i + 1);
                parts.remove(i - 1);
                brackets.remove(i - 1);
                counter++;

                mostRecent = i - 1;
            } else if (parts.get(i) == 33.0) {
                parts.set(i, hash(evalK(parts.get(i - 1), x) / evalK(parts.get(i + 1), x)));
                parts.remove(i + 1);
                brackets.remove(i + 1);
                parts.remove(i - 1);
                brackets.remove(i - 1);
                counter++;

                mostRecent = i - 1;
            }
        }

        for (int i = stIndex; i <= eIndex - counter; i++) {
            if (parts.get(i) == 30.0) {
                parts.set(i, hash(evalK(parts.get(i - 1), x) + evalK(parts.get(i + 1), x)));
                parts.remove(i + 1);
                brackets.remove(i + 1);
                parts.remove(i - 1);
                brackets.remove(i - 1);
                counter++;

                mostRecent = i - 1;
            } else if (parts.get(i) == 31.0) {
                parts.set(i, hash(evalK(parts.get(i - 1), x) - evalK(parts.get(i + 1), x)));
                parts.remove(i + 1);
                brackets.remove(i + 1);
                parts.remove(i - 1);
                brackets.remove(i - 1);
                counter++;

                mostRecent = i - 1;
            }
        }
    }

    /*
    
    If there are NO OPERATORS AND NO BRACKETS: it can only be a constant
    b/c no functions, no operators just a constant evaluation
    
    
    */
    
    public static boolean opExist(ArrayList<Double> parts, int START, int END) {
        for (int i = START; i <= END; i++) {
            if (parts.get(i).intValue() / 10 == 3) {
                return true;
            }
        }

        return false;
    }

    public static double hash(double constant) {
        int len = (int) Math.log10(constant) + 1;
        return constant + Math.pow(10, len);
    }

    public static double evalK(double key, double x) {
        if (((int) key) / 10 == 1) {
            int len = (int) Math.log10(key);
            return key - Math.pow(10, len);
        } else if (doubleEqual(key, 8)) {
            return x;
        } else if (doubleEqual(key, 20)) {
            return Math.E;
        } else if (doubleEqual(key, 21)) {
            return Math.PI;
        } else {
            System.err.println("Incorrect key: " + key);
            throw new IllegalArgumentException();
        }
    }

    public static boolean doubleEqual(double d1, double d2) {
        return Math.abs(d1 - d2) <= 0.00000001;
    }

    public static int findNum(ArrayList<Integer> array, int stIndex, double term) {
        for (int i = stIndex + 1; i < array.size(); i++) {
            if (array.get(i) == term) {
                return i;
            }
        }

        return -1;
    }
}
