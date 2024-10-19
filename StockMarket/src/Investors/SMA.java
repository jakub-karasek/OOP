package Investors;

import java.util.LinkedList;
import java.util.Queue;

public class SMA {
    private Queue<Integer> prices5; // Queue for the last 5 periods' prices
    private Queue<Integer> prices10; // Queue for the last 10 periods' prices
    private int sum5; // Sum of the last 5 periods' prices
    private int sum10; // Sum of the last 10 periods' prices

    // Constructor for the SMA class, initializes the queues and sums
    public SMA() {
        this.prices5 = new LinkedList<>(); // Initialize queue for SMA5
        this.prices10 = new LinkedList<>(); // Initialize queue for SMA10
        this.sum5 = 0; // Initialize sum for SMA5
        this.sum10 = 0; // Initialize sum for SMA10
    }

    // Method to add a new price for SMA calculations
    public void addPrice(int price) {
        // Add price to the SMA5 queue
        if (prices5.size() == 5) {
            sum5 -= prices5.poll(); // Remove the oldest price from the sum
        }
        prices5.add(price); // Add new price to the queue
        sum5 += price; // Add new price to the sum

        // Add price to the SMA10 queue
        if (prices10.size() == 10) {
            sum10 -= prices10.poll(); // Remove the oldest price from the sum
        }
        prices10.add(price); // Add new price to the queue
        sum10 += price; // Add new price to the sum
    }

    // Method returning SMA5
    public double getSMA5() {
        if (prices5.size() < 5) {
            throw new IllegalStateException("Not enough data to calculate SMA5"); // Exception if not enough data
        }
        return sum5 / 5.0; // Calculate and return SMA5
    }

    // Method returning SMA10
    public double getSMA10() {
        if (prices10.size() < 10) {
            throw new IllegalStateException("Not enough data to calculate SMA10"); // Exception if not enough data
        }
        return sum10 / 10.0; // Calculate and return SMA10
    }
}
