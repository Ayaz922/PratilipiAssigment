package com.ayazalam.pratlipi.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.ayazalam.pratlipi.R;


/**
 * Created by Ayaz Alam on 2019/5/03.
 */

public class SplashActivity extends AppCompatActivity {

    private static final int CONTACT_REQUEST = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if(checkPermissionForWriteContact()&& checkPermissionForReadContact()) {
            startActivity(new Intent(this,MainActivity.class));
        }
        else
        {
            final String [] permissions=new String []{ Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS};
            ActivityCompat.requestPermissions(this, permissions, CONTACT_REQUEST);
        }

    }

    private boolean checkPermissionForReadContact() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkPermissionForWriteContact() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if(requestCode==CONTACT_REQUEST)
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED) {
               startActivity(new Intent(this,MainActivity.class));
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
    }

}
