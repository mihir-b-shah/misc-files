
import java.util.regex.Pattern;

class LexTypes {
    static enum Operator {
        INCREMENT("++"), DECREMENT("--"), MINUS("-"), BIT_NOT("~"), LOG_NOT("!"),
        DEREFERENCE("."), MULTIPLY("*"), DIVIDE("/"), MODULUS("%"), ADD("+"),
        SUBTRACT("-"), BIT_LEFT("<<"), SBIT_RIGHT(">>"), UBIT_RIGHT(">>>"),
        LESS("<"), GREATER(">"), LESS_EQ("<="), GREATER_EQ(">="), EQUAL("=="),
        BIT_AND("&"), BIT_OR("|"), BIT_XOR("^"), LOG_AND("&&"), LOG_OR("||"), ASSIGN("=");

        static final Pattern OPERATOR_REGEX = Pattern.compile("[\\.\\[\\]!~/%\\*\\^]"
                + "|(\\+{1,2})|(\\-{1,2})|(<{1,2})|(>{1,3})|(<=)|(>=)|(\\&{1,2})|(\\"
                + "|{1,2})|(\\=){1,2}");

        String token;
        Operator(String s) {
            token = s;
        }

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
                    return Operator.DEREFERENCE;
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
                case "<=":
                    return Operator.LESS_EQ;
                case ">=":
                    return Operator.GREATER_EQ;
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
                default:
                    return null;
            }
        }
    }

    static class Id {
        static final Pattern ID_REGEX
                = Pattern.compile("[\\w&&\\D][\\w]*");
    }

    static class DataType {
        static final Pattern TYPE_REGEX = Pattern.compile("((struct\\s+\\w[\\w\\d]*)|"
                    + "(int)|(long)|(char)|(float)|(bool)|(void))(\\*)*");
        int ptrLvl;
        BaseType base;

        private DataType(int ptrLvl) {
            this.ptrLvl = ptrLvl;
        }
        
        static enum BaseType {
            VOID("void"), BOOL("bool"), I8("char"), I32("int"), I64("long"), F64("float");

            String token;
            BaseType(String s) {
                token = s;
            }
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
                    ret.base = BaseType.VOID;
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
   
    static enum LiteralType {
        BOOL_TRUE, BOOL_FALSE, INT, STRING, CHAR, FLOAT;

        static final Pattern LITERAL_REGEX = Pattern.compile("(true)|(false)|(\".*\")"
                + "|(\'.\')|(((0b[01]+)|(0x[0-9a-f]+)|([0-9]+))[SL]?)|(\\d+(\\.\\d+)?)");
        
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

    static enum Group {
        OPEN_PAREN("("), CLOSE_PAREN(")"), COLON(":"), OPEN_BRACKET("{"),
        CLOSE_BRACKET("}"), COMMA(","), SEMICOLON(";");

        static final Pattern GROUP_REGEX = Pattern.compile(":|\\{|\\}|,|\\(|\\)|;");

        String token;
        Group(String s) {
            token = s;
        }

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

    static enum Control {
        IF("if"), ELSE("else"), GOTO("goto"), RETURN("return"), WHILE("while");

        static final Pattern CONTROL_REGEX = Pattern.compile("(else)|(goto)|(if)"
                + "|(return)|(void)|(while)");

        String token;
        Control(String s) {
            token = s;
        }

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
                default:
                    return null;
            }
        }
    }
}