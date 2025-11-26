package tests.qa2pl.login.subpages;

import org.example.interfaces.SaveImage;
import org.example.utils.AbstractGetPaddingSupporter;

import java.util.LinkedHashMap;
import java.util.List;

public class QA2PLBasket2 extends AbstractGetPaddingSupporter implements SaveImage {
    @Override
    public void setLocationsList() {

    }

    @Override
    public void createMapFromLocationsList() {

    }

    @Override
    public LinkedHashMap<Integer, Integer[]> absoluteLocations() {
        return null;
    }

    @Override
    public LinkedHashMap<Integer, Integer[]> relativeLocations() {
        return null;
    }

    @Override
    public String imageUrl() {
        return null;
    }

    @Override
    public boolean saveImageToFile() {
        return false;
    }

    @Override
    public List<String[]> valuesList() {
        return null;
    }

    @Override
    public Integer[] getPaddings() {
        return new Integer[0];
    }
}
