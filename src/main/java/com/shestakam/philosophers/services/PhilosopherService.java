package com.shestakam.philosophers.services;

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
    private List<Semaphore> semaphores;

    public PhilosopherService() {
        forks = new ArrayList<>(PHILOSOPHERS_NUMBER);
        semaphores = new ArrayList<>(PHILOSOPHERS_NUMBER);
        for (int i = 0 ; i < PHILOSOPHERS_NUMBER ; i++) {
            forks.add(true);
            semaphores.add(new Semaphore(1, true));
        }
        philosophers = new ArrayList<>();
        for (int i = 0 ; i < PHILOSOPHERS_NUMBER ; i++) {
            Philosopher philosopher = new Philosopher();
            philosopher.setPhilosopherNumber(i);
            philosopher.setForks(forks);
            philosopher.setSemaphore(semaphores);
            philosophers.add(philosopher);
        }
    }

    public List<Philosopher> getPhilosophers() {
        return philosophers;
    }

    public List<Boolean> getForks() {
        return forks;
    }
}
