package com.sosha.python;

import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
public class PythonClient {
    private static final String BASE_URL = "http://127.0.0.1:8001";

    public String forecast(String productId, String historyJson) throws Exception {
        return post("/forecast", "{\"productId\":\"" + productId + "\",\"history\":" + historyJson + "}");
    }

    public boolean anomalyCheck(String payload) throws Exception {
        String resp = post("/anomaly/check", payload);
        return resp.contains("\"anomaly\":false");
    }

    private String post(String endpoint, String body) throws Exception {
        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.getOutputStream().write(body.getBytes(StandardCharsets.UTF_8));
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line);
        br.close();
        return sb.toString();
    }
}
