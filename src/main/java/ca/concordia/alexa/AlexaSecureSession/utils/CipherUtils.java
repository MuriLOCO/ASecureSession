package ca.concordia.alexa.AlexaSecureSession.utils;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.concordia.alexa.AlexaSecureSession.enums.BaseVariables;

public class CipherUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(CipherUtils.class);  
  
  private static Random rand = new Random();
   
  public static int getRandomNumber() {
    return rand.nextInt(99);
  }
  
  public static int getRandomKey() {
    return rand.nextInt(4) + 1;
  }
  
  /**
   * Checks the position of the current char based on the selected alphabe
   * @param  - Current char
   * @return Position of the char in a int format
   */
  private static int checkAlphabetPosition(final char c, String order) {
    int currentCharPosition = 0;
    final char[] alphabetIntoArray = order.toCharArray();
    for (int i = 0; i < alphabetIntoArray.length; i++) {
      if (c == alphabetIntoArray[i]) {
        currentCharPosition = i;
        break;
      }
    }
    return currentCharPosition;
  }
  
  /**
  * Decrypts the plain text ciphertext the corresponding key 
  * @param cipherText - Ciphertext
  * @param key - Current key
  * @return Plain Text in String format
  */
  public static String decrypt(final String cipherText, final String key) {    
    LOGGER.trace("First, transform the plainText into an Array of Chars");
    final char[] cipherTextChar = cipherText.toUpperCase().toCharArray();    
   
    LOGGER.trace("We create a String Buffer to be able to build our Cipher Text");
    final StringBuffer plainText = new StringBuffer();

    LOGGER.trace("Now we will iterate into each character of the plain text and we will apply the substitution");
    for (int i = 0; i < cipherTextChar.length; i++) {
      LOGGER.trace("Finds the position of the current character on the alphabet");
      int position = checkAlphabetPosition(cipherTextChar[i], key);
      LOGGER.trace("Inserts the current position of the key based on the position of the alphabet");    
      plainText.append(BaseVariables.NUMBER_ORDER.getValue().charAt(position));
    }

    // Returns the Cipher Text
    LOGGER.info("DECRYPTED TEXT IS: " + plainText.toString());
    return plainText.toString();
  }
  
}
