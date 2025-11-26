package org.example.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class AbstractGetPaddingSupporter extends AbstractLocationManager {

    private final AtomicInteger aInteger = new AtomicInteger(1);
    private final AtomicInteger xInteger = new AtomicInteger(0);

    private final String[] cssPaddings = {"padding-top", "padding-right", "padding-bottom", "padding-left"};
    private final String[] cssMargins = {"margin-top", "margin-right", "margin-bottom", "margin-left"};

    protected String[] paddingStringArray = new String[3000];

    protected String[][] twoDimensionalPaddingStringArray = new String[3000][2];

    /*protected AbstractGetPaddingSupporter(String[] paddingStringArray) {
        this.paddingStringArray = paddingStringArray;
    }*/

    public String[][] getTwoDimensionalPaddingStringArray(){
        return this.twoDimensionalPaddingStringArray;
    }

    protected void accumulatePaddingsMarginsOfBy(WebElement element) {

        Arrays.stream(cssPaddings).forEach(p -> {
            String cssValue = element.getCssValue(p);
            if (cssValue != null) {
                paddingStringArray[aInteger.getAndIncrement()] = cssValue;
            }
        });

        Arrays.stream(cssMargins).forEach(m -> {
            String cssValue = element.getCssValue(m);
            if (cssValue != null) {
                paddingStringArray[aInteger.getAndIncrement()] = cssValue;
            }
        });
    }

    protected void accumulatePaddingsMarginsOfByList(List<WebElement> element){
        Arrays.stream(cssPaddings).forEach(p -> {
            element.stream().filter(e -> e.getCssValue(p) != null)
                    .forEach(e -> {
                        paddingStringArray[aInteger.getAndIncrement()] = e.getCssValue(p);
                    });
        });
        Arrays.stream(cssMargins).forEach(m -> {
            element.stream().filter(e -> e.getCssValue(m) != null)
                    .forEach(e -> {
                        paddingStringArray[aInteger.getAndIncrement()] = e.getCssValue(m);
                    });
        });
    }

    protected void accumulatePaddingsMarginsOfByListInnerXpath(List<WebElement> element, String xPath){
        Arrays.stream(cssPaddings).forEach(p -> {
            element.stream().forEach(e -> {
                WebElement newE = e.findElement(By.xpath(xPath));
                if(newE.getCssValue(p) != null){
                    paddingStringArray[aInteger.getAndIncrement()] = newE.getCssValue(p);
                }
            });
        });
        Arrays.stream(cssMargins).forEach(m -> {
            element.stream().forEach(e -> {
                WebElement newE = e.findElement(By.xpath(xPath));
                if(newE.getCssValue(m) != null){
                    paddingStringArray[aInteger.getAndIncrement()] = newE.getCssValue(m);
                }
            });
        });
    }
    //TODO implement here twodimensional acuumulate methods to get xpath and padding in one-one column  --- DONE
    protected void accumulateTwoDimensionalPaddingsMarginsOfByList(List<String> xpathList, List<WebElement> elementList){
        /*Arrays.stream(cssPaddings).forEach(p -> {
            elementList.stream().filter(e -> e.getCssValue(p) != null)
                    .forEach(e -> {
                        twoDimensionalPaddingStringArray[aInteger.get()][0] = xpathList.get(xInteger.get());
                        twoDimensionalPaddingStringArray[aInteger.getAndIncrement()][1] = p + ": " + e.getCssValue(p);
                    });
            xInteger.getAndIncrement();
        });
        Arrays.stream(cssMargins).forEach(m -> {
            elementList.stream().filter(e -> e.getCssValue(m) != null)
                    .forEach(e -> {
                        twoDimensionalPaddingStringArray[aInteger.get()][0] = xpathList.get(xInteger.get());
                        twoDimensionalPaddingStringArray[aInteger.getAndIncrement()][1] = m + ": " + e.getCssValue(m);
                    });
            xInteger.getAndIncrement();
        });*/
        /*Arrays.stream(cssPaddings).forEach(p -> {
            elementList.stream().filter(e -> e.getCssValue(p) != null)
                    .forEach(e -> {
                        String xpath = xpathList.get(xInteger.get());
                        String cssValue = e.getCssValue(p);

                        boolean exists = Arrays.stream(twoDimensionalPaddingStringArray)
                                .anyMatch(entry -> entry[0] != null && entry[0].equals(xpath) && entry[1].contains(p));

                        if (!exists) {
                            twoDimensionalPaddingStringArray[aInteger.get()][0] = xpath;
                            twoDimensionalPaddingStringArray[aInteger.getAndIncrement()][1] = p + ": " + cssValue;
                        }
                    });
            xInteger.getAndIncrement();
        });*/

        Arrays.stream(elementList.toArray()).forEach(e -> {
            Arrays.stream(cssPaddings).filter(p -> p != null)
                    .forEach(p -> {
                        WebElement element = (WebElement) e;
                        String xpath = xpathList.get(xInteger.get());
                        String cssValue = element.getCssValue(p);

                        boolean exists = Arrays.stream(twoDimensionalPaddingStringArray)
                                .anyMatch(entry -> entry[0] != null && entry[0].equals(xpath) && entry[1].contains(p));

                        if (!exists) {
                            twoDimensionalPaddingStringArray[aInteger.get()][0] = xpath;
                            twoDimensionalPaddingStringArray[aInteger.getAndIncrement()][1] = p + ": " + cssValue;
                        }
                    });
            xInteger.getAndIncrement();
        });

        //aInteger.set(1);
        xInteger.set(0);

        Arrays.stream(elementList.toArray()).forEach(e -> {
            Arrays.stream(cssMargins).filter(m -> m != null)
                    .forEach(m -> {
                        WebElement element = (WebElement) e;
                        String xpath = xpathList.get(xInteger.get());
                        String cssValue = element.getCssValue(m);

                        boolean exists = Arrays.stream(twoDimensionalPaddingStringArray)
                                .anyMatch(entry -> entry[0] != null && entry[0].equals(xpath) && entry[1].contains(m));

                        if (!exists) {
                            twoDimensionalPaddingStringArray[aInteger.get()][0] = xpath;
                            twoDimensionalPaddingStringArray[aInteger.getAndIncrement()][1] = m + ": " + cssValue;
                        }
                    });
            xInteger.getAndIncrement();
        });

    }

    //TODO accumulateBordersOfBy

    protected void accumulateAndReturnCssProperties(WebElement element) {
        Arrays.stream(cssPaddings).forEach(p -> {
            String cssValue = element.getCssValue(p);
            if (cssValue != null) {
                paddingStringArray[aInteger.getAndIncrement()] = cssValue;
            }
        });

        Arrays.stream(cssMargins).forEach(m -> {
            String cssValue = element.getCssValue(m);
            if (cssValue != null) {
                paddingStringArray[aInteger.getAndIncrement()] = cssValue;
            }
        });

        String fontFamily = element.getCssValue("font-family");
        String fontSize = element.getCssValue("font-size");
        String color = element.getCssValue("color");

        if (fontFamily != null) paddingStringArray[aInteger.getAndIncrement()] = fontFamily;
        if (fontSize != null) paddingStringArray[aInteger.getAndIncrement()] = fontSize;
        if (color != null) paddingStringArray[aInteger.getAndIncrement()] = color;
    }


    protected void accumulateAndReturnCssPropertiesFromList(List<WebElement> elements) {
        elements.forEach(element -> {
            Arrays.stream(cssPaddings).forEach(p -> {
                String cssValue = element.getCssValue(p);
                if (cssValue != null) {
                    paddingStringArray[aInteger.getAndIncrement()] = cssValue;
                }
            });

            Arrays.stream(cssMargins).forEach(m -> {
                String cssValue = element.getCssValue(m);
                if (cssValue != null) {
                    paddingStringArray[aInteger.getAndIncrement()] = cssValue;
                }
            });

            String fontFamily = element.getCssValue("font-family");
            String fontSize = element.getCssValue("font-size");
            String color = element.getCssValue("color");

            if (fontFamily != null) paddingStringArray[aInteger.getAndIncrement()] = fontFamily;
            if (fontSize != null) paddingStringArray[aInteger.getAndIncrement()] = fontSize;
            if (color != null) paddingStringArray[aInteger.getAndIncrement()] = color;
        });
    }


    protected String[] resizeArrayAndRemoveNulls(String[] originalArray) {
        LinkedList<String> nonNullList = new LinkedList<>();

        for (String element : originalArray) {
            System.out.println(element);
            if (element != null && !element.equals("null")) {
                if(!element.startsWith("0")){
                    //nonNullList.add(element);
                    if(element.startsWith("-")){
                        System.out.println("NEGATIVE ELEMENT FOUND");
                        String[] parts = element.split("-", 2);
                        System.out.println("The negative number is: " + parts[1]);// Csak az első "-" karakter alapján bont
                        if (parts.length > 1) {
                            nonNullList.add("-" + parts[1]);
                        }
                    } else {
                        nonNullList.add(element);
                    }
                }
            }
        }

        return nonNullList.toArray(new String[0]);
    }

    protected String[][] resizeArrayAndRemoveNullsTwoDimensional(String[][] originalArray) {

        /*int nonNullCount = 0;

        for(int i = 0; i < originalArray.length; i++){
            for(int j = 0; j < originalArray[i].length; j++){
                if(originalArray[i][j] != null && !originalArray[i][j].equals("null")){
                    nonNullCount++;
                    if(originalArray[i][j].startsWith("-")){
                        System.out.println("NEGATIVE ELEMENT FOUND");
                    }
                }
            }
        }

        String[][] nonNullArray = new String[nonNullCount][2];

        for (int i = 0; i < originalArray.length; i++) {
            List<String> rowList = new ArrayList<>();

            for (int j = 0; j < originalArray[i].length; j++) {
                if (originalArray[i][j] != null && !originalArray[i][j].equals("null")) {
                    rowList.add(originalArray[i][j]);

                    if (originalArray[i][j].startsWith("-")) {
                        System.out.println("NEGATIVE ELEMENT FOUND: " + originalArray[i][j]);
                    }
                }
            }

            nonNullArray[i] = rowList.toArray(new String[0]);
        }

        return nonNullArray;*/
        List<String[]> nonNullList = new ArrayList<>();

        // Iterate through the original array and collect non-null, non-"null" values
        for (int i = 0; i < originalArray.length; i++) {
            List<String> rowList = new ArrayList<>();

            for (int j = 0; j < originalArray[i].length; j++) {
                String element = originalArray[i][j];

                // Check if element is neither null nor the string "null"
                if (element != null && !element.equals("null")) {
                    rowList.add(element);

                    // Check if the element starts with "-"
                    if (element.startsWith("-")) {
                        System.out.println("NEGATIVE ELEMENT FOUND: " + element);
                    }
                }
            }

            // If there are non-null elements in this row, add them to the final list
            if (!rowList.isEmpty()) {
                nonNullList.add(rowList.toArray(new String[0]));
            }
        }

        // Convert the list of rows into a 2D array
        String[][] nonNullArray = new String[nonNullList.size()][];
        for (int i = 0; i < nonNullList.size(); i++) {
            nonNullArray[i] = nonNullList.get(i);
        }

        return nonNullArray;
    }


    protected List<WebElement> resizeListToNumber(List<WebElement> originalList, Integer number) {
        if (originalList.size() > number) {
            return new ArrayList<>(originalList.subList(0, number));
        }
        return new ArrayList<>(originalList);
    }

    protected String[] removeNullsFromArray(String[] originalArray) {
        List<String> nonNullList = new ArrayList<>();

        for (String element : originalArray) {
            if (element != null) {
                nonNullList.add(element);
            }
        }
        return nonNullList.toArray(new String[0]); // Return as trimmed Integer array
    }

    //getElementXPath get xpath elements to reworked valueslist method
    protected String getElementXPath(WebElement element, WebDriver driver) {
        return (String) ((JavascriptExecutor) driver).executeScript(
                "function getXPath(el) {" +
                        "var xpath = '';" +
                        "for (; el && el.nodeType == 1; el = el.parentNode) {" +
                        "   var id = Array.prototype.indexOf.call(el.parentNode.children, el) + 1;" +
                        "   xpath = '/' + el.tagName.toLowerCase() + '[' + id + ']' + xpath;" +
                        "}" +
                        "return xpath;" +
                        "}" +
                        "return getXPath(arguments[0]);", element);
    }

    protected String getElementXPath2(WebElement element, WebDriver driver) {
        return (String) ((JavascriptExecutor) driver).executeScript(
                "function getXPath(element) {" +
                        "   if (element.id !== '') { return 'id(\"' + element.id + '\")'; }" +
                        "   if (element === document.body) { return '/html/' + element.tagName.toLowerCase(); }" +
                        "   var ix = 1;" +
                        "   var siblings = element.parentNode.childNodes;" +
                        "   for (var i = 0; i < siblings.length; i++) {" +
                        "       var sibling = siblings[i];" +
                        "       if (sibling === element) return getXPath(element.parentNode) + '/' + element.tagName.toLowerCase() + '[' + ix + ']';" +
                        "       if (sibling.nodeType === 1 && sibling.tagName === element.tagName) ix++;" +
                        "   }" +
                        "   return null;" +
                        "}" +
                        "return getXPath(arguments[0]);", element);
    }

    protected List<String> getElementXPath2List(List<WebElement> element, WebDriver driver) {
        List<String> resultList = new ArrayList<>();
        IntStream.range(0, element.size()).forEach(i -> {
            resultList.add((String) ((JavascriptExecutor) driver).executeScript(
                    "function getXPath(element) {" +
                            "   if (element.id !== '') { return 'id(\"' + element.id + '\")'; }" +
                            "   if (element === document.body) { return '/html/' + element.tagName.toLowerCase(); }" +
                            "   var ix = 1;" +
                            "   var siblings = element.parentNode.childNodes;" +
                            "   for (var i = 0; i < siblings.length; i++) {" +
                            "       var sibling = siblings[i];" +
                            "       if (sibling === element) return getXPath(element.parentNode) + '/' + element.tagName.toLowerCase() + '[' + ix + ']';" +
                            "       if (sibling.nodeType === 1 && sibling.tagName === element.tagName) ix++;" +
                            "   }" +
                            "   return null;" +
                            "}" +
                            "return getXPath(arguments[0]);", element.get(i)));
        });

        return resultList;
    }

}
