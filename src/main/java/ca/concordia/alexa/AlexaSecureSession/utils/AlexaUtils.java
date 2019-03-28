package ca.concordia.alexa.AlexaSecureSession.utils;

import ca.concordia.alexa.AlexaSecureSession.speechlets.models.SecureSessionKey;

public class AlexaUtils {

  public final static String ALEXA_API_KEY = "amzn1.ask.skill.6b183ddc-d527-4e83-a8d5-656083506666";

  public final static String URL_SECURE_SESSION = "http://asecuresession.ddns.net:8080/generate?keySize=512";

  public final static String CIPHER_TEXT = "cipher_text";

  public static byte[] loadAndDecryptChallenge(SecureSessionKey secureSessionKey, byte[] cipherText) throws Exception {   
    return SecureSessionKeyUtils.decrypt(secureSessionKey.getPrivateKey(), cipherText);
  }
  
  /**
   * Check if the challenge is null or incorrect, in those cases ask the question again
   * @param text
   * @param challenge
   * @return
   */
  public static boolean needToAskChallengeResponse(byte[] text, int challenge) {
    if (text != null) {
      byte[] challengeToCompare = String.valueOf(challenge).getBytes();
      for (int i = 0; i < text.length; i++) {
        if (text[i] != challengeToCompare[i]) {
          return true;
        }
      }
    }
    return true;
  }
}
