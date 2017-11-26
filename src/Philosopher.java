import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

class Philosopher extends Thread {
    private int tName, remainingTime, totalTime, eatingTimes;
    private final Fork leftFork;
    private final Fork rightFork;
    private boolean hasLeftFork, hasRightFork;
    private Date date;
    private SimpleDateFormat sdf;
    private Random randomNum;

    public Philosopher(int tName, Fork leftFork, Fork rightFork) {
        this.tName              = tName;
        this.leftFork           = leftFork;
        this.rightFork          = rightFork;
        this.remainingTime      = 20;
        this.eatingTimes        = 0;
        this.totalTime          = 0;
        this.sdf                = new SimpleDateFormat("hh:mm:ss a");
        this.randomNum          = new Random();

        System.out.println("Creating philosopher: " + tName);
    }

    public void run() {
        System.out.println("Running " +  tName );

        while (true) {
            // Philosopher is thinking
            this.think();

            // Philosopher is trying to pick the left fork
            this.hasLeftFork = this.leftFork.grabFork(tName, "LEFT");

            // Philosopher is trying to pick the right fork
            this.hasRightFork = this.rightFork.grabFork(tName, "RIGHT");

            // Philosopher is eating
            if (this.hasRightFork && this.hasLeftFork)
                this.eat();

            // Philosopher releases the right fork
            if (this.hasRightFork)
                this.hasRightFork = this.rightFork.releaseFork(tName, "RIGHT");

            // Philosopher releases the left fork
            if (this.hasLeftFork)
                this.hasLeftFork = this.leftFork.releaseFork(tName, "LEFT");

            // Checking if philosopher is done
            this.done();
        }
    }

    private void eat() {
        try {
            int eatingTime      = tName * 1000;

            this.date           = new Date();
            this.remainingTime  -= tName;
            this.eatingTimes++;

            System.out.println("#"+ tName + " Philosopher is EATING at time " + sdf.format(date) + " for " + tName + " seconds");

            Thread.sleep(eatingTime);
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    private void think() {
        this.date = new Date();

        System.out.println("#"+ this.tName + " Philosopher is THINKING at time " + sdf.format(date) );

        try {
            // Block thread for a random time
            int r = this.randomNum.nextInt(10);
            Thread.sleep((r+ 1) * 1000);
            this.totalTime+= r;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.hungry();
    }

    private void hungry() {
        this.date = new Date();

        System.out.println("#"+ this.tName + " Philosopher is HUNGRY at time " + sdf.format(date) );
    }

    private void done() {
        if (this.remainingTime <= 0) {
            try {
                System.out.println("================= SUMMARY =================" );
                System.out.println("#"+ tName + " Philosopher is DONE !!!" );
                System.out.println("Total times ate: " + this.eatingTimes );
                System.out.println("Average time waiting: " + (this.totalTime/this.eatingTimes) +" sec");
                System.out.println("===========================================" );
                this.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}