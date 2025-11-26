package tests;

import org.example.pages.uatpl.logout.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.testng.asserts.SoftAssert;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

public class UATShellPl extends WebTestTH{

    
    @Test
    public void testHeaderDisplayedPLUAT(){
        Instant startTime = Instant.now();
        Header header = new Header(driver);

        softAssert = new SoftAssert();

        navigateTo(header.getUrl());

        int actual = header.countHeaderElements();
        int expected = 6;

        softAssert.assertEquals(actual, expected);

        String[] actualArray = header.verifyHeaderElements();
        String[] expectedArray = {"https://shell247b-pl.lod.club/cdn/b/deriv/6f/3f29fe5ad85360c7b3e2908f5b8735/231x55.png?shell_logo_1.png",
                "Home", "Znajdź stację", "More", "Polski", "logout_logo"};

        softAssert.assertEquals(actualArray, expectedArray);
        softAssert.assertAll();
    }

    @Test
    public void testHeaderFunctionalityUATPL() throws InterruptedException {
        Instant startTime = Instant.now();
        Header header = new Header(driver);
        softAssert = new SoftAssert();

        navigateTo(header.getUrl());

        Assertions.assertTrue(header.clickOnHeaderElements());
    }

    @Test
    public void testHeaderDisplayedMoreOptionsUATPL() throws IOException {
        Instant startTime = Instant.now();
        Header header = new Header(driver);

        softAssert = new SoftAssert();

        navigateTo(header.getUrl());
        String[] actualArray = header.verifyMoreOptionElements();
        String[] expectedArray = {"Help & Support", "Contact us", "About Shell GO+"};

        Assertions.assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    public void testHeaderMoreOptionFunctionality() throws InterruptedException, IOException, AWTException {
        Instant startTime = Instant.now();
        Header header = new Header(driver);

        softAssert = new SoftAssert();

        navigateTo(header.getUrl());
        Thread.sleep(4000);

        Assertions.assertTrue(header.clickMoreElements());
    }


    @Test
    public void testHeaderImageUrlPLUAT() throws IOException {
        Instant startTime = Instant.now();
        Header header = new Header(driver);

        softAssert = new SoftAssert();

        navigateTo(header.getUrl());

        String actualShellImageUrl = header.imageUrl().split(",")[0];
        String actualHomeImageUrl = header.imageUrl().split(",")[1];
        String actualStationImageUrl = header.imageUrl().split(",")[2];
        String actualMoreImageUrl = header.imageUrl().split(",")[3];
        String actualMoreHoverImageUrl = header.imageUrl().split(",")[4];
        String actualFlagImageUrl = header.imageUrl().split(",")[5];
        String actualLoginImageUrl = header.imageUrl().split(",")[6];

        String expectedShellImageUrl = "https://shell247b-pl.lod.club/cdn/b/deriv/6f/3f29fe5ad85360c7b3e2908f5b8735/231x55.png?shell_logo_1.png";
        String expectedHomeImageUrl = "https://shell247b-pl.lod.club/cdn/b/orig/4a/85b8f5a9d6b5ecd70f0503bbc0f114/24x24.png";
        String expectedStationImageUrl = "https://shell247b-pl.lod.club/cdn/b/orig/1b/e17d52aa79661c6b952490062ace80/24x24.png?ic_map_outline.png";
        String expectedMoreImageUrl = "https://shell247b-pl.lod.club/cdn/b/orig/07/996260ff72ac4a0fa7ac8c1c36c654/24x24.png";
        String expectedMoreHoverImageUrl = "https://shell247b-pl.lod.club/cdn/b/orig/6f/fdc0c05297c0d922919aaa6606e146/24x24.png";
        String expectedFlagImageUrl = "https://shell247b-pl.lod.club/assets/images/web/flags/pl.svg";
        String expectedLoginImageUrl = "https://shell247b-pl.lod.club/cdn/b/orig/04/67d3ee06303d9db9df06e55d00bca5/24x24.png";

        softAssert.assertEquals(actualShellImageUrl, expectedShellImageUrl);
        softAssert.assertEquals(actualHomeImageUrl, expectedHomeImageUrl);
        softAssert.assertEquals(actualStationImageUrl, expectedStationImageUrl);
        softAssert.assertEquals(actualMoreImageUrl, expectedMoreImageUrl);
        softAssert.assertEquals(actualMoreHoverImageUrl, expectedMoreHoverImageUrl);
        softAssert.assertEquals(actualFlagImageUrl, expectedFlagImageUrl);
        softAssert.assertEquals(actualLoginImageUrl, expectedLoginImageUrl);


        softAssert.assertTrue(header.saveImageToFile());

        softAssert.assertAll();
    }

    @Test
    public void testHeaderValuesPLUAT(){
        Instant startTime = Instant.now();
        Header header = new Header(driver);

        navigateTo(header.getUrl());

        softAssert = new SoftAssert();

        String[] expectedHomeValues = {"FuturaStd-Book, Arial, Verdana", "16px", "rgba(64, 64, 64, 1)"};
        String[] expectedStationValues = {"FuturaStd-Book, Arial, Verdana", "16px", "rgba(127, 127, 127, 1)"};
        String[] expectedMoreValues = {"FuturaStd-Book, Arial, Verdana", "16px", "rgba(127, 127, 127, 1)"};
        String[] expectedFlagValues = {"FuturaStd-Book, Arial, Verdana", "16px", "rgba(127, 127, 127, 1)"};


        List<String[]> expectedHeaderValues = new LinkedList<>();
        expectedHeaderValues.add(expectedHomeValues);
        expectedHeaderValues.add(expectedStationValues);
        expectedHeaderValues.add(expectedMoreValues);
        expectedHeaderValues.add(expectedFlagValues);

        List<String[]> actualHeaderValues = header.valuesList();

        for(int i = 0; i < expectedHeaderValues.size(); i++){
            softAssert.assertEquals(actualHeaderValues.get(i), expectedHeaderValues.get(i));
        }

        softAssert.assertAll();
    }

    @Test
    public void testHeaderPaddingsUATPL(){
        Instant startTime = Instant.now();
        Header header = new Header(driver);
        navigateTo(header.getUrl());

        softAssert = new SoftAssert();

        Integer[] actualArray = header.getPaddings();
        Integer[] expectedArray = new Integer[]{16, 16, 20, 20, 16, 16, 16, 16, 16, 16,
                16, 16, 8, 8, 8, 8, 8, 8, 0};

        Assertions.assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    public void testHeaderAbsoluteLocationsUATPL(){
        Instant startTime = Instant.now();
        Header header = new Header(driver);

        softAssert = new SoftAssert();

        navigateTo(header.getUrl());

        header.setLocationsList();
        header.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = header.absoluteLocations();

        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key  + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{355, 38},
                new Integer[]{1020, 38},
                new Integer[]{1125, 38},
                new Integer[]{1281, 38},
                new Integer[]{1405, 38},
                new Integer[]{1499, 38},
                new Integer[]{1004, 20},
                new Integer[]{1109, 20},
                new Integer[]{1265, 20},
                new Integer[]{1389, 20}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i+1, absoluteLocationsList.get(i)));

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
    public void testHeaderRelativeLocationsUATPL(){
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
                new Integer[]{665, 0},
                new Integer[]{105, 0},
                new Integer[]{156, 0},
                new Integer[]{124, 0},
                new Integer[]{94, 0},
                new Integer[]{-495, -18},
                new Integer[]{105, 0},
                new Integer[]{156, 0},
                new Integer[]{124, 0}
        );
        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i+1, relativeLocationsList.get(i)));

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
    public void testPromoTileImageUrlUATPL(){
        Instant startTime = Instant.now();
        PromoTile promoTile = new PromoTile(driver);

        softAssert = new SoftAssert();

        navigateTo(promoTile.getUrl());
        promoTile.imageUrl();

        String actualDesktopImage = promoTile.getImageUrl().split(",")[0];
        String actualMobileImage = promoTile.getImageUrl().split(",")[1];

        String expectedDesktopImage = "https://shell247b-pl.lod.club/cdn/b/orig/6d/" +
                "fab897e1c68efdec0355f5cc37aa99/1176x372.jpg";
        String expectedMobileImage = "https://shell247b-pl.lod.club/cdn/b/orig/97/" +
                "af8b25c1bd4c85d7165174b5b6f07c/1088x602.png";

        softAssert.assertEquals(actualDesktopImage, expectedDesktopImage);
        softAssert.assertEquals(actualMobileImage, expectedMobileImage);

        softAssert.assertTrue(promoTile.saveImageToFile());

        softAssert.assertAll();
    }

    @Test
    public void testPromoTileValuesUATPL(){
        Instant startTime = Instant.now();
        PromoTile promoTile = new PromoTile(driver);

        navigateTo(promoTile.getUrl());

        softAssert = new SoftAssert();

        String[] expectedTitleValues = {"FuturaStd-Bold, Arial, Verdana", "24px", "rgba(255, 255, 255, 1)"};
        String[] expectedTextValues = {"FuturaStd-Book, Arial, Verdana", "16px", "rgba(247, 247, 247, 1)"};
        String[] expectedLoginValues = {"FuturaStd-Book, Arial, Verdana", "14px", "rgba(247, 247, 247, 1)"};
        String[] expectedRegisterValues = {"FuturaStd-Bold, Arial, Verdana", "16px", "rgba(64, 64, 64, 1)"};

        List<String[]> expectedHeaderValues = new LinkedList<>();
        expectedHeaderValues.add(expectedTitleValues);
        expectedHeaderValues.add(expectedTextValues);
        expectedHeaderValues.add(expectedLoginValues);
        expectedHeaderValues.add(expectedRegisterValues);

        List<String[]> actualHeaderValues = promoTile.valuesList();

        for(int i = 0; i < expectedHeaderValues.size(); i++){
            softAssert.assertEquals(actualHeaderValues.get(i), expectedHeaderValues.get(i));
        }

        softAssert.assertAll();
    }

    @Test
    public void testPromoTilePaddingsUATPL(){
        Instant startTime = Instant.now();
        PromoTile promoTile = new PromoTile(driver);

        softAssert = new SoftAssert();

        navigateTo(promoTile.getUrl());

        Integer[] actualArray = promoTile.getPaddings();
        Integer[] expectedArray = {24, 16, 24, 16, 24, 24, 24, 24, 8, 0, 16, 16};

        Assertions.assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    public void testPromoTileAbsoluteLocationsUATPL(){
        Instant startTime = Instant.now();
        PromoTile promoTile = new PromoTile(driver);

        softAssert = new SoftAssert();

        navigateTo(promoTile.getUrl());

        promoTile.setLocationsList();
        promoTile.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = promoTile.absoluteLocations();

        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key  + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{339, 105},
                new Integer[]{0, 105},
                new Integer[]{387, 161},
                new Integer[]{411, 185},
                new Integer[]{411, 225},
                new Integer[]{411, 301},
                new Integer[]{411, 341}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i+1, absoluteLocationsList.get(i)));

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
    public void testPromoTileRelativeLocationsUATPL(){
        Instant startTime = Instant.now();
        PromoTile promoTile = new PromoTile(driver);

        softAssert = new SoftAssert();

        navigateTo(promoTile.getUrl());

        promoTile.setLocationsList();
        promoTile.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = promoTile.relativeLocations();

        for (Map.Entry<Integer, Integer[]> entry : relativeLocationsMap.entrySet()) {
            Integer key = entry.getKey();
            Integer[] value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{-339, 0},
                new Integer[]{387, 56},
                new Integer[]{24, 24},
                new Integer[]{0, 40},
                new Integer[]{0, 76},
                new Integer[]{0, 40}
        );
        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i+1, relativeLocationsList.get(i)));

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
    public void testCatalogueImageUrlUATPL(){
        Instant startTime = Instant.now();
        Catalogue catalogue = new Catalogue(driver);

        softAssert = new SoftAssert();

        navigateTo(catalogue.getUrl());


        String[] actualImageUrl = catalogue.imageUrl().split(",");;
        String[] expectedImageUrl = {"https://test.coco-cdnimages.shell.com/images/shell-offers/58ee6a10-0653-4681-8a89-21ab526324ce-hi.jpeg",
                "https://shell247b-pl.lod.club/cdn/b/deriv/33/45ae050da94dffc7536126b052e8c3/378x350.jpg?spider.reward.default.product.image.png.jpg",
                "https://shell247b-pl.lod.club/cdn/b/deriv/33/45ae050da94dffc7536126b052e8c3/378x350.jpg?spider.reward.default.product.image.png.jpg",
                "https://test.coco-cdnimages.shell.com/images/shell-offers/91218c56-d375-4eff-ae78-5fe996c2c38c-hi.jpeg"};

        softAssert.assertEquals(actualImageUrl, expectedImageUrl);

        softAssert.assertTrue(catalogue.saveImageToFile());

        softAssert.assertAll();
    }

    @Test
    public void testCatalogueValuesUATPL(){
        Instant startTime = Instant.now();
        Catalogue catalogue = new Catalogue(driver);

        softAssert = new SoftAssert();

        navigateTo(catalogue.getUrl());

        List<String[]> actualValues = catalogue.valuesList();

        String[] catalogueTitle = {"FuturaStd-Bold, Arial, Verdana", "24px", "rgba(64, 64, 64, 1)"};
        String[] cardTitle = {"FuturaStd-Bold, Arial, Verdana", "18px", "rgba(64, 64, 64, 1)"};
        String[] cardText = {"FuturaStd-Book, Arial, Verdana", "14px", "rgba(89, 89, 89, 1)"};
        String[] catalogueButton = {"FuturaStd-Bold, Arial, Verdana", "16px", "rgba(64, 64, 64, 1)"};
        List<String[]> expectedValues = new LinkedList<>();

        expectedValues.add(catalogueTitle);

        IntStream.range(0,4).forEach(i -> {
            expectedValues.add(cardTitle);
        });
        IntStream.range(0,4).forEach(i -> {
            expectedValues.add(cardText);
        });

        expectedValues.add(catalogueButton);

        IntStream.range(0, expectedValues.size()).forEach(i ->
                softAssert.assertEquals(actualValues.get(i), expectedValues.get(i))
            );
        softAssert.assertAll();
    }

    @Test
    public void testCataloguePaddingUATPL(){
        Instant startTime = Instant.now();
        Catalogue catalogue = new Catalogue(driver);

        softAssert = new SoftAssert();

        navigateTo(catalogue.getUrl());

        Integer[] actualArray = catalogue.getPaddings();
        Integer[] expectedArray = {16, 24, 16, 339, 339, 48, 9, 15, 24, 24
                , 24, 6, 15, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,
                16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,
                16, 16, 16, 16, 16, 16, 16, 7, 7, 7, 7, 16, 16, 12};


        System.out.println(Arrays.toString(actualArray));

        Assertions.assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    public void testCatalogueAbsoluteLocationsMapUATPl(){
        Instant startTime = Instant.now();
        Catalogue catalogue = new Catalogue(driver);

        softAssert = new SoftAssert();

        navigateTo(catalogue.getUrl());

        catalogue.setLocationsList();
        catalogue.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = catalogue.absoluteLocations();

        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key  + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{339, 549},
                new Integer[]{355, 549},
                new Integer[]{357, 653},
                new Integer[]{657, 653},
                new Integer[]{957, 653},
                new Integer[]{1257, 653},
                new Integer[]{357, 838},
                new Integer[]{657, 838},
                new Integer[]{957, 838},
                new Integer[]{1257, 838},
                new Integer[]{0, 0},
                new Integer[]{0, 0},
                new Integer[]{0, 0},
                new Integer[]{0, 0},
                new Integer[]{373, 854},
                new Integer[]{673, 854},
                new Integer[]{973, 854},
                new Integer[]{1273, 854},
                new Integer[]{0, 0},
                new Integer[]{0, 0},
                new Integer[]{0, 0},
                new Integer[]{373, 904},
                new Integer[]{373, 904},
                new Integer[]{673, 105},
                new Integer[]{973, 883},
                new Integer[]{1273, 883},
                new Integer[]{0, 0},
                new Integer[]{0, 0},
                new Integer[]{0, 0},
                new Integer[]{0, 0},
                new Integer[]{355, 973}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i+1, absoluteLocationsList.get(i)));

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
    public void testCatalogueRelativeLocationsMapUATPL(){
        Instant startTime = Instant.now();
        Catalogue catalogue = new Catalogue(driver);

        softAssert = new SoftAssert();

        navigateTo(catalogue.getUrl());

        catalogue.setLocationsList();
        catalogue.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = catalogue.relativeLocations();

        for (Map.Entry<Integer, Integer[]> entry : relativeLocationsMap.entrySet()) {
            Integer key = entry.getKey();
            Integer[] value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{339, 549},
                new Integer[]{355, 549},
                new Integer[]{357, 653},
                new Integer[]{657, 653},
                new Integer[]{957, 653},
                new Integer[]{1257, 653},
                new Integer[]{357, 838},
                new Integer[]{657, 838},
                new Integer[]{957, 838},
                new Integer[]{1257, 838},
                new Integer[]{0, 0},
                new Integer[]{0, 0},
                new Integer[]{0, 0},
                new Integer[]{0, 0},
                new Integer[]{373, 854},
                new Integer[]{673, 854},
                new Integer[]{973, 854},
                new Integer[]{1273, 854},
                new Integer[]{0, 0},
                new Integer[]{0, 0},
                new Integer[]{0, 0},
                new Integer[]{373, 904},
                new Integer[]{373, 904},
                new Integer[]{673, 105},
                new Integer[]{973, 883},
                new Integer[]{1273, 883},
                new Integer[]{0, 0},
                new Integer[]{0, 0},
                new Integer[]{0, 0},
                new Integer[]{0, 0},
                new Integer[]{355, 973}
        );
        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i+1, relativeLocationsList.get(i)));

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
    public void testCataloguePopupImageUrlUATPL(){
        Catalogue catalogue = new Catalogue(driver);
        CataloguePopup cataloguePopup = catalogue.goToCataloguePopup();

        softAssert = new SoftAssert();

        navigateTo(cataloguePopup.getUrl());

        String[] expectedImageUrl = {"https://test.coco-cdnimages.shell.com/images/shell-offers/58ee6a10-0653-4681-8a89-21ab526324ce-hi.jpeg",
                "https://shell247b-pl.lod.club/cdn/b/deriv/33/45ae050da94dffc7536126b052e8c3/378x350.jpg?spider.reward.default.product.image.png.jpg",
                "https://test.coco-cdnimages.shell.com/images/shell-offers/58ee6a10-0653-4681-8a89-21ab526324ce-hi.jpeg",
                "https://shell247b-pl.lod.club/cdn/b/deriv/33/45ae050da94dffc7536126b052e8c3/378x350.jpg?spider.reward.default.product.image.png.jpg"};
        String[] actualImageUrl = cataloguePopup.imageUrl().split(",");

        IntStream.range(0, expectedImageUrl.length).forEach(i ->
                softAssert.assertEquals(actualImageUrl[i], expectedImageUrl[i])
        );

        softAssert.assertTrue(cataloguePopup.saveImageToFile());

        softAssert.assertAll();
    }


    @Test
    public void testCataloguePopupValuesUATPL(){
        Catalogue catalogue = new Catalogue(driver);

        CataloguePopup cataloguePopup = catalogue.goToCataloguePopup();

        softAssert = new SoftAssert();

        navigateTo(cataloguePopup.getUrl());

        List<String[]> actualValues = cataloguePopup.valuesList();

        String[] cataloguePopupPrice = {"FuturaStd-Book, Arial, Verdana", "14px", "rgba(255, 255, 255, 1)"};
        String[] cataloguePopupTitle = {"FuturaStd-Bold, Arial, Verdana", "18px", "rgba(64, 64, 64, 1)"};
        String[] cataloguePopupDesc = {"FuturaStd-Book, Arial, Verdana", "16px", "rgba(64, 64, 64, 1)"};
        String[] cataloguePopupLogin = {"FuturaStd-Bold, Arial, Verdana", "16px", "rgba(64, 64, 64, 1)"};
        String[] cataloguePopupRegister = {"FuturaStd-Bold, Arial, Verdana", "16px", "rgba(64, 64, 64, 1)"};


        List<String[]> expectedValues = new LinkedList<>();
        IntStream.range(0, 4).forEach(i -> {
            expectedValues.add(cataloguePopupPrice);
            expectedValues.add(cataloguePopupTitle);
            expectedValues.add(cataloguePopupDesc);
            expectedValues.add(cataloguePopupLogin);
            expectedValues.add(cataloguePopupRegister);
        });


        for (String[] item: actualValues){
            System.out.println(Arrays.toString(item));
        }

        System.out.println("----------------------------------------------");

        for (String[] item: expectedValues){
            System.out.println(Arrays.toString(item));
        }

        IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();
    }

    @Test
    public void testCataloguePopupPaddingsUATPL(){
        Catalogue catalogue = new Catalogue(driver);

        CataloguePopup cataloguePopup = catalogue.goToCataloguePopup();

        softAssert = new SoftAssert();

        navigateTo(cataloguePopup.getUrl());

        Integer[] actualPaddings = cataloguePopup.getPaddings();
        Integer[] expectedPaddings = {6, 8, 6, 8, 8, 8, 20, 20, 20, 6, 8,
                6, 8, 8, 8, 20, 20, 20, 6, 8, 6, 8, 8, 8, 20, 20, 20,
                6, 8, 6, 8, 8, 8, 20, 20, 20};

        Arrays.stream(actualPaddings).forEach(System.out::println);

        Assertions.assertArrayEquals(expectedPaddings, actualPaddings);
    }


    @Test
    public void testFooterImageUrlUATPL(){
        Instant startTime = Instant.now();
        FooterSection footerSection = new FooterSection(driver);

        softAssert = new SoftAssert();

        navigateTo(footerSection.getUrl());
        footerSection.imageUrl();

        String[] actualImageUrl = footerSection.imageUrl().split(",");
        String[] expectedImageUrl = {"https://shell247b-pl.lod.club/assets/images/brand/apple/Download_on_the_App_Store_Badge_US-UK_RGB_blk_092917.svg", "https://shell247b-pl.lod.club/assets/images/brand/android/google-play-badge.png"};

        String expectedDesktopImage = "https://shell247b-pl.lod.club/cdn/b/orig/6d/" +
                "fab897e1c68efdec0355f5cc37aa99/1176x372.jpg";
        String expectedMobileImage = "https://shell247b-pl.lod.club/cdn/b/orig/97/" +
                "af8b25c1bd4c85d7165174b5b6f07c/1088x602.png";

        softAssert.assertEquals(actualImageUrl, expectedImageUrl);

        softAssert.assertTrue(footerSection.saveImageToFile());

        softAssert.assertAll();
    }

    @Test
    public void testFooterValuesUATPL(){
        Instant startTime = Instant.now();
        FooterSection footerSection = new FooterSection(driver);

        navigateTo(footerSection.getUrl());

        softAssert = new SoftAssert();

        List<String[]> actualValues = footerSection.valuesList();

        for(String[] item: actualValues){
            System.out.println(Arrays.toString(item));
        }

        String[] footerTitles = {"FuturaStd-Book, Arial, Verdana", "12px", "rgba(64, 64, 64, 1)"};
        String[] footerSubTitles = {"FuturaStd-Book, Arial, Verdana", "14px", "rgba(127, 127, 127, 1)"};

        List<String[]> expectedValues = new LinkedList<>();
        IntStream.range(0, 4).forEach(i -> expectedValues.add(footerTitles));
        IntStream.range(0, 14).forEach(i -> expectedValues.add(footerSubTitles));

        IntStream.range(0, expectedValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();
    }

    @Test
    public void testFooterPaddingUATPL(){


    }

}
