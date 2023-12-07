import java.util.Scanner;

/**
 * Class that represents a hexaicosadecimal number and provides functionalities
 * to convert between hexaicosadecimal and decimal number systems.
 */
public class HexaicosadecimalNumber {

    // Represents the hexaicosadecimal format of the number
    private String stringRepresentation;
    // Represents the decimal format of the number
    private double doubleRepresentation;

    /**
     * Constructs the HexaicosadecimalNumber using a hexaicosadecimal string.
     * @param stringRepresentation The hexaicosadecimal input.
     */
    public HexaicosadecimalNumber (String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
        this.doubleRepresentation = hexaicosadecimalStringToDouble(stringRepresentation);
    }


    //Constructs the HexaicosadecimalNumber using a decimal value.

    public HexaicosadecimalNumber (double doubleRepresentation) {
        this.doubleRepresentation = doubleRepresentation;
        this.stringRepresentation = doubleToHexaicosadecimalString(doubleRepresentation);
    }

    /**
     * Converts a hexaicosadecimal string into its decimal representation.
     * @param in Hexaicosadecimal string.
     * @return Corresponding decimal value.
     */
    public double hexaicosadecimalStringToDouble(String in) {
        double accumulator = 0.0;
        String inputLowerCase = in.toLowerCase();

        // Identify the position of the decimal point, if present
        int decimalIndex = inputLowerCase.indexOf(".");

        // Determine if the input represents a negative number
        boolean negativeNumber = inputLowerCase.startsWith("-");

        // If the number is negative, remove the negative sign for further processing
        if (negativeNumber) {
            inputLowerCase = inputLowerCase.substring(1);
        }

        // Process whole numbers, if the input does not contain a decimal point
        if (decimalIndex == -1) {
            for (int i = 0; i < inputLowerCase.length(); i++) {
                char currentChar = inputLowerCase.charAt(i);
                int baseValue = currentChar - 'a';
                accumulator += baseValue * Math.pow(26, inputLowerCase.length() - i - 1);
            }
        } else {
            // Process both whole numbers and fractions if the input contains a decimal point
            String wholeNumPart = inputLowerCase.substring(0, decimalIndex);
            String postDecimalPart = inputLowerCase.substring(decimalIndex + 1);

            // Convert the whole number part
            for (int i = 0; i < wholeNumPart.length(); i++) {
                char currentChar = wholeNumPart.charAt(i);
                int baseValue = currentChar - 'a';
                accumulator += baseValue * Math.pow(26, wholeNumPart.length() - i - 1);
            }

            // Convert the fractional part
            double postDecimalAccumulator = 0.0;
            for (int i = 0; i < postDecimalPart.length(); i++) {
                char currentChar = postDecimalPart.charAt(i);
                int baseValue = currentChar - 'a';
                postDecimalAccumulator += baseValue * Math.pow(26, -(i + 1));
            }
            
            accumulator += postDecimalAccumulator;
        }

        // If the original number was negative, adjust the sign of the result
        if (negativeNumber) {
            accumulator = -accumulator;
        }

        return accumulator;
    }

    /**
     * Converts a decimal value into its hexaicosadecimal representation.
     * @param in Decimal value.
     * @return Corresponding hexaicosadecimal string.
     */
    public String doubleToHexaicosadecimalString(double in) {
        boolean negativeNumber = false;

        // Handle negative input values
        if (in < 0) {
            negativeNumber = true;
            in = Math.abs(in);
        }
        
        long temp = (long) in;  // Convert the double to a long to handle whole number part
        double fractionalPart = in - temp;

        String StringAccumulator = "";

        if (temp == 0 && fractionalPart == 0.0) {
            return "a";
        }

        // Convert the whole number part
        while (temp > 0) {
            long currValue = temp % 26;
            char c = (char) ('a' + currValue);
            StringAccumulator = c + StringAccumulator;
            temp /= 26;
        }

        // Convert the fractional part
        if (fractionalPart > 0.0) {
            StringAccumulator += '.';
            for (int i = 0; i < 12; i++) {
                fractionalPart *= 26;
                int currValue = (int) Math.floor(fractionalPart);
                char c = (char) ('a' + currValue);
                StringAccumulator += c;
                fractionalPart -= currValue;
            }
        }

        // Adjust for negative values
        if (negativeNumber) {
            StringAccumulator = "-" + StringAccumulator;
        }

        return StringAccumulator;
    }

    /**
     * Provides a string representation of the hexaicosadecimal number.
     * @return Formatted string showing both hexaicosadecimal and decimal values.
     */
    public String toString() {
        return stringRepresentation + " (" + doubleRepresentation + ")";
    }
    
    /**
     * Main method to serve as the starting point of the program. Provides a user interface
     * to facilitate the conversion between hexaicosadecimal and decimal formats.
     */
 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            char selection = promptUserForChoice(scanner);

            switch (selection) {
                case 'h':
                case 'H':
                    handleHexaicosadecimalConversion(scanner);
                    break;
                case 'd':
                case 'D':
                    handleDecimalConversion(scanner);
                    break;
                case 'q':
                case 'Q':
                    scanner.close();
                    return;
                default:
                    System.out.println("INVALID INPUT");
                    break;
            }
        }
    }

    /**
     * Prompt the user for their choice of operation.
     * @param scanner The Scanner object.
     * @return The character representing the user's choice.
     */
    private static char promptUserForChoice(Scanner scanner) {
            System.out.println("Please choose an option:");
            System.out.println("h: Convert from hexaicosadecimal number to decimal");
            System.out.println("d: Convert from decimal to hexaicosadecimal number");
            System.out.println("q: Quit");
        return scanner.nextLine().charAt(0);
    }

    /**
     * Handle the conversion from hexaicosadecimal to decimal.
     * @param scanner The Scanner object.
     */
    private static void handleHexaicosadecimalConversion(Scanner scanner) {
        System.out.println("Please input a hexaicosadecimal number: ");
        String hexaicosa = scanner.nextLine();
        HexaicosadecimalNumber hexaicosaConversion = new HexaicosadecimalNumber(hexaicosa);
        System.out.println(hexaicosaConversion);
    }

    /**
     * Handle the conversion from decimal to hexaicosadecimal.
     * @param scanner The Scanner object.
     */
    private static void handleDecimalConversion(Scanner scanner) {
        System.out.println("Please input a decimal number: ");
        double decimal = scanner.nextDouble();
        scanner.nextLine();  // Clear the buffer
        HexaicosadecimalNumber decimalConversion = new HexaicosadecimalNumber(decimal);
        System.out.println(decimalConversion);
    }
}