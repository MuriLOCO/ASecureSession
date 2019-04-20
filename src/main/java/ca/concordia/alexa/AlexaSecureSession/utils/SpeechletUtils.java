package ca.concordia.alexa.AlexaSecureSession.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

public class SpeechletUtils {  
  
  private static final Logger LOGGER = LoggerFactory.getLogger(SpeechletUtils.class); 
  private static final Map<Integer, String> ID_KEY_MAP = new HashMap<>();
  
  private static void populateIdKeyMap(){
    ID_KEY_MAP.put(1, "9264730815");
    ID_KEY_MAP.put(2, "5180374629");
    ID_KEY_MAP.put(3, "6308159264");
    ID_KEY_MAP.put(4, "6429057381");
    ID_KEY_MAP.put(5, "1234506789");
  }
  
  public static SpeechletResponse getSimpleSpeechletResponse(String speechText, String repromptText) {    
    SimpleCard card = new SimpleCard();

    card.setTitle("SecureSession");
    card.setContent(speechText);
   
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);
    return SpeechletResponse.newTellResponse(speech, card);
  }
  
  public static SpeechletResponse getSpeechletResponse(Intent intent, String speechText, String repromptText, String challenge, int keyId) throws Exception {    
    populateIdKeyMap();
    SimpleCard card = new SimpleCard();

    card.setTitle("SecureSession");
    card.setContent(speechText);
   
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);   
    
    String plainText = null;
    boolean needToAsk = false;        
    if(intent.getSlot(AlexaUtils.CIPHER_TEXT) != null && intent.getSlot(AlexaUtils.CIPHER_TEXT).getValue() != null && !intent.getSlot(AlexaUtils.CIPHER_TEXT).getValue().isEmpty()) {
      String cipherText = intent.getSlot(AlexaUtils.CIPHER_TEXT).getValue();             
  
      plainText = CipherUtils.decrypt(cipherText, ID_KEY_MAP.get(keyId));
    }
    LOGGER.info("PLAIN TEXT IS: " + plainText + ", CHALLENGE IS: " + challenge);
    needToAsk = AlexaUtils.needToAskChallengeResponse(plainText, challenge);   
    //Gets if the challenge is null  
    if (needToAsk) {      
      PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
      repromptSpeech.setText(repromptText);

      Reprompt reprompt = new Reprompt();
      reprompt.setOutputSpeech(repromptSpeech);

      return SpeechletResponse.newAskResponse(speech, reprompt, card);

    } else {      
      String correctChallengeAnswer = "You are authenticated!";
      PlainTextOutputSpeech correctChallengeSpeech = new PlainTextOutputSpeech();
      correctChallengeSpeech.setText(correctChallengeAnswer);   
      return SpeechletResponse.newTellResponse(correctChallengeSpeech, card);
    }
  }

  public static SpeechletResponse getChallenge(Intent intent, Session session, String challenge, int keyId) throws Exception {       
    String speechText = "Hello, the challenge is " + challenge + " and the Key ID is " + keyId + ", please tell me the answer of the challenge.";
    String repromptText = "Sorry, I did not understand."; 
    
    return getSpeechletResponse(intent, speechText, repromptText, challenge, keyId);
  } 
 
  
  public static SpeechletResponse getHelpIntentResponse(Intent intent,Session session) {
    String speechText = "Do you want me to send another challenge?";    

    return getSimpleSpeechletResponse(speechText, speechText);
  }
  
  public static SpeechletResponse getExitIntentResponse(Intent intent, Session session) {    
    String speechText = "Goodbye";

    return getSimpleSpeechletResponse(speechText, speechText);
  }  
}
