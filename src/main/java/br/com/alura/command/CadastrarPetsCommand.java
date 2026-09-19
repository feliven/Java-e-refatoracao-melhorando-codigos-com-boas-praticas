package br.com.alura.command;

import java.io.IOException;

import br.com.alura.service.PetService;

public class CadastrarPetsCommand implements Command {

    private final PetService petService;

    public CadastrarPetsCommand(PetService petService) {
        this.petService = petService;
    }

    @Override
    public void execute() {
        try {
            petService.cadastrarPets();
        } catch (IOException | InterruptedException | NumberFormatException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}