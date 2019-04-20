package ca.concordia.alexa.AlexaSecureSession.utils;

public class AlexaUtils {

  public final static String ALEXA_API_KEY = "amzn1.ask.skill.6b183ddc-d527-4e83-a8d5-656083506666";
  
  public final static String CIPHER_TEXT = "cipher_text"; 
  
  /**
   * Check if the challenge is null or incorrect, in those cases ask the question again
   * @param text
   * @param challenge
   * @return
   */
  public static boolean needToAskChallengeResponse(String plainText, String challenge) {
   if(plainText != null && plainText.equals(challenge)) {     
     return false;
   }    
    return true;
  }
}
