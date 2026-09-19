package br.com.alura.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.http.HttpResponse;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.alura.domain.Abrigo;

public class AbrigoServiceTest {
    private ConsumoApi client = mock(ConsumoApi.class);
    private Scanner scanner = mock(Scanner.class);
    private AbrigoService abrigoService = new AbrigoService(scanner, client);
    @SuppressWarnings("unchecked")
    private HttpResponse<String> response = mock(HttpResponse.class);
    private Abrigo abrigo = new Abrigo(0l, "Teste", "61981880392", "abrigo_alura@gmail.com");

    @Test
    public void deveVerificarSeGetAbrigosSeraChamado() throws IOException, InterruptedException {
        String expectedAbrigosCadastrados = "Abrigos cadastrados:";
        String expectedIdENome = "0 - Teste";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);

        when(response.body()).thenReturn("[{" + abrigo.toString() + "}]");
        when(client.getAbrigos()).thenReturn(response);

        abrigoService.listarAbrigos();

        String[] lines = baos.toString().split(System.lineSeparator());
        String actualAbrigosCadastrados = lines[0];
        String actualIdENome = lines[1];

        Assertions.assertEquals(expectedAbrigosCadastrados, actualAbrigosCadastrados);
        Assertions.assertEquals(expectedIdENome, actualIdENome);
    }
}
