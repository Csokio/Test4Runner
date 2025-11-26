package tests.qa2pl.swagger;

import com.google.common.net.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import tests.WebTestTH;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostmanQA2 extends WebTestTH {

    private String postTimeStamp = "";

    // CUSTOMER UUID: 6abb3614-501f-354e-a434-e84ff7f0cbaa

    @Test
    public void testApiPl() throws IOException {

        String responseBody = apiAuthentication();

        System.out.println(responseBody);

        String authToken = matchAuthTokenRegex(responseBody);

        setPostTimeStamp();



        getBalancePl2();
        /*getCataloguePL2();


        getRewardsPl2(authToken);
        getFinalizeRewardsPl2();*/

        //testResetRequestByCardPL(authToken);


    }


    private void testResetRequestByCardPL(String authToken) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody body = RequestBody.create("",  okhttp3.MediaType.parse("text/plain"));
        Request request = new Request.Builder()
                .url("https://lodqa2.wonderline.eu/frontend-ws/v1/customer/restore/70043708006444111")
                .method("POST", body)
                .addHeader("x-lod-device-id", "wl_collection")
                .addHeader("x-lod-client-id", "wl_postman")
                .addHeader("x-lod-source-application", "LOD WEB")
                .addHeader("x-lod-market", "pl-PL")
                .addHeader("x-lod-country-code", "PL")
                .addHeader("Authorization", "Bearer " + authToken)
                .addHeader("x-lod-reset-key", "4366343793832978")
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        System.out.println("Response Code: " + response.code());

    }


    public void getRewardsPl2(String authToken) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
       /*RequestBody body = RequestBody.create("{\r\n    \"customerData\": " +
                "[\r\n        {\r\n            \"customerDataType\": \"LoyaltyCard\",\r\n            " +
                "\"customerDataValue\": \"70043708006444111\",\r\n            \"loyaltyType\": \"Shell\"\r\n " +
                "       }\r\n    ],\r\n    \"posData\": {\r\n        \"posTimeStamp\": \"2025-04-25T08:17:25.000+02:00\",\r\n" +
                "        \"transactionNumber\": \"385931770\"\r\n    },\r\n    \"requestData\": {\r\n        \"requestID\": \"385931769\",\r\n" +
                "        \"requestType\": \"OfferQuery\",\r\n        \"workstationID\": \"1\"\r\n    },\r\n    \"tenders\": [\r\n        {\r\n            " +
                "\"tenderID\": 1,\r\n            \"methodOfPayment\": \"Cash\",\r\n            \"methodOfPaymentID\": \"1\",\r\n            \"totalAmount\":" +
                " 10.0,\r\n            \"voucherRules\": []\r\n        }\r\n    ],\r\n    \"saleItems\": [\r\n        {\r\n      \"itemID\": 1,\r\n      " +
                "\"saleItemType\": \"Sale\",\r\n      \"categoryCode\": \"\",\r\n      \"productCode\": \"\",\r\n      \"subCategoryCode\": \"\",\r\n      \"" +
                "amount\": 10.0,\r\n      \"originalAmount\": 10.0,\r\n      \"vatRate\": 12.0,\r\n      \"unitMeasure\": \"EA\",\r\n      \"unitPrice\": 10.0,\r\n  " +
                "    \"quantity\": 1.0,\r\n      \"additionalProductCode\": \"8019561020213\",\r\n      \"additionalProductInfo\": \"HOT DOG\",\r\n   " +
                "   \"loyaltyOffers\": [],\r\n      \"priceAdjustments\": []\r\n        }\r\n    ],\r\n    \"siteData\": {\r\n        \"countryCode\": \"PL\",\r\n  " +
                "      \"siteID\": \"3061\"\r\n    },\r\n    \"totalAmount\": 10.0\r\n}", okhttp3.MediaType.parse("application/json"));*/

        RequestBody body = RequestBody.create("{\r\n    \"customerData\": " +
                "[\r\n        {\r\n            \"customerDataType\": \"LoyaltyCard\",\r\n            " +
                "\"customerDataValue\": \"70043708006444111\",\r\n            \"loyaltyType\": \"Shell\"\r\n " +
                "       }\r\n    ],\r\n    \"posData\": {\r\n        \"posTimeStamp\": \" " + postTimeStamp + " \",\r\n" +
                "        \"transactionNumber\": \"58605495\"\r\n    },\r\n    \"requestData\": {\r\n        \"requestID\": \"58605495\",\r\n" +
                "        \"requestType\": \"OfferQuery\",\r\n        \"workstationID\": \"1\"\r\n    },\r\n    \"tenders\": [\r\n        {\r\n            " +
                "\"tenderID\": 1,\r\n            \"methodOfPayment\": \"Cash\",\r\n            \"methodOfPaymentID\": \"1\",\r\n            \"totalAmount\":" +
                " 10.0,\r\n            \"voucherRules\": []\r\n        }\r\n    ],\r\n    \"saleItems\": [\r\n        {\r\n      \"itemID\": 1,\r\n      " +
                "\"saleItemType\": \"Sale\",\r\n      \"categoryCode\": \"\",\r\n      \"productCode\": \"\",\r\n      \"subCategoryCode\": \"\",\r\n      \"" +
                "amount\": 10.0,\r\n      \"originalAmount\": 10.0,\r\n      \"vatRate\": 12.0,\r\n      \"unitMeasure\": \"EA\",\r\n      \"unitPrice\": 10.0,\r\n  " +
                "    \"quantity\": 1.0,\r\n      \"additionalProductCode\": \"8019561020213\",\r\n      \"additionalProductInfo\": \"HOT DOG\",\r\n   " +
                "   \"loyaltyOffers\": [],\r\n      \"priceAdjustments\": []\r\n        }\r\n    ],\r\n    \"siteData\": {\r\n        \"countryCode\": \"PL\",\r\n  " +
                "      \"siteID\": \"3061\"\r\n    },\r\n    \"totalAmount\": 10.0\r\n}", okhttp3.MediaType.parse("application/json"));


        Request request = new Request.Builder()
                .url("https://lodqa2.wonderline.eu/backend-ws/future/v1/getRewards")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                //.addHeader("Authorization", "Basic d2xfb2E6YQ==")
                .addHeader("Authorization", "Bearer " + authToken)
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        System.out.println("GetRewards response code: " + response.code());
        System.out.println("GetRewards response body: " + response.body());
        System.out.println(response.message());
        System.out.println(response.headers());

    }

    public void getFinalizeRewardsPl2() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create("{\r\n  \"metaData\": {\r\n    \"totalCount\": 0\r\n  },\r\n  \"objects\": [\r\n    {\r\n      " +
                "\"requestData\": {\r\n        \"requestType\": \"RetailTransaction\",\r\n        \"workstationID\": \"1\",\r\n        \"deliveryMode\": \"SiteDelivery\",\r\n " +
                "       \"requestID\": \"58605495\",\r\n        \"batchID\": 0,\r\n        \"currencyCode\": \"HUF\"\r\n      },\r\n      \"customerData\": [\r\n        {\r\n    " +
                "      \"customerDataType\": \"LoyaltyCard\",\r\n          \"customerDataValue\": \"70043708006444111\",\r\n          \"loyaltyType\": \"Shell\",\r\n        " +
                "  \"pointsRedeemed\": 0\r\n        }\r\n      ],\r\n      \"siteData\": {\r\n         \"countryCode\": \"PL\",\r\n         \"siteID\": \"3061\"\r\n      },\r\n      \"posData\": {\r\n    " +
                "    \"posTimeStamp\": \" " + postTimeStamp + " \",\r\n        \"transactionNumber\": \"58605495\"\r\n      },\r\n  \"tenders\": [\r\n    {\r\n            \"tenderID\": 1,\r\n        " +
                "    \"methodOfPayment\": \"Cash\",\r\n            \"methodOfPaymentID\": \"1\",\r\n            \"totalAmount\": 10.0,\r\n            \"voucherRules\": []\r\n        }\r\n      ],\r\n   " +
                "   \"totalAmount\": 10.0,\r\n      \"saleItems\": [{\"itemID\":1,\"saleItemType\":\"Sale\",\"productCode\":null,\"categoryCode\":null,\"subCategoryCode\":null,\"amount\":\"10\",\"originalAmount\":\"10\"," +
                "\"originalNetAmount\":null,\"vatRate\":\"12\",\"unitMeasure\":\"EA\",\"unitPrice\":\"10\",\"quantity\":\"1\",\"additionalProductCode\":\"8019561020213\",\"additionalProductInfo\":\"HOT DOG\",\"loyaltyOffers\"" +
                ":[],\"priceAdjustments\":[]}]\r\n    }\r\n  ]\r\n}", okhttp3.MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url("https://lodqa2.wonderline.eu/backend-ws/future/v1/finalizeRewards")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Basic d2w6YQ==")
                .addHeader("testkey", "1")
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        System.out.println("FinalizeRewards response code: " + response.code());
        System.out.println("FinalizeRewards response body: " + response.body());

        System.out.println(response.message());
        System.out.println(response.headers());
    }

    public void getBalancePl2() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create("{\r\n  \"requestData\": {\r\n    \"requestType\": \"BalanceEnquiry\",\r\n    \"workstationID\": \"POS01\",\r\n    \"requestID\": \"696873734\"\r\n  },\r\n  \"customerData\": [\r\n   " +
                " {\r\n      \"customerDataType\": \"LoyaltyCard\",\r\n      \"customerDataValue\": \"70043708006444111\"\r\n    }\r\n  ],\r\n  \"siteData\": {\r\n    \"countryCode\": \"PL\",\r\n    \"siteID\": \"3061\"\r\n  },\r\n " +
                " \"posData\": {\r\n    \"posTimeStamp\": \" " + postTimeStamp + "\",\r\n    \"transactionNumber\": \"696873735\"\r\n  }\r\n}", okhttp3.MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url("https://lodqa2.wonderline.eu/backend-ws/future/v1/getBalances")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Basic d2w6YQ==")
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        System.out.println("Response message: " + response.message());
        //System.out.println("Response body: "  + response.body().string());

        String responseBody = response.body().string();
        System.out.println("Response body:" + responseBody);


        JSONObject json = new JSONObject(responseBody);

// customerData egy tömb, abból az első elem totalPointBalance értéke
        JSONArray customerDataArray = json.getJSONArray("customerData");
        if (customerDataArray.length() > 0) {
            JSONObject firstCustomer = customerDataArray.getJSONObject(0);
            String totalPointBalance = firstCustomer.optString("totalPointBalance", "N/A");
            System.out.println("Total Point Balance: " + totalPointBalance);
        } else {
            System.out.println("customerData array is empty.");
        }


    }


    public void getCataloguePL2() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create("{\r\n  \"requestData\": {\r\n    \"requestType\": \"CatalogueEnquiry\",\r\n    \"workstationID\": \"POS01\",\r\n    \"deliveryMode\": \"All\",\r\n  " +
                "  \"requestID\": \"365721252\"\r\n  },\r\n  \"customerData\": [\r\n    {\r\n      \"customerDataType\": \"LoyaltyCard\",\r\n      \"customerDataValue\": \"70043708006444111\"\r\n    }\r\n  ]," +
                "\r\n  \"siteData\": {\r\n    \"countryCode\": \"PL\",\r\n    \"siteID\": \"3061\"\r\n  },\r\n  \"posData\": {\r\n    \"posTimeStamp\": \"2025-05-09T12:25:45+02:00\",\r\n   " +
                " \"transactionNumber\": \"365721253\"\r\n  }\r\n}", okhttp3.MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url("https://lodqa2.wonderline.eu/backend-ws/future/v1/getCatalogue")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Basic d2w6YQ==")
                .build();
        okhttp3.Response response = client.newCall(request).execute();

        String responseBody = response.body().string();
        System.out.println("Response body:" + responseBody);


        JSONObject json = new JSONObject(responseBody);

        JSONArray giftItemsArray = json.getJSONArray("giftItems");

        for (int i = 0; i < giftItemsArray.length(); i++) {
            JSONObject giftItem = giftItemsArray.getJSONObject(i);
            JSONArray giftDataArray = giftItem.getJSONArray("giftData");

            for (int j = 0; j < giftDataArray.length(); j++) {
                JSONObject giftData = giftDataArray.getJSONObject(j);
                String additionalProductCode = giftData.optString("additionalProductInfo", "N/A");
                System.out.println("Additional Product Info: " + additionalProductCode);
            }
        }
    }

    public void redemptionPL2() throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create("{\r\n  \"requestData\": {\r\n    \"requestType\": \"Redemption\",\r\n" +
                "    \"requestID\": \"716824374\",\r\n    \"workstationID\": \"1\"    \r\n  },\r\n  \"customerData\": [\r\n    {\r\n    " +
                "  \"customerDataType\": \"LoyaltyCard\",\r\n      \"customerDataValue\": \"70043708006444111\",\r\n      \"pointsRedeemed\": 10,\r\n" +
                "      \"giftData\": [\r\n        {\r\n          \"giftSequence\": 1,\r\n          \"giftID\": \"2407091545\",\r\n          " +
                "\"quantity\": 1.0,\r\n          \"pointsPerUnit\": 10,\r\n          \"cashPerUnit\": 0\r\n        }        \r\n      ]\r\n   " +
                " }\r\n  ],\r\n  \"siteData\": {    \r\n    \"countryCode\": \"PL\",\r\n    \"siteID\": \"3061\"\r\n  },\r\n  \"posData\": {\r\n   " +
                " \"posTimeStamp\": \"2025-05-09T14:24:28+02:00\",\r\n    \"transactionNumber\": \"716824375\"\r\n  }\r\n}", okhttp3.MediaType.parse("application/json"));
        okhttp3.Request request = new Request.Builder()
                .url("https://lodqa2.wonderline.eu/backend-ws/future/v1/redemption")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Basic d2w6YQ==")
                .build();
        okhttp3.Response response = client.newCall(request).execute();

    }

    private void setPostTimeStamp(){
        ZonedDateTime now = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String formatted = now.format(formatter);
        this.postTimeStamp = formatted;
        System.out.println(formatted);
    }

}
