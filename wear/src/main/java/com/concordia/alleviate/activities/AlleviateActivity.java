package com.concordia.alleviate.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.animation.LinearInterpolator;
import android.widget.TextSwitcher;
import android.widget.TextView;
import com.concordia.alleviate.R;
import pl.pawelkleczkowski.customgauge.CustomGauge;

public class AlleviateActivity extends WearableActivity {

    private static final String DEFAULT_EXERCISE = "Quick Exercise";

    private static final int INTRO_TIME = 3000;
    private static final int BREATHING_TIME = 5000;
    private static final int RESET_TIME = 600;

    private static final int ROUNDS = 3;
    private static final int PRECISION = 100000;

    private static final int[] EXERCISE_STEPS = {
        R.string.alleviate_get_ready,
        R.string.alleviate_breath_in,
        R.string.alleviate_breath_out,
        R.string.alleviate_done,
    };

    private CustomGauge exerciseGauge;
    private TextSwitcher actionView;

    private ValueAnimator introAnimation;
    private ValueAnimator exerciseAnimation;
    private ValueAnimator resetAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alleviate);

        setIntentExercise();
        startExercise();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setIntentExercise();
        startExercise();
    }

    private void setIntentExercise() {
        // Set default value
        TextView exerciseView = findViewById(R.id.alleviate_text);
        exerciseView.setText(DEFAULT_EXERCISE);
        // Override default value
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            String selectedExercise = intent.getExtras().getString("exercise");
            if (selectedExercise != null && selectedExercise.length() > 0) {
                exerciseView.setText(selectedExercise);
            }
        }
    }

    private void startExercise() {
        exerciseGauge = findViewById(R.id.alleviate_progress_bar);
        actionView = findViewById(R.id.alleviate_text_action);

        actionView.setInAnimation(this, android.R.anim.fade_in);
        actionView.setOutAnimation(this, android.R.anim.fade_out);
        actionView.setCurrentText(getString(EXERCISE_STEPS[0]));

        defineAnimations();
        introAnimation.start();
    }

    private void defineAnimations() {
        final int[] resetCount = {0};
        final int[] exerciseCount = {0};

        introAnimation = ValueAnimator.ofInt(0, PRECISION);
        introAnimation.setDuration(INTRO_TIME);
        introAnimation.setInterpolator(new LinearInterpolator());
        introAnimation.addUpdateListener(animation -> {
            exerciseGauge.setValue((int) animation.getAnimatedValue());
        });
        introAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                resetAnimation.start();
            }
        });

        exerciseAnimation = ValueAnimator.ofInt(0, PRECISION);
        exerciseAnimation.setDuration(BREATHING_TIME);
        exerciseAnimation.setInterpolator(new LinearInterpolator());
        exerciseAnimation.addUpdateListener(animation -> {
            exerciseGauge.setValue((int) animation.getAnimatedValue());
        });
        exerciseAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (exerciseCount[0]++ % 2 == 0)
                    actionView.setText(getString(EXERCISE_STEPS[1]));
                else
                    actionView.setText(getString(EXERCISE_STEPS[2]));
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                resetAnimation.start();
            }
        });

        resetAnimation = ValueAnimator.ofInt(PRECISION, 0);
        resetAnimation.setDuration(RESET_TIME);
        resetAnimation.setInterpolator(new LinearInterpolator());
        resetAnimation.addUpdateListener(animation -> {
            exerciseGauge.setValue((int) animation.getAnimatedValue());
        });
        resetAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (resetCount[0]++ < (ROUNDS * 2)) {
                    exerciseAnimation.start();
                } else {
                    actionView.setText(getString(EXERCISE_STEPS[3]));
                }
            }
        });
    }
}