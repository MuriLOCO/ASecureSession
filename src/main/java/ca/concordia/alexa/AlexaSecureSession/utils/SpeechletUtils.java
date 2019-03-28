package ca.concordia.alexa.AlexaSecureSession.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

import ca.concordia.alexa.AlexaSecureSession.speechlets.models.SecureSessionKey;

public class SpeechletUtils {  
  
  private static final Logger LOGGER = LoggerFactory.getLogger(SpeechletUtils.class);
  
  private static SecureSessionKey secureSessionKey; 
  
  public static SpeechletResponse getSimpleSpeechletResponse(String speechText, String repromptText) {
    SimpleCard card = new SimpleCard();

    card.setTitle("SecureSession");
    card.setContent(speechText);
   
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);
    return SpeechletResponse.newTellResponse(speech, card);
  }
  
  public static SpeechletResponse getSpeechletResponse(Intent intent, String speechText, String repromptText, int challenge) throws Exception {
    secureSessionKey = secureSessionKey == null ? SecureSessionKeyUtils.getSessionKey() : secureSessionKey;
    SimpleCard card = new SimpleCard();

    card.setTitle("SecureSession");
    card.setContent(speechText);
   
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);   
    
    byte[] text = null;   
    boolean needToAsk = false;
    LOGGER.info("*********THE VALUE IS: " + intent.getSlot(AlexaUtils.CIPHER_TEXT).getValue());
    if(intent.getSlot(AlexaUtils.CIPHER_TEXT) != null && intent.getSlot(AlexaUtils.CIPHER_TEXT).getValue() != null && !intent.getSlot(AlexaUtils.CIPHER_TEXT).getValue().isEmpty())
         text = AlexaUtils.loadAndDecryptChallenge(secureSessionKey, intent.getSlot(AlexaUtils.CIPHER_TEXT).getValue().getBytes());      
    
    needToAsk = AlexaUtils.needToAskChallengeResponse(text, challenge);
    LOGGER.info("********** NEED RESPONSE? " + needToAsk);
    //Gets if the challenge is null  
    if (needToAsk) {
      LOGGER.info("*********WAITING FOR ANSWER");
      PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
      repromptSpeech.setText(repromptText);

      Reprompt reprompt = new Reprompt();
      reprompt.setOutputSpeech(repromptSpeech);

      return SpeechletResponse.newAskResponse(speech, reprompt, card);

    } else {
      return SpeechletResponse.newTellResponse(speech, card);
    }
  }

  public static SpeechletResponse getChallenge(Intent intent, Session session, int challenge) throws Exception {       
    String speechText = "Hello, the challenge is " + challenge + " please tell me the answer of the challenge.";
    String repromptText = "Sorry, I did not understand. Your challenge is " + challenge + ", please tell me the answer of the challenge";
    
    return getSpeechletResponse(intent, speechText, repromptText, challenge);
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
