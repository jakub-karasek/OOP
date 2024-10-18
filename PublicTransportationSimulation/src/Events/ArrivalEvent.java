package Events;

import PriorityQueue.PriorityQueue;
import Vehicles.Tram;

public class ArrivalEvent extends Event {
    private Tram tram;

    public ArrivalEvent(int time, Tram tram){
        this.time = time;
        this.tram = tram;

    }

    @Override
    public void executeEvent(PriorityQueue queue, int day) {
        int position = tram.getPosition();
        int direction = tram.getDirection();
        int RouteLength = tram.getRouteLength();
        int time = tram.getTime();

        if (tram.isOnLoop()) { // the tram is going to the loop

            // the tram waits at the loop and adds the arrival at the next stop to the queue
            System.out.printf("%d, %d:%02d : tram no %d is waiting at the loop\n", day, time/60, time%60, tram.getNumber());

            tram.setTime(time + tram.getRoute().getTravelTime(RouteLength - 1));
            tram.addArrival(queue);

            tram.changeDirection();

            tram.setIsOnLoop(false);

        }
        else { // the tram has arrived at the stop

            // Check if the tram should enter the loop
            if ((position == 0 && direction == -1) || (position == RouteLength - 1 && direction == 1)) { // the tram is going to the loop
                tram.setIsOnLoop(true);
            }

            System.out.printf("%d, %d:%02d : tram no %d has arrived at stop %s\n",
                    day, time/60, time%60, tram.getNumber(), tram.getRoute().getStop(position).getName());

            // drop off passengers
            tram.dropOffPassengers(day);

            // accept passengers if this is not the last stop on the line
            if (!(position == 0 && direction == -1) && !(position == RouteLength - 1 && direction == 1)) {
                tram.boardPassengers(day);
            }

            // updates data and prepares the tram for arrival at the next stop
            if (!tram.isOnLoop()) {

                if (direction == 1) {
                    time += tram.getRoute().getTravelTime(position);
                } else {
                    time += tram.getRoute().getTravelTime(position - 1);
                }

                tram.setPosition(position + direction);

            }

            tram.setTime(time);
            tram.addArrival(queue);

        }

    }
}
