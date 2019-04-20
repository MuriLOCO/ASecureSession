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

import ca.concordia.alexa.AlexaSecureSession.utils.CipherUtils;
import ca.concordia.alexa.AlexaSecureSession.utils.SpeechletUtils;

public class Speechlet implements SpeechletV2 {

  private static final Logger LOGGER = LoggerFactory.getLogger(Speechlet.class);

  private static String speechText;
  private static String repromptText;
  private static String errorSpeech;
  private static Integer randomNumber;
  private static final int KEY_ID = CipherUtils.getRandomKey();

  public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
    LOGGER.info("onSessionStarted requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
          requestEnvelope.getSession().getSessionId());
  }

  public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
    LOGGER.info("onLaunch requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
          requestEnvelope.getSession().getSessionId());

    speechText = "Hello, to start your secure session please say Yes.";
    repromptText = "Hello, to start your secure session please say Yes.";
    return SpeechletUtils.getSimpleSpeechletResponse(speechText, repromptText);
  }

  public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
    IntentRequest request = requestEnvelope.getRequest();
    Session session = requestEnvelope.getSession();

    LOGGER.info("onIntent requestId={}, sessionId={}", request.getRequestId(), session);

    Intent intent = request.getIntent();
    String intentName = (intent != null) ? intent.getName() : null;
    
    try {
      if ("SecureSessionStartIntent".equals(intentName)) {
        randomNumber = randomNumber != null ? randomNumber : CipherUtils.getRandomNumber();
        String challenge = String.valueOf(randomNumber);
        return SpeechletUtils.getChallenge(intent, session, challenge, KEY_ID);
      } else if ("AMAZON.HelpIntent".equals(intent.getName())) {
        return SpeechletUtils.getHelpIntentResponse(intent, session);
      } else if ("AMAZON.CancelIntent".equals(intent.getName())) {
        return SpeechletUtils.getExitIntentResponse(intent, session);
      } else if ("AMAZON.StopIntent".equals(intent.getName())) {
        return SpeechletUtils.getExitIntentResponse(intent, session);
      } else {
        errorSpeech = "Sorry, Please try something else.";
        return SpeechletUtils.getSimpleSpeechletResponse(errorSpeech, errorSpeech);
      }
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      e.printStackTrace();
    }
    errorSpeech = "Sorry, an error has occured.";
    return SpeechletUtils.getSimpleSpeechletResponse(errorSpeech, errorSpeech);
  }

  public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
    LOGGER.info("onSessionEnded requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
          requestEnvelope.getSession().getSessionId());
  }

}
