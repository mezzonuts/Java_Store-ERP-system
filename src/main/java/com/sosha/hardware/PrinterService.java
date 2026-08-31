package com.sosha.hardware;
import org.springframework.stereotype.Service;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
@Service
public class PrinterService {
  public byte[] escposReceipt(String header, String body, String footer){
    String esc = "\u001B";
    StringBuilder b = new StringBuilder();
    b.append(esc).append("@");
    b.append(esc).append("a1");
    b.append(header).append("\n");
    b.append(esc).append("a0");
    b.append(body).append("\n");
    b.append(esc).append("a1");
    b.append(footer).append("\n");
    b.append(esc).append("d4");
    b.append(esc).append("V1");
    return b.toString().getBytes(StandardCharsets.UTF_8);
  }
  public void print(byte[] data, OutputStream out) throws Exception {
    out.write(data); out.flush();
  }
}
