package ca.concordia.alexa.AlexaSecureSession.keyGen;

import static org.junit.Assert.assertEquals;

import java.util.Base64;

import org.junit.Test;

public class Base64Test {

 
  private final static String TEXT = "CSsnjAXjJ1crY8YCRFkScw2AaNHW9qL4RpzBTNeuR7ugQR3O/9lZTBJqmm64JDuANVY6Lj8Yuw1cIbslbIkNqw==";
  private final static String BASE_64 = "YVEya2dGK1U5R1d2cTVHWHNLS2FaeDZXTHBxV2pNL0ZQN0N2ZU1lZGM5bEJWRytyTUVJNTNxWHdyYlZRK1pVSllETzZVTmJUZXU5T2p1T3ZFQi9rdkE9PQ==";
  private final static String COMPARE_STRING = "aQ2kgF+U9GWvq5GXsKKaZx6WLpqWjM/FP7CveMedc9lBVG+rMEI53qXwrbVQ+ZUJYDO6UNbTeu9OjuOvEB/kvA==";
  @Test
  public void encodeAndDecodeBase64() {
    String encodedString = Base64.getEncoder().encodeToString(TEXT.getBytes());
    byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
    String reencodedString = new String(decodedBytes);
    
    assertEquals(TEXT, reencodedString);
  }
  
  @Test
  public void decodeFixedBase64() {
    byte[] decodedBytes = Base64.getDecoder().decode(BASE_64);   
    String reencodedString = new String(decodedBytes);
    assertEquals(reencodedString, COMPARE_STRING);
  }
}
