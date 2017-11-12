import java.util.Scanner;

public class Main {

    public static void main(String args[]) {

        int philosophersNumber;

        // Reading the number of the philosophers
        // It should be >=3
        System.out.println("How many are the philosophers?");
        Scanner sc = new Scanner(System.in);
        philosophersNumber = sc.nextInt();

        Philosopher[] PhilosopherArray  = new Philosopher[philosophersNumber];
        Fork[] forksArray               = new Fork[philosophersNumber];

        // Create forks
        for (int i = 0; i < philosophersNumber; i++) {
            forksArray[i] = new Fork();
        }

        // Putting philosophers and forks in the arrays
        for (int i = 0; i < philosophersNumber; i++) {
            forksArray[i] = new Fork();
            PhilosopherArray[i] = new Philosopher(i, forksArray[i], forksArray[(i + 1) % philosophersNumber]);
        }

        // Start the threads
        for (int i = 0; i < philosophersNumber; i++) {
            PhilosopherArray[i].start();
        }

    }
}