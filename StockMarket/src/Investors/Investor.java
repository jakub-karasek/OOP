package Investors;

import Companies.Company;
import Orders.Order;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Investor {
    protected int id; // Investor identifier
    protected int accountBalance; // Investor's account balance
    protected ArrayList<Company> companies = new ArrayList<>(); // List of companies owned by the investor
    protected HashMap<String, Integer> shares = new HashMap<>(); // Map storing quantities of shares for each company

    // Constructor initializing the Investor object
    public Investor(int id, int accountBalance) {
        this.id = id;
        this.accountBalance = accountBalance;
    }

    // Abstract method for initializing specific types of investors
    public abstract void initialize();

    // Method to add shares of a specific company to the investor's portfolio
    public void addShares(Company company, int quantity) {
        shares.put(company.getIdentifier(), quantity);
        companies.add(company);
    }

    // Abstract method for selecting orders by the investor
    public abstract Order selectOrder(int turnNumber, int orderId);

    // Method selling shares of a given shareId by the investor
    public void sellShares(String shareId, int quantity, int price) {
        shares.put(shareId, shares.get(shareId) - quantity); // Update the quantity of owned shares
        accountBalance += quantity * price; // Increase the account balance by the transaction revenue
    }

    // Method buying shares of a given shareId by the investor
    public void buyShares(String shareId, int quantity, int price) {
        int i = 0;
        Company company = companies.get(i);
        // Find the appropriate company in the investor's portfolio
        while (!company.getIdentifier().equals(shareId)) {
            i++;
            company = companies.get(i);
        }
        company.setLastPrice(price); // Set the last price of shares in the company
        shares.put(shareId, shares.get(shareId) + quantity); // Update the quantity of owned shares
        accountBalance -= quantity * price; // Decrease the account balance by the transaction cost
    }

    // Method returning the current account balance of the investor
    public int getAccountBalance() {
        return accountBalance;
    }

    // Method returning the investor's id
    public int getId() {
        return id;
    }

    // Method returning the number of shares of a given company owned by the investor
    public int numberOfShares(String shareId) {
        return shares.get(shareId);
    }

    // Method displaying the current status of the investor's portfolio
    public void printPortfolioStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append(accountBalance); // Add account balance at the beginning

        for (Map.Entry<String, Integer> entry : shares.entrySet()) {
            sb.append(" ");
            sb.append(entry.getKey()).append(":").append(entry.getValue()); // Add each key-value pair
        }

        System.out.println(sb.toString()); // Display the resulting string
    }
}
