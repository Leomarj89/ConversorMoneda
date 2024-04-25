import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Scanner;

public class ConvertidorMonedas {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String apiUrl = "https://v6.exchangerate-api.com/v6/9235abc5a2e544e83549f6bf/latest/USD";

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .timeout(Duration.ofSeconds(10))
                .header("Accept", "application/json")
                .build();

        while (true) {
            System.out.println("=== Menú de Conversión de Monedas ===");
            System.out.println("1. Convertir moneda");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    realizarConversion(httpClient, request);
                    break;
                case 2:
                    System.out.println("¡Hasta luego!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }
    }

    public static void realizarConversion(HttpClient httpClient, HttpRequest request) {
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();

            RespuestaParser.parsearRespuesta(responseBody);

            Scanner scanner = new Scanner(System.in);

            System.out.println("Seleccione la opción de cambio:");
            System.out.println("1. CLP - USD");
            System.out.println("2. USD - CLP");
            System.out.println("3. CLP - COP");
            System.out.println("4. COP - USD");
            System.out.println("5. CLP - ARS");
            System.out.println("6. ARS - USD");
            System.out.print("Ingrese el número de opción: ");
            int opcionCambio = scanner.nextInt();

            String monedaOrigen;
            String monedaDestino;

            switch (opcionCambio) {
                case 1:
                    monedaOrigen = "CLP";
                    monedaDestino = "USD";
                    break;
                case 2:
                    monedaOrigen = "USD";
                    monedaDestino = "CLP";
                    break;
                case 3:
                    monedaOrigen = "CLP";
                    monedaDestino = "COP";
                    break;
                case 4:
                    monedaOrigen = "COP";
                    monedaDestino = "USD";
                    break;
                case 5:
                    monedaOrigen = "CLP";
                    monedaDestino = "ARS";
                    break;
                case 6:
                    monedaOrigen = "ARS";
                    monedaDestino = "USD";
                    break;
                default:
                    System.out.println("Opción no válida.");
                    return;
            }

            System.out.println("Ingrese la cantidad a convertir:");
            double cantidad = scanner.nextDouble();

            double tasaDeCambio = RespuestaParser.obtenerTasaDeCambio(monedaOrigen, monedaDestino);

            if (tasaDeCambio != -1) {
                double resultado = convertirMoneda(cantidad, tasaDeCambio);
                System.out.println("El resultado de la conversión es: " + resultado + " " + monedaDestino);
            } else {
                System.out.println("No se encontró la tasa de cambio para la moneda especificada.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static double convertirMoneda(double cantidad, double tasaDeCambio) {
        return cantidad * tasaDeCambio;
    }
}