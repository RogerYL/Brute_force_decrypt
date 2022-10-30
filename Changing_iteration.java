import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class Changing_iteration {
    // Salt
    public static byte[] salt = { (byte) 0xc7, (byte) 0x73, (byte) 0x21,
            (byte) 0x8c, (byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99 };
    // The real iteration count
    // public static int count = 32;
    // public static int count = 2048;
    public static int count = 65536;
    // public static int count = 2097152;
    // public static int count = 1073741824;

    // The right iteration count
    public static int hacker_count = 0;
    // Predefined plaintext
    public static String plaintext = "abcdefghigk";
    // Ciphertext
    public static byte[] ciphertext;
    // The real password
    public static String password = "123456";

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
        String targetCipher = Utils.toHex(generateCipher(salt, count, plaintext, password));
        boolean Ifeqals;
        long Time_start = System.nanoTime();
        for (int attempts_count = 1; attempts_count <= 1073741824; attempts_count++) {
            String forceCipher = Utils.toHex(generateCipher(salt, attempts_count, plaintext, password));
            System.out.println(forceCipher);
            Ifeqals = targetCipher.equals(forceCipher);
            hacker_count = attempts_count;
            if (Ifeqals) {
                break;
            }
        }
        long Time_end = System.nanoTime();
        long Total_time = Time_end - Time_start;

        System.out.println("\nNumber of iterations successfully cracked: " + hacker_count);
        System.out.println("\nAverage cost per iteration(nanoseconds): " + Total_time);
    }
}
