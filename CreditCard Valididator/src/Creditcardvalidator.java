import java.util.Scanner;

public class CreditCardvalidator { // Class name matches file name

    private String ccNumber; // Credit card number

    // Constructor to initialize credit card number
    public CreditCardvalidator(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    // Main function to drive the program
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for the credit card number
        System.out.print("Enter your credit card number: ");
        String ccNumber = scanner.nextLine();

        // Create a CreditCardValidator object
        CreditCardvalidator validator = new CreditCardvalidator(ccNumber);

        // Validate the card number based on the given rules
        validator.validate();

        scanner.close();
    }

    // Function to validate the credit card number
    public void validate() {
        // Check if the number is between 8 and 9 digits
        if (ccNumber.length() < 8 || ccNumber.length() > 9) {
            System.out.println("Invalid credit card number");
        } else {
            // Use switch-case to perform the validation steps
            switch (ccNumber.length()) {
                case 8:
                case 9:
                    if (validateCreditCard()) {
                        System.out.println("Valid credit card number");
                    } else {
                        System.out.println("Invalid credit card number");
                    }
                    break;

                default:
                    System.out.println("Invalid credit card number length");
                    break;
            }
        }
    }

    // Method to validate credit card using the steps
    public boolean validateCreditCard() {
        // Step a: Remove the last digit of the ccNumber
        int originalLastDigit = Integer.parseInt(ccNumber.substring(ccNumber.length() - 1));
        String withoutLastDigit = ccNumber.substring(0, ccNumber.length() - 1);
        System.out.println("Step a: Without last digit: " + withoutLastDigit);
        System.out.println("Original last digit: " + originalLastDigit);

        // Step b: Reverse the remaining digits
        String reversed = new StringBuilder(withoutLastDigit).reverse().toString();
        System.out.println("Step b: Reversed digits: " + reversed);

        // Step c: Double the digits in the odd-numbered positions
        int sum = 0;
        for (int i = 0; i < reversed.length(); i++) {
            int digit = Character.getNumericValue(reversed.charAt(i));
            if (i % 2 == 0) { // Odd-numbered positions in the original order
                digit *= 2;
                // If the result is a double-digit number, add its digits
                if (digit > 9) {
                    digit = (digit / 10) + (digit % 10);
                }
            }
            sum += digit; // Add the digits
            System.out.println("Step c: Digit at position " + (i + 1) + " after doubling if needed: " + digit);
        }

        // Step d: Add up all the digits
        System.out.println("Step d: Total sum: " + sum);

        // Step e: Subtract the last digit obtained in step a from 10
        int checksum = 10 - (sum % 10);
        System.out.println("Step e: Checksum: " + checksum);

        // Step f: Compare the result of step e with the last digit obtained in step a
        boolean isValid = checksum == originalLastDigit;
        System.out.println("Step f: Is valid? " + isValid);

        return isValid;
    }
}
