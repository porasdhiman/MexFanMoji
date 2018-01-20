package com.mexfanemoji;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Splash extends Activity {
    AppIndexingUpdateService appIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        appIndex=new AppIndexingUpdateService();

        appIndex.enqueueWork(Splash.this);
        String internetpermission = Manifest.permission.INTERNET;
        String readPermission = Manifest.permission.READ_EXTERNAL_STORAGE;
        String writePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasinternetpermission = Splash.this.checkSelfPermission(Manifest.permission.INTERNET);
            int hasReadpermission = Splash.this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            int hasWritepermission = Splash.this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);


            List<String> permissions = new ArrayList<String>();

            if (hasinternetpermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(internetpermission);
            }
            if (hasReadpermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(readPermission);
            }
            if (hasWritepermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(writePermission);
            }

            if (!permissions.isEmpty()) {
                String[] params = permissions.toArray(new String[permissions.size()]);
                requestPermissions(params, 0);

            } else {


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(Splash.this, MainViewActivity.class);
                        startActivity(intent);

                        finish();
                    }
                }, 2000);
//


                // We already have permission, so handle as norma
                //Toast.makeText(MainActivity.this,gps.getLatitude()+""+   gps.getLongitude(),Toast.LENGTH_SHORT).show();
            }
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(Splash.this, MainViewActivity.class);
                    startActivity(intent);

                    finish();
                }
            }, 2000);

            // Toast.makeText(MainActivity.this,gps.getLatitude()+""+   gps.getLongitude(),Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);


                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted

                    // Toast.makeText(MainActivity.this,gps.getLatitude()+""+   gps.getLongitude(),Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent(Splash.this, MainViewActivity.class);
                            startActivity(intent);

                            finish();
                        }
                    }, 2000);
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}



