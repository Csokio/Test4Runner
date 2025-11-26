package org.example.utils;

import java.io.*;
import java.util.Properties;

public class DeleteByCode {

    public DeleteByCode(String code){
        if(code.equals("counter")) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("counter.txt"));
                String firstLine = reader.readLine();

                BufferedWriter writer = new BufferedWriter(new FileWriter("counter.txt"));
                writer.write(firstLine + '\n');

                reader.close();
                writer.close();

                System.out.println("Successfully deleted all rows except the first one.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (code.equals("counter_output")) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("counter_output.txt"));
                String firstLine = reader.readLine();

                BufferedWriter writer = new BufferedWriter(new FileWriter("counter_output.txt"));
                writer.write(firstLine + '\n');

                reader.close();
                writer.close();

        } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (code.equals("properties")) {
            Properties properties = new Properties();
            try (InputStream input = new FileInputStream("src/main/resources/images.properties")) {
                properties.load(input);
            } catch (IOException e) {
                e.printStackTrace();
            }

            properties.clear();

            try (OutputStream output = new FileOutputStream("src/main/resources/images_copy.properties")) {
                properties.store(output, null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            properties.clear();
        }   else {
            System.out.println("Not a valid delete code: " + code);
        }

    }

}
