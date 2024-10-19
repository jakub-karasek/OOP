package Orders;

import Investors.Investor;

public class BuyOrder extends Order {

    public BuyOrder(int orderId, Investor investor, int turnNumber, String shareId, int quantity, int priceLimit, int deadline, boolean isWA) {
        super(orderId, investor, turnNumber, shareId, quantity, priceLimit, deadline, isWA);
        this.isPurchase = true;
    }

    // Method checks if the investor has sufficient funds for the purchase
    protected boolean checkSolvency() {
        return investor.getAccountBalance() > (quantity * priceLimit);
    }

    // Method executing the transaction from the order
    @Override
    public void executeTransaction(int quantity, int price) {
        this.quantity -= quantity;
        investor.buyShares(shareId, quantity, price);
    }

    @Override
    public int compareTo(Order o) {
        if (priceLimit > o.getPriceLimit()) return -1; // Order has a higher price limit
        else if (priceLimit < o.getPriceLimit()) return 1; // Order has a lower price limit

        // Orders have the same price limit
        if (turnNumber < o.getTurnNumber()) return -1; // Order was added in an earlier turn
        else if (turnNumber > o.getTurnNumber()) return 1; // Order was added in a later turn

        // Orders have the same turn number
        if (orderId < o.getOrderId()) return -1; // Order was added earlier
        return 1; // Order was added later
    }
}
