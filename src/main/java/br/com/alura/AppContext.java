package br.com.alura;

import java.net.http.HttpClient;
import java.util.Map;
import java.util.Scanner;

import br.com.alura.command.CadastrarAbrigoCommand;
import br.com.alura.command.CadastrarPetsCommand;
import br.com.alura.command.Command;
import br.com.alura.command.CommandExecutor;
import br.com.alura.command.ListarAbrigoCommand;
import br.com.alura.command.ListarPetsCommand;
import br.com.alura.service.AbrigoService;
import br.com.alura.service.ConsumoApi;
import br.com.alura.service.PetService;

public class AppContext implements AutoCloseable {

    private final Scanner scanner;
    private final Menu menu;

    public AppContext() {
        this.scanner = new Scanner(System.in);
        HttpClient client = HttpClient.newHttpClient();
        ConsumoApi consumoApi = new ConsumoApi(client);

        AbrigoService abrigoService = new AbrigoService(scanner, consumoApi);
        PetService petService = new PetService(scanner, consumoApi);

        Map<String, Command> comandos = Map.of(
                "1", new ListarAbrigoCommand(abrigoService),
                "2", new CadastrarAbrigoCommand(abrigoService),
                "3", new ListarPetsCommand(petService),
                "4", new CadastrarPetsCommand(petService));

        this.menu = new Menu(scanner, new CommandExecutor(), comandos);
    }

    public Menu getMenu() {
        return this.menu;
    }

    @Override
    public void close() {
        scanner.close();
    }
}
