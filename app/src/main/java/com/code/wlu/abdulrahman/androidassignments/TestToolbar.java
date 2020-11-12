package com.code.wlu.abdulrahman.androidassignments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity  {
    Snackbar snackbar ;
    ImageButton ib;
    String message ="You selected item 1";
    EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Love Android", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return  true;
        //return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_one:
                Log.d("TestToolbar", "Option 1 selected");
                //Toast.makeText(this, "You chose option 1", Toast.LENGTH_LONG).show();
                    Snackbar.make(  findViewById(android.R.id.content),message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                break;
            case R.id.action_two:
                //Log.d("TestToolbar", "Option 2 selected");
                /*I am using function for readability and not to make section crowded*/
                useDialog(this, "Do you want to go back?");
                break;
            case R.id.action_three:
                //Log.d("TestToolbar", "Option 3 selected");
                //Toast.makeText(this, "You chose option 3", Toast.LENGTH_LONG).show();
                // function call in use not to crowd the code
                useCustomDialog(this) ;
                break;
            case R.id.About:
                Log.d("TestToolbar", "Option 1 selected");
                Toast.makeText(this, "Version 1.0, by Fantaye S. Belayneh", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void useDialog(Context context, String title)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);  //(R.string.pick_color)
// Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
// Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void useCustomDialog(Context ctx) {
        AppCompatActivity myActivity ;

        AlertDialog.Builder customDialog = new AlertDialog.Builder(ctx);
        // Get the layout inflater
        LayoutInflater inflater =  this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.custom_dialog, null);
        customDialog.setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            edit = view.findViewById(R.id.dialog_message_box);
                            message = edit.getText().toString();
                            snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).setAction("Action", null);
                            snackbar.setText(message);
                            snackbar.show();
                        }
                        catch (Exception f)
                        {
                            Toast.makeText(TestToolbar.this,  f.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
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