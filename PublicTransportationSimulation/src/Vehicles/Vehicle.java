package Vehicles;

import Route.Route;

public abstract class Vehicle {
    protected int number;
    protected int capacity;
    protected int fillRate;
    protected Route route;

    public int getNumber() {
        return number;
    }

}
