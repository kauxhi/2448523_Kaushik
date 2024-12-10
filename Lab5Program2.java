import java.util.Scanner;
interface WaterConservationSystem {
    int calculateTrappedWater(int[] blockHeights);
}
abstract class RainySeasonConservation implements WaterConservationSystem {
}
class CityBlockConservation extends RainySeasonConservation {

    @Override
    public int calculateTrappedWater(int[] blockHeights) {
        if (blockHeights == null || blockHeights.length <= 2) {
            return 0; // Not enough blocks to trap water
        }
        int n = blockHeights.length;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        // Calculating the maximum height to the left of each block
        leftMax[0] = blockHeights[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], blockHeights[i]);
        }
        // Calculating the maximum height to the right of each block
        rightMax[n - 1] = blockHeights[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], blockHeights[i]);
        }
        // Calculating trapped water for each block
        int trappedWater = 0;
        for (int i = 0; i < n; i++) {
            trappedWater += Math.min(leftMax[i], rightMax[i]) - blockHeights[i];
        }
        return trappedWater;
    }
}

public class Lab5Program2 {
    public static void main(String[] args) {
        // Test cases
        CityBlockConservation conservationSystem = new CityBlockConservation();
        int[] test1 = {3, 0, 0, 2, 0, 4};
        System.out.println("Test Case 1: " + conservationSystem.calculateTrappedWater(test1) + " units");
        int[] test2 = {3, 0, 2, 0, 4};
        System.out.println("Test Case 2: " + conservationSystem.calculateTrappedWater(test2) + " units");
        Scanner scanner = new Scanner(System.in);
        //user input
        System.out.print("Enter the number of blocks: ");
        int numBlocks = scanner.nextInt();
        int[] userBlocks = new int[numBlocks];
        System.out.println("Enter the heights of the blocks:");
        for (int i = 0; i < numBlocks; i++) {
            System.out.print("Block " + (i + 1) + ": ");
            userBlocks[i] = scanner.nextInt();
        }
        // Calculating and displaying trapped water for user input
        int userTrappedWater = conservationSystem.calculateTrappedWater(userBlocks);
        System.out.println("Trapped Water: " + userTrappedWater + " units");
        scanner.close();
    }
}