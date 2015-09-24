package com.example.location;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class LocationModified extends Service 
{
       public static final String BROADCAST_ACTION = "Hello World";
       private static final int TWO_MINUTES = 1000 * 60 * 2;
       public LocationManager locationManager;
       public MyLocationListener listener;
       public Location previousBestLocation = null;

       Intent intent;
       int counter = 0;

    @Override
    public void onCreate() 
    {
        super.onCreate();
        intent = new Intent(BROADCAST_ACTION);      
    }

    @Override
    public void onStart(Intent intent, int startId) 
    {      
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new MyLocationListener();        
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 200, 1, listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 200, 1, listener);
    }

    @Override
    public IBinder onBind(Intent intent) 
    {
        return null;
    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
        // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }



/** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
          return provider2 == null;
        }
        return provider1.equals(provider2);
    }



@Override
    public void onDestroy() {       
       // handler.removeCallbacks(sendUpdatesToUI);     
        super.onDestroy();
        Log.v("STOP_SERVICE", "DONE");
        locationManager.removeUpdates(listener);        
    }   

    public static Thread performOnBackgroundThread(final Runnable runnable) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } finally {

                }
            }
        };
        t.start();
        return t;
    }




public class MyLocationListener implements LocationListener
    {

        public void onLocationChanged(final Location loc)
        {
            Log.i("**************************************", "Location changed");
            if(isBetterLocation(loc, previousBestLocation)) {
                loc.getLatitude();
                loc.getLongitude();             
                intent.putExtra("Latitude", loc.getLatitude());
                intent.putExtra("Longitude", loc.getLongitude());     
                intent.putExtra("Provider", loc.getProvider());                 
                sendBroadcast(intent);         
                double lat=(loc.getLatitude());
                String dlat=Double.toString(lat);
             //   Toast.makeText(getApplicationContext(),dlat, Toast.LENGTH_SHORT).show();
                double lon=loc.getLongitude();
                String dlon=Double.toString(lon);
                File file2 = new File(Environment.getExternalStorageDirectory(),"locationplus.txt");
                String text2;
                BufferedReader br2 = null;
  			try {
  				br2 = new BufferedReader(new FileReader(file2));
  			} catch (FileNotFoundException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
                String line,pline="X",ppline="X";
             //   lat=lat*(3.14)/180;
               // lon=lon*(3.14)/180;
                int count=0;
               String res = null;
  				try {
					while( (line = br2.readLine()) != null)
					  {
						
							
						  	if(count==3)
						  	{
						  		// Toast.makeText(getApplicationContext(),line,Toast.LENGTH_SHORT).show();
						  		// Toast.makeText(getApplicationContext(),pline,Toast.LENGTH_SHORT).show();
								
						  		double lon1=Double.valueOf(line).doubleValue();
							  	double lat1=Double.valueOf(pline).doubleValue();
						  		double r=6371;
						  		double ar = (lat1-lat)*(3.14)/180;
						  		double tr = (lon1-lon)*(3.14)/180; 
						  		double a = Math.sin(ar/2) * Math.sin(ar/2) +
						  		    Math.cos(lat*(3.14)/180) * Math.cos(lat1*(3.14)/180) * 
						  		    Math.sin(tr/2) * Math.sin(tr/2); 
						  		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
						  		double d = r * c;
						  		d = Math.round(d*100)/100.0d;
						  		String jeet=Double.toString(d);
						  		if(d<=100)
						  		{
						  			res+=ppline+"\n";	
						  			Toast.makeText(getApplicationContext(), ppline, Toast.LENGTH_SHORT).show();
						  			Toast.makeText(getApplicationContext(), jeet, Toast.LENGTH_SHORT).show();
						  		}
						  		if(d==0.36)
						  		{
						  			Intent i = new Intent();
						  			i.setClass(LocationModified.this, SendSMS.class);
						  			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						  			startActivity(i);
						  		}
						  		
								ppline=pline;
							  	pline=line;
								count=0;
						  		
						  	} 
						  	else 
						  	{
						  		ppline=pline;
							  	pline=line;
							  	count++;
						  	}

						
					  	
					  	
					  }
					NotificationManager notificationManager =
		  				    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		  				int icon = R.drawable.ic_launcher;
		  				CharSequence notiText = "Notification from Location plus";
		  				long meow = System.currentTimeMillis();

		  				Notification notification = new Notification(icon, notiText, meow);

		  				Context context = getApplicationContext();
		  				CharSequence contentTitle = "You are close!!";
		  				CharSequence contentText = "Jobs you can do: "+res;
		  				Intent notificationIntent = new Intent(LocationModified.this,JobList.class);
		  				PendingIntent contentIntent = PendingIntent.getActivity(LocationModified.this, 0, notificationIntent, 0);

		  				notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

		  				int SERVER_DATA_RECEIVED = 1;
		  				notificationManager.notify(SERVER_DATA_RECEIVED, notification);
				} catch (NumberFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
  		
                
                

            }                               
        }

        public void onProviderDisabled(String provider)
        {
            Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
        }


        public void onProviderEnabled(String provider)
        {
            Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
        }


        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }

    }

}