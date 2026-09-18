package br.com.alura.service;

import java.net.ConnectException;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AbrigoService {

    private final Scanner scanner;
    private final ConsumoApi consumoApi;

    public AbrigoService(Scanner scanner, ConsumoApi consumoApi) {
        this.scanner = scanner;
        this.consumoApi = consumoApi;
    }

    public void listarAbrigos() {

        try {
            HttpResponse<String> response = consumoApi.getAbrigos();
            JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();
            System.out.println("Abrigos cadastrados:");
            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                long id = jsonObject.get("id").getAsLong();
                String nome = jsonObject.get("nome").getAsString();
                System.out.println(id + " - " + nome);
            }
        } catch (ConnectException e) {
            System.out.println("Impossível acessar a API");
        } catch (Exception e) {
            System.out.println("listarAbrigos(): ");
            e.printStackTrace();
        }
    }

    public void cadastrarAbrigo() {
        System.out.println("Digite o nome do abrigo:");
        String nome = scanner.nextLine();
        System.out.println("Digite o telefone do abrigo:");
        String telefone = scanner.nextLine();
        System.out.println("Digite o email do abrigo:");
        String email = scanner.nextLine();

        JsonObject json = new JsonObject();
        json.addProperty("nome", nome);
        json.addProperty("telefone", telefone);
        json.addProperty("email", email);

        try {
            HttpResponse<String> response = consumoApi.postAbrigo(json);
            int statusCode = response.statusCode();
            if (statusCode == 200) {
                System.out.println("Abrigo cadastrado com sucesso!");
            } else if (statusCode == 400 || statusCode == 500) {
                System.out.println("Erro ao cadastrar o abrigo:");
            }
            System.out.println(response.body());
        } catch (ConnectException e) {
            System.out.println("Impossível acessar a API");
        } catch (Exception e) {
            System.out.println("cadastrarAbrigo(): ");
            e.printStackTrace();
        }
    }

}
