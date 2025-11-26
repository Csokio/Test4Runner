package utils.compare;

import java.util.Comparator;

public class MethodNameComparator implements Comparator<String> {

    @Override
    public int compare(String m1, String m2) {
        return m1.compareTo(m2);
    }

}
