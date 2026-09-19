package br.com.alura.command;

import java.io.IOException;

import br.com.alura.service.AbrigoService;

public class ListarAbrigoCommand implements Command {

    private final AbrigoService abrigoService;

    public ListarAbrigoCommand(AbrigoService abrigoService) {
        this.abrigoService = abrigoService;
    }

    @Override
    public void execute() {
        try {
            abrigoService.listarAbrigos();
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
