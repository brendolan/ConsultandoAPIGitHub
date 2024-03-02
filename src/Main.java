import br.com.brendolan.Exceptions.UserNotFoundException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("---- Informações do Usuário no GitHub ----");
            System.out.println("Digite o nome de usuário: ");

            String username = scanner.nextLine();

            String endereco = "https://api.github.com/users/" + username;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .header("Accept", "application/vnd.github.v3+json")
                    .build();

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 404) {
                throw new UserNotFoundException(username);
            }

            String json = response.body();
            System.out.println(json);

        } catch (IOException | InterruptedException e) {
            System.out.println("There was an error while processing your request");
            e.printStackTrace();
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            scanner.close();
        }

    }
}