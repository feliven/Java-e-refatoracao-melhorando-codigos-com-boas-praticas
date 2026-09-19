package br.com.alura;

import java.util.Map;
import java.util.Scanner;

import br.com.alura.command.Command;
import br.com.alura.command.CommandExecutor;

public class Menu {

    private final Scanner scanner;
    private final CommandExecutor executor;
    private final Map<String, Command> comandos;

    public Menu(Scanner scanner, CommandExecutor executor, Map<String, Command> comandos) {
        this.scanner = scanner;
        this.executor = executor;
        this.comandos = comandos;
    }

    public void exibir() {
        System.out.println("##### BOAS VINDAS AO SISTEMA ADOPET CONSOLE #####");
        String opcao = "";

        while (!"5".equals(opcao)) {
            System.out.println("\nDIGITE O NÚMERO DA OPERAÇÃO DESEJADA:");
            System.out.println("1 -> Listar abrigos cadastrados");
            System.out.println("2 -> Cadastrar novo abrigo");
            System.out.println("3 -> Listar pets do abrigo");
            System.out.println("4 -> Importar pets do abrigo");
            System.out.println("5 -> Sair");

            opcao = scanner.nextLine();

            if ("5".equals(opcao)) {
                break;
            }

            Command command = comandos.get(opcao);
            if (command != null) {
                executor.executeCommand(command);
            } else {
                System.out.println("NÚMERO INVÁLIDO!");
            }
        }
        System.out.println("Finalizando o programa...");
    }
}
