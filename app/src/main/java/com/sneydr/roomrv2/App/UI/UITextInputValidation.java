package com.sneydr.roomrv2.App.UI;

import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.sneydr.roomrv2.App.TextInput.TextInput;

import java.util.ArrayList;
import java.util.List;

public class UITextInputValidation {



    private List<TextInput> textInputList;

    public UITextInputValidation() {
        this.textInputList = new ArrayList<>();
    }

    private void invokeValidation() {
        for (TextInput textInput : this.textInputList){
            textInput.invokeValidation();
        }
    }

    private boolean isTextInputValid() {
        for (TextInput textInput : this.textInputList){
            if (textInput.getError() != null) {
                return false;
            }
        }
        return true;
    }

    private void shakeUI(View view) {
        YoYo.with(Techniques.Shake).playOn(view);
    }

    public boolean validateUI(View viewToShake) {
        invokeValidation();
        if (!isTextInputValid()) {
            shakeUI(viewToShake);
            return false;
        }
        return true;
    }


    public void addTextInput(TextInput textInput) {
        textInputList.add(textInput);
    }
}
