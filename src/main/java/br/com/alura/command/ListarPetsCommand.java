package br.com.alura.command;

import java.io.IOException;

import br.com.alura.service.PetService;

public class ListarPetsCommand implements Command {

    private final PetService petService;

    public ListarPetsCommand(PetService petService) {
        this.petService = petService;
    }

    @Override
    public void execute() {
        try {
            petService.listarPets();
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}