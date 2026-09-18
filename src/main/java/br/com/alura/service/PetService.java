package br.com.alura.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ConnectException;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.alura.dto.PetDto;

public class PetService {

    private final Scanner scanner;
    private final ConsumoApi consumoApi;

    public PetService(Scanner scanner, ConsumoApi consumoApi) {
        this.scanner = scanner;
        this.consumoApi = consumoApi;
    }

    public void listarPets() {
        System.out.println("Digite o id ou nome do abrigo:");
        String idOuNome = scanner.nextLine();

        try {
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
        } catch (ConnectException e) {
            System.out.println("Impossível acessar a API");
        } catch (Exception e) {
            System.out.println("listarPets(): ");
            e.printStackTrace();
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

                PetDto pet = new PetDto(tipo, nome, raca, idade, cor, peso);

                HttpResponse<String> response = consumoApi.postPets(idOuNome, pet);

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
        } catch (ConnectException e) {
            System.out.println("Impossível acessar a API");
        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo: " + nomeArquivo);
            return;
        } catch (Exception e) {
            System.out.println("cadastrarPets(): ");
            e.printStackTrace();
        }

    }
}
