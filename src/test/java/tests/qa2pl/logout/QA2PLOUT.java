package tests.qa2pl.logout;

import org.example.pages.qa2pl.logout.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.testng.asserts.SoftAssert;
import tests.WebTestTH;
import tests.parameterized.ParameterizedLoginQA2PL;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QA2PLOUT extends WebTestTH {

    private final ParameterizedLoginQA2PL parameterizedLoginQA2PL = new ParameterizedLoginQA2PL();

    @Test
    public void AAADummyBoolean(){
        Instant startTime = Instant.now();
        boolean isAlwaysTrue = true;
        Assertions.assertTrue(isAlwaysTrue);
        //initializeTestExecutionData("AAADummyBoolean", startTime, true);

    }

    @Test
    public void testHeaderImageUrlQA2PL() {
        Instant startTime = Instant.now();
        Header header = new Header(driver);

        softAssert = new SoftAssert();

        navigateTo(header.getUrl());

        String[] expectedImageUrl = {
                "https://shell247b-pl.lod.club/cdn/b/deriv/6f/3f29fe5ad85360c7b3e2908f5b8735/231x55.png?shell_logo_1.png",
                "https://lodqa2.wonderline.eu/cdn/b/deriv/f3/8c0298015f8d05058d5a4928eff2bd/84x84.png?StationLocator.png",
                "https://lodqa2.wonderline.eu/cdn/static/flat/d5/1c963b255b1bc27447c0ec9424727a/help_icon.png",
                "https://lodqa2.wonderline.eu/pl-pl/cdn/b/deriv/40/0faca7263d73979b7a9b47cf603ec9/85x84.png?UserIcon.png"
        };
        String[] actualImageUrl = header.imageUrl().split(",");

        IntStream.range(0, expectedImageUrl.length).forEach(i ->
                softAssert.assertEquals(actualImageUrl[i], expectedImageUrl[i])
        );

        softAssert.assertTrue(header.saveImageToFile());

        softAssert.assertAll();
    }

    @Test
    public void testHeaderValuesQA2PL() {
        Instant startTime = Instant.now();
        Header header = new Header(driver);

        softAssert = new SoftAssert();

        navigateTo(header.getUrl());

        List<String[]> actualValues = header.valuesList();
        String[] headerStationLocator = {"FuturaStd-Book, Arial, Verdana", "15.9722px", "rgba(64, 64, 64, 1)"};
        String[] headerFaqs = {"FuturaStd-Book, Arial, Verdana", "15.9722px", "rgba(64, 64, 64, 1)"};
        String[] headerProfile = {"FuturaStd-Book, Arial, Verdana", "15.9722px", "rgba(51, 51, 51, 1)"};

        List<String[]> expectedValues = new LinkedList<>();
        expectedValues.add(headerStationLocator);
        expectedValues.add(headerFaqs);
        expectedValues.add(headerProfile);
        expectedValues.add(headerProfile);

        /*for(String[] item: actualValues){
            System.out.println(Arrays.toString(item));
        }*/

        IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();
    }

    @Test
    public void testHeaderPaddingQA2PL() {
        Instant startTime = Instant.now();
        Header header = new Header(driver);

        softAssert = new SoftAssert();

        navigateTo(header.getUrl());

        Integer[] actualPaddings = header.getPaddings();
        Integer[] expectedPaddings = new Integer[]{2, 2, 7, -5, 4};

        /*for(Integer i: actualPaddings){
            System.out.print(i);
        }*/

        Assertions.assertArrayEquals(expectedPaddings, actualPaddings);
    }

    @Test
    public void testHeaderAbsoluteLocationsQA2PL() {
        Instant startTime = Instant.now();
        Header header = new Header(driver);

        softAssert = new SoftAssert();

        navigateTo(header.getUrl());

        header.setLocationsList();
        header.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = header.absoluteLocations();

        for (Map.Entry<Integer, Integer[]> entries : absoluteLocationsMap.entrySet()) {
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{162, 17},
                new Integer[]{1179, 0},
                new Integer[]{1370, 0},
                new Integer[]{1621, 15},
                new Integer[]{162, 24},
                new Integer[]{1216, 25},
                new Integer[]{1430, 25},
                new Integer[]{1590, 16}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i + 1, absoluteLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(absoluteLocationsMap, expectedAbsoluteLocations);

        /*IntStream.range(1, expectedAbsoluteLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = absoluteLocationsMap.get(i);
                    Integer[] expected = expectedAbsoluteLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });*/

        softAssert.assertAll();
    }

    @Test
    public void testHeaderRelativeLocationsQA2PL() {
        Instant startTime = Instant.now();
        Header header = new Header(driver);

        softAssert = new SoftAssert();

        navigateTo(header.getUrl());

        header.setLocationsList();
        header.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = header.relativeLocations();

        for (Map.Entry<Integer, Integer[]> entry : relativeLocationsMap.entrySet()) {
            Integer key = entry.getKey();
            Integer[] value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{1017, -17},
                new Integer[]{191, 0},
                new Integer[]{251, 15},
                new Integer[]{-1461, 9},
                new Integer[]{1054, 1},
                new Integer[]{214, 0},
                new Integer[]{160, -9}
        );
        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i + 1, relativeLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(relativeLocationsMap, expectedRelativeLocations);

        /*IntStream.range(1, expectedRelativeLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = relativeLocationsMap.get(i);
                    Integer[] expected = expectedRelativeLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });*/

        softAssert.assertAll();
    }


    @Test
    public void testPromoImageUrlQA2PL() {
        Instant startTime = Instant.now();
        PromoAndScroll promoAndScroll = new PromoAndScroll(driver);

        softAssert = new SoftAssert();

        navigateTo(promoAndScroll.getUrl());

        String expectedImageUrl =
                "/pl-pl/cdn/b/deriv/f4/4c280e23a9f8119c658993ec8cf36c/1280x373.jpg";
        String actualImageUrl =
                promoAndScroll.imageUrl();

        softAssert.assertEquals(actualImageUrl, expectedImageUrl);

        softAssert.assertTrue(promoAndScroll.saveImageToFile());

        softAssert.assertAll();
    }


    @Test
    public void testPromoScrollValuesQA2PL() {
        Instant startTime = Instant.now();
        PromoAndScroll promoAndScroll = new PromoAndScroll(driver);

        softAssert = new SoftAssert();

        navigateTo(promoAndScroll.getUrl());

        List<String[]> actualValues = promoAndScroll.valuesList();
        String[] promoText = {"FuturaStd-Book, Arial, Verdana", "49.5004px", "rgba(255, 255, 255, 1)"};
        String[] clubSmart = {"FuturaStd-Book, Arial, Verdana", "23.9167px", "rgba(64, 64, 64, 1)"};
        String[] simple = {"FuturaStd-Book, Arial, Verdana", "23.9167px", "rgba(64, 64, 64, 1)"};
        String[] clubMember = {"FuturaStd-Book, Arial, Verdana", "23.9167px", "rgba(64, 64, 64, 1)"};


        List<String[]> expectedValues = new LinkedList<>();
        expectedValues.add(promoText);
        expectedValues.add(clubSmart);
        expectedValues.add(simple);
        expectedValues.add(clubMember);

        /*for(String[] item: actualValues){
            System.out.println(Arrays.toString(item));
        }*/

        IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();
    }

    @Test
    public void testPromoScrollPaddingsQA2PL() {
        Instant startTime = Instant.now();
        PromoAndScroll promoAndScroll = new PromoAndScroll(driver);

        softAssert = new SoftAssert();

        navigateTo(promoAndScroll.getUrl());

        Integer[] actualPaddings = promoAndScroll.getPaddings();
        Integer[] expectedPaddings = {15, 15, 15, 15};

        for (Integer i : actualPaddings) {
            System.out.print(i);
        }

        Assertions.assertArrayEquals(expectedPaddings, actualPaddings);
    }

    @Test
    public void testPromoScrollAbsoluteLocationsQA2Pl() {
        Instant startTime = Instant.now();
        PromoAndScroll promoAndScroll = new PromoAndScroll(driver);

        softAssert = new SoftAssert();

        navigateTo(promoAndScroll.getUrl());

        promoAndScroll.setLocationsList();
        promoAndScroll.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = promoAndScroll.absoluteLocations();

        for (Map.Entry<Integer, Integer[]> entries : absoluteLocationsMap.entrySet()) {
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{0, 70},
                new Integer[]{283, 113},
                new Integer[]{0, 615},
                new Integer[]{629, 615},
                new Integer[]{1258, 615}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i + 1, absoluteLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(absoluteLocationsMap, expectedAbsoluteLocations);

        IntStream.range(1, expectedAbsoluteLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = absoluteLocationsMap.get(i);
                    Integer[] expected = expectedAbsoluteLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();
    }

    @Test
    public void testPromoScrollRelativeLocations() {
        Instant startTime = Instant.now();
        PromoAndScroll promoAndScroll = new PromoAndScroll(driver);

        softAssert = new SoftAssert();

        navigateTo(promoAndScroll.getUrl());

        promoAndScroll.setLocationsList();
        promoAndScroll.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = promoAndScroll.relativeLocations();

        for (Map.Entry<Integer, Integer[]> entry : relativeLocationsMap.entrySet()) {
            Integer key = entry.getKey();
            Integer[] value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{283, 43},
                new Integer[]{-283, 502},
                new Integer[]{629, 0},
                new Integer[]{629, 0}
        );
        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i + 1, relativeLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(relativeLocationsMap, expectedRelativeLocations);

        IntStream.range(1, expectedRelativeLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = relativeLocationsMap.get(i);
                    Integer[] expected = expectedRelativeLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();

    }

    @Test
    public void testScrollPromoScroll() throws InterruptedException {
        Instant startTime = Instant.now();
        PromoAndScroll promoAndScroll = new PromoAndScroll(driver);

        softAssert = new SoftAssert();

        navigateTo(promoAndScroll.getUrl());

        Assertions.assertTrue(promoAndScroll.scrollPromoBar());
    }

    @Test
    public void testClubSmartImageUrlQA2PL() {
        Instant startTime = Instant.now();
        ClubSmart clubSmart = new ClubSmart(driver);

        softAssert = new SoftAssert();

        navigateTo(clubSmart.getUrl());

        String[] expectedImageUrl = {
                "https://lodqa2.wonderline.eu/cdn/b/orig/5a/5de24346f9c926370ed115685dfdbd/510x378.jpg?shell_vouchery_1_correct.jpg",
                "https://lodqa2.wonderline.eu/cdn/b/orig/fe/5bdfecd91926debd9be2a5ee51ac31/800x593.jpg?1.jpg",
                "https://lodqa2.wonderline.eu/cdn/b/orig/2a/b89fdee2df08f3fe91ce77f2b9d5ce/800x593.jpg?2.jpg",
                "https://lodqa2.wonderline.eu/cdn/b/orig/c9/1fc22a24ff65b94c1eee875957783f/1800x1166.jpg?4.jpg",
                "https://lodqa2.wonderline.eu/cdn/b/deriv/09/cd7bdde9f90d765e1ad56825edb71d/75x76.jpg?1[35].jpg",
                "https://lodqa2.wonderline.eu/cdn/b/deriv/6e/ef22b0429754b6e47ae0afcdaf2884/75x76.jpg?2[83].jpg",
                "https://lodqa2.wonderline.eu/cdn/b/deriv/79/17f8e44782786f817169007b85689d/75x76.jpg?3[73].jpg"
        };
        String[] actualImageUrl = clubSmart.imageUrl().split(",");

        IntStream.range(0, expectedImageUrl.length).forEach(i ->
                softAssert.assertEquals(actualImageUrl[i], expectedImageUrl[i])
        );

        softAssert.assertTrue(clubSmart.saveImageToFile());

        softAssert.assertAll();
    }

    @Test
    public void testClubSmartValuesQA2Pl() {
        Instant startTime = Instant.now();
        ClubSmart clubSmart = new ClubSmart(driver);

        softAssert = new SoftAssert();

        navigateTo(clubSmart.getUrl());

        List<String[]> actualValues = clubSmart.valuesList();
        List<String[]> expectedValues = new LinkedList<>();

        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "22.1259px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "31.8612px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "27.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "17.9306px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Heavy, Arial, Verdana", "19.889px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Heavy, Arial, Verdana", "19.889px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Heavy, Arial, Verdana", "19.889px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "19.9029px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "19.9029px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "19.9029px", "rgba(64, 64, 64, 1)"});


        for (String[] item : actualValues) {
            System.out.println(Arrays.toString(item));
        }

        IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();
    }

    @Test
    public void testClubSmartPaddingsQA2PL() {
        Instant startTime = Instant.now();
        ClubSmart clubSmart = new ClubSmart(driver);

        softAssert = new SoftAssert();

        navigateTo(clubSmart.getUrl());

        Integer[] actualPaddings = clubSmart.getPaddings();
        Integer[] expectedPaddings = {59, 20, 15, 20, 15, 147, 147, 59, 14, 14, 14, 15,
                15, 15, 18, 18, 25, 25, 25, 29, 94, 94, 29, 59, 29, 5, 42, 42, 10, 10, 10};

        for (Integer padding : actualPaddings) {
            System.out.println(padding);
        }

        Assertions.assertArrayEquals(expectedPaddings, actualPaddings);
    }

    @Test
    public void testClubSmartAbsoluteLocationsQA2PL() {
        Instant startTime = Instant.now();
        ClubSmart clubSmart = new ClubSmart(driver);

        softAssert = new SoftAssert();

        navigateTo(clubSmart.getUrl());

        clubSmart.setLocationsList();
        clubSmart.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = clubSmart.absoluteLocations();

        for (Map.Entry<Integer, Integer[]> entries : absoluteLocationsMap.entrySet()) {
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{0, 1579},
                new Integer[]{943, 1579},
                new Integer[]{1037, 1670},
                new Integer[]{1037, 1709},
                new Integer[]{1037, 1760},
                new Integer[]{1037, 2000},
                new Integer[]{162, 784},
                new Integer[]{162, 1013},
                new Integer[]{162, 1072},
                new Integer[]{687, 1072},
                new Integer[]{1212, 1072}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i + 1, absoluteLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(absoluteLocationsMap, expectedAbsoluteLocations);

        IntStream.range(1, expectedAbsoluteLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = absoluteLocationsMap.get(i);
                    Integer[] expected = expectedAbsoluteLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();
    }

    @Test
    public void testClubSmartRelativeLocationsQA2Pl() {
        Instant startTime = Instant.now();
        ClubSmart clubSmart = new ClubSmart(driver);

        softAssert = new SoftAssert();

        navigateTo(clubSmart.getUrl());

        clubSmart.setLocationsList();
        clubSmart.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = clubSmart.relativeLocations();

        for (Map.Entry<Integer, Integer[]> entry : relativeLocationsMap.entrySet()) {
            Integer key = entry.getKey();
            Integer[] value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{943, 0},
                new Integer[]{94, 91},
                new Integer[]{0, 39},
                new Integer[]{0, 51},
                new Integer[]{0, 240},
                new Integer[]{-875, -1216},
                new Integer[]{0, 229},
                new Integer[]{0, 59},
                new Integer[]{525, 0},
                new Integer[]{525, 0}
        );
        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i + 1, relativeLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(relativeLocationsMap, expectedRelativeLocations);

        IntStream.range(1, expectedRelativeLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = relativeLocationsMap.get(i);
                    Integer[] expected = expectedRelativeLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();
    }

    @Test
    public void testPageInfoImageUrlQA2PL() {
        Instant startTime = Instant.now();
        PageInfo pageInfo = new PageInfo(driver);

        softAssert = new SoftAssert();

        navigateTo(pageInfo.getUrl());

        String[] expectedImageUrl = {
                "https://lodqa2.wonderline.eu/cdn/b/deriv/c9/0bbcff97eb38064a5254f2e8b9e2ef/520x353.jpg?5.jpg",
                "https://lodqa2.wonderline.eu/cdn/b/deriv/80/2919848650f61ad0eea0ca761aa574/520x353.jpg?6.jpg",
                "https://lodqa2.wonderline.eu/cdn/b/deriv/7d/7b4b6dd25ef943d7198bbef863d1ff/520x353.jpg?shell_oferty_specjalne_1_correct.jpg"
        };

        String[] actualImageUrl = pageInfo.imageUrl().split(",");

        IntStream.range(0, expectedImageUrl.length).forEach(i ->
                softAssert.assertEquals(actualImageUrl[i], expectedImageUrl[i])
        );

        softAssert.assertTrue(pageInfo.saveImageToFile());

        softAssert.assertAll();
    }

    @Test
    public void testPageInfoValuesQA2Pl() {
        Instant startTime = Instant.now();
        PageInfo pageInfo = new PageInfo(driver);

        softAssert = new SoftAssert();

        navigateTo(pageInfo.getUrl());

        List<String[]> actualValues = pageInfo.valuesList();
        List<String[]> expectedValues = new LinkedList<>();

        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "22.1259px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "31.8612px", "rgba(64, 64, 64, 1)"});

        IntStream.range(0, 3).forEach(i -> {
            expectedValues.add(new String[]{"FuturaStd-Heavy, Arial, Verdana", "31.8612px", "rgba(64, 64, 64, 1)"});
            expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "17.9167px", "rgba(89, 89, 89, 1)"});
            expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        });

        System.out.println("The size of expectedValues is: " + expectedValues.size());

        for (String[] item : actualValues) {
            System.out.println(Arrays.toString(item));
        }

        IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();
    }

    @Test
    public void testPageInfoPaddingsQA2PL() {
        Instant startTime = Instant.now();
        PageInfo pageInfo = new PageInfo(driver);

        softAssert = new SoftAssert();

        navigateTo(pageInfo.getUrl());

        Integer[] actualPaddings = pageInfo.getPaddings();
        Integer[] expectedPaddings = {71, 59, 15, 59, 29, 14, 15, 59, 29, 14, 15, 59, 29, 14, 59, 15, 15, 15,
                40, 40, 40, 15, 15, 15, 141, 48, 78, 48, 78, 78, 15, 20, 15, 20, 15, 20,
                141, 78, 78, 78};

        for (Integer padding : actualPaddings) {
            System.out.println(padding);
        }

        Assertions.assertArrayEquals(expectedPaddings, actualPaddings);
    }

    @Test
    public void testPageInfoAbsoluteLocationsQA2PL() {
        Instant startTime = Instant.now();
        PageInfo pageInfo = new PageInfo(driver);

        softAssert = new SoftAssert();

        navigateTo(pageInfo.getUrl());

        pageInfo.setLocationsList();
        pageInfo.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = pageInfo.absoluteLocations();

        for (Map.Entry<Integer, Integer[]> entries : absoluteLocationsMap.entrySet()) {
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{141, 2286},
                new Integer[]{141, 2286},
                new Integer[]{141, 2477},
                new Integer[]{141, 2537},
                new Integer[]{156, 2926},
                new Integer[]{234, 3008},
                new Integer[]{156, 3132},
                new Integer[]{701, 2926},
                new Integer[]{779, 3008},
                new Integer[]{701, 3132},
                new Integer[]{1232, 2537},
                new Integer[]{1247, 2926},
                new Integer[]{1325, 3008},
                new Integer[]{1247, 3132}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i + 1, absoluteLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(absoluteLocationsMap, expectedAbsoluteLocations);

        IntStream.range(1, expectedAbsoluteLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = absoluteLocationsMap.get(i);
                    Integer[] expected = expectedAbsoluteLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();
    }

    @Test
    public void testPageInfoRelativeLocationsQA2PL() {
        Instant startTime = Instant.now();
        PageInfo pageInfo = new PageInfo(driver);

        softAssert = new SoftAssert();

        navigateTo(pageInfo.getUrl());

        pageInfo.setLocationsList();
        pageInfo.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = pageInfo.relativeLocations();

        for (Map.Entry<Integer, Integer[]> entry : relativeLocationsMap.entrySet()) {
            Integer key = entry.getKey();
            Integer[] value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{0, 0},
                new Integer[]{0, 191},
                new Integer[]{0, 60},
                new Integer[]{15, 389},
                new Integer[]{78, 82},
                new Integer[]{-78, 124},
                new Integer[]{545, -206},
                new Integer[]{78, 82},
                new Integer[]{-78, 124},
                new Integer[]{531, -595},
                new Integer[]{15, 389},
                new Integer[]{78, 82},
                new Integer[]{-78, 124}
        );
        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i + 1, relativeLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(relativeLocationsMap, expectedRelativeLocations);

        IntStream.range(1, expectedRelativeLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = relativeLocationsMap.get(i);
                    Integer[] expected = expectedRelativeLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();
    }

    @Test
    public void testPageInfoTabLinksQA2PL() throws InterruptedException {
        Instant startTime = Instant.now();
        PageInfo pageInfo = new PageInfo(driver);

        softAssert = new SoftAssert();

        navigateTo(pageInfo.getUrl());

        Set<String> actualPageInfoTabLinks = pageInfo.getOpenedTabsLinks();

        Iterator<String> iterator = actualPageInfoTabLinks.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        Set<String> expectedPageInfoLinks = new HashSet<>();
        expectedPageInfoLinks.add("https://lodqa2.wonderline.eu/how-to-use#middle");
        expectedPageInfoLinks.add("https://lodqa2.wonderline.eu/pl-pl/how-to-use#middle");
        expectedPageInfoLinks.add("https://lodqa2.wonderline.eu/how-to-use#top");

        Set<String> differenceSet = actualPageInfoTabLinks.stream()
                .filter(val -> !expectedPageInfoLinks.contains(val))
                .collect(Collectors.toSet());

        System.out.println("The size of the differenceSet is: " + differenceSet.size());
        Assertions.assertTrue(differenceSet.isEmpty());
        //driver.quit();
    }

    @Test
    public void testDownloadAppImageUrlQA2PL() {
        Instant startTime = Instant.now();
        DownloadApp downloadApp = new DownloadApp(driver);

        softAssert = new SoftAssert();

        navigateTo(downloadApp.getUrl());

        String[] expectedImageUrl = {
                "https://lodqa2.wonderline.eu/cdn/b/orig/60/817bef630e86a5551d3fd3d1774006/392x128.png?google_app_392x128_2.png",
                "https://lodqa2.wonderline.eu/cdn/b/orig/0d/6edcce04df65d10e19b2d7546c3ff6/392x128.png?google_app_392x128_1.png",
                "https://lodqa2.wonderline.eu/cdn/b/orig/84/d01d717f74e610198f72f437d866b6/636x878.png?phone_636x878.png"
        };

        String[] actualImageUrl = downloadApp.imageUrl().split(",");

        IntStream.range(0, expectedImageUrl.length).forEach(i ->
                softAssert.assertEquals(actualImageUrl[i], expectedImageUrl[i])
        );

        softAssert.assertTrue(downloadApp.saveImageToFile());

        softAssert.assertAll();
    }

    @Test
    public void testDownloadAppValuesQA2PL() {
        Instant startTime = Instant.now();
        DownloadApp downloadApp = new DownloadApp(driver);

        softAssert = new SoftAssert();

        navigateTo(downloadApp.getUrl());

        List<String[]> actualValues = downloadApp.valuesList();
        List<String[]> expectedValues = new LinkedList<>();

        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "32px", "rgba(255, 255, 255, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "18px", "rgba(255, 255, 255, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "26px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(51, 51, 51, 1)"});

        System.out.println("The size of expectedValues is: " + expectedValues.size());

        for (String[] item : actualValues) {
            System.out.println(Arrays.toString(item));
        }

        IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();
    }

    @Test
    public void testDownloadAppPaddingsQA2PL() {
        Instant startTime = Instant.now();
        DownloadApp downloadApp = new DownloadApp(driver);

        softAssert = new SoftAssert();

        navigateTo(downloadApp.getUrl());

        Integer[] actualPaddings = downloadApp.getPaddings();
        Integer[] expectedPaddings = new Integer[]{71, 59, 15, 59, 29, 14, 15, 59, 29, 14, 15, 59, 29, 14,
                59, 15, 15, 15, 40, 40, 40, 15, 15, 15, 141, 48, 78, 48, 78, 78, 15, 20, 15, 20, 15, 20, 141, 78, 78, 78};

        for (Integer padding : actualPaddings) {
            System.out.println(padding);
        }

        //Assertions.assertArrayEquals(expectedPaddings, actualPaddings);
    }

    @Test
    public void testDownloadAppAbsoluteLocationsQA2PL() {
        Instant startTime = Instant.now();
        DownloadApp downloadApp = new DownloadApp(driver);

        softAssert = new SoftAssert();

        navigateTo(downloadApp.getUrl());

        downloadApp.setLocationsList();
        downloadApp.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = downloadApp.absoluteLocations();

        for (Map.Entry<Integer, Integer[]> entries : absoluteLocationsMap.entrySet()) {
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{0, 3283},
                new Integer[]{358, 3875},
                new Integer[]{1177, 3323},
                new Integer[]{302, 4338},
                new Integer[]{317, 4358},
                new Integer[]{358, 3459},
                new Integer[]{358, 3646}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i + 1, absoluteLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(absoluteLocationsMap, expectedAbsoluteLocations);

        IntStream.range(1, expectedAbsoluteLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = absoluteLocationsMap.get(i);
                    Integer[] expected = expectedAbsoluteLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();
    }

    @Test
    public void testDownloadAppRelativeLocationsQA2Pl() {
        Instant startTime = Instant.now();
        DownloadApp downloadApp = new DownloadApp(driver);

        softAssert = new SoftAssert();

        navigateTo(downloadApp.getUrl());

        downloadApp.setLocationsList();
        downloadApp.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = downloadApp.relativeLocations();

        for (Map.Entry<Integer, Integer[]> entry : relativeLocationsMap.entrySet()) {
            Integer key = entry.getKey();
            Integer[] value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{358, 592},
                new Integer[]{819, -552},
                new Integer[]{-875, 1015},
                new Integer[]{15, 20},
                new Integer[]{41, -899},
                new Integer[]{0, 187}
        );
        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i + 1, relativeLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(relativeLocationsMap, expectedRelativeLocations);

        IntStream.range(1, expectedRelativeLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = relativeLocationsMap.get(i);
                    Integer[] expected = expectedRelativeLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();
    }

    @Test
    public void testDownloadAppLinksQA2PL() {
        Instant startTime = Instant.now();
        DownloadApp downloadApp = new DownloadApp(driver);

        softAssert = new SoftAssert();

        navigateTo(downloadApp.getUrl());

        Set<String> actualDownloadAppLinks = downloadApp.getOpenedAppLinks();

        Iterator<String> iterator = actualDownloadAppLinks.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        Set<String> expectedDownloadAppLinks = new HashSet<>();
        expectedDownloadAppLinks.add("https://apps.apple.com/pl/app/shell-clubsmart/id1464649113?l=pl");
        expectedDownloadAppLinks.add("https://play.google.com/store/apps/details?id=com.shell.sitibv.retail");

        Set<String> differenceSet = actualDownloadAppLinks.stream()
                .filter(val -> !expectedDownloadAppLinks.contains(val))
                .collect(Collectors.toSet());

        System.out.println("The size of the differenceSet is: " + differenceSet.size());
        Assertions.assertTrue(differenceSet.isEmpty());
    }

    @Test
    public void testFooterValuesQA2PL() {
        Instant startTime = Instant.now();
        Footer footer = new Footer(driver);

        softAssert = new SoftAssert();

        navigateTo(footer.getUrl());

        List<String[]> actualValues = footer.valuesList();
        List<String[]> expectedValues = new LinkedList<>();


        IntStream.range(0, 4).forEach(i -> {
            expectedValues.add(new String[]{"FuturaStd-Heavy, Arial, Verdana, sans-serif", "19.9445px", "rgba(221, 29, 33, 1)"});
        });
        IntStream.range(0, 11).forEach(i -> {
            expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "17.9445px", "rgba(64, 64, 64, 1)"});
        });
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "16px", "rgba(51, 51, 51, 1)"});


        System.out.println("The size of expectedValues is: " + expectedValues.size());

        for (String[] item : actualValues) {
            System.out.println(Arrays.toString(item));
        }

        IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();
    }

    @Test
    public void testFooterPaddingsQA2PL() {
        Instant startTime = Instant.now();
        DownloadApp downloadApp = new DownloadApp(driver);

        softAssert = new SoftAssert();

        navigateTo(downloadApp.getUrl());

        Integer[] actualPaddings = downloadApp.getPaddings();
        Integer[] expectedPaddings = {39, 59, 59, 29, 59, 20, 226, 15, 15,
                39, 20, 358, 15, 15, 9, 288, 288};

        for (Integer padding : actualPaddings) {
            System.out.println(padding);
        }

        Assertions.assertArrayEquals(expectedPaddings, actualPaddings);
    }

    @Test
    public void testFooterAbsoluteLocationsQA2PL() {
        Instant startTime = Instant.now();
        Footer footer = new Footer(driver);

        softAssert = new SoftAssert();

        navigateTo(footer.getUrl());

        footer.setLocationsList();
        footer.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = footer.absoluteLocations();

        for (Map.Entry<Integer, Integer[]> entries : absoluteLocationsMap.entrySet()) {
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{0, 4472},
                new Integer[]{286, 4581},
                new Integer[]{286, 4625},
                new Integer[]{286, 4669},
                new Integer[]{702, 4581},
                new Integer[]{702, 4625},
                new Integer[]{702, 4669},
                new Integer[]{702, 4713},
                new Integer[]{1166, 4581},
                new Integer[]{1166, 4625},
                new Integer[]{1166, 4669},
                new Integer[]{1513, 4573},
                new Integer[]{850, 4816},
                new Integer[]{286, 4525},
                new Integer[]{702, 4525},
                new Integer[]{1166, 4525},
                new Integer[]{1523, 4525}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i + 1, absoluteLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(absoluteLocationsMap, expectedAbsoluteLocations);

        IntStream.range(1, expectedAbsoluteLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = absoluteLocationsMap.get(i);
                    Integer[] expected = expectedAbsoluteLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();
    }

    @Test
    public void testFooterRelativeLocationsQA2PL() {
        Instant startTime = Instant.now();
        Footer footer = new Footer(driver);

        softAssert = new SoftAssert();

        navigateTo(footer.getUrl());

        footer.setLocationsList();
        footer.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = footer.relativeLocations();

        for (Map.Entry<Integer, Integer[]> entry : relativeLocationsMap.entrySet()) {
            Integer key = entry.getKey();
            Integer[] value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{286, 109},
                new Integer[]{0, 44},
                new Integer[]{0, 44},
                new Integer[]{416, -88},
                new Integer[]{0, 44},
                new Integer[]{0, 44},
                new Integer[]{0, 44},
                new Integer[]{464, -132},
                new Integer[]{0, 44},
                new Integer[]{0, 44},
                new Integer[]{347, -96},
                new Integer[]{-663, 243},
                new Integer[]{-564, -292},
                new Integer[]{416, 0},
                new Integer[]{464, 0},
                new Integer[]{357, 0}
        );

        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i + 1, relativeLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(relativeLocationsMap, expectedRelativeLocations);

        IntStream.range(1, expectedRelativeLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = relativeLocationsMap.get(i);
                    Integer[] expected = expectedRelativeLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();
    }

    @Test
    public void testFooterTabLinksQA2PL() {
        Instant startTime = Instant.now();
        Footer footer = new Footer(driver);

        softAssert = new SoftAssert();

        navigateTo(footer.getUrl());

        Set<String> actualSetOfLinks = footer.getOpenedTabsLinks();

        /*Iterator<String> iterator = actualSetOfLinks.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }*/

        Set<String> expectedSetOfLinks = new HashSet<>();

        expectedSetOfLinks.add("https://shell247b-pl.lod.club/how-to-use");
        expectedSetOfLinks.add("https://shell247b-pl.lod.club/promotion/2401");
        expectedSetOfLinks.add("https://shell247b-pl.lod.club/contact-us");
        expectedSetOfLinks.add("https://tellshell.shell.com/pol?AspxAutoDetectCookieSupport=1");
        expectedSetOfLinks.add("https://www.shell.pl/");
        expectedSetOfLinks.add("https://www.shell.pl/cookie-policy.html");
        expectedSetOfLinks.add("https://www.shell.pl/klienci/paliwa-oleje-i-plyny-do-chlodnic/paliwa.html#vanity-aHR0cHM6Ly93d3cuc2hlbGwucGwva2llcm93Y3kvcGFsaXdhLmh0bWw");
        //expectedSetOfLinks.add("https://support.shell.pl/hc/pl?__cf_chl_rt_tk=N6NuMhAUTORiV8OdlTsZHAUSXq.Kv.FqD_aNrtycESk-1747037437-1.0.1.1-Ixr0UHHBHo0YdqDxwbmmrroYY0gLaZg5EAcYMrnSCqM");

        expectedSetOfLinks.add("https://support.shell.pl/hc/pl");
        expectedSetOfLinks.add("https://shell247b-pl.lod.club/privacy-policy");
        expectedSetOfLinks.add("https://www.facebook.com/ShellPolska");
        expectedSetOfLinks.add("https://shell247b-pl.lod.club/terms-and-conditions");

        Set<String> differenceSet = actualSetOfLinks.stream()
                .filter(val -> !expectedSetOfLinks.contains(val))
                .collect(Collectors.toSet());

        System.out.println("The size of the differenceSet is: " + differenceSet.size());
        System.out.println(differenceSet.iterator().next());
        Assertions.assertTrue(differenceSet.isEmpty());
    }


    @Test
    public void testLogin() throws InterruptedException, IOException {
        parameterizedLoginQA2PL.testLoginWithMultipleUsers();
    }

}