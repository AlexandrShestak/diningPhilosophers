package com.shestakam.philosophers;

public class BoundedSemaphore {
  private int signals;
  private int bound;

  public BoundedSemaphore(int upperBound){
    this.bound = upperBound;
    this.signals = 0;
  }

  public synchronized void take() throws InterruptedException{
    while(this.signals == bound) {
      wait();
    }
    this.signals++;
    this.notify();
  }

  public synchronized void release() throws InterruptedException{
    while(this.signals == 0) {
      wait();
    }
    this.signals--;
    this.notify();
  }
}