package com.code.wlu.abdulrahman.androidassignments;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;


public class ListItemsActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    protected CharSequence text;
    int duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        ImageButton takePicture = (ImageButton) findViewById(R.id.imageButton);
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        CheckBox cb = (CheckBox) findViewById(R.id.checkBox);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
              AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
              builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title)
                      .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              Intent resultIntent = new Intent();
                              resultIntent.putExtra("Response", "Herre is my response");
                              setResult(Activity.RESULT_OK, resultIntent);
                              finish();
                          }
                      })
                      .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
                                  @Override
                                  public void onClick(DialogInterface dialog, int which) {

                                  }
                              }
                      )
                      .show();
              Toast.makeText(ListItemsActivity.this, "User action being evaluated", Toast.LENGTH_LONG).show();
          }
      } );

        Switch switcher = (Switch) findViewById(R.id.switcher);
            switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 if (isChecked)
                 {
                     text = "Switch is On";
                     duration = Toast.LENGTH_SHORT;
                 }
                 else
                 {
                     text = "Switch is Off";
                     duration = Toast.LENGTH_LONG;
                 }
                 Toast.makeText(ListItemsActivity.this, text, duration).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
             Toast.makeText(this, "Camera use related error " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            assert extras != null;
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView imageView = null;
            imageView.setImageBitmap(imageBitmap);
        }
    }

}