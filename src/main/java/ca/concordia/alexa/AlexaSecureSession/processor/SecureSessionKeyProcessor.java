package ca.concordia.alexa.AlexaSecureSession.processor;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.concordia.alexa.AlexaSecureSession.speechlets.dto.SecureSessionKeyDTO;
import ca.concordia.alexa.AlexaSecureSession.speechlets.models.SecureSessionKey;
import ca.concordia.alexa.AlexaSecureSession.utils.SecureSessionKeyUtils;

public class SecureSessionKeyProcessor {

  private static ObjectMapper objectMapper = new ObjectMapper();
  
  public static SecureSessionKey convertSecureSessionKeyFromJSON(String jsonString) throws JsonParseException, JsonMappingException, IOException, InvalidKeySpecException, NoSuchAlgorithmException {
    SecureSessionKeyDTO secureSessionKeyDTO = objectMapper.readValue(jsonString, SecureSessionKeyDTO.class);     
    return new SecureSessionKey(secureSessionKeyDTO.getId(), SecureSessionKeyUtils.loadPrivateKey(secureSessionKeyDTO.getPrivateKey()), SecureSessionKeyUtils.loadPublicKey(secureSessionKeyDTO.getPublicKey()));
  }
  
}
