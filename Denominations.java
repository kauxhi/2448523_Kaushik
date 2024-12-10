import java.util.Scanner;
import java.util.concurrent.*;

class Denominations {
    public static int countWays(int[] coins, int sum) {
        int[] var = new int[sum + 1];
        var[0] = 1;
        for (int coin : coins) {
            for (int i = coin; i <= sum; i++) {
                var[i] += var[i - coin];
            }
        }
        return var[sum];
    }
    public static void main(String[] args) {
        // Sample Case 1
        int[] coins1 = {1, 2, 3};
        int sum1 = 4;
        System.out.println("Example 1 Output: " + countWays(coins1, sum1));

        // Sample Case 2
        int[] coins2 = {2, 5, 3, 6};
        int sum2 = 10;
        System.out.println("Example 2 Output: " + countWays(coins2, sum2));

        // Create an executor with two threads
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // User input
        Future<Void> userInputFuture = executor.submit(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the number of coins: ");
            int n = scanner.nextInt();
            System.out.print("Enter the target sum: ");
            int targetSum = scanner.nextInt();
            int[] userCoins = new int[n];
            System.out.println("Enter the denominations:");
            for (int i = 0; i < n; i++) {
                userCoins[i] = scanner.nextInt();
            }
            scanner.close();
            // Displaying the result from the computation thread
            System.out.println("Number of possible ways: " + countWays(userCoins, targetSum));

            return null;
        });
        // Computation task
        Callable<Void> computationTask = () -> {
            return null;
        };
        // Submitting computation task
        Future<Void> computationFuture = executor.submit(computationTask);
        //Waiting for both tasks to complete
        try {
            userInputFuture.get();
            computationFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}




