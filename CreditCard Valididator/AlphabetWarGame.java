import java.util.HashMap;
import java.util.Map;

public class AlphabetWarGame {

    private Map<Character, Integer> leftSideStrengths;
    private Map<Character, Integer> rightSideStrengths;

    // Default constructor with default strengths
    public AlphabetWarGame() {
        leftSideStrengths = new HashMap<>();
        rightSideStrengths = new HashMap<>();

        // Default left side strengths
        leftSideStrengths.put('w', 4);
        leftSideStrengths.put('p', 3);
        leftSideStrengths.put('b', 2);
        leftSideStrengths.put('s', 1);

        // Default right side strengths
        rightSideStrengths.put('m', 4);
        rightSideStrengths.put('q', 3);
        rightSideStrengths.put('d', 2);
        rightSideStrengths.put('z', 1);
    }

    // Parameterized constructor to allow custom strengths
    public AlphabetWarGame(Map<Character, Integer> leftSide, Map<Character, Integer> rightSide) {
        this.leftSideStrengths = leftSide;
        this.rightSideStrengths = rightSide;
    }

    // Method 1: Takes a single word, mixes both left and right side letters
    public String AlphabetWar(String word) {
        int leftScore = 0;
        int rightScore = 0;

        for (char ch : word.toCharArray()) {
            if (leftSideStrengths.containsKey(ch)) {
                leftScore += leftSideStrengths.get(ch);
            } else if (rightSideStrengths.containsKey(ch)) {
                rightScore += rightSideStrengths.get(ch);
            }
        }

        return decideWinner(leftScore, rightScore);
    }

    // Method 2: Takes two words, one for left side and one for right side
    public String AlphabetWar(String left, String right) {
        int leftScore = calculateScore(left, leftSideStrengths);
        int rightScore = calculateScore(right, rightSideStrengths);

        return decideWinner(leftScore, rightScore);
    }

    // Helper method to calculate the score for a given side
    private int calculateScore(String word, Map<Character, Integer> strengths) {
        int score = 0;
        for (char ch : word.toCharArray()) {
            if (strengths.containsKey(ch)) {
                score += strengths.get(ch);
            }
        }
        return score;
    }

    // Helper method to decide the winner based on scores
    private String decideWinner(int leftScore, int rightScore) {
        if (leftScore > rightScore) {
            return "Left side wins!";
        } else if (rightScore > leftScore) {
            return "Right side wins!";
        } else {
            return "Let's fight again!";
        }
    }

    // Test the class with the main method
    public static void main(String[] args) {
        AlphabetWarGame game1 = new AlphabetWarGame();

        // Default game tests
        System.out.println(game1.AlphabetWar("z")); // Right side wins!
        System.out.println(game1.AlphabetWar("zdqmwpbs")); // Let's fight again!
        System.out.println(game1.AlphabetWar("wwwwwwz")); // Left side wins!

        // Custom strengths
        Map<Character, Integer> customLeft = new HashMap<>();
        customLeft.put('a', 5); // Custom strength
        customLeft.put('w', 4);
        customLeft.put('p', 3);
        customLeft.put('b', 2);
        customLeft.put('s', 1);

        Map<Character, Integer> customRight = new HashMap<>();
        customRight.put('x', 5); // Custom strength
        customRight.put('m', 4);
        customRight.put('q', 3);
        customRight.put('d', 2);
        customRight.put('z', 1);

        AlphabetWarGame game2 = new AlphabetWarGame(customLeft, customRight);
        System.out.println(game2.AlphabetWar("awwb", "xxdz")); // Custom game test
    }
}
