package credentials;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class OAuthClient {
    private static final String TOKEN_URL = "https://lodqa.wonderline.eu/loyalty-ws/v1/api/oauth2/token";
    private static final String CLIENT_ID = "wl_oa";
    private static final String CLIENT_SECRET = "a";

    public static String getAccessToken() throws Exception {
        URL url = new URL(TOKEN_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setDoOutput(true);

        String params = "grant_type=client_credentials"
                + "&client_id=" + CLIENT_ID
                + "&client_secret=" + CLIENT_SECRET;

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = params.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.length());

            String accessToken = parseAccessToken(response.toString());
            return accessToken;
        }
    }

    private static String parseAccessToken(String jsonResponse) {
        int start = jsonResponse.indexOf("access_token\":\"") + "access_token\":\"".length();
        int end = jsonResponse.indexOf("\"", start);
        return jsonResponse.substring(start, end);
    }


}