package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ResponseToMap {

    public static class CustomMapResponse{

        private final String[] responseBody;

        public CustomMapResponse(String[] responseBody){
            this.responseBody = responseBody;
        }

        private List<String> finalList = new ArrayList<>();

        public Map<String, String> toMap(){
            return Stream.of(responseBody)
                    .map(s -> s.split(":", 2))
                    .filter(arr -> arr.length == 2)
                    .collect(Collectors.toMap(
                            arr -> arr[0],
                            arr -> arr[1],
                            (existingValue, newValue) -> existingValue
                    ));
        }
    }
}
