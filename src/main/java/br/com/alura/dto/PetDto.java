package br.com.alura.dto;

import br.com.alura.model.TipoAnimal;

public class PetDto {

    TipoAnimal tipo;
    String nome;
    String raca;
    int idade;
    String cor;
    Float peso;

    public PetDto(String tipo, String nome, String raca, int idade, String cor, Float peso) {
        this.tipo = TipoAnimal.fromString(tipo);

        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.cor = cor;
        this.peso = peso;
    }

    public TipoAnimal getTipo() {
        return tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getRaca() {
        return raca;
    }

    public int getIdade() {
        return idade;
    }

    public String getCor() {
        return cor;
    }

    public Float getPeso() {
        return peso;
    }

}
