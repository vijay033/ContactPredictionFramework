package com.example.vijay.contactpredictionframework;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Process;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.vijay.contactpredictionframework.DataBaseSQL.ContactFeatureDBAdapter;
import com.example.vijay.contactpredictionframework.DataBaseSQL.Features;
import com.example.vijay.contactpredictionframework.DataCollection.CallReceiver;

import java.util.List;

import static android.app.AppOpsManager.MODE_ALLOWED;
import static android.app.AppOpsManager.OPSTR_GET_USAGE_STATS;

public class MainActivity extends AppCompatActivity {

    public static String TAG = MainActivity.class.getSimpleName();
    private static final int PERMISSIONS_REQUEST=100;
    ContactFeatureDBAdapter contactFeatureDBAdapter;
    List<Features> featuresList;
    CallReceiver callReceiver;
    Context context;

    private boolean checkForPermission(Context context) {
        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(OPSTR_GET_USAGE_STATS, Process.myUid(), context.getPackageName());
        return mode == MODE_ALLOWED;
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.d(TAG,"Permission Granted");
                    Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d(TAG,"Permission Denied");
                    Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();


//        contactFeatureDBAdapter = ContactFeatureDBAdapter.getContactFeatureDBAdapterInstance(context);
//        featuresList = contactFeatureDBAdapter.getAllContactFeatures();


        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.PROCESS_OUTGOING_CALLS,
                        Manifest.permission.PACKAGE_USAGE_STATS,
                        Manifest.permission.GET_TASKS,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSIONS_REQUEST);


        if(!checkForPermission(this)){
            Log.i(TAG,"Permission Access");

        }

        callReceiver = new CallReceiver(context);

    }
}
