package br.com.alura.main;

import java.net.http.HttpClient;
import java.util.Scanner;

import br.com.alura.service.AbrigoService;
import br.com.alura.service.ConsumoApi;
import br.com.alura.service.PetService;

public class Main {

    Scanner scanner = new Scanner(System.in);
    HttpClient client = HttpClient.newHttpClient();
    ConsumoApi consumoApi = new ConsumoApi(client);
    AbrigoService abrigoService = new AbrigoService(scanner, consumoApi);
    PetService petService = new PetService(scanner, consumoApi);

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
                        abrigoService.listarAbrigos();
                        break;
                    case 2:
                        abrigoService.cadastrarAbrigo();
                        break;
                    case 3:
                        petService.listarPets();
                        break;
                    case 4:
                        petService.cadastrarPets();
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

}
