package ca.concordia.alexa.AlexaSecureSession.utils;

import java.util.Random;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

public class SpeechletUtils { 
 
  private static Random rand = new Random();
  private static String speechText;
  private static String repromptText;
  
  public static SpeechletResponse getSpeechletResponse(String speechText, String repromptText, boolean isAskResponse) {

    SimpleCard card = new SimpleCard();

    card.setTitle("SecureSession");
    card.setContent(speechText);
   
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);

    if (isAskResponse) {
      
      PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
      repromptSpeech.setText(repromptText);

      Reprompt reprompt = new Reprompt();
      reprompt.setOutputSpeech(repromptSpeech);

      return SpeechletResponse.newAskResponse(speech, reprompt, card);

    } else {
      return SpeechletResponse.newTellResponse(speech, card);
    }
  }

  public static SpeechletResponse getChallenge(final Intent intent, final Session session) {       
    int challenge = getRandomNumber();
    speechText = "Hello, the challenge is " + challenge;
    repromptText = "Hello, the challenge is " + challenge;
    
    return getSpeechletResponse(speechText, repromptText, true);
  }
  
  private static int getRandomNumber() {
    return rand.nextInt(99);
  }

}
