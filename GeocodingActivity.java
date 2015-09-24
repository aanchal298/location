package com.example.location;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class GeocodingActivity extends Activity {
    Button addressButton;
    TextView lattv;
    TextView longtv;
    String adr;
    static String aString,bb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geocodingactivity);


        lattv = (TextView) findViewById(R.id.textView1);
        longtv = (TextView) findViewById(R.id.textView2);

        addressButton = (Button) findViewById(R.id.button1);
        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {


                GeocodingLocation locationAddress = new GeocodingLocation();
                locationAddress.getAddressFromLocation(JobList.job2,getApplicationContext(), new GeocoderHandler());
             //   Toast.makeText(getApplicationContext(), aString, Toast.LENGTH_SHORT);
              
            }
        });

    }
   
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
           
            switch (message.what) {
                case 1:
                	 Bundle bundle = message.getData();
                     double arr[]=bundle.getDoubleArray("address");
                     double aDouble = arr[0];
                     aString = Double.toString(aDouble);
                     double b = arr[1];
                     bb = Double.toString(b);
                    break;
                default:
                    aString = null;
                    bb=null;
            }
            longtv.setText(aString);
            lattv.setText(bb);
            Toast.makeText(getApplicationContext(), aString, Toast.LENGTH_SHORT).show();
            Intent i=new Intent(GeocodingActivity.this,UpdateFile.class);
            startActivity(i);
           
        }
    }
}