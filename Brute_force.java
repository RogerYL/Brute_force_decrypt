
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Brute_force {
    // If there is only one letter for password!
    // We need to encrypt a cipher with one letter password

    // Salt
    public static byte[] salt = { (byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c,
            (byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99 };
    // Iteration count
    public static int count = 2048;
    // Predefined plaintext
    public static String plaintext = "abcd";
    // Ciphertext
    public static byte[] ciphertext;
    // The real password
    public static String real_password = "";
    // The final result of breaking real password
    public static String breaking_point = "";
    // The length of password
    public static int NUM_password = 2;
    // The cycle counting
    public static int Times = 0;
    // All password characters
    public static String[] All_password_characters = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o",
            "p", "q", "r",
            "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "!", "#", "$", "%", " &", "(", ")", "*", "+", ",", "-", ".", "/", ":", ";", "<", "=", ">", "?",
            "@", "[", "]", "^", "_", "`", "{", "|", "}", "~" };
    // All lowercase letters
    public static String[] All_lowercase_characters = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o",
            "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
    // All capital letters
    public static String[] All_capital_characters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
    // All characters
    public static String[] All_characters = { "!", "#", "$", "%", " &", "(", ")", "*", "+", ",", "-", ".", "/", ":",
            ";", "<", "=", ">", "?",
            "@", "[", "]", "^", "_", "`", "{", "|", "}", "~" };
    // Length of generated key
    public static int n = 3;
    // Save the generated key
    public static String[] temp_save_CH;

    public static String RandomStr(String[] strs, int numOfpassword) {
        String feedBack = "";
        for (int i = 0; i <= numOfpassword; i++) {
            int random_index = (int) (Math.random() * strs.length);
            feedBack += strs[random_index];
        }
        return feedBack;
    }

    public static Set getPermutation_str(String str) {
        Set permutations = new HashSet();
        if (str == null) {
            return null;
        } else if (str.length() == 0) {
            permutations.add("");
            return permutations;
        }
        char first = str.charAt(0);
        String sub = str.substring(1);
        Set words = getPermutation_str(sub);
        for (String strNew : (Set<String>) words) {
            for (int i = 0; i <= strNew.length(); i++) {
                permutations.add(strNew.substring(0, i) + first + strNew.substring(i));
            }
        }
        return permutations;
    }

    public static String listTostring(List<String> list, char separator) {
        StringBuilder new_string = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                new_string.append(list.get(i));
            } else {
                new_string.append(list.get(i));
                new_string.append(separator);
            }
        }
        return new_string.toString();
    }

    public static byte[] generateCipher(byte[] C_salt, int C_count, String C_plaintext, String C_password)
            throws Exception {
        PBEKeySpec pbeKeySpec;
        PBEParameterSpec pbeParamSpec;
        SecretKeyFactory keyFac;
        // Create PBE parameter set
        pbeParamSpec = new PBEParameterSpec(C_salt, C_count);
        // Initialization of the password
        char[] password = C_password.toCharArray();
        // Create parameter for key generation
        pbeKeySpec = new PBEKeySpec(password);
        // Create instance of SecretKeyFactory for password-based encryption
        // using DES and MD5
        keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        // Generate a key
        Key pbeKey = keyFac.generateSecret(pbeKeySpec);
        // Create PBE Cipher
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        // Initialize PBE Cipher with key and parameters
        pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);
        // Our plaintext
        byte[] cleartext = C_plaintext.getBytes();
        // Encrypt the plaintext
        ciphertext = pbeCipher.doFinal(cleartext);
        return ciphertext;
    }

    public static void main(String[] args) throws Exception {
        // Useful libraries: All_password_characters, All_lowercase_characters,
        // All_capital_characters, All_capital_characters, All_characters
        real_password = RandomStr(All_password_characters, NUM_password);
        System.out.printf("\n The generate password is: %s", real_password);
        String targetCipher = Utils.toHex(generateCipher(salt, count, plaintext, real_password));
        System.out.printf("\n The cipher is: %s", targetCipher);

        // Starting point for timekeeping
        long startTime = System.nanoTime();

        // Logic:
        // 1. Select the library for generating all passwords
        // 2. Use the key generated by all passwords to compare with the original key
        System.out.printf("\n\n Generating all possible! \n\n");
        for (int i = 1; i < 26; i++) {
            
        }
        permutation_num(All_password_characters, 0, Alphabet_test.length);
        String hacker_string = listTostring(hacker_password, ',');
        String[] hacker_arr = hacker_string.split(",");

        System.out.printf("\n Possible: %s \n", hacker_arr);
        // permutation_num(hacker_arr, 0, hacker_arr.length);

        boolean Ifeqals;
        for (int i = 0; i < hacker_string.length(); i++) {
            String forceCipher = Utils.toHex(generateCipher(salt, count, plaintext, hacker_arr[i]));
            Ifeqals = targetCipher.equals(forceCipher);
            breaking_point = hacker_arr[i].toString();
            Times++;
            if (Ifeqals) {
                break;
            }
        }
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        // permutation_num(Alphabet, 0, NUM_password);
        // System.out.printf("\n All sorting:\n", Times);
        // System.out.println(Sorting_password);

        System.out.println("\n Execution time in nanoseconds: " + timeElapsed);
        System.out.println("\n Execution time in milliseconds: " + timeElapsed / 1000000);
        System.out.println("\n Execution time in seconds: " + timeElapsed / 1000000000);

        System.out.printf("\n The number of cycle: %d \n", Times);
        System.out.printf("\n The hacker successfully try: %s \n", breaking_point);
    }

}
