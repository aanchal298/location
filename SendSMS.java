package com.example.location;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendSMS extends Activity {
   Button sendBtn;
   EditText txtphoneNo;
   EditText txtMessage;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.sendsms);

      sendBtn = (Button) findViewById
    		  (R.id.button1);
      txtphoneNo = (EditText) findViewById(R.id.editText1);
      txtMessage = (EditText) findViewById(R.id.editText2);
      Toast.makeText(getApplicationContext(),"message hu mai ",Toast.LENGTH_SHORT).show();
      sendBtn.setOnClickListener(new View.OnClickListener() {
         public void onClick(View view) {
          //  sendSMSMessage();
        	 String path = Environment.getExternalStorageDirectory().getPath() + "aan.txt";
        	 File f = new File(path);
        	 if (f.exists()) {
        	    Toast.makeText(getApplicationContext(), "sorry",Toast.LENGTH_SHORT).show();
        	 }
        	 else {
        		 Toast.makeText(getApplicationContext(), "NOt found",Toast.LENGTH_SHORT).show();
        	 }
         }
      });
   }
   protected void sendSMSMessage() {
      Log.i("Send SMS", "");
      String phoneNo = txtphoneNo.getText().toString();
      String message = txtMessage.getText().toString();
      
      try {
         SmsManager smsManager = SmsManager.getDefault();
         smsManager.sendTextMessage(phoneNo, null, message, null, null);
         Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
      } 
      
      catch (Exception e) {
         Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
         e.printStackTrace();
      }
   }
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
   }
}