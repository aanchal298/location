package com.example.location;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Fragment.SavedState;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JobList extends Activity{
	public static String job2,job1;
	boolean a;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.joblist);
	    Button b =(Button)findViewById(R.id.button1);
	    a=isMyServiceRunning(LocationModified.class);
	    String str = String.valueOf(a);
	    //Toast.makeText(getApplicationContext(),str, Toast.LENGTH_SHORT).show();
	    b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				EditText et2= (EditText)findViewById(R.id.editText2);
				 job2 = et2.getText().toString();
				 EditText et1= (EditText)findViewById(R.id.editText1);
				job1 = et1.getText().toString();
				Intent ii = new Intent(JobList.this,GeocodingActivity.class);
				startActivity(ii);
				//Toast.makeText(getApplicationContext(), "please", Toast.LENGTH_SHORT).show();
				
				
				
	    		
				// TODO Auto-generated method stub
			
			}     
		});
	    
	    Button b2=(Button)findViewById(R.id.button2);
	    b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent i =new Intent(JobList.this,MyActivity.class);
			startActivity(i);
			Toast.makeText(getApplicationContext(), "sahi ", Toast.LENGTH_LONG).show();	
			}
		});
	}
    


private boolean isMyServiceRunning(Class<?> serviceClass) {
    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
        if (serviceClass.getName().equals(service.service.getClassName())) {
            return true;
        }
    }
    return false;
}
}