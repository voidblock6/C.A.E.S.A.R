package dev.voidblock;

import java.util.Scanner;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

//C.E.A.S.A.R.  Coded Encryption Algorithm for Safety and Randomness


public class Main {
    static boolean online = true;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("---C.E.A.S.A.R. is online---");
        while (online == true) {
            System.out.print("Make a choice: [1] Encrypt [2] Decrypt [3] exit program: ");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                // ENCRYPTION MODE
                System.out.print("Input the message you want encrypted, then press Enter twice: ");
                StringBuilder text = new StringBuilder();
                String line;
                while (!(line = scanner.nextLine()).isEmpty()) {
                    text.append(line).append("\n");
                }
                String input = text.toString().trim();

                String generatedKey = CryptoUtils.generateRandomKey(32);
                String config = "";
                String ceasarShift = "00";

                System.out.println("input which encryption steps do you want to use \n ----------------");

                String encryptionRounds = encryptionNumberQuestion(scanner, "how many rounds of encryption?", 1, 9999, 4);
                config += encryptionRounds;
                int encryptionRoundsInt = Integer.parseInt(encryptionRounds);

                String XOR = encryptionQuestions(scanner, "XOR encryption?");
                String vigenere = encryptionQuestions(scanner, "Vigenere chiper encryption?");
                String ceasar = encryptionQuestions(scanner, "Ceasar chiper encryption?");

                if (ceasar.equals("y")) {
                     ceasarShift = encryptionNumberQuestion(scanner, "what shift for Ceasar chiper", 1, 26, 2);
                }
                if (XOR.equals("y")) {
                    config += "X";
                } else {
                    config += "_";
                }

                if (vigenere.equals("y")) {
                    config += "V";
                } else {
                    config += "_";
                }

                if (ceasar.equals("y")) {
                    config += "C" + ceasarShift;
                } else {
                    config += "___";
                }

                byte[] encryptedRaw = input.getBytes(StandardCharsets.UTF_8);
                byte[] keyBytes = generatedKey.getBytes(StandardCharsets.UTF_8);

                while (encryptionRoundsInt > 0) {

                    //check if XOR is chosen, then runs XOR encryption
                    if (XOR.equals("y")) {
                        encryptedRaw = CryptoUtils.XOREncryption(encryptedRaw, keyBytes);
                    }

                    if (vigenere.equals("y")) {
                        encryptedRaw = CryptoUtils.vigenereEncryption(encryptedRaw, keyBytes);
                    }

                    if (ceasar.equals("y")) {
                        encryptedRaw = CryptoUtils.vigenereEncryption(encryptedRaw, keyBytes);
                    }

                        encryptionRoundsInt--;
                }

                // base64 conversion for easy copy
                String base64Result = Base64.getEncoder().encodeToString(encryptedRaw);


                //results
                System.out.println("\n--- Results ---");
                System.out.println("V Encrypted message V \n" + base64Result);
                System.out.println("---------------");
                System.out.println("Your generated key: " + generatedKey + "\nKeep your key secret, it can be used to decrypt your message");
                System.out.println("---------------");
                System.out.println("Your config: " + config + "\nFeel free to share your config, its worthless without the key");


            } else if (choice.equals("2")) {

                // DECRYPTION MODE
                System.out.print("Input the Base64 message: ");
                String base64Input = scanner.nextLine().trim(); // .trim() prevetn crashes because of spaces

                System.out.print("Input the key: ");
                String keyInput = scanner.nextLine();

            } else if (choice.equals("3")) {
                System.out.println("exiting program");
                online = false;
            } else {
                System.out.println("Invalid choice, restart the program");
            }
            System.out.println("--------------");
        }
        scanner.close();
    }

//end of main class


    //questions for what encryption to use
    public static String encryptionQuestions(Scanner scanner, String question) {
        while (true) {
            System.out.print(question + " [y/n]: ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("y") || input.equals("n")) {
                return input;
            }
            System.out.println("Invalid input. Please type 'y' or 'n'.");
        }
    }



    public static String encryptionNumberQuestion(Scanner scanner, String numberQuestion, int min, int max, int format) {
        while (true) {
            System.out.print( numberQuestion + "[" + min + "-" + max + "]: ");
            String input = scanner.nextLine().trim();
            try {
                int number = Integer.parseInt(input);

                if (number >= min && number <= max) {
                    return String.format("%0"+ format + "d", number);
                } else {
                    System.out.println("Invalid range. Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please type a valid number:");
            }
        }
    }
}




