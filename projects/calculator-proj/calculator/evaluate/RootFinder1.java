package misc;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import javax.swing.JOptionPane;
import java.util.HashSet;

public class RootFinder1 {

    public static final String[] choices = {"e^(", "sin(", "cos(", "x^(", "ln(", "k", "(", ")", "x", "OPERATOR", "CONST", "DONE"};
    public static final String[] consts = {"e", "π"};
    public static final String[] operators = {"+", "-", "*", "/"};
    public static HashSet<Double> roots = new HashSet<>();

    public static class Function {
        // How do I transcribe the function ?!

        public double returnValue(double x) {
            double y = 0;
            return x;
        }
    }

    public static void main(String[] args) {
        ArrayList<Double> function = enterFX();
        double finAmt = evaluator(function, 2, 0, function.size() - 1);

        System.out.println(finAmt);
        System.exit(0);
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
        return functions;
    }

    public static double evaluator(ArrayList<Double> parts, double x, int START, int END) {

        // CURRENTLY ONLY MONOMIALS ARE SUPPORTED
        
        int nEnd = 0;

        if (START <= END) {
            while (nEnd < END) {
                int nStart = findInt(parts, 6, START, END);
                nEnd = bracketMatchLoc(parts, START, END);

                if (nStart == -1 || nEnd == -1) {
                    splitOp(parts, x, START, END);
                    parts.remove(END + 1);
                    parts.remove(START - 1);

                    break;
                } else {
                    START += 1;
                    return evaluator(parts, x, nStart + 1, nEnd - 1);
                }
            }
        }

        noParHandler(parts, x);
        return parts.get(0);
    }

    public static void noParHandler(ArrayList<Double> parts, double x) {

        // NO PARENTHESIS
        // CAN HAVE OPERATORS, CONSTANTS, NUMBERS, FUNCTIONS, VARIABLE (LIMIT 1)
        // CURRENTLY ONLY FUNCTIONS ARE SUPPORTED
        
        for(int i = 1; i<parts.size(); i++)
        {
            evalK(parts.get(i-1), x);
        }
    }
    
    public static double hash(double constant) {
        int len = (int) Math.log10(constant) + 1;
        return constant + Math.pow(10, len);
    }

    public static void splitOp(ArrayList<Double> parts, double x, int START, int END) {

        if (!opExist(parts, START, END)) {
            parts.set(START, hash(evalK(parts.get(START), x)));
        }

        for (int i = START + 1; i < END; i += 2) {
            if (doubleEqual(parts.get(i), 32) || doubleEqual(parts.get(i), 33)) {
                if (doubleEqual(parts.get(i), 32)) {
                    parts.set(i, hash(evalK(parts.get(i - 1), x) * evalK(parts.get(i + 1), x)));
                } else {
                    parts.set(i, hash(evalK(parts.get(i - 1), x) / evalK(parts.get(i + 1), x)));
                }

                parts.remove(i + 1);
                parts.remove(i - 1);

                i -= 2;
            }
        }

        for (int i = START + 1; i < END; i += 2) {
            if (doubleEqual(parts.get(i), 30) || doubleEqual(parts.get(i), 31)) {
                if (doubleEqual(parts.get(i), 30)) {
                    parts.set(i, hash(evalK(parts.get(i - 1), x) + evalK(parts.get(i + 1), x)));
                } else {
                    parts.set(i, hash(evalK(parts.get(i - 1), x) - evalK(parts.get(i + 1), x)));
                }

                parts.remove(i + 1);
                parts.remove(i - 1);

                i -= 2;
            }
        }
    }

    public static double evalK(double key, double x, double) {
        if (((int) key) / 10 == 1) {
            int len = (int) Math.log10(key) + 1;
            return key - Math.pow(10, len);
        } else if (doubleEqual(key, 8)) {
            return x;
        } else if (doubleEqual(key, 20)) {
            return Math.E;
        } else if (doubleEqual(key, 0)) {
            return Math.exp(x);
        } else if (doubleEqual(key, 1)) {
            return Math.sin(x);
        } else if (doubleEqual(key, 2)) {
            return Math.pow(x, );
        } else if (doubleEqual(key, 3)) {
            
        } else if (doubleEqual(key, 4)) {
            
        } else if (doubleEqual(key, 21)) {
            return Math.PI;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static void recur(double lB, double uB, int tolerance, int level, ArrayList<Double> parts) {
        if (level <= tolerance) {
            double dx = (uB - lB) / 100;

            for (double i = lB; i < uB; i += dx) {
                if ((evaluator(parts, i, 0, parts.size() - 1) < 0 && evaluator(parts, i + dx, 0, parts.size() - 1) > 0) || (evaluator(parts, i, 0, parts.size() - 1) > 0 && evaluator(parts, i + dx, 0, parts.size() - 1) < 0)) {
                    recur(i, i + dx, tolerance, level + 1, parts);
                } else if (doubleEqual(0, evaluator(parts, i, 0, parts.size() - 1))) {
                    roots.add(round(i, tolerance));
                    i += dx;
                } else if (doubleEqual(0, evaluator(parts, i + dx, 0, parts.size() - 1))) {
                    roots.add(round(i + dx, tolerance));
                    i += dx;
                }

            }
        } else {
            roots.add(round((lB + uB) / 2, tolerance));
        }
    }

    public static double round(double i, int tolerance) {
        int x = (int) (i * Math.pow(10, tolerance));
        double res = x / Math.pow(10, tolerance);

        return res;
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

    public static int bracketMatchLoc(ArrayList<Double> s, int START, int END) {

        int six = 0;
        boolean start = true;

        for (int i = START; i <= END; i++) {
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

    public static char returnOpp(char c) {
        if (c == '(') {
            return ')';
        } else {
            return '(';
        }
    }

    public static int findInt(ArrayList<Double> parts, int num, int START, int END) {
        for (int i = START; i <= END; i++) {
            if (doubleEqual(parts.get(i), num)) {
                return i;
            }
        }

        return -1;
    }

    public static boolean opExist(ArrayList<Double> parts, int START, int END) {
        for (int i = START; i <= END; i++) {
            if (parts.get(i).intValue() / 10 == 3) {
                return true;
            }
        }

        return false;
    }

    public static boolean doubleEqual(double d1, double d2) {
        return Math.abs(d1 - d2) <= 0.00000001;
    }
}
