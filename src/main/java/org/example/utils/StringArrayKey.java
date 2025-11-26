package org.example.utils;

import java.util.Arrays;

public class StringArrayKey {
    private final String[] key;

    public StringArrayKey(String[] key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        StringArrayKey arrayKey = (StringArrayKey) obj;
        return Arrays.equals(key, arrayKey.key);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(key);
    }

    @Override
    public String toString(){
        return Arrays.toString(key);
    }
}