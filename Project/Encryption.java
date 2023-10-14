
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

//Created by Prudhvi Krishna Bodavula  24-Mar-2023

public class Encryption {

	// Generate a secret key to use for encryption and decryption
	static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
	    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
	    keyGenerator.init(256);
	    return keyGenerator.generateKey();
	}

	// Encrypts the given string using the specified secret key
	static String encrypt(String str, SecretKey secretKey) throws Exception {
	    Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	    byte[] encryptedBytes = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
	    return Base64.getEncoder().encodeToString(encryptedBytes);
	}
	
	// Decrypts the given string using the specified secret key
	static String decrypt(String str, SecretKey secretKey) throws Exception {
	    Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.DECRYPT_MODE, secretKey);
	    byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(str));
	    return new String(decryptedBytes, StandardCharsets.UTF_8);
	}
}
