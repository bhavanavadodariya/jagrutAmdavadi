package com.codeforahmedabad.niyamrakshak;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

/**
 * Created by bhavana on 26-12-2015.
 */
public class ChoiceActivity extends AppCompatActivity implements View.OnClickListener {
    private final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;

    private FloatingActionButton fabMap, fabCam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choice);

        fabMap = (FloatingActionButton) findViewById(R.id.fabMap);
        fabCam = (FloatingActionButton) findViewById(R.id.fabCam);

        fabMap.setOnClickListener(this);
        fabCam.setOnClickListener(this);

//        fabMap.setBackgroundTintList(ColorStateList.valueOf(256));
//        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        Uri uri = Uri.fromParts("package", getPackageName(), null);
//        intent.setData(uri);
//        startActivityForResult(intent, 0);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.fabMap:
                Intent intentMap = new Intent(ChoiceActivity.this, MapActivity.class);
                startActivity(intentMap);
                break;
            case R.id.fabCam:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                Intent intentCam = new Intent(ChoiceActivity.this, PostActivity.class);
                intentCam.putExtra("data", photo);
                startActivity(intentCam);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}