package utils;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResponseToString {

    public static class CustomStringResponse{

        private final String responseBody;

        public CustomStringResponse(String responseBody){
            this.responseBody = responseBody;
        }

        public boolean isResponseBodyEmpty(){
            return responseBody.isEmpty();
        }

        @Override
        public String toString(){
            return STR."String representation of the response: \{responseBody}";
        }
    }
}
