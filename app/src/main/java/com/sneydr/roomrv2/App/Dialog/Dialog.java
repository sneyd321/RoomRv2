package com.sneydr.roomrv2.App.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.sneydr.roomrv2.R;

public class Dialog {


    private AlertDialog.Builder dialog;

    public Dialog(Context context) {
        this.dialog = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
    }

    public AlertDialog buildErrorDialog() {
        return this.dialog.setTitle("Error").setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create();
    }

    public AlertDialog buildSuccessDialog() {
        return this.dialog.setTitle("Success").setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create();
    }


    public void setMessage(String message) {
        this.dialog.setMessage(message);
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.dialog.setOnDismissListener(onDismissListener);
    }


}
