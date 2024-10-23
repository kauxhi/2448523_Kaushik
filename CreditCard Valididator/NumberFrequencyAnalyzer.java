import java.util.*;

public class NumberFrequencyAnalyzer {
    // Static variable to store the input array of N numbers
    static int[] numbers;

    // Static method to find the top K numbers with the highest occurrences
    public static void findTopKFrequentNumbers(int K) {
        // Step 1: Create a HashMap to store the frequency of each number
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : numbers) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        // Step 2: Create a list to store frequency entries
        List<Map.Entry<Integer, Integer>> frequencyList = new ArrayList<>(frequencyMap.entrySet());

        // Step 3: Sort the list by frequency (descending) and by number (descending)
        Collections.sort(frequencyList, (a, b) -> {
            if (b.getValue().equals(a.getValue())) {
                return b.getKey() - a.getKey(); // If frequencies are the same, prioritize by number
            }
            return b.getValue() - a.getValue(); // Otherwise, sort by frequency
        });

        // Step 4: Print the top K numbers based on their frequencies
        System.out.print("Output: ");
        for (int i = 0; i < K && i < frequencyList.size(); i++) {
            System.out.print(frequencyList.get(i).getKey() + " ");
        }
        System.out.println(); // Move to the next line after output
    }

    public static void main(String[] args) {
        // Test case 1
        numbers = new int[]{3, 1, 4, 4, 5, 2, 6, 1}; // Given array
        int K1 = 2; // Given K
        System.out.println("Given array: " + Arrays.toString(numbers) + ", K = " + K1);
        findTopKFrequentNumbers(K1); // Call the method

        // Test case 2
        numbers = new int[]{7, 10, 11, 5, 2, 5, 5, 7, 11, 8, 9}; // Given array
        int K2 = 4; // Given K
        System.out.println("Given array: " + Arrays.toString(numbers) + ", K = " + K2);
        findTopKFrequentNumbers(K2); // Call the method
    }
}
