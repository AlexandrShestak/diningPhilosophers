package com.shestakam.philosophers;

import com.shestakam.philosophers.BoundedSemaphore;
import com.shestakam.philosophers.Philosopher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

@Service
public class PhilosopherService {
    public static final int PHILOSOPHERS_NUMBER = 5;
    private List<Philosopher> philosophers;
    private List<Boolean> forks;
    private List<BoundedSemaphore> semaphores;

    public PhilosopherService() {
        forks = new ArrayList<>(PHILOSOPHERS_NUMBER);
        semaphores = new ArrayList<>(PHILOSOPHERS_NUMBER);
        for (int i = 0 ; i < PHILOSOPHERS_NUMBER ; i++) {
            forks.add(true);
            semaphores.add(new BoundedSemaphore(1));
        }
        philosophers = new ArrayList<>();
        for (int i = 0 ; i < PHILOSOPHERS_NUMBER ; i++) {
            Philosopher thread = new Philosopher();
            thread.setPhilosopherNumber(i);
            thread.setForks(forks);
            thread.setSemaphore(semaphores);
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
