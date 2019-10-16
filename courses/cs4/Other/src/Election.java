
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class Election {

    private final Scanner user;
    private static final String printString;
    private final TreeMap<String, Candidate> byName;
    private final TreeSet<Candidate> byCand;

    static {
        printString = "Hi choose from the following options\n:"
                + "1: Vote for a candidate\n"
                + "2: Displa a list of all candidates by last name\n"
                + "3:  Display all candidates ranked by number of votes\n"
                + "4: Display the number of votes for a specified candidate\n"
                + "5: Save and EXIT";
    }

    public Election(Scanner user) {
        this.user = user;
        byName = new TreeMap<>();
        byCand = new TreeSet<>();
        load();
    }

    public Scanner getScn() {
        return user;
    }

    public void vote() {
        System.out.println("Candidate name: ");
        String cand = user.nextLine();
        byName.get(cand).incrVotes();
    }

    public void displayAllCandidates() {
        System.out.println(byName.keySet());
    }

    public void rankByVotes() {
        System.out.println(byCand);
    }

    public void getVotesFor() {
        System.out.println("Candidate name: ");
        String cand = user.nextLine();
        System.out.println(byName.get(cand).getVotes());
    }

    public void save() {
        try {
            PrintWriter file = new PrintWriter("election.txt");
            Collection<Candidate> contacts = byName.values();
            for (Candidate c : contacts) {
                file.write(c.write());
            }
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void load() {
        try {
            BufferedReader file = new BufferedReader(new FileReader("election.txt"));
            String line;
            String[] items;
            while ((line = file.readLine()) != null) {
                items = line.split(",");
                Candidate c = new Candidate(items[0], items[1]);
                c.setVotes(Integer.parseInt(items[2]));
                String s = String.format("%s %s", items[0], items[1]);
                byName.put(s, c);
                byCand.add(c);
            }
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Scanner user = new Scanner(System.in);
        Election ab = new Election(user);
        System.out.println();

        System.out.println("hi!" + printString);

        outer:
        while (true) {
            System.out.print("Enter code: ");
            int code = user.nextInt();
            user.nextLine();
            switch (code) {
                case 1:
                    ab.vote();
                    break;
                case 2:
                    ab.displayAllCandidates();
                    break;
                case 3:
                    ab.rankByVotes();
                    break;
                case 4:
                    ab.getVotesFor();
                    break;
                case 5:
                    ab.save();
                    ab.getScn().close();
                    break outer;
                default:
                    System.err.printf("%s%n%s%n", "Code not recognized.", printString);
            }
        }

    }
}
