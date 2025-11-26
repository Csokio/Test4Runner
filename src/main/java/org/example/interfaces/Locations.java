package org.example.interfaces;

import java.util.LinkedHashMap;

public interface Locations {

    void setLocationsList();

    void createMapFromLocationsList();

    LinkedHashMap<Integer, Integer[]> absoluteLocations();

    LinkedHashMap<Integer, Integer[]> relativeLocations();

}
