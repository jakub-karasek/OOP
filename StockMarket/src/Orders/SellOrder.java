package Orders;

import Investors.Investor;

public class SellOrder extends Order {

    public SellOrder(int orderId, Investor investor, int turnNumber, String shareId, int quantity, int priceLimit, int deadline, boolean isWA) {
        super(orderId, investor, turnNumber, shareId, quantity, priceLimit, deadline, isWA);
        this.isPurchase = false;
    }

    // Function checks if the investor has enough shares to sell
    protected boolean checkSolvency() {
        return investor.numberOfShares(shareId) > (quantity);
    }

    // Function executing the transaction from the order
    @Override
    public void executeTransaction(int quantity, int price) {
        this.quantity -= quantity;
        investor.sellShares(shareId, quantity, price);
    }

    @Override
    public int compareTo(Order o) {
        if (priceLimit > o.getPriceLimit()) return 1; // The order has a higher price limit
        else if (priceLimit < o.getPriceLimit()) return -1; // The order has a lower price limit

        // Orders have the same price limit

        if (turnNumber < o.getTurnNumber()) return -1; // The order was placed in an earlier turn
        else if (turnNumber > o.getTurnNumber()) return 1; // The order was placed in a later turn

        // Orders have the same turn number

        if (orderId < o.getOrderId()) return -1; // The order was placed earlier
        return 1; // The order was placed later
    }
}
