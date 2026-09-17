package br.com.alura.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import br.com.alura.service.ConsumoApi;

public class Main {

    Scanner scanner = new Scanner(System.in);
    ConsumoApi consumoApi = new ConsumoApi();

    public void exibirMenu() {
        System.out.println("##### BOAS VINDAS AO SISTEMA ADOPET CONSOLE #####");
        try {
            int opcaoEscolhida = 0;
            while (opcaoEscolhida != 5) {
                System.out.println("\nDIGITE O NÚMERO DA OPERAÇÃO DESEJADA:");
                System.out.println("1 -> Listar abrigos cadastrados");
                System.out.println("2 -> Cadastrar novo abrigo");
                System.out.println("3 -> Listar pets do abrigo");
                System.out.println("4 -> Importar pets do abrigo");
                System.out.println("5 -> Sair");

                opcaoEscolhida = scanner.nextInt();
                scanner.nextLine();

                switch (opcaoEscolhida) {
                    case 1:
                        listarAbrigos();
                        break;
                    case 2:
                        cadastrarAbrigo();
                        break;
                    case 3:
                        listarPets();
                        break;
                    case 4:
                        cadastrarPets();
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("NÚMERO INVÁLIDO!");
                        opcaoEscolhida = 0;
                        break;
                }
            }
            System.out.println("Finalizando o programa...");
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        } catch (JsonSyntaxException e) {
            System.out.println(e.getMessage());
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

        HttpResponse<String> response = consumoApi.postAbrigo(json);

        int statusCode = response.statusCode();
        if (statusCode == 200) {
            System.out.println("Abrigo cadastrado com sucesso!");
        } else if (statusCode == 400 || statusCode == 500) {
            System.out.println("Erro ao cadastrar o abrigo:");
        }
        System.out.println(response.body());
    }

    public void listarPets() {
        System.out.println("Digite o id ou nome do abrigo:");
        String idOuNome = scanner.nextLine();

        HttpResponse<String> response = consumoApi.getPets(idOuNome);

        int statusCode = response.statusCode();
        if (statusCode == 404 || statusCode == 500) {
            System.out.println("ID ou nome não cadastrado!");
            return;
        }

        JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();
        System.out.println("Pets cadastrados:");
        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            long id = jsonObject.get("id").getAsLong();
            String tipo = jsonObject.get("tipo").getAsString();
            String nome = jsonObject.get("nome").getAsString();
            String raca = jsonObject.get("raca").getAsString();
            int idade = jsonObject.get("idade").getAsInt();
            System.out.println(id + " - " + tipo + " - " + nome + " - " + raca + " - " + idade + " ano(s)");
        }
    }

    public void cadastrarPets() {
        System.out.println("Digite o id ou nome do abrigo:");
        String idOuNome = scanner.nextLine();

        System.out.println("Digite o nome do arquivo CSV:");
        String nomeArquivo = scanner.nextLine();

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(nomeArquivo));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] campos = line.split(",");
                String tipo = campos[0];
                String nome = campos[1];
                String raca = campos[2];
                int idade = Integer.parseInt(campos[3]);
                String cor = campos[4];
                Float peso = Float.parseFloat(campos[5]);

                JsonObject json = new JsonObject();
                json.addProperty("tipo", tipo.toUpperCase());
                json.addProperty("nome", nome);
                json.addProperty("raca", raca);
                json.addProperty("idade", idade);
                json.addProperty("cor", cor);
                json.addProperty("peso", peso);

                HttpResponse<String> response = consumoApi.postPets(idOuNome, json);

                int statusCode = response.statusCode();
                if (statusCode == 200) {
                    System.out.println("Pet cadastrado com sucesso: " + nome);
                } else if (statusCode == 404) {
                    System.out.println("Id ou nome do abrigo não encontado!");
                    break;
                } else if (statusCode == 400 || statusCode == 500) {
                    System.out.println("Erro ao cadastrar o pet: " + nome);
                    System.out.println(response.body());
                    break;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo: " + nomeArquivo);
            return;
        }

    }

}
