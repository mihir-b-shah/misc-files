
public class Candidate implements Comparable<Candidate> {

    private final String firstName;
    private final String lastName;
    private int numVotes;

    public Candidate(String f, String l) {
        firstName = f;
        lastName = l;
    }

    public String getLast() {
        return lastName;
    }

    public String getFirst() {
        return firstName;
    }

    public int getVotes() {
        return numVotes;
    }

    public void setVotes(int v) {
        numVotes = v;
    }

    public void incrVotes() {
        ++numVotes;
    }

    @Override
    public int compareTo(Candidate o) {
        if (o.getVotes() == numVotes) {
            return o.getLast().equals(lastName) ? o.getLast().compareTo(lastName) : o.getFirst().compareTo(firstName);
        } else {
            return o.getVotes() - numVotes;
        }
    }

    @Override
    public String toString() {
        return String.format("Name: %s %s, Votes: %d", firstName, lastName, numVotes);
    }

    public String write() {
        return String.format("%s,%s,%d\n", firstName, lastName, numVotes);
    }
}
