package de.qytera.qtaf.security.aes;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Objects;

/**
 * Class that provides methods for encryption and decryption with the AES algorithm
 */
public class AES {

    /**
     * Base64 Encoder
     */
    private static final Base64.Encoder base64Encoder = Base64.getEncoder();

    /**
     * Base64 Decoder
     */
    private static final Base64.Decoder base64Decoder = Base64.getDecoder();

    /**
     * AES iteration count
     */
    private static final int ITERATION_COUNT = 40000;
    /**
     * AES default key length
     */
    private static final int KEY_LENGTH = 128;

    /**
     * AES algorithm
     */
    private static String SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA512";

    /**
     * Cipher transformation algorithm
     */
    private static String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";


    /**
     * Encrypts string content using the provided passphrase.
     *
     * @param plainText     the plaintext that should be encrypted
     * @param key           the AES secret key
     * @return the encrypted ciphertext
     * @throws GeneralSecurityException whenever encryption fails
     */
    public static String encrypt(String plainText, String key) throws GeneralSecurityException {
        byte[] salt = String.valueOf(new SecureRandom().nextInt()).getBytes();
        return encrypt(plainText, key, salt);
    }

    /**
     * Encrypts string content using the provided passphrase.
     *
     * @param plainText     the plaintext that should be encrypted
     * @param key           the AES secret key
     * @param salt          the salt
     * @return the encrypted ciphertext
     * @throws GeneralSecurityException whenever encryption fails
     */
    public static String encrypt(String plainText, String key, byte[] salt) throws GeneralSecurityException {
        // Create random salt
        SecretKeySpec aesKey = createAESKey(key.toCharArray(), salt);
        Cipher pbeCipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        pbeCipher.init(Cipher.ENCRYPT_MODE, aesKey);
        AlgorithmParameters parameters = pbeCipher.getParameters();
        IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);

        byte[] cipherText = pbeCipher.doFinal(Objects.requireNonNull(plainText).getBytes(StandardCharsets.UTF_8));
        byte[] iv = ivParameterSpec.getIV();

        return base64Encoder.encodeToString(salt) + ":" + base64Encoder.encodeToString(iv) + ":" + base64Encoder.encodeToString(cipherText);
    }

    /**
     * Decrypts an AES-encrypted ciphertext using the provided passphrase.
     *
     * @param ciphertext the AES ciphertext
     * @param key        the AES secret key
     * @return the decrypted plaintext
     * @throws GeneralSecurityException whenever decryption fails
     */
    public static String decrypt(String ciphertext, String key) throws GeneralSecurityException {
        String[] split = Objects.requireNonNull(ciphertext).split(":");
        String salt = split[0];
        String iv = split[1];
        String content = split[2];

        SecretKeySpec aesKey = createAESKey(
                Objects.requireNonNull(key).toCharArray(),
                base64Decoder.decode(salt)
        );

        Cipher pbeCipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        pbeCipher.init(Cipher.DECRYPT_MODE, aesKey, new IvParameterSpec(base64Decoder.decode(iv)));

        return new String(pbeCipher.doFinal(base64Decoder.decode(content)), StandardCharsets.UTF_8);
    }


    /**
     * Creates a secret cryptographic AES key from a given passphrase and a salt.
     *
     * @param passphrase the passphrase to base the key on
     * @param salt       the salt to use
     * @return a corresponding, secret AES key
     * @throws NoSuchAlgorithmException if the factory used for creating the key does not know the PBE algorithm
     * @throws InvalidKeySpecException  if the given key specification is inappropriate for this secret-key factory
     *                                  to produce a secret key
     */
    public static SecretKeySpec createAESKey(char[] passphrase, byte[] salt) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM);
        PBEKeySpec keySpec = new PBEKeySpec(passphrase, salt, ITERATION_COUNT, KEY_LENGTH);
        SecretKey keyTmp = keyFactory.generateSecret(keySpec);
        return new SecretKeySpec(keyTmp.getEncoded(), "AES");
    }

}
