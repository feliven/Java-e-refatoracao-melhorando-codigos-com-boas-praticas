package br.com.alura;

public class AdopetApp {

    public void run() {
        try (AppContext context = new AppContext()) {
            context.getMenu().exibir();
        }
    }
}
