package Investors;

import Companies.Company;
import Orders.Order;
import Orders.BuyOrder;
import Orders.SellOrder;
import java.util.Random;

public class InvestorRandom extends Investor {

    // Constructor for the InvestorRandom class
    public InvestorRandom(int id, int accountBalance) {
        super(id, accountBalance); // Call to the base class constructor Investor
    }

    @Override
    public void initialize() {
        return; // No additional initialization operations in the InvestorRandom class
    }

    @Override
    public Order selectOrder(int turnNumber, int orderId) {
        Random random = new Random();

        // Randomly selecting order parameters
        boolean isPurchase = random.nextBoolean(); // Randomly determining whether the order will be a buy or sell
        int type = random.nextInt(4); // Randomly selecting the type of order

        // Randomly selecting the company for which the order will be placed
        int shareNumber = random.nextInt(shares.size());
        Company company = companies.get(shareNumber);

        // Randomly selecting the number of shares involved in the order
        int numberOfShares = random.nextInt(shares.get(company.getIdentifier())) + 1;

        // Randomly selecting the order price within a range of -10 to +10 relative to the company's last price
        int price = -10 + random.nextInt(20);

        Order order = null;

        if (isPurchase) { // Buy order
            if (type <= 1) { // Immediate order
                order = new BuyOrder(orderId, this, turnNumber, company.getIdentifier(), numberOfShares,
                        company.getLastPrice() + price, turnNumber + 1, false);
            } else if (type == 2) { // Order without a specific term
                order = new BuyOrder(orderId, this, turnNumber, company.getIdentifier(), numberOfShares,
                        company.getLastPrice() + price, -1, false);
            } else if (type == 3) { // Order valid until the end of a specified turn
                int numberOfTurns = random.nextInt(50);
                order = new BuyOrder(orderId, this, turnNumber, company.getIdentifier(), numberOfShares,
                        company.getLastPrice() + price, turnNumber + numberOfTurns, false);
            } else if (type == 4) { // Execute or cancel order
                order = new BuyOrder(orderId, this, turnNumber, company.getIdentifier(), numberOfShares,
                        company.getLastPrice() + price, turnNumber, true);
            }
        } else { // Sell order
            if (type <= 1) { // Immediate order
                order = new SellOrder(orderId, this, turnNumber, company.getIdentifier(), numberOfShares,
                        company.getLastPrice() + price, turnNumber, false);
            } else if (type == 2) { // Order without a specific term
                order = new SellOrder(orderId, this, turnNumber, company.getIdentifier(), numberOfShares,
                        company.getLastPrice() + price, -1, false);
            } else if (type == 3) { // Order valid until the end of a specified turn
                int numberOfTurns = random.nextInt(20);
                order = new SellOrder(orderId, this, turnNumber, company.getIdentifier(), numberOfShares,
                        company.getLastPrice() + price, turnNumber + numberOfTurns, false);
            } else if (type == 4) { // Execute or cancel order
                order = new SellOrder(orderId, this, turnNumber, company.getIdentifier(), numberOfShares,
                        company.getLastPrice() + price, turnNumber, true);
            }
        }

        return order; // Return the generated order
    }
}
