import Companies.Company;
import Companies.CompanyOrders;
import Investors.Investor;
import Orders.Order;

import java.util.ArrayList;
import java.util.HashMap;

public class Simulation {
    private int numberOfTurns; // Number of simulation turns
    private int orderId; // Order identification number
    private ArrayList<String> shareNames = new ArrayList<>(); // List of share names
    private HashMap<String, Company> shares = new HashMap<>(); // Map to store companies
    private ArrayList<Investor> investors = new ArrayList<>(); // List of investors
    private HashMap<String, CompanyOrders> shareOrders = new HashMap<>(); // Map to store orders for individual shares

    // Constructor of the Simulation class
    public Simulation(int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
        this.orderId = 0; // Initialize order number
    }

    // Adding a new company to the simulation
    public void addShares(String identifier, int lastPrice) {
        shareNames.add(identifier); // Add share name to the list
        shares.put(identifier, new Company(identifier, lastPrice)); // Create a new company and add it to the share map
        shareOrders.put(identifier, new CompanyOrders(identifier, shares.get(identifier))); // Create a CompanyOrders object for the given share
    }

    // Adding a new investor to the simulation
    public void addInvestor(Investor investor) {
        investors.add(investor); // Add the new investor to the investor list
    }

    // Adding shares to investors' portfolios
    public void addSharesToPortfolio(String shareName, int quantity) {
        for (int i = 0; i < investors.size(); i++) {
            investors.get(i).addShares(shares.get(shareName), quantity); // Add the specified quantity of shares to each investor's portfolio
        }
    }

    // Initializing investors before starting the simulation
    public void initializeInvestors() {
        for (int i = 0; i < investors.size(); i++) {
            investors.get(i).initialize(); // Call the initializing method for each investor
        }
    }

    // Adding orders to appropriate collections based on investor selections in the current turn
    public void addOrders(int turnNumber) {
        for (int i = 0; i < investors.size(); i++) {
            Order newOrder = investors.get(i).selectOrder(turnNumber, orderId); // Investor selects an order
            if (newOrder != null) {
                if (newOrder.isPurchase()) {
                    shareOrders.get(newOrder.getShareId()).addBuyOrder(newOrder); // Add buy order to the appropriate collection
                } else {
                    shareOrders.get(newOrder.getShareId()).addSellOrder(newOrder); // Add sell order to the appropriate collection
                }
            }
        }
    }

    // Executing turns of the simulation, including adding orders and executing them
    private void executeTurn(int turnNumber) {
        addOrders(turnNumber); // Add orders to appropriate collections
        for (int i = 0; i < shareNames.size(); i++) {
            shareOrders.get(shareNames.get(i)).executeOrders(turnNumber); // Execute orders for each share
        }
    }

    // Displaying the status of all investors' portfolios after the simulation ends
    private void printInvestors() {
        for (int i = 0; i < investors.size(); i++) {
            investors.get(i).printPortfolioStatus(); // Display the status of the given investor's portfolio
        }
    }

    // Conducting the entire simulation
    public void runSimulation() {
        for (int i = 0; i < numberOfTurns; i++) {
            executeTurn(i); // Execute the next turns of the simulation
        }
        printInvestors(); // Display the status of investors' portfolios after the simulation ends
    }
}
