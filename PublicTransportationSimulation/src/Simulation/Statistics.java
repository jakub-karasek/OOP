package Simulation;

public class Statistics {
    private int[] numberOfRides;
    private int[] waitingTimes;
    private int numberOfDays;

    public Statistics(int numberOfDays){
        this.numberOfRides = new int[numberOfDays];
        this.waitingTimes = new int[numberOfDays];
        this.numberOfDays = numberOfDays;
    }

    public int getWaitingTime(int dayNumber) {
        return waitingTimes[dayNumber];
    }

    public int getNumberOfRides(int dayNumber) {
        return numberOfRides[dayNumber];
    }

    public void increaseWaitingTime(int dayNumber, int w) {
        waitingTimes[dayNumber] += w;
    }

    public void increaseNumberOfRides(int dayNumber, int w) {
        numberOfRides[dayNumber] += w;
    }
}
