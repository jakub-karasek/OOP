package Companies;

public class Company {
    private String identifier;
    private int lastPrice;

    public Company(String identifier, int lastPrice) {
        assert identifier.length() == 5 : "The company identifier must be 5 characters long";
        assert identifier.matches("[A-Z]+") : "The identifier contains characters outside the range A to Z!";

        this.identifier = identifier;
        this.lastPrice = lastPrice;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(int lastPrice) {
        this.lastPrice = lastPrice;
    }
}
