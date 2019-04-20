package ca.concordia.alexa.AlexaSecureSession.enums;

public enum BaseVariables {
  
  NUMBER_ORDER("0123456789");

  private final String value;

  private BaseVariables(final String baseVariable) {
    value = baseVariable;
  }

  public String getValue() {
    return value;
  }
}
