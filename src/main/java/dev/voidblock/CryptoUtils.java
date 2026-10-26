package dev.voidblock;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;

public class CryptoUtils {

    //Key generator
    public static String generateRandomKey(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()`~_-=+[{]}/?.>,<";
        StringBuilder keygen = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            keygen.append(chars.charAt(random.nextInt(chars.length())));
        }
        return keygen.toString();
    }

    //XOR de/encryption
    public static byte[] XOREncryption(byte[] data, byte[] key) {
        byte[] result = new byte[data.length];


            for (int i = 0; i < data.length; i++) {
                //XORs the next charachter of the text with the next charachter of the key
                result[i] = (byte) (data[i] ^ key[i % key.length]);
            }

        return result;
    }

    public static byte[] atbashEncryption(byte[] data, boolean transformNumbers) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {

            if (data[i] >= 'A' && data[i] <= 'Z') {

                int position = data[i] - 'A';
                int reversedPosition = 25 - position;
                result[i] = (byte) ('A' + reversedPosition);
            }
            else if (data[i] >= 'a' && data[i] <= 'z') {
                int position = data[i] - 'a';
                int reversedPosition = 25 - position;
                result[i] = (byte) ('a' + reversedPosition);

            } else if (transformNumbers && data[i] >= '0' && data[i] <= '9') {
                int position = data[i] - '0';
                int reversedPosition = 9 - position;
                result[i] = (byte) ('0' + reversedPosition);
            } else {
            result[i] = data[i];
            }
        }

        return result;
    }

    //Byte Vigenère Chiper Encryption
    public static byte[] byteVigenereEncryption(byte[] data, byte[] key) {
        byte[] result = new byte[data.length];

        for (int i = 0; i < data.length; i++) {
            // Gets the current charachter from the input text
            byte inputByte = data[i];
            // Picks a key character, repeating the key if it's shorter than the text
            byte keyByte = key[i % key.length];
            // Uses the ASCII value of the key character as the shift amount
            int shift = (int) keyByte;
            // Shift the input character by adding the key's ASCII value
            byte encryptedByte = (byte) (inputByte + shift); //encrypts the char by adding the ascii value of the keys char
            //add encrypted char to the result
            result[i] = encryptedByte;
        }

        // give back result as a string
        return result;
    }

    //Byte ceasar chiper encryption
    public static byte[] byteCeasarEncryption(byte[] data, int shift) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            int val = data[i] & 0xFF;
            // apply the shift (max 256 for ascii)
            int shifted = (val + shift) % 256;
            result[i] = (byte) shifted;
        }
        return result;
    }

    //encrypt
    public static EncryptionResult encrypt(byte[] data, byte[] key, int xorRounds, int vignereRounds, int ceasarRounds, int ceasarShift, int atbashRounds, boolean atbashTransformNumbers) {
        List<String> operationOrder = new ArrayList<>();
        int roundsLeft = xorRounds + vignereRounds + ceasarRounds + atbashRounds;

        while(roundsLeft > 0) {

            if (xorRounds > 0) {
                data = CryptoUtils.XOREncryption(data, key);
                xorRounds--;
                roundsLeft--;
                operationOrder.add("X");

            }

            if (vignereRounds > 0) {
                data = CryptoUtils.byteVigenereEncryption(data, key);
                vignereRounds--;
                roundsLeft--;
                operationOrder.add("V");
            }

            if (ceasarRounds > 0) {
                data = CryptoUtils.byteCeasarEncryption(data, ceasarShift);
                ceasarRounds--;
                roundsLeft--;
                operationOrder.add("C" + String.format("%03d", ceasarShift));
            }

            if (atbashRounds > 0) {
                data = CryptoUtils.atbashEncryption(data, atbashTransformNumbers);
                atbashRounds--;
                roundsLeft--;
                operationOrder.add("A" + (atbashTransformNumbers ? "1" : "0"));

            }
        }
        String result = Base64.getEncoder().encodeToString(data);
       return new EncryptionResult(result, operationOrder);
    }





    //salts encryption
    /*
    public static String saltsEncryption(byte[] data, int amount) {
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            positions.add(i);
        }
        Collections.shuffle(positions);

        StringBuilder saltInfo = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < amount; i++) {
            int pos = positions.get(i);
            byte originalByte = data[pos];

            // %03d for the byte (0-255), %04d for the position
            saltInfo.append(String.format("%03d%04d", (originalByte & 0xFF), pos));
            // Overwrite a byte in the text with a random byte
            data[pos] = (byte) rand.nextInt(256);
        }
        return saltInfo.toString();
    }

     */


    public static class EncryptionResult {
        public String data;
        public List<String> operationOrder;

        public EncryptionResult(String data, List<String> operationOrder) {
            this.data = data;
            this.operationOrder = operationOrder;
        }
    }

    // decryption
    public static List<String> parseConfig(String config) {
        List<String> operationOrder = new ArrayList<>();
        int i = 0;

        while (i < config.length()) {
            char current = config.charAt(i);

            if (current == 'C') {
                String shiftDigits = config.substring(i + 1, i + 4);
                operationOrder.add("C" + shiftDigits);
                i += 4;

            }
            else if (current == 'A') {
                String option = config.substring(i + 1, i + 2);
                boolean atbashTransformNumbers = option.equals("1");
                operationOrder.add("A" + (atbashTransformNumbers ? "1" : "0"));
                i += 2;
            } else {

                operationOrder.add(String.valueOf(current));
                i += 1;
            }
        }

        return operationOrder;
    }

    //byte ceasar decryption
    public static byte[] byteCeasarDecryption(byte[] data, int shift) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            int val = data[i] & 0xFF;
            // apply the shift (max 256 for ascii)
            int shifted = (val - shift) % 256;
            result[i] = (byte) shifted;
        }
        return result;
    }

    //byte vignere decryption
    public static byte[] byteVigenereDecryption(byte[] data, byte[] key) {
        byte[] result = new byte[data.length];

        for (int i = 0; i < data.length; i++) {
            // Gets the current charachter from the input text
            byte inputByte = data[i];
            // Picks a key character, repeating the key if it's shorter than the text
            byte keyByte = key[i % key.length];
            // Uses the ASCII value of the key character as the shift amount
            int shift = (int) keyByte;
            // Shift the input character by adding the key's ASCII value
            byte encryptedByte = (byte) (inputByte - shift); //encrypts the char by adding the ascii value of the keys char
            //add encrypted char to the result
            result[i] = encryptedByte;
        }
        // give back result as a string
        return result;
    }




    public static String decrypt(String sData, String sKey, List<String> config) {
        List<String> operationOrder = new ArrayList<>();


        byte[] data = sData.getBytes(StandardCharsets.UTF_8);
        byte[] key = sKey.getBytes(StandardCharsets.UTF_8);

        data = Base64.getDecoder().decode(data);

        for (String operation : config) {

            if (operation.equals("X")) {
                data = CryptoUtils.XOREncryption(data, key);
            }

            else if (operation.equals("V")) {
                data = CryptoUtils.byteVigenereDecryption(data, key);
            }

            else if (operation.startsWith("C")) {
                String shiftText = operation.substring(1);
                int shift = Integer.parseInt(shiftText);

                data = CryptoUtils.byteCeasarDecryption(data, shift);
            }

            else if (operation.startsWith("A")) {
                char option = operation.charAt(1);
                boolean transformNumbers = option == '1';
                data = CryptoUtils.atbashEncryption(data,transformNumbers);
            }


        }

        String result = new String(data, StandardCharsets.UTF_8);
        return result;
    }
}

