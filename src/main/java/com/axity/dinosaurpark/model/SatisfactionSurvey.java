package com.axity.dinosaurpark.model;

public record SatisfactionSurvey(int touristId, String enclosureName, int score) {
    public SatisfactionSurvey {
        if (score < 1 || score > 5) {
            throw new IllegalArgumentException("El score debe estar entre 1 y 5");
        }
    }
}
