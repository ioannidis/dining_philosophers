import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

class Philosopher extends Thread {
    private int tName, remainingTime, totalTime, eatingTimes, averageTotalTime;
    private final Fork leftFork;
    private final Fork rightFork;
    private boolean hasLeftFork, hasRightFork, isDone;
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

        while (!isDone) {
            // Philosopher is thinking
            this.think();

            // Philosopher is trying to pick the left fork
            this.hasLeftFork = this.leftFork.grabFork(this, "LEFT");

            // Philosopher is trying to pick the right fork
            this.hasRightFork = this.rightFork.grabFork(this, "RIGHT");


            // Philosopher is eating
            if (this.hasRightFork && this.hasLeftFork)
                this.eat();

            // Philosopher releases the right fork
            this.hasRightFork = this.rightFork.releaseFork(this,"RIGHT");

            // Philosopher releases the left fork
            this.hasLeftFork = this.leftFork.releaseFork(this, "LEFT");

            // Checking if philosopher is done
            this.done();
        }
    }

    // Eating condition
    private void eat() {
        try {
            int eatingTime      = this.tName * 1000;

            this.date           = new Date();
            this.remainingTime  -= this.tName;
            this.eatingTimes++;

            System.out.println("#"+ this.getName() + " is EATING at time " + this.sdf.format(date) + " for " + this.tName + " seconds");

            Thread.sleep(eatingTime);
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    // Thinking condition
    private void think() {
        this.date = new Date();

        System.out.println("#"+ this.getName() + " is THINKING at time " + this.sdf.format(date) );

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

    private void endThink() {
        try {
            this.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Hungry condition
    private void hungry() {
        this.date = new Date();

        System.out.println("#"+ this.getName() + " is HUNGRY at time " + this.sdf.format(date) );
    }

    private void done() {
        if (this.remainingTime <= 0) {
            this.averageTotalTime = this.totalTime/this.eatingTimes;

            System.out.println("================= SUMMARY =================" );
            System.out.println("#"+ this.getName() + " is DONE and now is THINKING!!!" );
            System.out.println("Total times ate: " + this.eatingTimes );
            System.out.println("Average time waiting: " + this.averageTotalTime +" sec");
            System.out.println("===========================================" );

            this.isDone = true;
            this.endThink();

        }
    }

    // Returns the total average time for this philosopher
    public int getAverageTime() {
        return this.averageTotalTime;
    }

}