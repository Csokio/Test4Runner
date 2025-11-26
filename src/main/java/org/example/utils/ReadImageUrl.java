package org.example.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReadImageUrl {

    private String imageUrl;
    private String filePath;

    public ReadImageUrl(String imageUrl, String filePath) {
        this.imageUrl = imageUrl;
        this.filePath = filePath;
    }

    public boolean writeImage(String fileName) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();

            OutputStream outputStream = new FileOutputStream(filePath + "\\" + fileName);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("Kép letöltve: " + fileName);

            inputStream.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Hiba történt a kép letöltése közben: " + e.getMessage());
            return false;
        }
        return true;
    }
}
