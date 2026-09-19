package br.com.alura;

import java.util.InputMismatchException;
import java.util.Scanner;

import br.com.alura.command.CadastrarAbrigoCommand;
import br.com.alura.command.CadastrarPetsCommand;
import br.com.alura.command.CommandExecutor;
import br.com.alura.command.ListarAbrigoCommand;
import br.com.alura.command.ListarPetsCommand;

public class AdopetConsoleApplication {

    public static void main(String[] args) {

        CommandExecutor executor = new CommandExecutor();
        Scanner scanner = new Scanner(System.in);

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

                try {
                    opcaoEscolhida = scanner.nextInt();
                } catch (InputMismatchException e) {
                }

                scanner.nextLine();

                switch (opcaoEscolhida) {
                    case 1 -> executor.executeCommand(new ListarAbrigoCommand());
                    case 2 -> executor.executeCommand(new CadastrarAbrigoCommand());
                    case 3 -> executor.executeCommand(new ListarPetsCommand());
                    case 4 -> executor.executeCommand(new CadastrarPetsCommand());
                    case 5 -> {
                        break;
                    }
                    default -> {
                        System.out.println("NÚMERO INVÁLIDO!");
                        opcaoEscolhida = 0;
                    }
                }
            }
            System.out.println("Finalizando o programa...");
        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner.close();

    }

}
