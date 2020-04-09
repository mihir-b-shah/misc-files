


import java.util.regex.Pattern;

public class LexTypes {
    public static enum Operator {
        INCREMENT, DECREMENT, BIT_NOT, LOG_NOT,
        ACCESS, MULTIPLY, DEREFERENCE, DIVIDE, MODULUS, ADD,
        SUBTRACT, BIT_LEFT, SBIT_RIGHT, UBIT_RIGHT,
        LESS, GREATER, EQUAL, ADDRESS, BIT_AND, BIT_OR, 
        BIT_XOR, LOG_AND, LOG_OR, ASSIGN;

        static final Pattern OPERATOR_REGEX = Pattern.compile("[\\.\\[\\]!~/%\\*\\^]"
                + "|(\\+{1,2})|(\\-{1,2})|(<{1,2})|(>{1,3})|(\\&{1,2})|(\\"
                + "|{1,2})|(\\=){1,2}|@|\\$");

        // returns 0 for confused, 1 for unary, returns 2 for binary
        /*int type() {
            switch(this) {
                case INCREMENT:
                case DECREMENT:
                case BIT_NOT:
                case LOG_NOT:
                case DEREFERENCE:
                
            }
        } */
        
        static Operator createOperator(String s) {
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

    public static final class Id {
        static final Pattern ID_REGEX
                = Pattern.compile("[\\w&&\\D][\\w]*");
    }

    public static final class DataType {
        static final Pattern TYPE_REGEX = Pattern.compile("((struct\\s+[\\w&&\\D][\\w]*)|"
                    + "(int)|(long)|(char)|(float)|(bool)|(void))(\\*)*");
        int ptrLvl;
        BaseType base;

        private DataType(int ptrLvl) {
            this.ptrLvl = ptrLvl;
        }
        
        public static enum BaseType {
            VOID, BOOL, I8, I32, I64, F64, STRUCT;
        }
        
        static DataType createDataType(String s) {
            int idx;
            DataType ret;
            if((idx = s.indexOf('*')) == -1) {
                ret = new DataType(0);
            } else {
                ret = new DataType(s.length()-idx);
                s = s.substring(0, idx);
            }

            switch(s) {
                case "bool":
                    ret.base = BaseType.BOOL;
                    break;
                case "char":
                    ret.base = BaseType.I8;
                    break;
                case "int":
                    ret.base = BaseType.I32;
                    break;
                case "long":
                    ret.base = BaseType.I64;
                    break;
                case "float":
                    ret.base = BaseType.F64;
                    break;
                default:
                    if(s.substring(0,6).equals("struct")) {
                        ret.base = BaseType.STRUCT;
                    } else {
                        ret.base = BaseType.VOID;
                    }  
                    break;
            }
            return ret;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(base.name());
            if(ptrLvl > 0) {
                sb.append('_');
                sb.append(ptrLvl);
                sb.append("_PTR");
            }
            return sb.toString();
        }
    }
   
    public static enum LiteralType {
        BOOL_TRUE, BOOL_FALSE, INT, STRING, CHAR, FLOAT;

        static final Pattern LITERAL_REGEX = Pattern.compile("(true)|(false)|(\".*\")"
                + "|('(\\\\)?.')|(((\\-)?(0b[01]+)|((\\-)?0x[0-9a-f]+)|((\\-)?[0-9]+))[SL]?)|((\\-)?\\d+(\\.\\d+)?)");
        
        static LiteralType createLiteral(String s) {
            switch (s.charAt(0)) {
                case 't':
                    return LiteralType.BOOL_TRUE;
                case 'f':
                    return LiteralType.BOOL_FALSE;
                case '\'':
                    return LiteralType.CHAR;
                case '"':
                    return LiteralType.STRING;
                default:
                    if (s.indexOf('.') == -1) {
                        return LiteralType.INT;
                    } else {
                        return LiteralType.FLOAT;
                    }
            }
        }
    }

    public static enum Group {
        OPEN_PAREN, CLOSE_PAREN, COLON, OPEN_BRACKET,
        CLOSE_BRACKET, COMMA, SEMICOLON;

        static final Pattern GROUP_REGEX = Pattern.compile(":|\\{|\\}|,|\\(|\\)|;");

        static Group createGroup(String s) {
            switch(s) {
                case "(":
                    return Group.OPEN_PAREN;
                case ")":
                    return Group.CLOSE_PAREN;
                case ":":
                    return Group.COLON;
                case "{":
                    return Group.OPEN_BRACKET;
                case "}":
                    return Group.CLOSE_BRACKET;
                case ",":
                    return Group.COMMA;
                case ";":
                    return Group.SEMICOLON;
                default:
                    return null;
            }
        }
    }

    public static enum Control {
        IF, ELSE, GOTO, RETURN, WHILE,
        SWITCH, CASE, DEFAULT;

        static final Pattern CONTROL_REGEX = Pattern.compile("(else)|(goto)|(if)"
                + "|(return)|(void)|(while)|(switch)|(case)|(default)");

        static Control createControl(String s) {
            switch(s) {
                case "if":
                    return Control.IF;
                case "else":
                    return Control.ELSE;
                case "goto":
                    return Control.GOTO;
                case "return":
                    return Control.RETURN;
                case "while":
                    return Control.WHILE;
                case "switch":
                    return Control.SWITCH;
                case "case":
                    return Control.CASE;
                case "default":
                    return Control.DEFAULT;
                default:
                    return null;
            }
        }
    }
}
