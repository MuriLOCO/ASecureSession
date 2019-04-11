package ca.concordia.alexa.AlexaSecureSession.utils;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import javax.crypto.Cipher;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.concordia.alexa.AlexaSecureSession.processor.SecureSessionKeyProcessor;
import ca.concordia.alexa.AlexaSecureSession.speechlets.models.SecureSessionKey;

public class SecureSessionKeyUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(SecureSessionKeyUtils.class);
  
  private static Random rand = new Random();

  public static PrivateKey loadPrivateKey(byte[] privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKey);
    KeyFactory fact = KeyFactory.getInstance("RSA");
    return fact.generatePrivate(spec);
  }
  
  public static PublicKey loadPublicKey(byte[] publicKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
    X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKey);
    KeyFactory fact = KeyFactory.getInstance("RSA");
    return fact.generatePublic(spec);
  }

  public static byte[] decrypt(PrivateKey privateKey, byte[] cipherText) throws Exception {
    LOGGER.info("**** CIPHER TEXT IS: " + new String(cipherText));
    LOGGER.info("*** DATA SIZE IS: " + cipherText.length);
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.DECRYPT_MODE, privateKey);
    return cipher.doFinal(cipherText);
  }
  
  /**
   * Encrypts a Text (for testing purposes)
   * @param publicKey
   * @param text
   * @return
   * @throws Exception
   */
  public static byte[] encrypt(PublicKey publicKey, byte[] text) throws Exception {  
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
    return cipher.doFinal(text);
  }

  public static int getRandomNumber() {
    return rand.nextInt(99);
  }
  
  public static SecureSessionKey getSessionKey() throws ClientProtocolException, IOException, InvalidKeySpecException, NoSuchAlgorithmException {
    LOGGER.info("*****GETTING KEY");
    String response = HttpUtils.makeRestCallAndGetResponse(AlexaUtils.URL_SECURE_SESSION);
    SecureSessionKey secureSessionKey = SecureSessionKeyProcessor.convertSecureSessionKeyFromJSON(response);
    return secureSessionKey;
  }

  
}
