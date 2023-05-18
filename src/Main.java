


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String operation;

        do {
            System.out.println("Please input operation (encode/decode/exit):");
            operation = scanner.nextLine();

            switch (operation) {
                case "encode":
                    System.out.println("Input string:");
                    String inputString = scanner.nextLine();
                    String encodedString = encodeString(toBinaryString(inputString));
                    System.out.println("Encoded string:");
                    System.out.println(encodedString + "\n");
                    break;
                case "decode":
                    System.out.println("Input encoded string:");
                    String decodedInput = scanner.nextLine();
//                    System.out.println("decoded input" + decodedInput);
                    String decodedString = decodeString(decodedInput);
//                    System.out.println("decodedString" + decodedString);
                    if (isEncodedStringValid(decodedInput, decodedString)) {

                        System.out.println("Decoded string:");
                        System.out.println(binaryToString(decodedString));
                        System.out.println();
                    } else {
                        System.out.println("Encoded string is not valid.\n");
                    }
                    break;

                case "exit":
                    System.out.println("Bye!");
                    break;
                default:
                    System.out.println("There is no '" + operation + "' operation\n");
                    break;
            }
        } while (!operation.equals("exit"));

        scanner.close();
    }

    private static String toBinaryString(String message) {
        StringBuilder binary = new StringBuilder();
        for (char c : message.toCharArray()) {
            String binaryString = Integer.toBinaryString(c);
            binary.append(String.format("%7s", binaryString).replace(' ', '0'));
        }
        return binary.toString();
    }

    private static String binaryToString(String binary) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < binary.length(); i += 7) {
            int endIndex = Math.min(i + 7, binary.length());
            String binaryChar = binary.substring(i, endIndex);
            int decimal = Integer.parseInt(binaryChar, 2);
            result.append((char) decimal);
        }

        return result.toString();
    }

    private static String encodeString(String inputString) {
        StringBuilder encoded = new StringBuilder();

        int i = 0;
        while (i < inputString.length()) {
            char bit = inputString.charAt(i);
            int count = 1;

            // Count consecutive bits
            while (i + 1 < inputString.length() && inputString.charAt(i + 1) == bit) {
                i++;
                count++;
            }

            // Add encoding
            encoded.append(bit == '0' ? "00 " : "0 ");
            encoded.append("0".repeat(Math.max(0, count)));
            encoded.append(" ");

            i++;
        }

        return encoded.toString().trim();
    }

    private static boolean isEncodedStringValid(String decodedInput, String decodedString) {
        String[] blocks = decodedInput.split(" ");

        if (blocks.length % 2 != 0) {
//            System.out.println("Encoded string is not valid. The number of blocks is odd.\n");
            return false;
        }

        for (int i = 0; i < blocks.length; i += 2) {
            String countBlock = blocks[i];
            String bitBlock = blocks[i + 1];

            if (!countBlock.equals("0") && !countBlock.equals("00")) {
//                System.out.println("Encoded string is not valid. The first block of each sequence is not 0 or 00.\n");
                return false;
            }

            if (!bitBlock.matches("(0|00)+")) {
//                System.out.println("Encoded string is not valid. The encoded message includes characters other than 0 or spaces.\n");
                return false;
            }
        }

        String binaryString = decodedString.replaceAll(" ", "");
        if (binaryString.length() % 7 != 0) {
//            System.out.println("Encoded string is not valid. The length of the decoded binary string is not a multiple of 7.\n");
            return false;
        }

        return true;
    }


    private static String decodeString(String encodedString) {
        StringBuilder decoded = new StringBuilder();

        String[] blocks = encodedString.split(" ");
        int i = 0;
        while (i < blocks.length) {
            String countBlock = blocks[i];
            String bitBlock = "";

            if (i + 1 < blocks.length) {
                bitBlock = blocks[i + 1];
            }

            char bit = countBlock.equals("00") ? '0' : '1';
            int count = bitBlock.length();

            // Add decoded characters
            decoded.append(String.valueOf(bit).repeat(Math.max(0, count)));

            i += 2;
        }

        return decoded.toString();
    }
}
