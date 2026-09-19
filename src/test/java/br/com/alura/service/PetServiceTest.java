package br.com.alura.service;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.http.HttpResponse;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.alura.dto.PetDto;

public class PetServiceTest {
    private Scanner scanner = mock(Scanner.class);
    private ConsumoApi client = mock(ConsumoApi.class);
    private PetService petService = new PetService(scanner, client);
    // private BufferedReader reader = mock(BufferedReader.class);
    @SuppressWarnings("unchecked")
    private HttpResponse<String> response = mock(HttpResponse.class);
    private String jsonPetDto = """
            "tipo":"gato","nome":"Leopoldo","raca":"Siamês","idade":"4","cor":"Cinza","peso":"6.5"
            """;
    private String nomePet = "Leopoldo";

    @Test
    public void deveCadastrarPetComSucesso() throws IOException, InterruptedException {

        String expected = "Pet cadastrado com sucesso: " + nomePet;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);

        String idAbrigo = "1";

        when(scanner.nextLine()).thenReturn(idAbrigo, "pets.csv");
        when(response.body()).thenReturn("[{" + jsonPetDto + "}]");
        when(client.postPets(eq(idAbrigo), any(PetDto.class))).thenReturn(response);
        when(response.statusCode()).thenReturn(200);

        petService.cadastrarPets();

        String actual = baos.toString();

        Assertions.assertTrue(actual.contains(expected));

    }

    @Test
    public void deveVerificarSeDispararRequisicaoPostSeraChamado() throws IOException, InterruptedException {
        String userInput = String.format("Teste%spets.csv",
                System.lineSeparator());
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);

        when(client.postPets(anyString(), any())).thenReturn(response);

        petService.cadastrarPets();
        verify(client.postPets(anyString(), anyString()), atLeast(1));
    }
}
