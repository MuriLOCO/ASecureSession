package ca.concordia.alexa.AlexaSecureSession.speechlets.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SecureSessionKeyDTO {

  @JsonProperty("id")
  private int id;
  
  @JsonProperty("privateKey")
  public byte[] privateKey;
  
  @JsonProperty("publicKey")
  public byte[] publicKey;
}
