package Route;

public class Route {
    private int routeLength;
    private Stop[] stops;
    private int[] travelTime;
    private int numberOfVehicles;

    public Route(int numberOfVehicles, int routeLength) {
        this.stops = new Stop[routeLength];
        this.travelTime = new int[routeLength];
        this.numberOfVehicles = numberOfVehicles;
        this.routeLength = routeLength;
    }

    public void setStop(int nr, Stop stop) {
        stops[nr] = stop;
    }

    public void setTravelTime(int nr, int czas) {
        travelTime[nr] = czas;
    }

    public Stop getStop(int nr) {
        return stops[nr];
    }

    public int getTravelTime(int nr) {
        return travelTime[nr];
    }

    public int getNumberOfVehicles() {
        return numberOfVehicles;
    }

    public int getRouteLength() {
        return routeLength;
    }

    // Function returning the total travel time in both directions, including the stop at the loop
    public int getTotalTravelTime() {
        int result = 0;

        for (int i = 0; i < routeLength; i++) {
            result += travelTime[i];
        }

        result *= 2;
        return result;
    }

    // Function to empty all stops of passengers
    public void emptyRoute() {
        for (int i = 0; i < routeLength; i++) {
            stops[i].emptyStop();
        }
    }
}
