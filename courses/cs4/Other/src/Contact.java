
public class Contact {

    private final String name;
    private final String birthdate;
    private String phoneNumber;

    public Contact(String n, String b, String p) {
        name = n;
        birthdate = b;
        phoneNumber = p;
    }

    public String getName() {
        return name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean setPhoneNumber(String p) {
        phoneNumber = p;
        return true;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Birthdate: %s, Number: %s",
                name, birthdate, phoneNumber);
    }

    public String write() {
        return String.format("%s,%s,%s%n",
                name, birthdate, phoneNumber);
    }
}
