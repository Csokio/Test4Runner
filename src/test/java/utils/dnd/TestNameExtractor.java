package utils.dnd;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestNameExtractor {

    public List<String> getTestNames(Class<?> testClass) {
        List<String> testNames = new ArrayList<>();
        Method[] methods = testClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(org.junit.Test.class) || method.isAnnotationPresent(org.testng.annotations.Test.class)) {
                testNames.add(method.getName());
            }
        }
        return testNames;
    }
}