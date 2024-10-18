package Passengers;

import PriorityQueue.PriorityQueue;
import Route.Stop;
import Simulation.Losowanie;
import Events.PassengerArrivalEvent;

public class Passenger {
    private int id;
    private Stop stop;
    private Stop destination;
    private int numberOfRides;
    private int waitingTime;
    private int arrivalTime;

    public Passenger(int id, Stop stop) {
        this.id = id;
        this.stop = stop;
        this.destination = null;
        this.numberOfRides = 0;
        this.waitingTime = 0;
        this.arrivalTime = 0;

    }

    public Stop getStop() {
        return stop;
    }

    public int getId() {
        return id;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getNumberOfRides() {
        return numberOfRides;
    }

    public Stop getDestination() {
        return destination;
    }

    public void setNumberOfRides(int numberOfRides) {
        this.numberOfRides = numberOfRides;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void increaseWaitingTime(int waitingTime) {
        this.waitingTime += waitingTime;
    }

    // Function that adds the passenger's arrival at the stop to the event queue after leaving home
    public void addArrival(PriorityQueue queue) {
        int time  = Losowanie.losuj(6 * 60, 12 * 60);
        arrivalTime = time;
        queue.insert(new PassengerArrivalEvent(time, this));
    }

    // Function that handles the passenger boarding the tram
    public void boardTram(int time, int tramNumber, Stop destination, int dayNumber) {
        waitingTime += time - arrivalTime;
        numberOfRides++;
        this.destination = destination;

        System.out.printf("%d, %d:%02d : passenger %d boarded tram no %d intending to get to stop %s\n",
                dayNumber, time / 60, time % 60, id, tramNumber, destination.getName());
    }

    // Function that handles the passenger exiting the tram
    public void exitTram(int time, int tramNumber, int dayNumber) {
        arrivalTime = time;

        System.out.printf("%d, %d:%02d : passenger %d exited tram no %d at stop %s\n",
                dayNumber, time / 60, time % 60, id, tramNumber, destination.getName());
    }
}
