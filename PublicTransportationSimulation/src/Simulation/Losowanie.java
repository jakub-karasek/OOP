package Simulation;
import java.util.Random;

public class Losowanie {
    public static int losuj(int from, int to){
        Random rand = new Random();
        return rand.nextInt(to - from + 1) + from;

    }
}
