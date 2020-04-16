
import docutils.GroupFinder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import lexer.Lexeme;
import lexer.Lexer;

public class Compiler {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("testprog.txt"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append('\n');
        }

        String program = sb.toString();
        Lexer.initialize(program);
        List<Lexeme> lexemes = Lexer.getInstance().getLexemes();
        GroupFinder.initialize(lexemes);
    }    
}
