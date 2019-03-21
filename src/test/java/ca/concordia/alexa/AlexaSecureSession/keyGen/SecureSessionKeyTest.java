package ca.concordia.alexa.AlexaSecureSession.keyGen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import ca.concordia.alexa.AlexaSecureSession.processor.SecureSessionKeyProcessor;
import ca.concordia.alexa.AlexaSecureSession.speechlets.models.SecureSessionKey;
import ca.concordia.alexa.AlexaSecureSession.utils.AlexaUtils;
import ca.concordia.alexa.AlexaSecureSession.utils.HttpUtils;
import ca.concordia.alexa.AlexaSecureSession.utils.SecureSessionKeyUtils;


public class SecureSessionKeyTest { 
  
  @Test
  public void getValidKey__validUrl_success() throws ClientProtocolException, IOException, InvalidKeySpecException, NoSuchAlgorithmException {
   String response = HttpUtils.makeRestCallAndGetResponse(AlexaUtils.URL_SECURE_SESSION);
   SecureSessionKey secureSessionKey = SecureSessionKeyProcessor.convertSecureSessionKeyFromJSON(response);
   assertNotNull(secureSessionKey);
   assertNotNull(secureSessionKey.getPrivateKey());
   assertNotNull(secureSessionKey.getPublicKey());
  }
  
  @Test
  public void encryptAndDecrypt_validInput_success() throws Exception {
    
    int challenge = SecureSessionKeyUtils.getRandomNumber();
    byte[] challengeBytes = String.valueOf(challenge).getBytes();
    
    String response = HttpUtils.makeRestCallAndGetResponse(AlexaUtils.URL_SECURE_SESSION);
    SecureSessionKey secureSessionKey = SecureSessionKeyProcessor.convertSecureSessionKeyFromJSON(response);
    
    byte[] cipherText = SecureSessionKeyUtils.encrypt(secureSessionKey.getPublicKey(), challengeBytes);
    byte[] text = SecureSessionKeyUtils.decrypt(secureSessionKey.getPrivateKey(), cipherText);
    
    assertEquals(challengeBytes.length, text.length);
    
    for(int i = 0; i < challengeBytes.length; i++) {
      assertEquals(challengeBytes[i], text[i]);
    }    
    
  }

}
