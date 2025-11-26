package org.example.interfaces;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface PaddingSupport {

    void accumulatePaddingsMarginsOfByList(List<WebElement> element);

    void accumulatePaddingsMarginsOfByListInnerXpath(List<WebElement> element, String xPath);
    String[] resizeArrayAndRemoveNulls(String[] originalArray);

    List<WebElement> resizeListToNumber(List<WebElement> originalList, Integer number);


}
