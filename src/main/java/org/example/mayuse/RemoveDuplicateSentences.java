package org.example.mayuse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RemoveDuplicateSentences {



    public RemoveDuplicateSentences(){

    }

    public String removeDuplicateSentencesSingleKeyword(String text, String keyword) {
        String[] sentences = text.split("\\. ");
        Set<String> seenKeywords = new HashSet<>();
        StringBuilder result = new StringBuilder();

        for (String sentence : sentences) {
            if (sentence.contains(keyword)) {
                if (seenKeywords.contains(keyword)) {
                    continue;
                }
                seenKeywords.add(keyword);
            }
            result.append(sentence.trim()).append(". ");
        }

        if (result.length() > 0) {
            result.setLength(result.length() - 2);
        }

        return result.toString();
    }
    public String removeDuplicateSentencesListKeyword(String text, List<String> keywords) {
        String[] sentences = text.split("\\. ");
        Set<String> seenKeywords = new HashSet<>();
        StringBuilder result = new StringBuilder();

        for (String sentence : sentences) {
            boolean containsKeyword = false;
            for (String keyword : keywords) {
                if (sentence.contains(keyword)) {
                    containsKeyword = true;
                    if (seenKeywords.contains(keyword)) {
                        containsKeyword = false;
                        break;
                    }
                    seenKeywords.add(keyword);
                }
            }
            if (!containsKeyword || !seenKeywords.containsAll(keywords.stream()
                    .filter(sentence::contains)
                    .collect(Collectors.toList()))) {
                result.append(sentence.trim()).append(". ");
            }
        }

        if (result.length() > 0) {
            result.setLength(result.length() - 2);
        }

        return result.toString();

    }

    public String removeDuplicateSentencesSetKeyword(String text, Set<String> keywords) {
        String[] sentences = text.split("\\. ");
        Set<String> seenKeywords = new HashSet<>();
        StringBuilder result = new StringBuilder();

        for (String sentence : sentences) {
            boolean containsKeyword = false;
            for (String keyword : keywords) {
                if (sentence.contains(keyword)) {
                    containsKeyword = true;
                    if (seenKeywords.contains(keyword)) {
                        containsKeyword = false;
                        break;
                    }
                    seenKeywords.add(keyword);
                }
            }
            if (!containsKeyword || !seenKeywords.containsAll(keywords)) {
                result.append(sentence.trim()).append(". ");
            }
        }

        if (result.length() > 0) {
            result.setLength(result.length() - 2);
        }

        return result.toString();
    }
}