import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String args[]) {

        int philosophersNumber;
        int sumTotalWaitingTime = 0;
        //int averageTotalWaitingTime = 0;

        // Reading the number of the philosophers
        // It should be >=3 and <= 10
        Scanner sc;
        do {
            System.out.println("How many are the philosophers? (Select between 3-10)");
            sc = new Scanner(System.in);
            philosophersNumber = sc.nextInt();
        } while ((philosophersNumber < 3) || (philosophersNumber > 10));

        // Initialize arrays
        Philosopher[] PhilosopherArray  = new Philosopher[philosophersNumber];
        Fork[] forksArray               = new Fork[philosophersNumber];

        // Create forks
        for (int i = 0; i < philosophersNumber; i++) {
            forksArray[i] = new Fork();
        }

        for (int i = 0; i < philosophersNumber; i++) {

            PhilosopherArray[i] = new Philosopher(i+1, forksArray[(i+1) % philosophersNumber], forksArray[i]);

            if (i == philosophersNumber - 1) {
                PhilosopherArray[i].setNumberOfPhilosophers(philosophersNumber);
            }

        }

        ExecutorService executor = Executors.newFixedThreadPool(philosophersNumber);

        for (int i = 0; i < philosophersNumber; i++) {
            executor.execute(PhilosopherArray[i]);
        }

        executor.shutdown();

        System.out.println(executor.toString());

        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < philosophersNumber; i++) {
            System.out.println(PhilosopherArray[i].getTime());
            sumTotalWaitingTime += PhilosopherArray[i].getTime();
        }

        System.out.println("=================FINAL SUMMARY ============" );
        System.out.println("Total average waiting time for all philosophers: " + (sumTotalWaitingTime/philosophersNumber) +" sec");
        System.out.println("===========================================" );


    }
}