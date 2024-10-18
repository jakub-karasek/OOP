package Route;

import Passengers.Passenger;

public class Stop {
    private String name;
    private int capacity;
    private int fillRate;
    private Passenger[] passengers;

    public Stop(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.passengers = new Passenger[capacity];
        this.fillRate = 0;
    }

    public String getName() {
        return name;
    }

    public boolean isFull() {
        return capacity == fillRate;
    }

    public boolean isEmpty() {
        return fillRate == 0;
    }

    // Function to add a passenger to the array of passengers
    public void addPassenger(Passenger passenger) {
        int index = 0;

        while (passengers[index] != null) index++;

        passengers[index] = passenger;
        fillRate++;
    }

    // Function to remove the first passenger from the array and return it as a result
    public Passenger transferPassenger() {
        if (fillRate == 0) return null;

        Passenger result = null;
        int index = 0;

        while (passengers[index] == null) index++;

        result = passengers[index];
        passengers[index] = null;
        fillRate--;

        return result;
    }

    // Function to remove passengers from the list, updating their waiting time at the end of the day
    public void emptyStop() {
        for (int i = 0; i < capacity; i++) {
            if (passengers[i] != null) {
                passengers[i].increaseWaitingTime(24 * 60 - passengers[i].getArrivalTime());
                passengers[i] = null;
            }
        }

        fillRate = 0;
    }
}
