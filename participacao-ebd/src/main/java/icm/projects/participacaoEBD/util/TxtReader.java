package icm.projects.participacaoEBD.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;



public class TxtReader extends JsonReader{


  public static String readTxtFromUrl(String url) throws IOException{
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String text = readAll(rd);
      return text;
    } finally {
      is.close();
    }
  }
}