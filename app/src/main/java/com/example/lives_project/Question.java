package com.example.lives_project;

import java.util.Map;

public class Question {
    private String text;
    private Map<String, String> options;
    private String correctOption;

    public Question() {
    }

    public String getText() {
        return text;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public String getCorrectOption() {
        return correctOption;
    }
}
