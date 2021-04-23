package com.concordia.alleviate.ui.relief;

import androidx.lifecycle.ViewModel;
import com.concordia.alleviate.models.Difficulty;
import com.concordia.alleviate.models.ReliefExercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ReliefViewModel extends ViewModel {

    private boolean showMeditation = false;
    private boolean showBreathing = true;
    private boolean showGrounding = false;

    public ReliefViewModel() { }

    public boolean isShowMeditation() {
        return showMeditation;
    }

    public void setShowMeditation(boolean showMeditation) {
        this.showMeditation = showMeditation;
    }

    public boolean isShowBreathing() {
        return showBreathing;
    }

    public void setShowBreathing(boolean showBreathing) {
        this.showBreathing = showBreathing;
    }

    public boolean isShowGrounding() {
        return showGrounding;
    }

    public void setShowGrounding(boolean showGrounding) {
        this.showGrounding = showGrounding;
    }

    public ReliefExercise[] getExercises() {
        ArrayList<ReliefExercise> exercises = new ArrayList<>();
        if (showMeditation)
            exercises.addAll(Arrays.asList(getMeditationExercises()));
        if (showBreathing)
            exercises.addAll(Arrays.asList(getBreathingExercises()));
        if (showGrounding)
            exercises.addAll(Arrays.asList(getGroundingExercises()));

        ReliefExercise[] retVal = exercises.toArray(new ReliefExercise[] {});
        Arrays.sort(retVal, Comparator.comparing(ReliefExercise::getOrder));

        return retVal;
    }

    private ReliefExercise[] getMeditationExercises() {
        return new ReliefExercise[] {
            new ReliefExercise("Walking Meditation", Difficulty.BEGINNER, 15),
            new ReliefExercise("Gratitude Meditation", Difficulty.INTERMEDIATE, 7),
            new ReliefExercise("Stillness Challenge", Difficulty.BEGINNER, 10),
            new ReliefExercise("Sound Focus Meditation", Difficulty.ADVANCED, 12),
            new ReliefExercise("One Minute of Mindfulness", Difficulty.INTERMEDIATE, 25),
            new ReliefExercise("Conscious Observation", Difficulty.INTERMEDIATE, 30),
            new ReliefExercise("Transcendental Meditation", Difficulty.BEGINNER, 17),
            new ReliefExercise("Stoic Meditation", Difficulty.ADVANCED, 20),
            new ReliefExercise("Deep breathing", Difficulty.ADVANCED, 15),
            new ReliefExercise("STOP Technique", Difficulty.BEGINNER, 5),
        };
    }

    private ReliefExercise[] getBreathingExercises() {
        return new ReliefExercise[] {
            new ReliefExercise("Pursed lip breathing", Difficulty.BEGINNER, 15),
            new ReliefExercise("Diaphragmatic breathing", Difficulty.INTERMEDIATE, 7),
            new ReliefExercise("Breath focus technique", Difficulty.BEGINNER, 10),
            new ReliefExercise("Lion’s breath", Difficulty.ADVANCED, 12),
            new ReliefExercise("Alternate nostril breathing", Difficulty.INTERMEDIATE, 25),
            new ReliefExercise("Equal breathing", Difficulty.INTERMEDIATE, 30),
            new ReliefExercise("Resonant or coherent breathing", Difficulty.BEGINNER, 17),
            new ReliefExercise("Sitali breath", Difficulty.ADVANCED, 20),
            new ReliefExercise("Deep breathing", Difficulty.ADVANCED, 15),
            new ReliefExercise("Humming bee breath (bhramari)", Difficulty.BEGINNER, 5),
        };
    }

    private ReliefExercise[] getGroundingExercises() {
        return new ReliefExercise[] {
            new ReliefExercise("Savor a scent", Difficulty.BEGINNER, 15),
            new ReliefExercise("Piece of ice", Difficulty.INTERMEDIATE, 7),
            new ReliefExercise("Move your body", Difficulty.BEGINNER, 10),
            new ReliefExercise("Listen to your surroundings", Difficulty.ADVANCED, 12),
            new ReliefExercise("Feel your body", Difficulty.INTERMEDIATE, 25),
            new ReliefExercise("5-4-3-2-1 method", Difficulty.INTERMEDIATE, 30),
            new ReliefExercise("Memory game", Difficulty.BEGINNER, 17),
            new ReliefExercise("Think in categories", Difficulty.ADVANCED, 20),
            new ReliefExercise("Recite something", Difficulty.ADVANCED, 15),
            new ReliefExercise("Anchoring phrase", Difficulty.BEGINNER, 5),
        };
    }
}