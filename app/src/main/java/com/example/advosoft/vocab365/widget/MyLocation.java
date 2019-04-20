package com.example.advosoft.vocab365.widget;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Advosoft2 on 4/13/2017.
 */

public class MyLocation {
    Timer timer1;
    LocationManager lm;
    LocationResult locationResult;
    boolean gps_enabled = false;
    private Activity context;
    boolean network_enabled = false;
    GetLastLocation getLastThread = null;

    public boolean getLocation(Activity context, LocationResult result, int tillTimeToWaitForLocation) {
        this.context = context;
        //I use LocationResult callback class to pass location value from MyLocation to user code.
        locationResult = result;

        timer1 = new Timer();

        if (lm == null)
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);



        //exceptions will be thrown if provider is not permitted.
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        checkPROVIDER(context, lm);

        getLastThread = new GetLastLocation();
        timer1.schedule(getLastThread, tillTimeToWaitForLocation * 1000);
        return true;
    }

    public void removeUpdate() {
        try {
            removeListener(context, lm);

            if (timer1 != null) {
                timer1.cancel();
                //timer1 = null;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            if (location != null) {
                timer1.cancel();
                removeListener(context, lm);

                if(locationResult != null)
                    locationResult.gotLocation(context, location);
                locationResult = null;
            }
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            if (location != null) {
                timer1.cancel();

                removeListener(context, lm);

                if(locationResult != null)
                    locationResult.gotLocation(context, location);
                locationResult = null;
            }
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    class GetLastLocation extends TimerTask {
        @Override
        public void run() {
            getLastLocation();
        }
    }

    public void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Location gps_loc = null;
            if (gps_enabled)
                gps_loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location net_loc = null;
            if (network_enabled)
                net_loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            //if there are both values use the latest one
            try {
                if (gps_loc != null) {
                    getLastThread.cancel();
                    timer1.cancel();
                    lm.removeUpdates(locationListenerGps);
                    if(locationResult != null)
                        locationResult.gotLocation(context, gps_loc);
                    locationResult = null;

                    return;
                }

                if (net_loc != null) {
                    getLastThread.cancel();
                    timer1.cancel();
                    lm.removeUpdates(locationListenerNetwork);
                    if(locationResult != null)
                        locationResult.gotLocation(context, net_loc);
                    locationResult = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static abstract class LocationResult {
        public abstract void gotLocation(Context context, Location location);
    }

    final int fineReq = 101;

    public void checkPROVIDER(Activity context, LocationManager lm) {
        boolean isFine = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean isCoarse = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (isFine && isCoarse) {
            if (gps_enabled)
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
            if (network_enabled)
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
        } else {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, fineReq);
        }
    }

    public void removeListener(Activity context, LocationManager lm) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            lm.removeUpdates(locationListenerGps);
            lm.removeUpdates(locationListenerNetwork);
        } else {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
    }

}
