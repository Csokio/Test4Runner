package org.example.endpoints.qa2pl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private static Properties properties = new Properties();

    static {
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Nem sikerült betölteni a konfigurációs fájlt.");
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}