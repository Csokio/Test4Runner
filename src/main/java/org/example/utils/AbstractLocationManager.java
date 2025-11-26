package org.example.utils;

import org.example.interfaces.Locations;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.stream.IntStream;

public abstract class AbstractLocationManager implements Locations {

    protected LinkedList<Point> locationsList = new LinkedList<>();

    private LinkedHashMap<Integer, Integer[]> mapOfPoints;

    protected int getSizeOfLocationList(){
        return this.locationsList.size();
    }

    protected LinkedHashMap<Integer, Integer[]> getMapOfPoints(){
        return this.mapOfPoints;
    }
    protected void setMapOfPointsFAQs(LinkedHashMap<Integer, Integer[]> mapOfPoints){
        this.mapOfPoints = mapOfPoints;
    }
    private Integer[] getLocations(int index){
        return new Integer[]{locationsList.get(index).getX(), locationsList.get(index).getY()};
    }

    protected void accumulateMapBasedOnLocations(){
        LinkedHashMap<Integer, Integer[]> temporaryMap = new LinkedHashMap<>();
        int index = 0;
        int  sizeOfLocationList = getSizeOfLocationList();
        System.out.println("The size of location list in accumulate method is: " + sizeOfLocationList);
        locationsList.forEach(System.out::println);
        if(sizeOfLocationList != 0){
            while (sizeOfLocationList != 0){
                temporaryMap.put(index+1, getLocations(index++));
                sizeOfLocationList--;
                System.out.println(temporaryMap.size());
            }
        }
        System.out.println("The size of temporaryMap in accumulateMapBasedOnLocations method is: " + temporaryMap.size());
        if(!temporaryMap.isEmpty()){
            this.mapOfPoints = temporaryMap;
        }
    }
    protected LinkedHashMap<Integer, Integer[]> calculateDifference(LinkedHashMap<Integer, Integer[]> mapOfPoints){

        LinkedHashMap<Integer, Integer[]> pointDiffXY = new LinkedHashMap<>();
        IntStream.range(0, mapOfPoints.size()-1).forEach(i ->
                pointDiffXY.put(i+1, new Integer[]{mapOfPoints.get(i+2)[0] - mapOfPoints.get(i+1)[0],
                        mapOfPoints.get(i+2)[1] - mapOfPoints.get(i+1)[1]})
        );

        System.out.println("The size of pointDiff Map in calculateDiff method is: " + pointDiffXY.size());
        return pointDiffXY;
    }

    protected void windowScrollTo(WebDriver driver, WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

}
