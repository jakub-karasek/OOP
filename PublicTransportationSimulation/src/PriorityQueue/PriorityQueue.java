package PriorityQueue;

import Events.Event;
import java.util.Arrays;
import java.util.Comparator;

public class PriorityQueue {
    private Event[] events;
    private int size;
    private int lastIndex;

    public PriorityQueue() {
        this.events = new Event[1];
        this.size = 1;
        this.lastIndex = 0;
    }

    public PriorityQueue(int size) {
        this.events = new Event[size];
        this.size = size;
        this.lastIndex = 0;
    }

    // Function to check if the queue is empty
    public boolean isEmpty() {
        return lastIndex == 0;
    }

    // Function to retrieve the first element from the queue
    public Event retrieve() {
        // Get the first element from the array
        Event result = events[0];

        int tempIndex = 0;

        // Shift remaining elements one position to the left
        while (tempIndex < size - 1 && events[tempIndex + 1] != null) {
            events[tempIndex] = events[tempIndex + 1];
            tempIndex++;
        }

        if (tempIndex != size - 1) {
            events[tempIndex] = events[tempIndex + 1];
        } else {
            events[tempIndex] = null;
        }

        lastIndex--;

        return result;
    }

    // Function to insert a new event into the queue, ensuring that elements remain sorted by time
    public void insert(Event newEvent) {
        if (lastIndex == size) { // The array is too small and needs to be increased
            // Create a new array with double the size
            Event[] temp = new Event[size * 2];

            // Copy the contents of the original array to the new array
            System.arraycopy(events, 0, temp, 0, size);

            // Assign the reference of the new array to the original reference
            events = temp;
            size *= 2;
        }

        lastIndex++;

        // Find the position where the new event should be inserted
        int index = Arrays.binarySearch(events, newEvent, new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                if (o1 == null) {
                    return 1; // null is greater than non-null
                } else if (o2 == null) {
                    return -1;
                } else {
                    return o1.compareTo(o2); // Comparison for non-null values
                }
            }
        });

        if (index < 0) {
            index = Math.abs(index) - 1;
        } else {
            while (index < size && events[index] != null && events[index].compareTo(newEvent) == 0) {
                index++;
            }
        }

        Event previous = newEvent;

        // Insert the new event in the correct position and shift all greater elements one position to the right
        while (index < size && previous != null) {
            Event temp = events[index];
            events[index] = previous;
            previous = temp;
            index++;
        }
    }
}
