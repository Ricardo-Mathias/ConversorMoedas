import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

    public static void main(String[] args) {
        String apiKey = "44906997cba612a86a2c6efe";
        String fromCurrency;
        String toCurrency;
        double exchangeRate = 0.0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Digite a moeda de origem (ex: USD): ");
            fromCurrency = reader.readLine();

            System.out.println("Digite a moeda de destino (ex: BRL): ");
            toCurrency = reader.readLine();

            String urlStr = "https://v6.exchangerate-api.com/v6/44906997cba612a86a2c6efe/latest/USD";


            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String result = response.toString();

            String[] parts = result.split("\"rate\":");
            if (parts.length > 1) {
                exchangeRate = Double.parseDouble(parts[1].split("}")[0]);
                System.out.println("A taxa de câmbio de " + fromCurrency + " para " + toCurrency + " é: " + exchangeRate);
            } else {
                System.out.println("Erro ao obter a taxa de câmbio da resposta JSON.");
                return;
            }

            System.out.println("Digite o valor em " + fromCurrency + ": ");
            double valueInFromCurrency = Double.parseDouble(reader.readLine());

            double valueInToCurrency = valueInFromCurrency * exchangeRate;
            System.out.println("O valor equivalente em " + toCurrency + " é: " + valueInToCurrency);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}