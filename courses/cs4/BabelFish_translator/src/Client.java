
import java.io.*;
import java.util.*;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import javax.swing.JOptionPane;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.application.Application;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Client extends Application {

    private static int ctr = 0;
    private static final TreeMap<String, Integer> languages;
    private static final ArrayList<String> languageIndex;
    private static final ArrayList<HashMap<String, vector_str>> dict;
    private static final HashMap<String, String> corrections;
    private static final TextArea ta1;
    private static final TextArea corr;
    private static ComboBox<String> se1;
    private static ComboBox<String> se2;
    private static String input;
    private static Stage stage;
    private static final ArrayList<Log> log;

    public static String translate(int sloc, int dloc, String text) {
        text = text.replaceAll("(\\W+)", " ");
        text = text.replaceAll("\\s+", " ");
        text = text.toLowerCase().trim();
        text += " ";
        input = text;

        vector_str complist = new vector_str();

        String token;
        StringBuilder buf = new StringBuilder();
        int index = 0;
        int len = 0;

        char ch;
        while (index < text.length()) {
            ch = text.charAt(index);
            if (ch != '\n' && ch != '\t' && ch != ' ' && ch != '\r') {
                buf.append(text.charAt(index));
            } else {
                token = buf.toString();
                vector_str t = dict.get(sloc).get(token);
                if (t == null) {
                    complist.add(token);
                } else {
                    complist.add(t.get(dloc));
                }
                ++len;
                buf = new StringBuilder();
            }
            ++index;
        }

        for (int LEN = len; LEN > 0; --LEN) {
            for (int i = 0; i <= len - LEN; ++i) {
                String corr_key = join_sp(complist, i, i + LEN);
                String corr_val = corrections.get(
                        String.format("%-12s",
                                languageIndex.get(sloc)) + corr_key);
                if (corr_val != null) {
                    complist.remove_range_repl(corr_val, i, LEN);
                }
            }
        }

        return join_sp(complist, 0, complist.size());
    }

    static {
        languages = new TreeMap<>();
        languageIndex = new ArrayList<>();
        dict = new ArrayList<>();
        ta1 = new TextArea();
        ta1.setPrefRowCount(10);
        ta1.setPrefColumnCount(40);
        corr = new TextArea();
        corr.setPrefRowCount(10);
        corr.setPrefColumnCount(20);
        log = new ArrayList<>();
        corrections = new HashMap<>();
    }

    /**
     * This is inclusive,exclusive
     */
    public static String join_sp(vector_str list, int st, int end) {

        StringBuilder sb = new StringBuilder();
        for (int it = st; it < end; ++it) {
            sb.append(list.get(it));
            sb.append(' ');
        }

        if (st != end) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    public static void comp(vector_str s1,
            vector_str s2, String slang) {

        Queue<Long> pairs_1 = new ArrayDeque<>();
        Queue<Long> pairs_2 = new ArrayDeque<>();

        int past_ptr_1 = 0;
        int past_ptr_2 = 0;
        int ptr_1 = 0;
        int ptr_2 = 0;
        int aux_ptr_1;
        int aux_ptr_2;
        boolean wi_pr = false;
        boolean wi = false;

        // tokenize by common strings
        // dump if at the end of either string
        while (ptr_1 < s1.size() && ptr_2 < s2.size()) {
            if (s1.get(ptr_1).equals(s2.get(ptr_2))) {
                wi_pr = wi;
                wi = true;
                past_ptr_1 = ptr_1;
                past_ptr_2 = ptr_2;
                ++ptr_1;
                ++ptr_2;
            } else {
                wi_pr = wi;
                wi = false;
                past_ptr_1 = ptr_1;
                past_ptr_2 = ptr_2;
                aux_ptr_1 = ptr_1;
                aux_ptr_2 = ptr_2;
                while (aux_ptr_1 < s1.size() && !s1.get(aux_ptr_1).
                        equals(s2.get(ptr_2))) {
                    ++aux_ptr_1;
                }
                while (aux_ptr_2 < s2.size() && !s2.get(aux_ptr_2).
                        equals(s1.get(ptr_1))) {
                    ++aux_ptr_2;
                }

                if (aux_ptr_2 == s2.size() && aux_ptr_1 == s1.size()) {
                    ++ptr_1;
                    ++ptr_2;
                } else if (aux_ptr_1 == s1.size()) {
                    ptr_2 = aux_ptr_2;
                } else if (aux_ptr_2 == s2.size()) {
                    ptr_1 = aux_ptr_1;
                } else if (aux_ptr_1 - ptr_1 > aux_ptr_2 - ptr_2) {
                    ptr_1 = aux_ptr_1;
                } else {
                    ptr_2 = aux_ptr_2;
                }
            }

            if (wi == false && wi_pr == true || wi == true && wi_pr == false
                    && past_ptr_1 == s1.size() - 1
                    && past_ptr_2 == s2.size() - 1) {
                int ctr = 0;
                int aux_pp1 = past_ptr_1 - 1;
                int aux_pp2 = past_ptr_2 - 1;
                while (aux_pp1 >= 0 && aux_pp2 >= 0
                        && s1.get(aux_pp1).equals(s2.get(aux_pp2))) {
                    --aux_pp1;
                    --aux_pp2;
                    ++ctr;
                }
                if (ctr > 1 || (past_ptr_1 == 0 || past_ptr_1 == s1.size() - 1)
                        && (past_ptr_2 == 0 || past_ptr_2 == s2.size() - 1)) {
                    pairs_1.offer((((long) past_ptr_1) << 32)
                            + past_ptr_1 - ctr);
                    pairs_2.offer((((long) past_ptr_2) << 32)
                            + past_ptr_2 - ctr);
                }
            }
        }

        int st_1 = 0;
        int st_2 = 0;
        int intermed_1 = 0;
        int intermed_2 = 0;
        int e_1 = 0;
        int e_2 = 0;

        long V1, V2;

        // manually do the start and end
        while (!pairs_1.isEmpty() && !pairs_2.isEmpty()) {
            V1 = pairs_1.poll();
            V2 = pairs_2.poll();

            intermed_1 = (int) (V1 & 0xffff_ffff);
            intermed_2 = (int) (V2 & 0xffff_ffff);
            e_1 = (int) (V1 >>> 32);
            e_2 = (int) (V2 >>> 32);

            String j1 = join_sp(s1, st_1, intermed_1);
            String j2 = join_sp(s2, st_2, intermed_2);

            if (!j1.equals(j2)) {
                if (j1.substring(0, j1.indexOf(' ')).equals(
                        j2.substring(0, j2.indexOf(' ')))) {
                    j1 = j1.substring(j1.indexOf(' ') + 1);
                    j2 = j2.substring(j2.indexOf(' ') + 1);
                }
                corrections.put(String.format("%-12s%s", slang, j1), j2);
            }

            st_1 = e_1;
            st_2 = e_2;
        }

        String j1 = join_sp(s1, e_1, s1.size());
        String j2 = join_sp(s2, e_2, s2.size());

        if (!j1.equals(j2)) {
            if (j1.substring(0, j1.indexOf(' ')).equals(
                    j2.substring(0, j2.indexOf(' ')))) {
                j1 = j1.substring(j1.indexOf(' ') + 1);
                j2 = j2.substring(j2.indexOf(' ') + 1);
            }
            corrections.put(String.format("%-12s%s", slang, j1), j2);
        }
    }

    public static void readData() {
        try {
            final BufferedReader br = new BufferedReader(
                    new FileReader("word_1k_20.txt"));
            StringTokenizer st = new StringTokenizer(br.readLine());
            final String rgx = "\t";
            String line;

            while (st.hasMoreTokens()) {
                String lang = st.nextToken();
                languages.put(lang, ctr++);
                languageIndex.add(lang);
                dict.add(new HashMap<>());
            }

            while ((line = br.readLine()) != null) {
                st = new StringTokenizer(line, rgx);
                final vector_str w = new vector_str(languages.size());
                String tr;
                int ct = 0;
                while (st.hasMoreTokens()) {
                    tr = st.nextToken();
                    dict.get(ct++).put(tr, w);
                    w.add(tr);
                }
            }

            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Hi");
        }
    }

    public static void intermedComp(String s1, String s2,
            String slang) {
        StringTokenizer st1 = new StringTokenizer(s1);
        StringTokenizer st2 = new StringTokenizer(s2);

        vector_str a1 = new vector_str();
        vector_str a2 = new vector_str();

        while (st1.hasMoreTokens()) {
            a1.add(st1.nextToken());
        }

        while (st2.hasMoreTokens()) {
            a2.add(st2.nextToken());
        }

        comp(a1, a2, slang);
    }

    public static void load() {
        try {
            final BufferedReader br = new BufferedReader(
                    new FileReader("logfile.txt"));
            String line;
            StringTokenizer st;
            while ((line = br.readLine()) != null) {
                st = new StringTokenizer(line, "\t");
                String aux;
                Log lg = new Log(Integer.parseInt(st.nextToken()),
                        Integer.parseInt(st.nextToken()),
                        st.nextToken(), st.nextToken(), st.nextToken());
                log.add(lg);
                intermedComp(lg.getTrans(), lg.getCorr(),
                        languageIndex.get(lg.getSLang()));
            }

            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Other error");
        } catch (Exception e1) {
            System.err.println(e1.getMessage());
            dump();
            JOptionPane.showMessageDialog(null, "There's been an error, "
                    + "please try launching the application again.");
        }
    }

    public static void dump() {
        try {
            final BufferedWriter bw = new BufferedWriter(
                    new FileWriter("logfile.txt"));
            for (Log lg : log) {
                if (!lg.getCorr().isEmpty()) {
                    bw.write(lg.write());
                }
            }
            bw.close();
        } catch (IOException e) {
        }
    }

    public static void genGUI() {
        final VBox box = new VBox();
        final HBox[] jp = new HBox[2];
        for (int i = 0; i < 2; ++i) {
            jp[i] = new HBox();
        }

        box.getChildren().add(new Text("\n"));
        jp[0].getChildren().add((new Text("\tSelect original language: ")));
        jp[0].getChildren().add(se1 = new ComboBox<String>());
        se1.getItems().addAll(languages.keySet());
        jp[0].getChildren().add((new Text("\t\tSelect final language: ")));
        jp[0].getChildren().add(se2 = new ComboBox<String>());
        se2.getItems().addAll(languages.keySet());

        Border border = new Border(new BorderStroke(Paint.valueOf("BLACK"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

        ta1.setWrapText(true);

        ta1.setBorder(border);
        jp[1].getChildren().add(new Text("\tSource: \t"));
        jp[1].getChildren().add(ta1);

        box.getChildren().add(jp[0]);
        box.getChildren().add(new Text("\n"));
        box.getChildren().add(jp[1]);
        box.getChildren().add(new Text("\n"));

        HBox button_encap = new HBox();
        button_encap.getChildren().add(new Text("\t\t\t\t\t\t\t\t"));
        Node button = new Button("\t\t\tTranslate\t\t\t");
        button_encap.getChildren().add(button);
        button_encap.getChildren().add(new Text("\t\t\t\t\t\t\t\t"));
        box.getChildren().add(button_encap);

        BorderPane top = new BorderPane();
        top.setLeft(box);
        Scene scene = new Scene(top, 600, 400, Color.RED);
        stage.setScene(scene);
        stage.setTitle("An AI created by Jeff Dean to translate things");
        stage.show();

        try {
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                stage.hide();
                String src = ta1.getText();
                HBox hb1 = new HBox();
                HBox hb2 = new HBox();
                HBox button_en = new HBox();
                button_en.getChildren().add(new Text("\t\t\t\t\t\t\t\t"));
                Button exit_button = new Button("\t\t\tExit\t\t\t");
                button_en.getChildren().add(exit_button);
                button_en.getChildren().add(new Text("\t\t\t\t\t\t\t\t"));

                System.out.println(se1.getValue());
                System.out.println(se2.getValue());
                int srcLang = languages.get(se1.getValue());
                int destLang = languages.get(se2.getValue());

                String dest = translate(srcLang, destLang, src);

                VBox disp = new VBox();
                disp.getChildren().add(new Text("\n"));
                disp.getChildren().add(hb1 = new HBox());
                disp.getChildren().add(new Text("\n"));
                disp.getChildren().add(hb2 = new HBox());
                disp.getChildren().add(new Text("\n"));
                disp.getChildren().add(button_en);
                disp.getChildren().add(new Text("\n"));

                hb1.getChildren().add(new Text(String.format("\t\tTranslation "
                        + "(%s->%s)\t\t%n", se1.getValue(),
                        (String) se2.getValue())));

                TextArea ta2 = new TextArea();
                ta2.setPrefRowCount(10);
                ta2.setPrefColumnCount(20);
                ta2.setEditable(false);
                ta2.setText(dest);

                ta2.setWrapText(true);
                ta2.setBorder(border);

                hb1.getChildren().add(ta2);

                hb2.getChildren().add(new Text("\t\tEnter corrected translation (if any): \t"));
                corr.setWrapText(true);
                corr.setBorder(border);
                hb2.getChildren().add(corr);

                top.setLeft(disp);
                stage.setTitle("Jeff Dean speaks");
                stage.show();

                exit_button.addEventHandler(MouseEvent.MOUSE_CLICKED, f -> {

                    /* 
			        Data format for dumping to files
			        LANG_INIT_INDEX[TAB]LANG_FINAL_INDEX[TAB]INITIAL_TEXT[TAB]GEN_TEXT[TAB]
			        CORR_TEXT[NEWLINE]
                     */
                    stage.close();
                    Log lg = new Log(languages.get(se1.getValue()), languages.get(se2.getValue()),
                            input, ta2.getText(), corr.getText());
                    log.add(lg);
                });
            });
        } catch (NullPointerException e) {
            System.err.println("Nothing selected");
        }
    }

    public static void main(String[] args) {
        readData();
        load();
        launch(args);
        dump();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Client.stage = stage;
        genGUI();
    }
}
