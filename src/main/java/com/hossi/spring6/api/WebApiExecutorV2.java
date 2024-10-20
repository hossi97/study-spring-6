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

public class WebApiExecutorV2 implements ApiExecutor {
  @Override
  public String execute(URI uri) throws IOException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(uri)
        .GET()
        .build();

    try (HttpClient httpClient = HttpClient.newBuilder().build()) {
      return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
