package sample;

import java.util.List;

public class Philosopher extends Thread {

    private int philosopherNumber;
    private List<Boolean> forks;
    private boolean leftFork = false;
    private boolean rightFork = false;


    public void setPhilosopherNumber(int philosophernumber) {
        this.philosopherNumber = philosophernumber;
    }

    public void setForks(List<Boolean> forks) {
        this.forks = forks;
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
        putLeftFork();
        putRightFork();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void think() {
        System.out.println(Thread.currentThread().getName() + "thinking");
        getLeftFork();
        getRightFork();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
    }

    private synchronized void putRightFork() {
        forks.set(philosopherNumber + 1, true);
        rightFork = false;
    }
}
