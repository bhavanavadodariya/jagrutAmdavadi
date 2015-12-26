package com.codeforahmedabad.niyamrakshak;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by bhavana on 26-12-2015.
 */
public class PostActivity extends AppCompatActivity implements View.OnClickListener {
    private final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    private final LatLng LOC = new LatLng(23.0387521, 72.5222175);
    private final String url = "http://whatsapp.views2share.com/webservice.php";
    private final String[] catArray = {"Traffic", "Public Transport", "Clean Ahmedabad", "Safety", "Dirty Water", "Other"};

    private Spinner spinner;
    private FloatingActionButton fab;
    private ImageView imageView;
    private Button postBtn;
    private EditText editText;

    private RequestQueue requestQueue;
    private String selectedCategory = "";
    private Context context;
    private Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_post);

        context = this;
        requestQueue = Volley.newRequestQueue(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        spinner = (Spinner) findViewById(R.id.spinner);
        imageView = (ImageView) findViewById(R.id.imageView);
        postBtn = (Button) findViewById(R.id.postBtn);
        editText = (EditText) findViewById(R.id.editText);

        setPhoto(getIntent());
        fab.setOnClickListener(this);
        postBtn.setOnClickListener(this);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = parent.getItemAtPosition(position).toString();
                System.out.println("   --->   Selected Category = " + selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                selectedCategory = "";
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.fab:
                capture();
                break;
            case R.id.postBtn:
                final ProgressDialog pDialog = new ProgressDialog(PostActivity.this);
                pDialog.setMessage("Loading...");
                pDialog.show();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();
                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                Log.e("Bhavana", encoded);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();

                final JSONObject  jsonObject = new JSONObject();
                try {
                    jsonObject.put("func", "savedata");
                    jsonObject.put("datetime", dateFormat.format(cal.getTime()));
                    jsonObject.put("category", catArray[spinner.getSelectedItemPosition()]);
                    jsonObject.put("description", editText.getText().toString());
                    jsonObject.put("name", "Bhavana");//Logged in User name will be passed
                    jsonObject.put("location", LOC.toString());//Geocoding can be applied
                    jsonObject.put("phonenumber", "9012345678");
                    jsonObject.put("image", encoded);
                } catch(JSONException e) {
                    e.printStackTrace();
                } catch(Exception e) {
                    e.printStackTrace();
                }

                Log.e("Bhavana", jsonObject.toString());
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                        url, jsonObject,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                pDialog.hide();
                                Toast.makeText(context, "Request submitted successfully.", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.hide();
                        Toast.makeText(context, "Please try again.", Toast.LENGTH_LONG).show();
                    }
                });
                requestQueue.add(jsonObjReq);
                break;
        }
    }

    private void capture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                setPhoto(data);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(context, " Picture was not taken ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, " Picture was not taken ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setPhoto(Intent data) {
        photo = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(photo);
    }
}