package com.example.location;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import android.app.Activity;
import android.app.Fragment.SavedState;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateFile extends Activity{
	public static String job2,job1;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// Toast.makeText(getApplicationContext(),"delhi", Toast.LENGTH_SHORT).show();
         String combi="$\n"+JobList.job1+"\n"+ GeocodingActivity.aString+"\n"+ GeocodingActivity.bb;
			File externalStorageDir = Environment.getExternalStorageDirectory();
			File myFile = new File(externalStorageDir , "locationplus.txt");
			if(!myFile.exists())
			{
				try {
					myFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String fileName="locationplus.txt"; 
			FileOutputStream str = null;
     	
			
 		try {
 			//str.write(combi.getBytes());
 			BufferedReader br3 = new BufferedReader(new FileReader(new File(Environment.getExternalStorageDirectory(),"locationplus.txt")));
 			String line,fullFile = combi;
 			while((line = br3.readLine() )!=null)
 			{
 				fullFile+="\n"+line;
 			}
 			str = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/"+ fileName));

 			str.write(fullFile.getBytes());
 			
 		} catch (Exception e) {
 		} finally {
 			try {
					str.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
 		}
	         
	  
	}
    

}
