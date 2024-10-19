package Companies;

import Orders.OrderQueue;
import Orders.Order;

public class CompanyOrders {
    private String shareId;
    private Company company;
    private OrderQueue sellQueue;
    private OrderQueue buyQueue;

    // Constructor initializing the CompanyOrders object
    public CompanyOrders(String shareId, Company company) {
        this.shareId = shareId;
        this.company = company;
        this.buyQueue = new OrderQueue();
        this.sellQueue = new OrderQueue();
    }

    // Function to add a sell order to the queue
    public void addSellOrder(Order order) {
        sellQueue.add(order);
    }

    // Function to add a buy order to the queue
    public void addBuyOrder(Order order) {
        buyQueue.add(order);
    }

    // Function to check if any orders can still be executed
    private boolean isEndOfTurn() {
        if (sellQueue.isEmpty() || buyQueue.isEmpty()) {
            return true;
        }
        if (buyQueue.peek().getPriceLimit() < sellQueue.peek().getPriceLimit()) {
            return true;
        }
        return false;
    }

    // Function to remove expired and impossible orders
    private void removeInvalidOrders(int turnNumber) {
        while (!sellQueue.isEmpty() &&
                (!sellQueue.peek().isValid(turnNumber) || sellQueue.peek().getQuantity() == 0)) {
            sellQueue.pop();
        }
        while (!buyQueue.isEmpty() &&
                (!buyQueue.peek().isValid(turnNumber) || buyQueue.peek().getQuantity() == 0)) {
            buyQueue.pop();
        }
    }

    // Function executing the first order in the queue
    private void executeOrder(int turnNumber) {
        removeInvalidOrders(turnNumber);

        // Check whether to execute the first buy order or sell order
        boolean isPurchase = true;
        boolean isEnd = isEndOfTurn();

        if (buyQueue.isEmpty() || sellQueue.isEmpty()) {
            return;
        }
        if (buyQueue.peek().getOrderId() > sellQueue.peek().getOrderId()) {
            isPurchase = false;
        }

        OrderQueue queue1;
        OrderQueue queue2;
        Order order;

        if (isPurchase) {
            queue1 = buyQueue;
            queue2 = sellQueue;
            order = buyQueue.pop();
        } else {
            queue1 = sellQueue;
            queue2 = buyQueue;
            order = sellQueue.pop();
        }

        Order order2;
        while (order.getQuantity() > 0 && !isEnd) {
            if (!order.isWA()) { // Order is not a "fill or kill"
                if (!queue2.peek().isWA()) { // Opposite side order is not "fill or kill"
                    order2 = queue2.pop();
                    int quantity = Math.min(order.getQuantity(), order2.getQuantity());
                    int price = order.getPriceLimit();
                    if (order.getOrderId() > order2.getOrderId()) {
                        price = order2.getPriceLimit();
                    }
                    order.executeTransaction(quantity, price);
                    order2.executeTransaction(quantity, price);
                    queue1.add(order);
                    queue2.add(order2);
                    return;
                } else { // Opposite side order is "fill or kill"
                    OrderQueue tempQueue = new OrderQueue();
                    order2 = queue2.pop();
                    order2.setQuantity(order2.getQuantity() - order.getQuantity());
                    executeWA(order2, tempQueue, queue2, queue1);
                }
            } else {
                OrderQueue tempQueue = new OrderQueue();
                executeWA(order, tempQueue, queue1, queue2);
            }
        }
    }

    // Function executing orders until the end of the turn
    public void executeOrders(int turnNumber) {
        while (!isEndOfTurn()) {
            executeOrder(turnNumber);
        }
    }

    // Function executing "fill or kill" orders
    private boolean executeWA(Order order, OrderQueue currentQueue,
                              OrderQueue oppositeQueue, OrderQueue tempQueue) {
        int howManyLeft = order.getQuantity();
        Order order2;

        while (howManyLeft > 0) {
            if (order.isPurchase() && oppositeQueue.peek().getPriceLimit() > order.getPriceLimit()) {
                return false;
            } else if (!order.isPurchase() && oppositeQueue.peek().getPriceLimit() < order.getPriceLimit()) {
                return false;
            }

            order2 = oppositeQueue.pop();
            tempQueue.add(order2);

            if (!order2.isWA()) {
                if (howManyLeft > order2.getQuantity()) {
                    howManyLeft -= order2.getQuantity();
                } else {
                    int price = order.getPriceLimit();
                    if (order.getOrderId() > order2.getOrderId()) {
                        price = order2.getPriceLimit();
                    }
                    order.executeTransaction(howManyLeft, price);
                    order2.executeTransaction(howManyLeft, price);

                    if (order2.getQuantity() > 0) {
                        oppositeQueue.add(order2);
                    }
                }
            } else {
                if (order2.getQuantity() < howManyLeft) {
                    howManyLeft -= order2.getQuantity();
                } else {
                    order2.setQuantity(order2.getQuantity() - howManyLeft);
                    OrderQueue tempQueue2 = new OrderQueue();
                    if (!executeWA(order2, oppositeQueue, currentQueue, tempQueue2)) {
                        while (!tempQueue2.isEmpty()) {
                            currentQueue.add(tempQueue2.pop());
                        }
                        tempQueue.pop();
                    } else {
                        int price = order.getPriceLimit();
                        if (order.getOrderId() > order2.getOrderId()) {
                            price = order2.getPriceLimit();
                        }
                        order.executeTransaction(howManyLeft, price);
                        order2.executeTransaction(howManyLeft, price);
                        order.setQuantity(order.getQuantity() - howManyLeft);
                        howManyLeft = 0;
                    }
                }
            }
        }

        while (!tempQueue.isEmpty()) {
            order2 = tempQueue.pop();
            int price = order.getPriceLimit();
            if (order.getOrderId() > order2.getOrderId()) {
                price = order2.getPriceLimit();
            }
            order.executeTransaction(Math.max(order.getQuantity(), order2.getQuantity()), price);
            order2.executeTransaction(Math.max(order.getQuantity(), order2.getQuantity()), price);
        }

        return true;
    }
}
