package com.concordia.alleviate.models;

public enum Difficulty {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED;

    public String getString() {
        switch (this) {
            case BEGINNER: return "Beginner";
            case INTERMEDIATE: return "Intermediate";
            case ADVANCED: return "Advanced";
            default: return "Unknown Difficulty";
        }
    }

    public int getInt() {
        switch (this) {
            case BEGINNER: return 0;
            case INTERMEDIATE: return 1;
            case ADVANCED: return 2;
            default: return Integer.MAX_VALUE;
        }
    }
}
