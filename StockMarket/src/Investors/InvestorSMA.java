package Investors;

import Companies.Company;
import Orders.Order;
import Orders.BuyOrder;
import Orders.SellOrder;
import java.util.Random;

public class InvestorSMA extends Investor {

    private SMA sma; // SMA object for calculating moving averages
    private Company watchedCompany; // Company being watched by the investor

    // Constructor for the InvestorSMA class
    public InvestorSMA(int id, int accountBalance) {
        super(id, accountBalance); // Call to the base class constructor Investor
        this.sma = new SMA(); // Initialize the SMA object
    }

    // Initialization method that randomly selects a company to watch
    public void initialize() {
        Random random = new Random();
        watchedCompany = companies.get(random.nextInt(shares.size()));
    }

    // Method selecting an order based on SMA analysis
    @Override
    public Order selectOrder(int turnNumber, int orderId) {
        sma.addPrice(watchedCompany.getLastPrice()); // Add the last price to the SMA

        // If the turn is less than 10, we do not make a decision (return null)
        if (turnNumber < 10) return null;

        Random random = new Random();
        Order order = null;

        // Sell signal analysis
        if (sma.getSMA5() > sma.getSMA10()) { // If SMA5 exceeds SMA10
            int type = random.nextInt(4); // Randomly select the order type
            int numberOfShares = random.nextInt(shares.get(watchedCompany.getIdentifier())) + 1; // Randomly select the number of shares
            int price = -10 + random.nextInt(20); // Randomly select the price

            // Generate a sell order based on the type
            if (type <= 1) {
                order = new SellOrder(orderId, this, turnNumber, watchedCompany.getIdentifier(),
                        numberOfShares, watchedCompany.getLastPrice() + price, turnNumber + 1, false);
            } else if (type == 2) {
                order = new SellOrder(orderId, this, turnNumber, watchedCompany.getIdentifier(),
                        numberOfShares, watchedCompany.getLastPrice() + price, -1, false);
            } else if (type == 3) {
                int numberOfTurns = random.nextInt(50);
                order = new SellOrder(orderId, this, turnNumber, watchedCompany.getIdentifier(),
                        numberOfShares, watchedCompany.getLastPrice() + price, turnNumber + numberOfTurns, false);
            } else if (type == 4) {
                order = new SellOrder(orderId, this, turnNumber, watchedCompany.getIdentifier(),
                        numberOfShares, watchedCompany.getLastPrice() + price, turnNumber, true);
            }
        }
        // Buy signal analysis
        else if (sma.getSMA5() < sma.getSMA10()) { // If SMA5 is less than SMA10
            int type = random.nextInt(4); // Randomly select the order type
            int numberOfShares = random.nextInt(shares.get(watchedCompany.getIdentifier())) + 1; // Randomly select the number of shares
            int price = -10 + random.nextInt(20); // Randomly select the price

            // Generate a buy order based on the type
            if (type <= 1) {
                order = new BuyOrder(orderId, this, turnNumber, watchedCompany.getIdentifier(),
                        numberOfShares, watchedCompany.getLastPrice() + price, turnNumber + 1, false);
            } else if (type == 2) {
                order = new BuyOrder(orderId, this, turnNumber, watchedCompany.getIdentifier(),
                        numberOfShares, watchedCompany.getLastPrice() + price, -1, false);
            } else if (type == 3) {
                int numberOfTurns = random.nextInt(50);
                order = new BuyOrder(orderId, this, turnNumber, watchedCompany.getIdentifier(),
                        numberOfShares, watchedCompany.getLastPrice() + price, turnNumber + numberOfTurns, false);
            } else if (type == 4) {
                order = new BuyOrder(orderId, this, turnNumber, watchedCompany.getIdentifier(),
                        numberOfShares, watchedCompany.getLastPrice() + price, turnNumber, true);
            }
        }

        return order; // Return the generated order
    }
}
