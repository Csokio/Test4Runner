package org.example.endpoints.qa2pl;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.IntStream;


public class PostGetFinalizeRewardsQA2PL2 {

    // The URL for GetRewards and FinalizeRewards API endpoints
    //private final String getRewardsUrl = "https://lodqa2.wonderline.eu/backend-ws/v1/getRewards";
    //private final String getRewardsUrl = "https://lodqa2.wonderline.eu/backend-ws/index.html#/SIP/getRewards";
    private final String getRewardsUrl = "https://lodqa2.wonderline.eu/backend-ws/future/v1/getRewards";


    private final String finalizeRewardsUrl = "https://lodqa2.wonderline.eu/backend-ws/future/v1/finalizeRewards";

    private final String username = "wl";   // Replace with actual username
    private final String password = "a";    // Replace with actual password
    private final String auth = username + ":" + password;

    private final String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

    public String testDate;
    private String loyaltyCard;
    private String transactionNumber;
    private Integer siteID;

    // Constructor to initialize the object with testDate, loyaltyCard, and siteID
    public PostGetFinalizeRewardsQA2PL2(String testDate, String loyaltyCard, Integer siteID, String transactionNumber) {
        this.testDate = testDate;
        this.loyaltyCard = loyaltyCard;
        this.siteID = siteID;
        this.transactionNumber = transactionNumber;
    }


        // Getter methods for URLs and encoded authentication
    public String getGetRewardsUrl() {
        return this.getRewardsUrl;
    }

    public String getFinalizeRewardsUrl() {
        return this.finalizeRewardsUrl;
    }

    public String getEncodedAuth() {
        return this.encodedAuth;
    }

    public void printConfigProperties(){
        List<String> propertiesList = List.of("url", "sso-env", "sso-webhook-updatekey", "crypto-secret-pp", "g-qa-sso-digiest-auth", "g-qa-sso-digiest-token", "f-qa-sso-digiest-auth", "f-qa-sso-digiest-token", "g-uat-sso-digiest-auth",
                "g-uat-sso-digiest-token", "f-uat-sso-digiest-auth", "f-uat-sso-digiest-token" , "uat-sso-url", "qa-sso-url");
        IntStream.range(0, propertiesList.size()).forEach(i -> System.out.println(ConfigLoader.get(propertiesList.get(i))));


    }

}
