package com.sneydr.roomrv2.App.UI;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;


public class CircularProgressBarAnimation extends Animation {

    private ProgressBar progressBar;
    private float from;
    private float to;
    final int SMOOTH_ANIMATION_VALUE = 100;

    public CircularProgressBarAnimation(ProgressBar progressBar, float from, float to, int max) {
        super();
        this.progressBar = progressBar;
        this.progressBar.setMax(max * SMOOTH_ANIMATION_VALUE);
        this.from = from * SMOOTH_ANIMATION_VALUE;
        this.to = to * SMOOTH_ANIMATION_VALUE;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);
    }
}
