package com.example.lives_project;

import java.util.Map;

public class Question {
    private String text;
    private String correctOption;
    private Map<String, String> options;

    public Question() {
        // Пустой конструктор необходим для Firebase
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(String correctOption) {
        this.correctOption = correctOption;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }
}
