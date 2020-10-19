package com.sneydr.roomrv2.App.DatePicker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SelectDateDialog {


    private final Calendar date = Calendar.getInstance();
    private Context context;
    private EditText editText;

    public SelectDateDialog(View view, int editTextResourceId) {
        editText = view.findViewById(editTextResourceId);
        editText.setOnClickListener(onEditTextClicked);
        context = view.getContext();
    }

    private int getCurrentYear() {
        return date.get(Calendar.YEAR);
    }

    private int getCurrentMonth() {
        return date.get(Calendar.MONTH);
    }

    private int getCurrentDay() {
        return date.get(Calendar.DAY_OF_MONTH);
    }


    View.OnClickListener onEditTextClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new DatePickerDialog(context, onDateSetListener, getCurrentYear(), getCurrentMonth(), getCurrentDay()).show();
        }
    };


    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, month);
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SelectDateDialog.this.editText.setText(getFormattedDate("yyyy/MM/dd"));
        }
    };

    private String getFormattedDate(String format) {
        return new SimpleDateFormat(format, Locale.CANADA).format(date.getTime());
    }

    public String getText() {
        return this.editText.getText().toString();
    }

}
