package org.example.endpoints;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StampCounter {


    private final String getRewardsUrl = "https://lodqa.wonderline.eu/backend-ws/future/v1/getRewards";
    private final String finalizeRewardsUrl = "https://lodqa.wonderline.eu/backend-ws/future/v1/finalizeRewards";
    private final String username = "wl";
    private final String password = "a";
    private final String auth = username + ":" + password;

    private final String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

    private String transactionNumber;
    private String testDate;

    private Integer iterationNumber;

    private String[] transactionNumberArray;

    private String[] postTimeStampArray;

    public StampCounter(String testDate, Integer iterationNumber){
        this.transactionNumber = readTransactionNumber();
        this.testDate = testDate;
        this.iterationNumber = iterationNumber;
    }

    public String getGetRewardsUrl(){
        return this.getRewardsUrl;
    }

    public String getFinalizeRewardsUrl(){
        return this.finalizeRewardsUrl;
    }

    public String getTransactionNumber(){
        return this.transactionNumber;
    }

    public String getTestDate(){
        return this.testDate;
    }

    public Integer getIterationNumber() {
        return this.iterationNumber;
    }

    public void setTransactionNumber(String transactionNumber){
        this.transactionNumber = transactionNumber;
    }

    public void setTestDate(String testDate){
        this.testDate = testDate;
    }

    public String getEncodedAuth(){
        return this.encodedAuth;
    }

    public String getRequestBody() {
        System.out.println(transactionNumber);
        return "{\n" +
                "  \"customerData\": [\n" +
                "    {\n" +
                "      \"customerDataType\": \"LoyaltyCard\",\n" +
                "      \"customerDataValue\": \"70048855605631815\",\n" +
                //"      \"customerDataValue\": \"+66333001003\",\n" +
                "      \"loyaltyType\": \"Shell\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"posData\": {\n" +
                "    \"posTimeStamp\": \"" + testDate + "\",\n" +
                "    \"transactionNumber\": \"" + transactionNumber + "\"\n" +
                "  },\n" +
                "  \"requestData\": {\n" +
                "    \"requestID\": \"" + transactionNumber + "\",\n" +
                "    \"requestType\": \"OfferQuery\",\n" +
                "    \"workstationID\": \"1\"\n" +
                "  },\n" +
                "  \"tenders\": [\n" +
                "    {\n" +
                "      \"tenderID\": 1,\n" +
                "      \"methodOfPaymentID\": \"1\",\n" +
                "      \"methodOfPayment\": \"Cash\",\n" +
                "      \"totalAmount\": \"400\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"saleItems\": [\n" +
                "    {\n" +
                "      \"additionalProductCode\": \"5016878010435\",\n" +
                "      \"additionalProductInfo\": \"\",\n" +
                "      \"amount\": 400,\n" +
                "      \"categoryCode\": \"\",\n" +
                "      \"itemID\": 1,\n" +
                "      \"originalAmount\": 400,\n" +
                "      \"vatRate\": 20,\n" +
                "      \"quantity\": 2,\n" +
                "      \"saleItemType\": \"Sale\",\n" +
                "      \"unitMeasure\": \"EA\",\n" +
                "      \"unitPrice\": 200,\n" +
                "      \"productCode\": \"\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"siteData\": {\n" +
                "    \"countryCode\": \"TH\",\n" +
                "    \"siteID\": \"55501\"\n" +
                "  },\n" +
                "  \"totalAmount\": \"400\"\n" +
                "}";
    }

    public String finalizeStampReward(String totalAmount, String bonusPoints){

        return  "{\n" +
                "    \"metaData\": {\n" +
                "        \"totalCount\": 0\n" +
                "    },\n" +
                "    \"objects\": [\n" +
                "        {\n" +
                "            \"requestData\": {\n" +
                "                \"requestType\": \"RetailTransaction\",\n" +
                "                \"deliveryMode\": \"SiteDelivery\",\n" +
                "                \"workstationID\": \"1\",\n" +
                "                \"requestID\": \"" + transactionNumber + "\",\n" +
                "                \"batchID\": 0,\n" +
                "                \"currencyCode\": \"TH\"\n" +
                "            },\n" +
                "            \"tenders\": [\n" +
                "                {\n" +
                "                    \"methodOfPaymentID\": \"1\",\n" +
                "                    \"methodOfPayment\": \"Cash\",\n" +
                "                    \"tenderID\": 1,\n" +
                "                    \"totalAmount\": \"" + totalAmount + "\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"siteData\": {\n" +
                "                \"countryCode\": \"TH\",\n" +
                "                \"siteID\": \"55501\"\n" +
                "            },\n" +
                "            \"posData\": {\n" +
                "                \"posTimeStamp\": \"" + testDate + "\",\n" +
                "                \"transactionNumber\": \"" + transactionNumber + "\",\n" +
                "                \"languageCode\": \"eng\"\n" +
                "            },\n" +
                "            \"totalAmount\": \"" + totalAmount + "\",\n" +
                "            \"saleItems\": [\n" +
                "                {\n" +
                "                    \"itemID\": 1,\n" +
                "                    \"saleItemType\": \"Sale\",\n" +
                "                    \"amount\": \"" + totalAmount + "\",\n" +
                "                    \"originalAmount\": \"" + totalAmount + "\",\n" +
                "                    \"vatRate\": \"20\",\n" +
                "                    \"unitMeasure\": \"EA\",\n" +
                "                    \"unitPrice\": \"200\",\n" +
                "                    \"quantity\": \"2\",\n" +
                "                    \"additionalProductCode\": \"5016878010435\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"customerData\": [\n" +
                "                {\n" +
                "                    \"customerDataType\": \"LoyaltyCard\",\n" +
                "                    \"customerDataValue\": \"70048855605631815\",\n" +
                "                    \"bonusPoints\": \"" + bonusPoints + "\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }

    public String[] getTransactionNumberArray() {
        return transactionNumberArray;
    }

    public String[] getPostTimeStampArray() {
        return postTimeStampArray;
    }

    public void increaseTransactionNumber() {
        transactionNumberArray = new String[iterationNumber];
        String prefix = "THSO_00";
        int startIndex = prefix.length();
        int number;
        try {
            number = Integer.parseInt(transactionNumber.substring(startIndex));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Transaction number format is incorrect.");
        }

        for (int i = 0; i < iterationNumber; i++) {
            transactionNumberArray[i] = prefix + (number + i);
        }
    }

    public void increasePostTimestamp() {
        postTimeStampArray = new String[iterationNumber];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date;
        try {
            date = sdf.parse(testDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Date format is incorrect.");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        for (int i = 0; i < iterationNumber; i++) {
            calendar.add(Calendar.MINUTE, 1);
            postTimeStampArray[i] = sdf.format(calendar.getTime());
        }
    }

    private String readTransactionNumber(){
        List<String> counterList = new LinkedList<>();

        try {
            File file = new File("C:\\Users\\SőregiKrisztián\\IdeaProjects\\wat" +
                    "\\src\\main\\java\\org\\example\\endpoints\\transaction_number");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()){
                counterList.addLast(scanner.nextLine());
            }
        }   catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(counterList.size());

        return counterList.getLast();
    }
    public void updateTransactionNumber(){
        try {
            File file = new File("C:\\Users\\SőregiKrisztián\\IdeaProjects\\wat" +
                    "\\src\\main\\java\\org\\example\\endpoints\\transaction_number");
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write('\n' + transactionNumberArray[transactionNumberArray.length-1]);
            fileWriter.close();
            System.out.println("File is written");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
