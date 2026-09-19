package br.com.alura.command;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Scanner;

import br.com.alura.service.AbrigoService;
import br.com.alura.service.ConsumoApi;

public class ListarAbrigoCommand implements Command {
    @Override
    public void execute() {
        try {
            Scanner scanner = new Scanner(System.in);
            HttpClient client = HttpClient.newHttpClient();
            ConsumoApi consumoApi = new ConsumoApi(client);
            AbrigoService abrigoService = new AbrigoService(scanner, consumoApi);

            abrigoService.listarAbrigos();
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
