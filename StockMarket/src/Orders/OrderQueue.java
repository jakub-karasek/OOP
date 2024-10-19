package Orders;

import java.util.PriorityQueue;

public class OrderQueue {
    private PriorityQueue<Order> queue = new PriorityQueue<>();

    // Method to add an order to the queue
    public void add(Order order) {
        queue.add(order);
    }

    // Method to remove and return the order from the front of the queue
    public Order pop() {
        return queue.poll();
    }

    // Method to return the order from the front of the queue without removing it
    public Order peek() {
        return queue.peek();
    }

    // Method to check if the queue is empty
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
