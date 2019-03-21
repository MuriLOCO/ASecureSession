package ca.concordia.alexa.AlexaSecureSession.speechlets.models;

import java.security.PrivateKey;
import java.security.PublicKey;

import lombok.Data;

@Data
public class SecureSessionKey {
  
  public SecureSessionKey(int id, PrivateKey privateKey, PublicKey publicKey) {
    this.id = id;
    this.privateKey = privateKey;
    this.publicKey = publicKey;
  }
  
  private int id;
  private PrivateKey privateKey;
  private PublicKey publicKey;
  
  
}
