package br.com.alura.command;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Scanner;

import br.com.alura.service.ConsumoApi;
import br.com.alura.service.PetService;

public class ListarPetsCommand implements Command {

    @Override
    public void execute() {
        try {
            Scanner scanner = new Scanner(System.in);
            HttpClient client = HttpClient.newHttpClient();
            ConsumoApi consumoApi = new ConsumoApi(client);
            PetService petService = new PetService(scanner, consumoApi);

            petService.listarPets();

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}