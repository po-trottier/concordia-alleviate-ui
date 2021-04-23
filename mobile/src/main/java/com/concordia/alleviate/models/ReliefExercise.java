package com.concordia.alleviate.models;

public class ReliefExercise {
    private final String title;
    private final Difficulty difficulty;
    private final int duration;

    public ReliefExercise(String title, Difficulty difficulty, int duration) {
        this.title = title;
        this.difficulty = difficulty;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getDifficulty() {
        return difficulty.getString();
    }

    public int getOrder() {
        return difficulty.getInt();
    }

    public int getDuration() {
        return duration;
    }
}
