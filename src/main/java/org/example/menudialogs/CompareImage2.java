package org.example.menudialogs;
import org.example.mayuse.RemoveDuplicateSentences;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
public class CompareImage2 {

    public String compareImages() {
    /*try (OutputStream output = new FileOutputStream("src/main/resources/config.properties")) {

        Properties prop = new Properties();

        // set the properties value
        prop.setProperty("db.url", "localhost");
        prop.setProperty("db.user", "mkyong");
        prop.setProperty("db.password", "password");

        // save properties to project root folder
        prop.store(output, null);

        System.out.println(prop);

    } catch (IOException io) {
        io.printStackTrace();
    }*/
        storeImage("selenium_test_pictures\\qa2_pl", "images.properties");
        storeImage("selenium_test_pictures_overtime\\qa2_pl_overtime", "images_copy.properties");

        List<String> values1 = getValues("images.properties");

        List<String> values2 = getValues("images_copy.properties");

        List<String> keys1 = getKeys("images.properties");

        List<String> keys2 = getKeys("images_copy.properties");

        for(String key: keys2){
            System.out.println(key);
        }
        assert values1 != null;
        System.out.println(values1.size());
        //List<String> noDuplicateValues1 = deleteDuplicateValues(values1);
        assert values2 != null;
        System.out.println(values2.size());

        Map<String, String> map1 = getKeyValues("images.properties");
        Map<String, String> map2 = getKeyValues("images_copy.properties");
        assert map1 != null;
        assert map2 != null;


        //List<String> noDuplicateValues2 = deleteDuplicateValues(values2);

        //List<String> noDuplicateValues1 = values1;
        //List<String> noDuplicateValues2 = values2;

        List<String> noDuplicateValues1 = getNonDuplicateValuesWithKeys(values1);
        List<String> noDuplicateValues2 = getNonDuplicateValuesWithKeys(values2);



        //System.out.println(map1.get("shutterbug_headless_screenshot1.png.png").equals(map1.get("shutterbug_headless_screenshot6.png.png")));

        StringJoiner joiner = new StringJoiner(System.lineSeparator());

        //The result of findAndPrintKeyPairs method is saved to the Megegyező képek folder
        joiner.add(findAndPrintKeyPairs(map1,map2));

        joiner.add(findCommonKey(noDuplicateValues1, noDuplicateValues2));


        //The result of the findCommonKeys method is saved to the selenium same picture folder

        transferPicturesFromFolderToAnotherFolder("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures\\qa2_pl",
                "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_same_pics\\qa2_pl",
                findCommonKeysList(noDuplicateValues1, noDuplicateValues2));

        return joiner.toString();

        //TODO SAVE PICTURES OF FINDCOMMONKEYS METHOD INTO SAMEIMAGE.PROPERTIES


    }

    public String findCommonKey(List<String> noDuplicateValues1, List<String> noDuplicateValues2) {
        StringBuilder strBuilder = new StringBuilder();

        Set<String> processedValues = new HashSet<>();

        strBuilder.append("Ezeket a képeket tartalmazza mindkét lista:" + '\n');


        for (String value : new HashSet<>(noDuplicateValues1)) {
            if (noDuplicateValues2.contains(value) && !processedValues.contains(value)) {
                processedValues.add(value);

                List<String> originalKeys = getAllOriginalKeys(noDuplicateValues1, value);

                List<String> overTimeKeys = getAllOvertimeKeys(noDuplicateValues2, value);

                StringJoiner joiner = new StringJoiner(System.lineSeparator());


                if (!originalKeys.isEmpty()) {
                    joiner.add("A kulcs a(z) " + String.join(", ", originalKeys) + " értékhez. (original)");
                    System.out.println("A kulcs a(z) " + String.join(", ", originalKeys) + " értékhez. TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
                } else {
                    joiner.add("Nem található kulcs az értékhez. (original)");
                }

                if (!overTimeKeys.isEmpty()) {
                    joiner.add("A kulcs a(z) " + String.join(", ", overTimeKeys) + " értékhez. (copy) OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                    System.out.println("A kulcs a(z) " + String.join(", ", overTimeKeys) + " értékhez.");
                } else {
                    joiner.add("Nem található kulcs az értékhez. (copy)");
                }

                strBuilder.append(joiner.toString()).append('\n');
            }
            strBuilder.append('\n');
        }

        System.out.println("-------------------------------------------" + '\n' + new RemoveDuplicateSentences().removeDuplicateSentencesSetKeyword(strBuilder.toString(), processedValues) +
                "\n" + "------------------------------------------------------------------------");
        return new RemoveDuplicateSentences().removeDuplicateSentencesSetKeyword(strBuilder.toString(), processedValues);
    }

    private static List<String> getAllOriginalKeys(List<String> list, String value) {
        List<String> keys = new ArrayList<>();
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String currentValue = iterator.next();
            if (currentValue.equals(value)) {
                String key = getKeyByValue("images.properties", currentValue);
                if (key != null) {
                    keys.add(key);
                }
            }
        }
        return keys;
    }
    private static List<String> getAllOvertimeKeys(List<String> list, String value) {
        List<String> keys = new ArrayList<>();
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String currentValue = iterator.next();
            if (currentValue.equals(value)) {
                String key = getKeyByValue("images_copy.properties", currentValue);
                if (key != null) {
                    keys.add(key);
                }
            }
        }
        return keys;
    }

