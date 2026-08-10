package dev.voidblock;

import java.util.*;

public class CryptoUtils {

    //Key generator
    public static String generateRandomKey(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()`~_-=+[{]}/?.>,<";
        StringBuilder keygen = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            keygen.append(chars.charAt(random.nextInt(chars.length())));
        }
        return keygen.toString();
    }

    //XOR encryption
    public static byte[] XOREncryption(byte[] data, byte[] key) {
        byte[] result = new byte[data.length];


            for (int i = 0; i < data.length; i++) {
                //XORs the next charachter of the text with the next charachter of the key
                result[i] = (byte) (data[i] ^ key[i % key.length]);
            }

        return result;
    }

    //Vigenère Chiper Encryption
    public static byte[] vigenereEncryption(byte[] data, byte[] key) {
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

    public static byte[] ceasarEncryption(byte[] data, int shift) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            int val = data[i] & 0xFF;
            // apply the shift (max 256 for ascii)
            int shifted = (val + shift) % 256;
            result[i] = (byte) shifted;
        }
        return result;
    }

    public static EncryptionResult encrypt(byte[] data, byte[] key, int xorRounds, int vignereRounds, int ceasarRounds, int ceasarShift) {
        List<String> operationOrder = new ArrayList<>();
        int roundsLeft = xorRounds + vignereRounds + ceasarRounds;

        while(roundsLeft > 0) {

            if (xorRounds > 0) {
                data = CryptoUtils.XOREncryption(data, key);
                xorRounds--;
                roundsLeft--;
                operationOrder.add("X");

            }

            if (vignereRounds > 0) {
                data = CryptoUtils.vigenereEncryption(data, key);
                vignereRounds--;
                roundsLeft--;
                operationOrder.add("V");
            }

            if (ceasarRounds > 0) {
                data = CryptoUtils.ceasarEncryption(data, ceasarShift);
                ceasarRounds--;
                roundsLeft--;
                operationOrder.add("C" + String.format("%03d", ceasarShift));
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
}


