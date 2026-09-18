package br.com.alura.model;

public enum TipoAnimal {
    CACHORRO("cachorro"),
    GATO("gato");

    private String tipoString;

    TipoAnimal(String tipoString) {
        this.tipoString = tipoString;
    }

    public static TipoAnimal fromString(String tipoString) {

        for (TipoAnimal tipoAnimal : TipoAnimal.values()) {
            if (tipoAnimal.tipoString.equalsIgnoreCase(tipoString.trim())) {
                return tipoAnimal;
            }
        }

        throw new IllegalArgumentException("Nenhum tipo encontrado para " + tipoString);
    }
}
