package ca.concordia.alexa.AlexaSecureSession.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpUtils {

  /**
   * Make a rest call to a certain url and get it's content in JSON format
   * @param url
   * @return
   * @throws ClientProtocolException
   * @throws IOException
   */
  public static String makeRestCallAndGetResponse(String url) throws ClientProtocolException, IOException {
    HttpClient httpClient = HttpClientBuilder.create().build();
    HttpGet httpGet = new HttpGet(url);
    HttpResponse response = httpClient.execute(httpGet);

    BufferedReader bufferedReader = new BufferedReader(
          new InputStreamReader(response.getEntity().getContent()));

    StringBuffer result = new StringBuffer();
    String line = "";
    while ((line = bufferedReader.readLine()) != null) {
      result.append(line);
    }    
   return result.toString();
  }

}
