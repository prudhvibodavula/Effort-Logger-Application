import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

//Created by Aditya Sai Nune  25-Mar-2023

public class OTPSender {
    private static final String ACCOUNT_SID = "your Accnt SID here";
    private static final String AUTH_TOKEN = "your auth token here";
    private static final String FROM_NUMBER = "phone number here";
    private static final String OTP_MESSAGE = "Your OTP is: %s";

    public static void sendOTP(String to, String otp) throws Exception {
        String message = String.format(OTP_MESSAGE, otp);
        String encodedAuth = Base64.getEncoder().encodeToString((ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes());
        String url = "https://api.twilio.com/2010-04-01/Accounts/" + ACCOUNT_SID + "/Messages.json";
        String data = "From=" + FROM_NUMBER + "&To=" + to + "&Body=" + message;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Basic " + encodedAuth);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", String.valueOf(data.length()));
        connection.setDoOutput(true);

        try (OutputStream outputStream = connection.getOutputStream()) {
            outputStream.write(data.getBytes());
        }

        int statusCode = connection.getResponseCode();
        if (statusCode != HttpURLConnection.HTTP_CREATED) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                throw new RuntimeException("Failed to send OTP: " + response.toString());
            }
        }
    }
}
