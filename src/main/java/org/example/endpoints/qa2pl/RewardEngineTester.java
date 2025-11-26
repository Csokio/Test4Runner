package org.example.endpoints.qa2pl;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Base64;

public class RewardEngineTester {
    private static final String GET_REWARDS_URL = "https://lodqa2.wonderline.eu/backend-ws/v1/getRewards";
    private static final String FINALIZE_REWARDS_URL = "https://lodqa2.wonderline.eu/backend-ws/v1/finalizeRewards";
    private static final String USERNAME = "wl";
    private static final String PASSWORD = "a";
    private static final String AUTH_HEADER = "Basic " + Base64.getEncoder().encodeToString((USERNAME + ":" + PASSWORD).getBytes());

    public static String sendPostRequest(String url, String body) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost postRequest = new HttpPost(url);
            postRequest.setHeader("Authorization", AUTH_HEADER);
            postRequest.setHeader("Content-Type", "application/json");
            postRequest.setHeader("Accept", "application/json");

            StringEntity entity = new StringEntity(body);
            postRequest.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(postRequest)) {
                int statusCode = response.getCode();
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder responseBody = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBody.append(line);
                }

                System.out.println("Status Code: " + statusCode);
                System.out.println("Response Body: " + responseBody);


                return responseBody.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void testGetRewards() {
        String getRewardsBody = "{\n" +
                "  \"requestData\": {\n" +
                "    \"requestType\": \"OfferQuery\",\n" +
                "    \"workstationID\": \"1101412412\",\n" +
                "    \"requestID\": \"skplqa2_38\",\n" +
                "    \"batchID\": 0\n" +
                "  },\n" +
                "  \"siteData\": {\n" +
                "    \"countryCode\": \"PL\",\n" +
                "    \"siteID\": 2604\n" +
                "  },\n" +
                "  \"posData\": {\n" +
                "    \"posTimeStamp\": \"2025-01-30T14:10:00+00:00\",\n" +
                "    \"transactionNumber\": \"skplqa2_38\"\n" +
                "  },\n" +
                "  \"loyalty\": {\n" +
                "    \"loyaltyCard\": \"70043708007230816\",\n" +
                "    \"loyaltyPointAmount\": 0,\n" +
                "    \"loyaltyType\": \"Shell\"\n" +
                "  },\n" +
                "  \"totalAmount\": 10.0,\n" +
                "  \"saleItems\": [\n" +
                "    {\n" +
                "      \"itemID\": 1,\n" +
                "      \"saleItemType\": \"Sale\",\n" +
                "      \"productCode\": \"\",\n" +
                "      \"categoryCode\": \"\",\n" +
                "      \"amount\": 10.0,\n" +
                "      \"originalAmount\": 10.0,\n" +
                "      \"vatRate\": 20.0,\n" +
                "      \"unitMeasure\": \"EA\",\n" +
                "      \"unitPrice\": 10.0,\n" +
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
                "  ]\n" +
                "}";
        System.out.println("Testing getRewards API...");
        sendPostRequest(GET_REWARDS_URL, getRewardsBody);
    }

    public static void testFinalizeRewards() {
        String finalizeRewardsBody = "{\n" +
                "  \"metaData\": {\n" +
                "    \"totalCount\": 1\n" +
                "  },\n" +
                "  \"objects\": [\n" +
                "    {\n" +
                "      \"requestData\": {\n" +
                "        \"requestType\": \"RetailTransaction\",\n" +
                "        \"workstationID\": \"1101412412\",\n" +
                "        \"requestID\": \"skplqa2_38\"\n" +
                "      },\n" +
                "      \"siteData\": {\n" +
                "        \"countryCode\": \"PL\",\n" +
                "        \"siteID\": 2604\n" +
                "      },\n" +
                "      \"posData\": {\n" +
                "        \"posTimeStamp\": \"2025-01-30T14:10:00+00:00\",\n" +
                "        \"transactionNumber\": \"skplqa2_38\"\n" +
                "      },\n" +
                "      \"tenders\": [\n" +
                "        {\n" +
                "          \"tenderID\": 1,\n" +
                "          \"methodOfPaymentID\": \"001321\",\n" +
                "          \"methodOfPayment\": \"CARD\",\n" +
                "          \"totalAmount\": 10.0\n" +
                "        }\n" +
                "      ],\n" +
                "      \"loyalty\": {\n" +
                "        \"loyaltyCard\": \"70043708007230816\",\n" +
                "        \"loyaltyPointAmount\": 0,\n" +
                "        \"loyaltyType\": \"Shell\"\n" +
                "      },\n" +
                "      \"totalAmount\": 10.0,\n" +
                "      \"saleItems\": [\n" +
                "        {\n" +
                "          \"itemID\": 1,\n" +
                "          \"saleItemType\": \"Sale\",\n" +
                "          \"productCode\": \"\",\n" +
                "          \"categoryCode\": \"\",\n" +
                "          \"amount\": 10.0,\n" +
                "          \"originalAmount\": 10.0,\n" +
                "          \"vatRate\": 20.0,\n" +
                "          \"unitMeasure\": \"EA\",\n" +
                "          \"unitPrice\": 10.0,\n" +
                "          \"quantity\": 1,\n" +
                "          \"additionalProductCode\": \"502102\",\n" +
                "          \"additionalProductInfo\": \"SANDWICHES\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        System.out.println("Testing finalizeRewards API...");
        sendPostRequest(FINALIZE_REWARDS_URL, finalizeRewardsBody);
    }

    public static void main(String[] args) {
        testGetRewards();
        testFinalizeRewards();
    }
}