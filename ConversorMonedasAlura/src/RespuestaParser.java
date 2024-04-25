import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RespuestaParser {

    private static JsonObject jsonResponse;

    public static void parsearRespuesta(String responseBody) {
        jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

        if (jsonResponse.has("conversion_rates")) {
            JsonObject conversionRates = jsonResponse.getAsJsonObject("conversion_rates");
            mostrarTasaDeCambio(conversionRates, "ARS");
            mostrarTasaDeCambio(conversionRates, "BOB");
            mostrarTasaDeCambio(conversionRates, "BRL");
            mostrarTasaDeCambio(conversionRates, "CLP");
            mostrarTasaDeCambio(conversionRates, "COP");
            mostrarTasaDeCambio(conversionRates, "USD");
        } else {
            System.out.println("La respuesta JSON no contiene el campo 'conversion_rates'.");
        }
    }

    private static void mostrarTasaDeCambio(JsonObject conversionRates, String currencyCode) {
        if (conversionRates.has(currencyCode)) {
            double rate = conversionRates.get(currencyCode).getAsDouble();
            System.out.println("Tasa de cambio para " + currencyCode + ": " + rate);
        } else {
            System.out.println("No se encontró la tasa de cambio para " + currencyCode);
        }
    }

    public static double obtenerTasaDeCambio(String monedaOrigen, String monedaDestino) {
        if (jsonResponse.has("conversion_rates")) {
            JsonObject conversionRates = jsonResponse.getAsJsonObject("conversion_rates");
            if (conversionRates.has(monedaDestino)) {
                double tasaDestino = conversionRates.get(monedaDestino).getAsDouble();
                if (conversionRates.has(monedaOrigen)) {
                    double tasaOrigen = conversionRates.get(monedaOrigen).getAsDouble();
                    // Se asume que el valor de retorno es el resultado de dividir la tasa de destino por la tasa de origen
                    return tasaDestino / tasaOrigen;
                }
            }
        }
        return -1;
    }
}
