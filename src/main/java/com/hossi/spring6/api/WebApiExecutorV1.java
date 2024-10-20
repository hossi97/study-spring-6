package com.hossi.spring6.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Collectors;

public class WebApiExecutorV1 implements ApiExecutor {

  @Override
  public String execute(URI uri) throws IOException {
    String response;
    HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
      response = br.lines().collect(Collectors.joining());
    }
    return response;
  }

}
