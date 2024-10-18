package Events;

import PriorityQueue.PriorityQueue;
import Passengers.Passenger;

public class PassengerArrivalEvent extends Event {

    private Passenger passenger;

    public PassengerArrivalEvent(int time, Passenger passenger) {
        this.time = time;
        this.passenger = passenger;
    }

    @Override
    public void executeEvent(PriorityQueue queue, int day) {

        if (passenger.getStop().isFull()) { // there is no space at the stop

            System.out.printf("%d, %d:%02d : passenger %d returned home due to lack of space at the stop\n",
                    day, time / 60, time % 60, passenger.getId());

            passenger.setArrivalTime(24 * 60);
        } else {

            passenger.getStop().addPassenger(passenger);

            System.out.printf("%d, %d:%02d : passenger %d arrived at stop %s\n",
                    day, time / 60, time % 60, passenger.getId(), passenger.getStop().getName());

        }

    }
}
