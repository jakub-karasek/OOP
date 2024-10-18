package Vehicles;

import PriorityQueue.PriorityQueue;
import Route.Route;
import Route.Stop;
import Passengers.Passenger;
import Simulation.Losowanie;
import Events.ArrivalEvent;

public class Tram extends Vehicle {
    private Passenger[] passengers;
    private final Route route;
    private int destination;
    private final int startingPosition;
    private int position;
    private int time;
    private int startingTime;
    private final int routeLength;
    private boolean isOnLoop;

    public Tram(int nr, int capacity, Route route, int destination, int time) {
        this.number = nr;
        this.capacity = capacity;
        this.fillRate = 0;
        this.route = route;
        this.routeLength = route.getRouteLength();
        this.passengers = new Passenger[capacity];
        this.destination = destination;
        this.isOnLoop = false;

        if (destination == 1) {
            this.startingPosition = 0;
        } else {
            this.startingPosition = routeLength - 1;
        }

        this.position = startingPosition;
        this.startingTime = time;
        this.time = time;
    }

    public int getPosition() {
        return position;
    }

    public int getDirection() {
        return destination;
    }

    public int getTime() {
        return time;
    }

    public int getRouteLength() {
        return routeLength;
    }

    public Route getRoute() {
        return route;
    }

    public void setIsOnLoop(boolean bool) {
        this.isOnLoop = bool;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isOnLoop() {
        return isOnLoop;
    }

    public void changeDirection() {
        if (destination == 1) {
            destination = -1;
        } else {
            destination = 1;
        }
    }

    // Function that adds an arrival event of the tram at the stop to the event queue
    public void addArrival(PriorityQueue queue) {
        if (time > 23 * 60 && position == startingPosition && isOnLoop) return;

        queue.insert(new ArrivalEvent(time, this));
    }

    // Function that prepares the tram for its first trip of the day
    public void prepare() {
        position = startingPosition;
        fillRate = 0;

        for (int i = 0; i < capacity; i++) {
            passengers[i] = null;
        }

        if (position == 0) {
            destination = 1;
        } else {
            destination = -1;
        }

        isOnLoop = false;
        time = startingTime;
    }

    // Function that adds a passenger to the passenger array
    public void addPassenger(Passenger passenger) {
        int index = 0;

        while (passengers[index] != null) index++;

        passengers[index] = passenger;
        fillRate++;
    }

    // Function that removes a passenger from the passenger array
    public void removePassenger(Passenger passenger) {
        int index = 0;

        while (passengers[index] != passenger) index++;

        passengers[index] = null;
        fillRate--;
    }

    // Function that handles boarding passengers upon the tram's arrival
    public void boardPassengers(int dayNumber) {
        while (fillRate < capacity && !route.getStop(position).isEmpty()) {

            Stop destination = null;

            if (this.destination == 1)
                destination = route.getStop(Losowanie.losuj(position + 1, routeLength - 1));
            else
                destination = route.getStop(Losowanie.losuj(0, position - 1));

            Passenger newPassenger = route.getStop(position).transferPassenger();
            newPassenger.boardTram(time, number, destination, dayNumber);
            this.addPassenger(newPassenger);
        }
    }

    // Function that handles dropping off passengers upon the tram's arrival
    public void dropOffPassengers(int dayNumber) {
        int index = 0;

        while (!route.getStop(position).isFull() && fillRate > 0 && index < capacity) {
            if (passengers[index] != null) {
                if (route.getStop(position) == passengers[index].getDestination()) {
                    route.getStop(position).addPassenger(passengers[index]);
                    passengers[index].exitTram(time, number, dayNumber);
                    this.removePassenger(passengers[index]);
                }
            }

            index++;
        }
    }
}
