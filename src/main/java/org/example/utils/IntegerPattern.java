package org.example.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntegerPattern {

    private final Integer[] matchedInteger;
    private int counter = 0;

    public IntegerPattern(String[] text, int arraySize){
        matchedInteger = new Integer[arraySize];

        for(String str: text){
            Pattern pattern = Pattern.compile("^-?[0-9]+", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(str);
            if(matcher.find()){
                matchedInteger[counter++] = Integer.valueOf(matcher.group());
            }
        }

    }

    /*public IntegerPattern(List<String[]> textList, int arraySize) {
        matchedInteger = new Integer[arraySize];

        for (String[] array : textList) {  // Iterate over each String[] in the list
            for (String str : array) {  // Iterate over each String in the String[] array
                Pattern pattern = Pattern.compile("^[0-9]+", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(str);
                if (matcher.find()) {
                    matchedInteger[counter++] = Integer.valueOf(matcher.group());
                }
            }
        }
    }*/

    public Integer[] getMatchedInteger(){
        //return this.matchedInteger;
        return Arrays.stream(matchedInteger)
                .filter(Objects::nonNull)
                .toArray(Integer[]::new);
    }
}
