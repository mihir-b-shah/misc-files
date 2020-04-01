
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class Lexer {

    private enum LexType {
        TYPE, LITERAL, ID, GROUP, KEYWORD;
    }

    class Lexeme {

        LexType type;
        String token;

        Lexeme(LexType type, String token) {
            this.type = type;
            this.token = token;
        }
    }

    private static final Pattern TYPE_REGEX
            = Pattern.compile("((struct\\s+\\w[\\w\\d]*)|"
                    + "(int)|(long)|(double)|(bool))(\\*)*");

    private static final Pattern LITERAL_REGEX
            = Pattern.compile("(true)|(false)|(\".*\")|(\'.\')|((0[bx])?[0-9a-f]+[SL]?)"
                    + "|(\\d+(\\.\\d+)?F?)");

    private static final Pattern ID_REGEX
            = Pattern.compile("\\w[\\w\\d]*");

    private static final Pattern GROUP_REGEX
            = Pattern.compile(":|\\{|\\}|,|\\(|\\)|;");

    private static final Pattern KEYWORD_REGEX
            = Pattern.compile("(bool)|(double)|(else)|(for)|(goto)|(if)|(int)"
                    + "|(long)|(return)|(void)|(while)");

    private static final int ENDED = 0b11111;
    
    private static List<Lexeme> lex(String program) {
        
        List<Lexeme> lexemes = new ArrayList<>();
        final Matcher[] matchers = {TYPE_REGEX.matcher(program), 
                                    LITERAL_REGEX.matcher(program), 
                                    ID_REGEX.matcher(program),
                                    GROUP_REGEX.matcher(program),
                                    KEYWORD_REGEX.matcher(program)};
        
        final int[] ptrs = new int[5];         
        
        // bit fields
        int available = 0;
        int ended = 0;
        
        while(ended != ENDED) {
            for(int i = 0; i<5; ++i) {
                if((available & 1 << i) == 0) {
                    if(!matchers[i].find(ptrs[i])) {
                        ended |= 1 << i;
                    } else {
                        ptrs[i] = matchers[i].start();
                        available |= 1 << i;
                    }
                } else {
                    String prev = matchers[i].group();
                }
            }
        }
    }

    public static void main(String[] args) {
        String program = "int main() {\n\treturn 2;\n}";
    }
}
