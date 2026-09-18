package br.com.alura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

public class ConsumoApi {
    String uriBase = "http://localhost:8080/abrigos";
    private final HttpClient client;

    public ConsumoApi(HttpClient client) {
        this.client = client;
    }

    public HttpResponse<String> getAbrigos() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uriBase))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());

    }

    public HttpResponse<String> postAbrigo(Object object) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uriBase))
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(new Gson().toJson(object)))
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

    public HttpResponse<String> postPets(String idOuNome, Object object) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uriBase + '/' + idOuNome + "/pets"))
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(new Gson().toJson(object)))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());

    }
}
