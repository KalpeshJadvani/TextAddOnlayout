package com.example.kalpesh.textaddonlayout.CusttomImageView;

/**
 * Created by omsai on 8/6/2016.
 */
public interface SimpleValueAnimator {
    void startAnimation(long duration);
    void cancelAnimation();
    boolean isAnimationStarted();
    void addAnimatorListener(SimpleValueAnimatorListener animatorListener);
}
