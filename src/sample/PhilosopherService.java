package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shestakam on 07.03.2016.
 */
public class PhilosopherService {
    public static final int PHILOSOPHERS_NUMBER = 5;
    private List<Philosopher> philosophers;
    private List<Boolean> forks;


    public PhilosopherService() {
        forks = new ArrayList<Boolean>(PHILOSOPHERS_NUMBER){
            @Override
            public Boolean get(int index) {
                Boolean aBoolean = false;
                try {
                    aBoolean = super.get(index % this.size());
                } catch (IndexOutOfBoundsException e) {
                    String breakpointstring = "str";
                    System.out.println("!!!!!!!!!!!!!!!");
                }
                return aBoolean;
            }
        };
        for (int i = 0 ; i < PHILOSOPHERS_NUMBER ; i++) {
            forks.add(true);
        }
        philosophers = new ArrayList<>();
        for (int i = 0 ; i < PHILOSOPHERS_NUMBER ; i++) {
            Philosopher thread = new Philosopher();
            thread.setName("Philosopher number " + i);
            thread.setPhilosopherNumber(i);
            thread.setForks(forks);
            philosophers.add(thread);
        }
    }

    public List<Philosopher> getPhilosophers() {
        return philosophers;
    }

    public List<Boolean> getForks() {
        return forks;
    }
}
