package Events;

import PriorityQueue.PriorityQueue;

public abstract class Event implements Comparable<Event> {
    protected int time;

    // Function handling the given event
    public abstract void executeEvent(PriorityQueue queue, int day);

    public int getTime() {
        return time;
    }

    @Override
    public int compareTo(Event other) {
        // Comparing objects based on time
        if (other == null) return -1;
        return Integer.compare(this.time, other.getTime());
    }
}
