package Simulation;

import PriorityQueue.PriorityQueue;
import Route.Route;
import Route.Stop;
import Passengers.Passenger;
import Vehicles.Tram;
import Events.Event;

import java.util.Scanner;

public class Simulation {
    private int numberOfDays;
    private Statistics statistics;
    private int passengerCapacity;
    private int numberOfStops;
    private Stop[] stops;
    private int numberOfPassengers;
    private Passenger[] passengers;
    private int tramCapacity;
    private int numberOfRoutes;
    private Route[] routes;
    private int numberOfTrams;
    private Tram[] trams;

    public Simulation() {
        // Creating a new simulation and loading data
        Scanner scanner = new Scanner(System.in);

        this.numberOfDays = scanner.nextInt();
        this.statistics = new Statistics(numberOfDays);
        this.passengerCapacity = scanner.nextInt();
        this.numberOfStops = scanner.nextInt();
        this.stops = new Stop[numberOfStops];

        // Creating the list of stops
        for (int i = 0; i < numberOfStops; i++) {
            String name = scanner.next();
            stops[i] = new Stop(name, passengerCapacity);
        }

        this.numberOfPassengers = scanner.nextInt();
        this.passengers = new Passenger[numberOfPassengers];

        // Creating the list of passengers
        for (int i = 0; i < numberOfPassengers; i++) {
            passengers[i] = new Passenger(i, stops[Losowanie.losuj(0, numberOfStops - 1)]);
        }

        this.tramCapacity = scanner.nextInt();
        this.numberOfRoutes = scanner.nextInt();
        this.routes = new Route[numberOfRoutes];

        this.numberOfTrams = 0;

        // Creating the list of routes
        for (int i = 0; i < numberOfRoutes; i++) {
            int numberOfVehicles = scanner.nextInt();
            numberOfTrams += numberOfVehicles;
            int routeLength = scanner.nextInt();
            routes[i] = new Route(numberOfVehicles, routeLength);

            for (int j = 0; j < routeLength; j++) {
                String name = scanner.next();
                routes[i].setStop(j, findStop(name));
                routes[i].setTravelTime(j, scanner.nextInt());
            }
        }

        this.trams = new Tram[numberOfTrams];
        initializeTrams();

        scanner.close();
    }

    private Stop findStop(String name) {
        for (int i = 0; i < numberOfStops; i++) {
            if (stops[i].getName().equals(name)) {
                return stops[i];
            }
        }
        return null;
    }

    // Function to initialize trams
    private void initializeTrams() {
        int nr = 0;

        for (int i = 0; i < numberOfRoutes; i++) {
            int timeInterval = routes[i].getTotalTravelTime() / routes[i].getNumberOfVehicles();
            int time = 6 * 60 - timeInterval;

            for (int j = 0; j < routes[i].getNumberOfVehicles(); j++) {
                int destination;
                if (j % 2 == 0) {
                    destination = 1;
                    time += timeInterval;
                } else {
                    destination = -1;
                }
                trams[nr] = new Tram(nr, tramCapacity, routes[i], destination, time);
                nr++;
            }
        }
    }

    // Function that performs one day of simulation
    private void daySimulation(PriorityQueue queue, int dayNumber) {
        // Resetting trams to their starting positions and adding the next action of each tram to the queue

        for (int i = 0; i < numberOfTrams; i++) {
            trams[i].prepare();
            trams[i].addArrival(queue);
        }

        // Randomizing the arrival times of passengers at the stops and adding them to the event queue

        for (int i = 0; i < numberOfPassengers; i++) {
            passengers[i].addArrival(queue);
        }

        // The main simulation loop executes operations from the queue until it is empty
        while (!queue.isEmpty()) {
            Event event = queue.retrieve();
            event.executeEvent(queue, dayNumber);
        }

        // Emptying all stops at midnight

        for (int i = 0; i < numberOfRoutes; i++) {
            routes[i].emptyRoute();
        }

        // Collecting statistics from passengers and resetting them

        for (int i = 0; i < numberOfPassengers; i++) {
            statistics.increaseNumberOfRides(dayNumber, passengers[i].getNumberOfRides());
            passengers[i].setNumberOfRides(0);
            statistics.increaseWaitingTime(dayNumber, passengers[i].getWaitingTime());
            passengers[i].setWaitingTime(0);
        }
    }

    // Function that conducts the entire simulation
    public void executeSimulation() {
        PriorityQueue queue = new PriorityQueue(numberOfTrams);

        // Running the simulation for all days
        for (int i = 0; i < numberOfDays; i++) {
            daySimulation(queue, i);
        }

        // Printing the statistics of the simulation
        int totalRides = 0;
        int totalWaitingTime = 0;

        for (int i = 0; i < numberOfDays; i++) {
            System.out.printf("Day %d of the simulation: number of rides is %d, total waiting time is %d\n", i,
                    statistics.getNumberOfRides(i), statistics.getWaitingTime(i));

            totalRides += statistics.getNumberOfRides(i);
            totalWaitingTime += statistics.getWaitingTime(i);
        }

        System.out.printf("In the entire simulation, the number of rides is %d, and the average waiting time is %f",
                totalRides, (float) totalWaitingTime / totalRides);
    }
}
