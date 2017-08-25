package com.shestakam.philosophers;

import java.util.List;
import java.util.concurrent.Semaphore;

public class Philosopher implements Runnable {

    private Integer philosopherNumber;
    private List<Boolean> forks;
    private List<Semaphore> semaphores;
    private boolean leftFork = false;
    private boolean rightFork = false;
    private boolean eating;
    private Integer eatCount = 0;
    private boolean isRunning = true;

    @Override
    public void run() {
        while (isRunning) {
            if (leftFork && rightFork) {
                eat();
            } else {
                think();
            }
        }
    }

    private void eat() {
        eatCount += 1;
        eating = true;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        putLeftFork();
        putRightFork();
    }

    private void think() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (philosopherNumber.equals(0)) {
            getRightFork();
            getLeftFork();
        } else {
            getLeftFork();
            getRightFork();
        }
    }

    private void getLeftFork() {
        try {
            semaphores.get(philosopherNumber).acquire();
            if (forks.get(philosopherNumber).booleanValue()) {
                forks.set(philosopherNumber, false);
                leftFork = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getRightFork() {
        try {
            semaphores.get((philosopherNumber + 1) % semaphores.size()).acquire();
            if (forks.get((philosopherNumber + 1) % forks.size())) {
                forks.set((philosopherNumber + 1) % forks.size() , false);
                rightFork = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void putLeftFork() {
        semaphores.get(philosopherNumber).release();
        forks.set(philosopherNumber, true);
        leftFork = false;
        eating = false;
    }

    private void putRightFork() {
        semaphores.get((philosopherNumber + 1) % semaphores.size()).release();
        forks.set((philosopherNumber + 1) % forks.size(), true);
        rightFork = false;
    }

    public void setSemaphore(List<Semaphore> semaphores) {
        this.semaphores = semaphores;
    }

    public void setPhilosopherNumber(int philosopherNumber) {
        this.philosopherNumber = philosopherNumber;
    }

    public void setForks(List<Boolean> forks) {
        this.forks = forks;
    }

    public boolean isEating() {
        return eating;
    }

    public int getPhilosopherNumber() {
        return philosopherNumber;
    }

    public Integer getEatCount() {
        return eatCount;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
