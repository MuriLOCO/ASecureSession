package ca.concordia.alexa.AlexaSecureSession.speechlets;

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import ca.concordia.alexa.AlexaSecureSession.utils.AlexaUtils;

public class SpeechletRequestHandler extends SpeechletRequestStreamHandler {
	private static final Set<String> supportedApplicationIds;

    static {        
        supportedApplicationIds = new HashSet<String>();
        supportedApplicationIds.add(AlexaUtils.ALEXA_API_KEY);
    }

    public SpeechletRequestHandler() {
    	
        super(new Speechlet(), supportedApplicationIds);
    }
}
