package com.code.wlu.abdulrahman.androidassignments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

 public class DialogBuilder {
    AppCompatActivity mainActivity ;
    Snackbar snackbar ;

    public  DialogBuilder (AppCompatActivity mainActivity, Snackbar snackbar)
    {
        this.mainActivity = mainActivity;
        this.snackbar = snackbar ;
    }

    public void case3() {
        AlertDialog.Builder customDialog =
                new AlertDialog.Builder(mainActivity);
        // Get the layout inflater
        LayoutInflater inflater = mainActivity.getLayoutInflater();
        final View view = inflater.inflate(R.layout.custom_dialog, null);
        customDialog.setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText edit = view.findViewById(R.id.dialog_message_box);
                        String message = edit.getText().toString();
                        snackbar.setText(message);
                    }
                })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
        Dialog dialog = customDialog.create();
        dialog.show();
    }
}