package br.com.alura.domain;

public class Abrigo {

    private long id;
    private String nome;
    private String telefone;
    private String email;

    public Abrigo() {
    }

    public Abrigo(Long id, String nome, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

}
