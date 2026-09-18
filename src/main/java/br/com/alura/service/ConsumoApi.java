package br.com.alura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.JsonObject;

public class ConsumoApi {
    String uriBase = "http://localhost:8080/abrigos";
    HttpClient client = HttpClient.newHttpClient();

    public HttpResponse<String> getAbrigos() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uriBase))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());

    }

    public HttpResponse<String> postAbrigo(JsonObject json) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uriBase))
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> getPets(String idOuNome) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uriBase + '/' + idOuNome + "/pets"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());

    }

    public HttpResponse<String> postPets(String idOuNome, JsonObject json) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uriBase + '/' + idOuNome + "/pets"))
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());

    }
}
