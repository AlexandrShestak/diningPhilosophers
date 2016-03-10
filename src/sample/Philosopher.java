package sample;

import javafx.application.Platform;

import java.util.List;

public class Philosopher extends Thread {

    private Integer philosopherNumber;
    private List<Boolean> forks;
    private List<BoundedSemaphore> semaphores;
    private boolean leftFork = false;
    private boolean rightFork = false;
    private boolean eating;
    private boolean thinking;
    private Integer eatCount = 0;

    public void setPhilosopherNumber(int philosopherNumber) {
        this.philosopherNumber = philosopherNumber;
    }

    public void setForks(List<Boolean> forks) {
        this.forks = forks;
    }

    public boolean isEating() {
        return eating;
    }

    public boolean isThinking() {
        return thinking;
    }

    public int getPhilosopherNumber() {
        return philosopherNumber;
    }

    @Override
    public void run() {
        while (true) {
            if (leftFork && rightFork) {
                eat();
            } else {
                think();
            }
        }
    }

    private void eat() {
        Platform.runLater(() ->  eatCount++);
        eating = true;
        thinking = false;
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
        getLeftFork();
        getRightFork();
    }

    private void getLeftFork() {
        try {
            semaphores.get(philosopherNumber).take();
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
            semaphores.get((philosopherNumber + 1) % semaphores.size()).take();
            if (forks.get((philosopherNumber + 1) % forks.size())) {
                forks.set((philosopherNumber + 1) % forks.size() , false);
                rightFork = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void putLeftFork() {
        try {
            semaphores.get(philosopherNumber).release();
            forks.set(philosopherNumber, true);
            leftFork = false;
            thinking = true;
            eating = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void putRightFork() {
        try {
            semaphores.get((philosopherNumber + 1) % semaphores.size()).release();
            forks.set((philosopherNumber + 1) % forks.size(), true);
            rightFork = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setSemaphore(List<BoundedSemaphore> semaphores) {
        this.semaphores = semaphores;
    }
}
