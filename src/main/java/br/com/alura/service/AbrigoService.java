package br.com.alura.service;

import java.net.ConnectException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;

import br.com.alura.domain.Abrigo;
import br.com.alura.dto.AbrigoDto;
import tools.jackson.databind.ObjectMapper;

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

            Abrigo[] arrayAbrigos = new ObjectMapper().readValue(response.body(), Abrigo[].class);
            List<Abrigo> listaAbrigos = List.of(arrayAbrigos);

            if (listaAbrigos.isEmpty()) {
                System.out.println("Não há abrigos cadastrados");
            } else {
                System.out.println("Abrigos cadastrados:");
                mostrarAbrigos(listaAbrigos);
            }

        } catch (ConnectException e) {
            System.out.println("Impossível acessar a API");
        } catch (Exception e) {
            System.out.println("listarAbrigos(): ");
            e.printStackTrace();
        }
    }

    private void mostrarAbrigos(List<Abrigo> listaAbrigos) {
        for (Abrigo abrigo : listaAbrigos) {
            long id = abrigo.getId();
            String nome = abrigo.getNome();
            System.out.println(id + " - " + nome);
        }
    }

    public void cadastrarAbrigo() {
        System.out.println("Digite o nome do abrigo:");
        String nome = scanner.nextLine();
        System.out.println("Digite o telefone do abrigo:");
        String telefone = scanner.nextLine();
        System.out.println("Digite o email do abrigo:");
        String email = scanner.nextLine();

        AbrigoDto abrigo = new AbrigoDto(nome, telefone, email);

        try {
            HttpResponse<String> response = consumoApi.postAbrigo(abrigo);
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
