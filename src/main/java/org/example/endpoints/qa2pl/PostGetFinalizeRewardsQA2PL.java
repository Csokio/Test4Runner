package org.example.endpoints.qa2pl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PostGetFinalizeRewardsQA2PL {

    private final String getRewardsUrl = "https://lodqa2.wonderline.eu/backend-ws/future/v1/getRewards";
    private final String finalizeRewardsUrl = "https://lodqa2.wonderline.eu/backend-ws/future/v1/finalizeRewards";
    private final String username = "wl";
    private final String password = "a";
    private final String auth = username + ":" + password;

    private final String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

    private String transactionNumber;
    private String testDate;
    private String countryCode;
    private  Integer siteID;
    private Long loyaltyCard;


    private Integer iterationNumber;

    private String[] transactionNumberArray;

    private String[] postTimeStampArray;

    public PostGetFinalizeRewardsQA2PL(String testDate, String loyaltyCard, Integer siteID){
        this.transactionNumber = readTransactionNumber();
        this.testDate = testDate;
        this.loyaltyCard = Long.parseLong(loyaltyCard);
        this.siteID = siteID;
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
        return "{\n" +
                "  \"requestData\": {\n" +
                "    \"requestType\": \"OfferQuery\",\n" +
                "    \"workstationID\": \"1101412412\",\n" +
                "    \"requestID\": \"skplqa2_100531\",\n" +
                "    \"batchID\": 0\n" +
                "  },\n" +
                "  \"siteData\": {\n" +
                "    \"countryCode\": \"PL\",\n" +
                "    \"siteID\": \"2604\"\n" +
                "  },\n" +
                "  \"posData\": {\n" +
                "    \"posTimeStamp\": \"" + testDate + "\",\n" +
                "    \"transactionNumber\": \"" + transactionNumber + "\"\n" +
                "  },\n" +
                "  \"loyalty\": {\n" +
                "    \"loyaltyCard\": \"" + loyaltyCard + "\",\n" +
                "    \"loyaltyPointAmount\": \"0\",\n" +
                "    \"loyaltyType\": \"Shell\"\n" +
                "  },\n" +
                "  \"totalAmount\": 10,\n" +
                "  \"saleItems\": [\n" +
                "    {\n" +
                "      \"itemID\": 1,\n" +
                "      \"saleItemType\": \"Sale\",\n" +
                "      \"productCode\": \"\",\n" +
                "      \"categoryCode\": \"\",\n" +
                "      \"amount\": 10,\n" +
                "      \"originalAmount\": 10,\n" +
                "      \"vatRate\": 20.0,\n" +
                "      \"unitMeasure\": \"EA\",\n" +
                "      \"unitPrice\": 10,\n" +
                "      \"quantity\": 1,\n" +
                "      \"additionalProductCode\": \"502102\",\n" +
                "      \"additionalProductInfo\": \"SANDWICHES\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"tenders\": [\n" +
                "    {\n" +
                "      \"methodOfPaymentID\": \"001\",\n" +
                "      \"methodOfPayment\": \"CASH\",\n" +
                "      \"totalAmount\": 10.0\n" +
                "    }\n" +
                "  ],\n" +
                "  \"applicableOffers\": []\n" +
                "}";
    }


    public String finalizeReward() {
        return "{\n" +
                "    \"metaData\": {\n" +
                "        \"totalCount\": 1\n" +
                "    },\n" +
                "    \"objects\": [\n" +
                "        {\n" +
                "            \"requestData\": {\n" +
                "                \"requestType\": \"RetailTransaction\",\n" +
                "                \"workstationID\": \"1101412412\",\n" +
                "                \"requestID\": \"skplqa2_100536\"\n" +
                "            },\n" +
                "            \"siteData\": {\n" +
                "                \"countryCode\": \"PL\",\n" +
                "                \"siteID\": \"2604\"\n" +
                "            },\n" +
                "            \"posData\": {\n" +
                "                \"posTimeStamp\": \"" + postTimeStampArray + "\",\n" +
                "                \"transactionNumber\": \"skplqa2_100534\"\n" +
                "            },\n" +
                "            \"loyalty\": {\n" +
                "                \"loyaltyCard\": \"" + loyaltyCard + "\",\n" +
                "                \"loyaltyPointAmount\": \"0\",\n" +
                "                \"loyaltyType\": \"Shell\"\n" +
                "            },\n" +
                "            \"totalAmount\": \"10\",\n" +
                "            \"saleItems\": [\n" +
                "                {\n" +
                "                    \"itemID\": 1,\n" +
                "                    \"saleItemType\": \"Sale\",\n" +
                "                    \"productCode\": null,\n" +
                "                    \"categoryCode\": null,\n" +
                "                    \"amount\": \"10\",\n" +
                "                    \"originalAmount\": \"10\",\n" +
                "                    \"vatRate\": 20,\n" +
                "                    \"unitMeasure\": \"EA\",\n" +
                "                    \"unitPrice\": \"10\",\n" +
                "                    \"quantity\": \"1\",\n" +
                "                    \"additionalProductCode\": \"502102\",\n" +
                "                    \"additionalProductInfo\": \"SANDWICHES\",\n" +
                "                    \"priceAdjustments\": [],\n" +
                "                    \"loyaltyOffers\": []\n" +
                "                }\n" +
                "            ],\n" +
                "            \"receipt\": {\n" +
                "                \"receiptLines\": null\n" +
                "            },\n" +
                "            \"applicableOffers\": null\n" +
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
