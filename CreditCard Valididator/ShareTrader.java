public class ShareTrader {
    
    // Static variable to store the maximum profit
    public static int maxProfit = 0;
    
    // Static method to find the maximum profit with at most two transactions
    public static int findMaxProfit(int[] price) {
        int n = price.length;
        if (n < 2) return 0; // No transaction is possible with less than 2 prices
        
        // Arrays to store the maximum profit up to each point
        int[] leftMaxProfit = new int[n];  // Max profit from the left side (first transaction)
        int[] rightMaxProfit = new int[n]; // Max profit from the right side (second transaction)
        
        // Calculate the maximum profit if we sell on each day for the first transaction
        int minPrice = price[0];
        for (int i = 1; i < n; i++) {
            minPrice = Math.min(minPrice, price[i]);
            leftMaxProfit[i] = Math.max(leftMaxProfit[i - 1], price[i] - minPrice);
        }
        
        // Calculate the maximum profit if we buy on each day for the second transaction
        int maxPrice = price[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            maxPrice = Math.max(maxPrice, price[i]);
            rightMaxProfit[i] = Math.max(rightMaxProfit[i + 1], maxPrice - price[i]);
        }
        
        // Calculate the combined maximum profit from both transactions
        maxProfit = 0;
        for (int i = 0; i < n; i++) {
            maxProfit = Math.max(maxProfit, leftMaxProfit[i] + rightMaxProfit[i]);
        }
        
        return maxProfit;
    }
    
    // Main method to test the solution
    public static void main(String[] args) {
        // Sample test cases
        int[] price1 = {10, 22, 5, 75, 65, 80};
        int[] price2 = {2, 30, 15, 10, 8, 25, 80};
        
        System.out.println("Maximum Profit for price1: " + findMaxProfit(price1));  // Output: 87
        System.out.println("Maximum Profit for price2: " + findMaxProfit(price2));  // Output: 100
    }
}
