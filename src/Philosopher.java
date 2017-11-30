import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

class Philosopher extends Thread {
    private int tName, remainingTime, totalTime, eatingTimes;
    private final Fork leftFork;
    private final Fork rightFork;
    private boolean hasLeftFork, hasRightFork, isDone;
    private Date date;
    private SimpleDateFormat sdf;
    private Random randomNum;

    private static int totalWaitingTimeForEveryOne = 0;
    private static int philosophersNum;
    private static int countDown;

    private static synchronized void increaseTotalWaitingTime(int time) {
        totalWaitingTimeForEveryOne += time;
        countDown--;
    }

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
            this.hasLeftFork = this.leftFork.grabFork(this,tName, "LEFT");

            // Philosopher is trying to pick the right fork
            this.hasRightFork = this.rightFork.grabFork(this, tName, "RIGHT");

            //this.eat();

            // Philosopher is eating
            if (this.hasRightFork && this.hasLeftFork)
                this.eat();

            // Philosopher releases the right fork
            //if (this.hasRightFork)
                this.hasRightFork = this.rightFork.releaseFork(this, tName, "RIGHT");

            // Philosopher releases the left fork
            //if (this.hasLeftFork)
               this.hasLeftFork = this.leftFork.releaseFork(this, tName, "LEFT");

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

            System.out.println("#"+ this.getName() + " Philosopher is EATING at time " + sdf.format(date) + " for " + tName + " seconds");

            Thread.sleep(eatingTime);
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    private void think() {
        this.date = new Date();

        System.out.println("#"+ this.getName() + " Philosopher is THINKING at time " + sdf.format(date) );

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

        System.out.println("#"+ this.getName() + " Philosopher is HUNGRY at time " + sdf.format(date) );
    }

    private void done() {
        if (this.remainingTime <= 0) {
                this.increaseTotalWaitingTime(this.totalTime);
                System.out.println("================= SUMMARY =================" );
                System.out.println("#"+ this.getName() + " Philosopher is DONE !!!" );
                System.out.println("Total times ate: " + this.eatingTimes );
                System.out.println("Average time waiting: " + (this.totalTime/this.eatingTimes) +" sec");
                System.out.println("===========================================" );


                if (countDown == 0) {
                    System.out.println("###========================================###");
                    System.out.println("The total average waiting time for all the philosophers is: " + this.totalWaitingTimeForEveryOne/philosophersNum + " seconds!");
                    System.out.println("###========================================###");
                }

                this.isDone = true;

        }
    }

    public int getTime() {
        return totalTime;
    }

    public void setNumberOfPhilosophers(int philosophersNumber) {
        this.philosophersNum = philosophersNumber;
        this.countDown = philosophersNumber;
    }
}