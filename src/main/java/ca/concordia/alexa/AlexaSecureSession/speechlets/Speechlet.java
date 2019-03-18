package ca.concordia.alexa.AlexaSecureSession.speechlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.SpeechletV2;

import ca.concordia.alexa.AlexaSecureSession.utils.SpeechletUtils;

public class Speechlet implements SpeechletV2 {

  private static final Logger LOGGER = LoggerFactory.getLogger(Speechlet.class);
  
  private static String speechText;
  private static String repromptText;  
  
  
  public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
    LOGGER.info("onSessionStarted requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
          requestEnvelope.getSession().getSessionId());
  }

  public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {    
    LOGGER.info("onLaunch requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
          requestEnvelope.getSession().getSessionId());
    
    speechText = "Hello, to start your secure session please say Yes.";
    repromptText = "Hello, to start your secure session please say Yes.";
    return SpeechletUtils.getSpeechletResponse(speechText, repromptText, true);
  }

  public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
    IntentRequest request = requestEnvelope.getRequest();
    Session session = requestEnvelope.getSession();

    LOGGER.info("onIntent requestId={}, sessionId={}", request.getRequestId(), session);
    
    // Get intent from the request object.
    Intent intent = request.getIntent();
    String intentName = (intent != null) ? intent.getName() : null;

    if ("SecureSessionStartIntent".equals(intentName))
      return SpeechletUtils.getChallenge(intent, session);
    else {
      String errorSpeech = "Sorry, Please try something else.";
      return SpeechletUtils.getSpeechletResponse(errorSpeech, errorSpeech, true);
    }
  }

  public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
    // TODO Auto-generated method stub
  }

}
