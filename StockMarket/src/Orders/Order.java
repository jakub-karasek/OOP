package Orders;

import Investors.Investor;

public abstract class Order implements Comparable<Order> {
    protected int orderId; // Order identifier
    protected Investor investor; // Investor placing the order
    protected int turnNumber; // Turn number in which the order was placed
    protected String shareId; // Share identifier
    protected int quantity; // Number of shares in the order
    protected int priceLimit; // Price limit for the order
    protected int deadline; // Order validity deadline (-1 indicates no deadline)
    protected boolean isWA; // Is the order conditional
    protected boolean isPurchase; // Is the order a buy (true) or sell (false)

    // Constructor for the Order class.
    public Order(int orderId, Investor investor, int turnNumber, String shareId, int quantity, int priceLimit, int deadline, boolean isWA) {
        assert shareId.length() == 5 : "Share identifier must be 5 characters long";
        assert shareId.matches("[A-Z]+") : "Share identifier contains characters outside the A-Z range!";

        this.orderId = orderId;
        this.investor = investor;
        this.turnNumber = turnNumber;
        this.shareId = shareId;
        this.quantity = quantity;
        this.priceLimit = priceLimit;
        this.deadline = deadline;
        this.isWA = isWA;
    }

    // Abstract method to check the solvency of the order.
    protected abstract boolean checkSolvency();

    // Abstract method to execute the order transaction.
    public abstract void executeTransaction(int quantity, int price);

    // Method checking if the order is still valid in the given turn.
    public boolean isValid(int turnNumber) {
        if (deadline == -1) return checkSolvency();
        return checkSolvency() && turnNumber < deadline;
    }

    // Getters and setters
    public boolean isWA() {
        return isWA;
    }

    public String getShareId() {
        return shareId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPriceLimit() {
        return priceLimit;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public boolean isPurchase() {
        return isPurchase;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
