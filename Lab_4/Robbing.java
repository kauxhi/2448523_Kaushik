import java.util.Arrays;
import java.util.Scanner;

abstract class Robber {
    abstract int RowHouses(int[] money);
    abstract int RoundHouses(int[] money);
    abstract int SquareHouse(int[] money);
    abstract int MultiHouseBuilding(int[] type1, int[] type2, int[] type3, int[] type4);
    void RobbingClass() {
        System.out.println("MSc AI & ML");
    }
    void MachineLearning() {
        System.out.println("I love Machine Learning.");
    }
}

class JAVAProfessionalRobber extends Robber {
    @Override
    int RowHouses(int[] money) {
        int n = money.length;
        if (n == 0) return 0;
        if (n == 1) return money[0];
        int[] dp = new int[n];
        dp[0] = money[0];
        dp[1] = Math.max(money[0], money[1]);
        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + money[i]);
        }
        return dp[n - 1];
    }

    @Override
    int RoundHouses(int[] money) {
        int n = money.length;
        if (n == 0) return 0;
        if (n == 1) return money[0];
        return Math.max(RowHouses(Arrays.copyOfRange(money, 0, n - 1)),
                RowHouses(Arrays.copyOfRange(money, 1, n)));
    }

    @Override
    int SquareHouse(int[] money) {
        int n = money.length;
        if (n == 0) return 0;
        if (n == 1) return money[0];
        int[] dp = new int[n];
        dp[0] = money[0];
        dp[1] = Math.max(money[0], money[1]);
        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + money[i]);
        }
        return dp[n - 1];
    }

    @Override
    int MultiHouseBuilding(int[] type1, int[] type2, int[] type3, int[] type4) {
    int n = type1.length;
    if (n == 0) return 0;
    if (n == 1) return Math.max(type1[0], Math.max(type2[0], Math.max(type3[0], type4[0])));
    int[][] dp = new int[n][4];
    for (int j = 0; j < 4; j++) {
        dp[0][j] = getTypeValue(type1, type2, type3, type4, 0, j);
    }
    for (int i = 1; i < n; i++) {
        for (int j = 0; j < 4; j++) {
            int maxAmountWithoutAdj = 0;
            for (int k = 0; k < 4; k++) {
                if (k != j) {
                    maxAmountWithoutAdj = Math.max(maxAmountWithoutAdj, dp[i - 1][k]);
                }
            }
            dp[i][j] = maxAmountWithoutAdj + getTypeValue(type1, type2, type3, type4, i, j);
        }
    }
    return Math.max(Math.max(Math.max(dp[n - 1][0], dp[n - 1][1]), dp[n - 1][2]), dp[n - 1][3]);
}
private int getTypeValue(int[] type1, int[] type2, int[] type3, int[] type4, int i, int j) {
    switch (j) {
        case 0:
            return type1[i];
        case 1:
            return type2[i];
        case 2:
            return type3[i];
        case 3:
            return type4[i];
        default:
            return 0;
    }
}

}

public class Robbing {
    public static void main(String[] args) {
        JAVAProfessionalRobber robber = new JAVAProfessionalRobber();
        Scanner scanner = new Scanner(System.in);

        try {
            // Test Cases
            int[] testRowHouses = {1, 2, 3, 0};
            int[] testRoundHouses = {1, 2, 3, 4};
            int[] testSquareHouses = {5, 10, 2, 7};
            int[] testMultiHouseBuilding1 = {5, 3, 8, 2};
            int[] testMultiHouseBuilding2 = {10, 12, 7, 6};
            int[] testMultiHouseBuilding3 = {4, 9, 11, 5};
            int[] testMultiHouseBuilding4 = {8, 6, 3, 7};
            int resultRowHouses = robber.RowHouses(testRowHouses);
            int resultRoundHouses = robber.RoundHouses(testRoundHouses);
            int resultSquareHouses = robber.SquareHouse(testSquareHouses);
            int resultMultiHouseBuilding = robber.MultiHouseBuilding(
                    testMultiHouseBuilding1, testMultiHouseBuilding2, testMultiHouseBuilding3, testMultiHouseBuilding4);

            System.out.println("RowHouses Test Case: " + Arrays.toString(testRowHouses) + " -> " + resultRowHouses);
            System.out.println("RoundHouses Test Case: " + Arrays.toString(testRoundHouses) + " -> " + resultRoundHouses);
            System.out.println("SquareHouses Test Case: " + Arrays.toString(testSquareHouses) + " -> " + resultSquareHouses);
            System.out.println("MultiHouseBuilding Test Case: " +
                    Arrays.toString(testMultiHouseBuilding1) + ", " +
                    Arrays.toString(testMultiHouseBuilding2) + ", " +
                    Arrays.toString(testMultiHouseBuilding3) + ", " +
                    Arrays.toString(testMultiHouseBuilding4) + " -> " + resultMultiHouseBuilding);
            System.out.println("Choose a function to run:");
            System.out.println("1. RowHouses");
            System.out.println("2. RoundHouses");
            System.out.println("3. SquareHouses");
            System.out.println("4. MultiHouseBuilding");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.println("Enter prices for RowHouses (comma-separated): ");
                    int[] rowHouses = parseInput(scanner.nextLine());
                    int resultRowHousesInput = robber.RowHouses(rowHouses);
                    System.out.println("RowHouses Result: " + resultRowHousesInput);
                    break;

                case 2:
                    System.out.println("Enter prices for RoundHouses (comma-separated): ");
                    int[] roundHouses = parseInput(scanner.nextLine());
                    int resultRoundHousesInput = robber.RoundHouses(roundHouses);
                    System.out.println("RoundHouses Result: " + resultRoundHousesInput);
                    break;

                case 3:
                    System.out.println("Enter prices for SquareHouses (comma-separated): ");
                    int[] squareHouses = parseInput(scanner.nextLine());
                    int resultSquareHousesInput = robber.SquareHouse(squareHouses);
                    System.out.println("SquareHouses Result: " + resultSquareHousesInput);
                    break;

                case 4:
                    System.out.println("Enter prices for MultiHouseBuilding (type1, type2, type3, type4) - each comma-separated: ");
                    int[] type1 = parseInput(scanner.next());
                    int[] type2 = parseInput(scanner.next());
                    int[] type3 = parseInput(scanner.next());
                    int[] type4 = parseInput(scanner.next());
                    int resultMultiHouseBuildingInput = robber.MultiHouseBuilding(type1, type2, type3, type4);
                    System.out.println("MultiHouseBuilding Result: " + resultMultiHouseBuildingInput);
                    break;

                default:
                    System.out.println("Invalid choice. Please choose a number from 1 to 4.");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid numerical choice.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static int[] parseInput(String input) {
        String[] values = input.split(",");
        if (values.length > 4) {
            throw new IllegalArgumentException("Please provide at most 4 values.");
        }
        int[] result = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = Integer.parseInt(values[i].trim());
        }
        return result;
    }
}


