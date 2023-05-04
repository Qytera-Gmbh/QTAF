package de.qytera.qtaf.security;

import de.qytera.qtaf.security.aes.AES;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.crypto.BadPaddingException;
import java.security.GeneralSecurityException;
import java.util.HexFormat;

/**
 * Tests for the AES encryption module
 */
public class AESTest {
    /**
     * Test if AES can encrypt a plain text and decrypt the ciphertext
     * @throws GeneralSecurityException Security Exception
     */
    @Test(testName = "Test AES encryption and decryption")
    public void testAesEncryption() throws GeneralSecurityException {
        String cipherText = AES.encrypt("Hello World!", "my-key");
        String plainText = AES.decrypt(cipherText, "my-key");
        Assert.assertEquals(plainText, "Hello World!", "Decrypted plain text should be 'Hello World!'");
    }

    /**
     * Test if AES can encrypt a plain text and decrypt the ciphertext
     * @throws GeneralSecurityException Security Exception
     */
    @Test(testName = "Test AES encryption and decryption")
    public void testAesEncryptionWithRandomSalts() throws GeneralSecurityException {
        String cipherText1 = AES.encrypt("Hello World!", "my-key");
        String cipherText2 = AES.encrypt("Hello World!", "my-key");
        Assert.assertNotEquals(cipherText1, cipherText2, "ciphertext 1 and ciphertext 2 should be different, because different salts were used");
        String plainText1 = AES.decrypt(cipherText1, "my-key");
        String plainText2 = AES.decrypt(cipherText2, "my-key");
        Assert.assertEquals(plainText1, "Hello World!", "Decrypted plain text should be 'Hello World!'");
        Assert.assertEquals(plainText2, "Hello World!", "Decrypted plain text should be 'Hello World!'");
    }

    /**
     * Test if AES can encrypt a plain text and decrypt the ciphertext
     * @throws GeneralSecurityException Security Exception
     */
    @Test(testName = "Test AES encryption and decryption with custom salt")
    public void testAESCustomSalt() throws GeneralSecurityException {
        byte[] salt = HexFormat.of().parseHex("0000000000000000");
        String cipherText = AES.encrypt("Hello World!", "my-key", salt);
        Assert.assertTrue(cipherText.startsWith("AAAAAAAAAAA"), "ciphertext 1 should start with salt AAAAAAAAAAA");
        String plainText = AES.decrypt(cipherText, "my-key");
        Assert.assertEquals(plainText, "Hello World!", "Decrypted plain text should be 'Hello World!'");
    }

    /**
     * Test if ciphertext can only be decrypted with a valid key
     * @throws GeneralSecurityException Security exception
     */
    @Test(
            testName = "Test if ciphertext can only be decrypted with valid key",
            expectedExceptions = BadPaddingException.class,
            expectedExceptionsMessageRegExp = "Given final block not properly padded. Such issues can arise if a bad key is used during decryption."
    )
    public void testCiphertextOnlyDecryptableWithValidKey() throws GeneralSecurityException {
        String cipherText = AES.encrypt("Hello World!", "my-key-1");
        AES.decrypt(cipherText, "my-key-2");
    }
}
