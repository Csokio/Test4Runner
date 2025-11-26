package org.example.interfaces;

import java.util.List;

public interface SaveImage {

    String imageUrl();

    boolean saveImageToFile();

    List<String[]> valuesList();

    Integer[] getPaddings();


    //TODO Verify locations of the elements with getlocation()(example in FAQs class), may implement map
}
