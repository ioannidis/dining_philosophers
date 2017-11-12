import java.time.*;

class Philosopher extends Thread {
    private int tName;
    private Fork leftFork;
    private Fork rightFork;
    private int totalTime;

    public Philosopher(int tName, Fork leftFork, Fork rightFork) {
        this.tName      = tName;
        this.leftFork   = leftFork;
        this.rightFork  = rightFork;
        this.totalTime  = 20;
        System.out.println("Creating philosopher: " + tName);
    }

    public void run() {
        System.out.println("Running " +  tName );
        Instant instant = Instant.now();
        while (true) {
            leftFork.grab();
            System.out.println("Philosopher " + tName + " grabs left fork at " + instant);
            rightFork.grab();
            System.out.println("Philosopher " + tName + " grabs right fork at " + instant);
            this.eat();
            leftFork.release();
            System.out.println("Philosopher " + tName + " releases left fork at " + instant);
            rightFork.release();
            System.out.println("Philosopher " + tName + " releases right fork at " + instant);
            this.done();
        }
    }

    private void eat() {
        try {
            int sleepTime = (tName + 1) * 1000;
            this.totalTime -= (tName + 1);
            System.out.println("Philosopher "+ tName + " eats for " + sleepTime);
            Thread.sleep(sleepTime);
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    private void done() {
        if (this.totalTime <= 0) {
            try {
                this.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}