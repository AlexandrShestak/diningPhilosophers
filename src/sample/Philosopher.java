package sample;

import java.util.List;

public class Philosopher extends Thread {

    private int philosopherNumber;
    private List<Boolean> forks;
    private boolean leftFork = false;
    private boolean rightFork = false;
    private boolean eating;
    private boolean thinking;


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
        System.out.println(Thread.currentThread().getName() + "eating");
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
        System.out.println(Thread.currentThread().getName() + "thinking");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getLeftFork();
        getRightFork();
    }

    private synchronized void getLeftFork() {
        if (forks.get(philosopherNumber).booleanValue()) {
            forks.set(philosopherNumber, false);
            leftFork = true;
        }
    }

    private synchronized void getRightFork() {
        if (forks.get(philosopherNumber + 1)) {
            forks.set(philosopherNumber + 1, false);
            rightFork = true;
        }
    }

    private synchronized void putLeftFork() {
        forks.set(philosopherNumber, true);
        leftFork = false;
        thinking = true;
        eating = false;
    }

    private synchronized void putRightFork() {
        forks.set(philosopherNumber + 1, true);
        rightFork = false;
    }
}
