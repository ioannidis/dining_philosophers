import java.util.Scanner;

public class Main {

    public static void main(String args[]) {

        int philosophersNumber;
        int averageTotalWaitingTime = 0;

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

        // Adding philosophers and forks in the arrays
        for (int i = 0; i < philosophersNumber; i++) {

            // Trick to avoid deadlock
            if (i == PhilosopherArray.length - 1) {
                PhilosopherArray[i] = new Philosopher(i+1, forksArray[(i + 1) % philosophersNumber], forksArray[i]);
                PhilosopherArray[i].setNumberOfPhilosophers(philosophersNumber);
            } else {
                PhilosopherArray[i] = new Philosopher(i+1, forksArray[i], forksArray[(i + 1) % philosophersNumber]);
            }

        }

        // Start the threads
        for (int i = 0; i < philosophersNumber; i++) {
            PhilosopherArray[i].start();
        }

    }
}