    private Map<String, String> getKeyValues(String filePath) {
        Properties properties = new Properties();
        Map<String, String> keyValues = new HashMap<>();
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/" + filePath)) {
            properties.load(fileInputStream);
            for (String key : properties.stringPropertyNames()) {
                keyValues.put(key, properties.getProperty(key));
            }
        } catch (IOException e) {
            System.err.println("Hiba történt a(z) " + filePath + " fájl beolvasása során.");
            e.printStackTrace();
            return null;
        }
        return keyValues;
    }


    private String findAndPrintKeyPairs(Map<String, String> map1, Map<String, String> map2) {
        StringBuilder strBuilder = new StringBuilder();
        Set<String> processedValues = new HashSet<>();

        for (Map.Entry<String, String> entry1 : map1.entrySet()) {
            String value = entry1.getValue();

            if (processedValues.contains(value)) {
                continue;
            }

            List<String> originalKeys = getKeysByValue(map1, value);
            List<String> overTimeKeys = getKeysByValue(map2, value);

            StringJoiner joiner = new StringJoiner(System.lineSeparator());
            joiner.add("Egyenlő értékekkel bíró kulcsok:");

            if (!originalKeys.isEmpty()) {
                joiner.add("A kulcs a(z) " + String.join(", ", originalKeys) + " értékhez. (original)");
                System.out.println("A kulcs a(z) " + String.join(", ", originalKeys) + " értékhez.");
            } else {
                joiner.add("Nem található kulcs az értékhez. (original)");
            }

            if (!overTimeKeys.isEmpty()) {
                joiner.add("A kulcs a(z) " + String.join(", ", overTimeKeys) + " értékhez. (copy)");
                System.out.println("A kulcs a(z) " + String.join(", ", overTimeKeys) + " értékhez OVERTIME.");
            } else {
                joiner.add("Nem található kulcs az értékhez. (copy)");
            }

            strBuilder.append(joiner.toString()).append('\n').append('\n');
            processedValues.add(value); // Feldolgozott érték hozzáadása a set-hez
        }

        return strBuilder.toString();
    }

    private static List<String> getKeysByValue(Map<String, String> map, String value) {
        List<String> keys = new ArrayList<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

    private static void storeImage(String directoryName, String resourceFile) {
        // Először beolvassuk a PNG képeket egy könyvtárból
        File directory = new File("C:\\Users\\SőregiKrisztián\\Pictures\\" + directoryName);
        File[] imageFiles = directory.listFiles();

        // Létrehozzuk a Properties objektumot
        Properties properties = new Properties();

        if (imageFiles != null) {
            for (File imageFile : imageFiles) {
                try {
                    // Beolvassuk a PNG képeket byte tömbbe
                    FileInputStream fileInputStream = new FileInputStream(imageFile);
                    byte[] imageData = new byte[(int) imageFile.length()];
                    fileInputStream.read(imageData);
                    fileInputStream.close();

                    // Átkonvertáljuk a képeket Base64 kódra
                    String base64Image = Base64.getEncoder().encodeToString(imageData);

                    // Hozzáadjuk a Base64 kódolt képet a Properties objektumhoz
                    properties.setProperty(imageFile.getName(), base64Image);

                    //properties.keySet().forEach(x -> System.out.println(x));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Végül mentjük a Properties objektumot egy fájlba
            try {
                FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/" + resourceFile);
                properties.store(fileOutputStream, "PNG pic's Base64 code");
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static List<String> getValues(String filePath) {
        Properties properties = new Properties();
        List<String> values = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/" + filePath)) {
            properties.load(fileInputStream);
            for (String value : properties.stringPropertyNames()) {
                values.add(properties.getProperty(value));
            }
        } catch (IOException e) {
            System.err.println("Hiba történt a(z) " + filePath + " fájl beolvasása során.");
            e.printStackTrace();
            return null;
        }
        return values;
    }

    private static List<String> getKeys(String filePath) {
        Properties properties = new Properties();
        List<String> keys = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/" + filePath)) {
            properties.load(fileInputStream);
            keys.addAll(properties.stringPropertyNames());
        } catch (IOException e) {
            System.err.println("Hiba történt a(z) " + filePath + " fájl beolvasása során.");
            e.printStackTrace();
            return null;
        }
        return keys;
    }

    private static List<String> deleteDuplicateValues(List<String> valuesList){
        Map<String, Integer> valueCountMap = new HashMap<>();

        for (String value : valuesList) {
            valueCountMap.put(value, valueCountMap.getOrDefault(value, 0) + 1);
        }

        List<String> noDuplicates = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : valueCountMap.entrySet()) {
            if (entry.getValue() == 1) {
                noDuplicates.add(entry.getKey());
            }
        }
        System.out.println("None duplicate values: ");
        //noDuplicates.forEach(v -> System.out.println(v));
        return noDuplicates;
    }

    private static List<String> getNonDuplicateValuesWithKeys(List<String> valuesList){
        Map<String, Integer> valueCountMap = new HashMap<>();
        Map<String, List<String>> valueKeysMap = new HashMap<>();

        // Value-k számolása és kulcsok tárolása
        for (String value : valuesList) {
            valueCountMap.put(value, valueCountMap.getOrDefault(value, 0) + 1);
            if (!valueKeysMap.containsKey(value)) {
                valueKeysMap.put(value, new ArrayList<>());
            }
        }

        // Korábbi kulcsok hozzárendelése azonos értékekhez
        for (Map.Entry<String, Integer> entry : valueCountMap.entrySet()) {
            if (entry.getValue()  > 1) {
                System.out.println(entry.getValue());
                // Nem duplikált érték
                valueKeysMap.get(entry.getKey()).add(entry.getKey());
            }
        }

        // String létrehozása a nem duplikált értékekkel és hozzájuk tartozó kulcsokkal
        List<String> valueList = new LinkedList<>();
        for (Map.Entry<String, List<String>> entry : valueKeysMap.entrySet()) {
            valueList.add(entry.getKey());
        }
        return valueList;
    }



    private static String getKeyByValue(String filePath, String value) {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/" + filePath)) {
            properties.load(fileInputStream);
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                if (entry.getValue().equals(value)) {
                    return (String) entry.getKey();
                }
            }
        } catch (IOException e) {
            System.err.println("Hiba történt a(z) " + filePath + " fájl beolvasása során.");
            e.printStackTrace();
        }
        return null;
    }

    private static void decodeAndSaveImages(String filePath, String outputDirectory) {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/" + filePath)) {
            properties.load(fileInputStream);
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String fileName = (String) entry.getKey();
                String base64Image = (String) entry.getValue();

                // Kép dekódolása base64-ből
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);

                // Új képfájl létrehozása és írása a megadott kimeneti könyvtárba
                File outputFile = new File(outputDirectory + "/" + fileName);
                try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
                    fileOutputStream.write(imageBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Hiba történt a(z) " + filePath + " fájl beolvasása során.");
            e.printStackTrace();
        }
    }


    public void transferPicturesFromFolderToAnotherFolder(String fromFolder, String toFolder, List<String> fileNames) {
        File destinationDir = new File(toFolder);
        if (!destinationDir.exists()) {
            destinationDir.mkdirs();
        }

        for (String fileName : fileNames) {
            File sourceFile = new File(fromFolder + "/" + fileName);
            System.out.println(fileName);

            if (!sourceFile.exists() || !sourceFile.isFile()) {
                System.err.println("File " + fileName + " does not exist in the source folder.");
                continue;
            }

            try {
                byte[] imageBytes = Files.readAllBytes(sourceFile.toPath());

                File destinationFile = new File(toFolder + "/" + fileName);
                try (FileOutputStream fileOutputStream = new FileOutputStream(destinationFile)) {
                    fileOutputStream.write(imageBytes);
                } catch (IOException e) {
                    System.err.println("Error writing file " + fileName + " to destination folder.");
                    e.printStackTrace();
                }
            } catch (IOException e) {
                System.err.println("Error reading file " + fileName + " from source folder.");
                e.printStackTrace();
            }
        }
    }

    public String findCommonKeys(List<String> noDuplicateValues1, List<String> noDuplicateValues2) {
        StringBuilder strBuilder = new StringBuilder();

        Set<String> processedValues = new HashSet<>();

        strBuilder.append("Ezeket a képeket tartalmazza mindkét lista:" + '\n');

        for (String value : new HashSet<>(noDuplicateValues1)) {
            if (noDuplicateValues2.contains(value) && !processedValues.contains(value)) {
                processedValues.add(value);

                List<String> originalKeys = getAllOriginalKeys(noDuplicateValues1, value);
                List<String> overTimeKeys = getAllOriginalKeys(noDuplicateValues2, value);

                StringJoiner joiner = new StringJoiner(System.lineSeparator());


                if (!originalKeys.isEmpty()) {
                    joiner.add("A kulcs a(z) " + String.join(", ", originalKeys) + " értékhez. (original)");
                    System.out.println("A kulcs a(z) " + String.join(", ", originalKeys) + " értékhez.");
                } else {
                    joiner.add("Nem található kulcs az értékhez. (original)");
                }

                if (!overTimeKeys.isEmpty()) {
                    joiner.add("A kulcs a(z) " + String.join(", ", overTimeKeys) + " értékhez. (copy)");
                    System.out.println("A kulcs a(z) " + String.join(", ", overTimeKeys) + " értékhez.");
                } else {
                    joiner.add("Nem található kulcs az értékhez. (copy)");
                }

                strBuilder.append(joiner.toString()).append('\n');
            }
            strBuilder.append('\n');
        }

        return strBuilder.toString();
    }

    public void transferPicturesFromFoldersToAnotherFolder(String[] fromFolder, String toFolder, List<String> fileNames) {
        File destinationDir = new File(toFolder);
        if (!destinationDir.exists()) {
            destinationDir.mkdirs();
        }

        for(int i = 0; i < fromFolder.length; i++){
            for (String fileName : fileNames) {
                File sourceFile = new File(fromFolder[i] + "/" + fileName);
                System.out.println(fileName);

                if (!sourceFile.exists() || !sourceFile.isFile()) {
                    System.err.println("File " + fileName + " does not exist in the source folder.");
                    continue;
                }

                try {
                    byte[] imageBytes = Files.readAllBytes(sourceFile.toPath());

                    File destinationFile = new File(toFolder + "/" + fileName);
                    try (FileOutputStream fileOutputStream = new FileOutputStream(destinationFile)) {
                        fileOutputStream.write(imageBytes);
                    } catch (IOException e) {
                        System.err.println("Error writing file " + fileName + " to destination folder.");
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    System.err.println("Error reading file " + fileName + " from source folder.");
                    e.printStackTrace();
                }
            }
        }

    }

    public List<String> findCommonKeysList(List<String> noDuplicateValues1, List<String> noDuplicateValues2) {
        List<String> resultKeys = new LinkedList<>();
        Set<String> processedValues = new HashSet<>();


        for (String value : new HashSet<>(noDuplicateValues1)) {
            if (noDuplicateValues2.contains(value) && !processedValues.contains(value)) {
                processedValues.add(value);

                List<String> originalKeys = getAllOriginalKeys(noDuplicateValues1, value);

                resultKeys.addAll(originalKeys);

            }
        }

        return resultKeys;
    }
}
