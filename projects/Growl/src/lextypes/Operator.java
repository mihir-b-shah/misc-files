
package lextypes;

import java.util.regex.Pattern;

import parsetypes.enums.*;

/**
 * Operator enum.
 * 
 * @author mihir
 */
public enum Operator {
    INCREMENT, DECREMENT, BIT_NOT, LOG_NOT,
    ACCESS, MULTIPLY, DEREFERENCE, DIVIDE, MODULUS, ADD,
    SUBTRACT, BIT_LEFT, SBIT_RIGHT, UBIT_RIGHT,
    LESS, GREATER, EQUAL, ADDRESS, BIT_AND, BIT_OR, 
    BIT_XOR, LOG_AND, LOG_OR, ASSIGN;

    public static final Pattern OPERATOR_REGEX = Pattern.compile("[\\.\\[\\]!~/%\\*\\^]"
            + "|(\\+{1,2})|(\\-{1,2})|(<{1,2})|(>{1,3})|(\\&{1,2})|(\\"
            + "|{1,2})|(\\=){1,2}|@|\\$");

    // returns 0 for confused, 1 for unary, returns 2 for binary
    public OpType type() {
        switch(this) {
            case INCREMENT:
            case DECREMENT:
            case BIT_NOT:
            case LOG_NOT:
            case DEREFERENCE:
            case ADDRESS:
                return OpType.UNARY;
            default:
                return OpType.BINARY;
        }
    } 

    public int precedence() {
        switch(this) {
            case ACCESS:
            case DEREFERENCE:
            case ADDRESS:
                return 1;
            case INCREMENT:
            case DECREMENT:
                return 2;
            case LOG_NOT:
            case BIT_NOT:
                return 3;
            case MULTIPLY:
            case DIVIDE:
            case MODULUS:
                return 5;
            case ADD:
            case SUBTRACT:
                return 6;
            case BIT_LEFT:
            case SBIT_RIGHT:
            case UBIT_RIGHT:
                return 7;
            case LESS:
                return 8;
            case GREATER:
                return 8;
            case EQUAL:
                return 9;
            case BIT_AND:
                return 10;
            case BIT_XOR:
                return 11;
            case BIT_OR:
                return 12;
            case LOG_AND:
                return 13;
            case LOG_OR:
                return 14;
            case ASSIGN:
                return 15;
            default:
                return Integer.MAX_VALUE;
        }
    }

    public static boolean higher(Operator o1, Operator o2) {
        return o1.precedence() < o2.precedence();
    }

    public Associativity associate() {
        switch(this) {
            case INCREMENT:
            case DECREMENT:
            case LOG_NOT:
            case BIT_NOT:
            case LESS:
            case GREATER:
                return Associativity.NONE;
            case DEREFERENCE:
            case ASSIGN:
                return Associativity.RIGHT_TO_LEFT;
            default:
                return Associativity.LEFT_TO_RIGHT;
        }
    }

    public static Operator createOperator(String s) {
        switch(s) {
            case "++":
                return Operator.INCREMENT;
            case "--":
                return Operator.DECREMENT;
            case "~":
                return Operator.BIT_NOT;
            case "!":
                return Operator.LOG_NOT;
            case ".":
                return Operator.ACCESS;
            case "*":
                return Operator.MULTIPLY;
            case "/":
                return Operator.DIVIDE;
            case "%":
                return Operator.MODULUS;
            case "+":
                return Operator.ADD;
            case "-":
                return Operator.SUBTRACT;
            case "<<":
                return Operator.BIT_LEFT;
            case ">>":
                return Operator.SBIT_RIGHT;
            case ">>>":
                return Operator.UBIT_RIGHT;
            case "<":
                return Operator.LESS;
            case ">":
                return Operator.GREATER;
            case "==":
                return Operator.EQUAL;
            case "&":
                return Operator.BIT_AND;
            case "|":
                return Operator.BIT_OR;
            case "^":
                return Operator.BIT_XOR;
            case "&&":
                return Operator.LOG_AND;
            case "||":
                return Operator.LOG_OR;
            case "=":
                return Operator.ASSIGN;
            case "@":
                return Operator.ADDRESS;
            case "$":
                return Operator.DEREFERENCE;
            default:
                return null;
        }
    }
}
