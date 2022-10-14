
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
    // The random password, for brute force
    public static String[] for_select = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
            "p", "q", "r",
            "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "_", "-", "!", "@", "#", "$", "%", "^", "&", "*", "+" };

    public static String[] initialization_str(String[] strs, int numOfpassword) {
        String[] hand_made = new String[numOfpassword];
        for (int i = 0; i < numOfpassword; i++) {
            hand_made[i] = strs[0];
        }
        return hand_made;
    }

    public static String RandomStr(String[] strs, int numOfpassword) {
        String feedBack = "";
        for (int i = 0; i <= numOfpassword; i++) {
            int random_index = (int) (Math.random() * strs.length);
            feedBack += strs[random_index];
        }
        return feedBack;
    }

    public static String listALLhacker_password(List<String> candicate, String prefix) {
        System.out.printf("\n Cauculating! \n");
        for (int i = 0; i < candicate.size(); i++) {
            List<String> tempList = new LinkedList<String>(candicate);
            String tempString = (String) tempList.remove(i);
            listALLhacker_password(tempList, prefix + tempString);
        }
        return prefix;
    }

    public static boolean permutation_collision(String[] strs, String targetCipher, boolean Ifeqals) throws Exception {
        System.out.printf("\n\n Generating all possible! \n\n");
        String hacker_password = listALLhacker_password(Arrays.asList(for_select), "");
        String[] hacker_arr = hacker_password.split(",");
        for (int i = 0; i < hacker_password.length(); i++) {
            String forceCipher = Utils.toHex(generateCipher(salt, count, plaintext, hacker_arr[i].toString()));
            Ifeqals = targetCipher.equals(forceCipher);
            breaking_point = hacker_arr[i].toString();
            Times++;
            if (Ifeqals) {
                break;
            }
        }
        return Ifeqals;
    }

    public static List<String> Sorting_password = new ArrayList<>();

    public static void permutation_num(String[] strs, int start, int end) {
        if (start == end) {
            Sorting_password.add(join(strs, "", 0, end));
        } else {
            for (int i = start; i <= strs.length; i++) {
                String temp = strs[start];
                strs[i] = temp;
                permutation_num(strs, start + 1, end);
                strs[i] = strs[start];
                strs[start] = temp;
            }
        }
    }

    public static String join(String[] arr, String spor, int begain, int end) {
        StringBuilder temp = new StringBuilder();
        for (int i = begain; i < end; i++) {
            temp.append(arr[i]).append(spor);
        }
        return temp.toString();
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
        real_password = RandomStr(for_select, NUM_password);
        System.out.printf("\n The generate password is: %s", real_password);

        String targetCipher = Utils.toHex(generateCipher(salt, count, plaintext, real_password));
        System.out.printf("\n The cipher is: %s", targetCipher);
        String initialization_made[] = initialization_str(for_select, NUM_password);

        permutation_num(for_select, 0, NUM_password);
        System.out.printf("\n All sorting:\n", Times);
        System.out.println(Sorting_password);

        // System.out.printf("\n The number of cycle: %d", Times);
        // System.out.printf("\n The hacker successfully try: %s", breaking_point);
    }

}
