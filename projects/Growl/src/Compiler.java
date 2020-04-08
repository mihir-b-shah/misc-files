
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Compiler {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("testprog.txt"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append('\n');
        }

        List<Lexer.Lexeme> lexemes = Lexer.lex(sb.toString());
    }    
}
