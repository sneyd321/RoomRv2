package com.sneydr.roomrv2.App.CompoundButtonInput;

import android.animation.Animator;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class AnimatedSelectedNotSelectedSwitchInput extends CompoundButtonInput {



    private LinearLayout layout;

    public AnimatedSelectedNotSelectedSwitchInput(View view, int compoundButton, int layout) {
        super(view, compoundButton);
        this.compoundButton.setOnCheckedChangeListener(onChecked);
        this.layout = view.findViewById(layout);
    }



    private CompoundButton.OnCheckedChangeListener onChecked = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(final CompoundButton compoundButton, boolean b) {
            if (b) {
                layout.setEnabled(true);
                layout.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeInDown).duration(700).playOn(layout);
                compoundButton.setText("Selected");
                return;
            }
            layout.setEnabled(false);
            YoYo.with(Techniques.SlideOutLeft).duration(500).onStart(new YoYo.AnimatorCallback() {
                @Override
                public void call(Animator animator) {
                    compoundButton.setEnabled(false);
                }
            }).onEnd(new YoYo.AnimatorCallback() {
                @Override
                public void call(Animator animator) {
                    layout.setVisibility(View.GONE);
                    compoundButton.setEnabled(true);
                    compoundButton.setText("Not Selected");
                }
            }).playOn(layout);
        }
    };
}
