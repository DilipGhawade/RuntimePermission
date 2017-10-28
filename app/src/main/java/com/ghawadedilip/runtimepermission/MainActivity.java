package com.ghawadedilip.runtimepermission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.BoolRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button buttoncamera,buttonwestorage,buttonreadcontact,buttongrouppermission;

    private static final int REQUEST_CAMERA = 125;
    private static final int REQUEST_STORGE = 225;
    private static final int REQUEST_CONTACTS = 325;
    private static final int REQUEST_GROUPP_PERMISSION = 425;

    private static final int TXT_CAMERA = 1;
    private static final int TXT_STORAGE = 2;
    private static final int TXT_CONTACTS = 3;


    PermissionUtil permissionUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttoncamera = (Button)findViewById(R.id.btn_use_camera);
        buttonwestorage = (Button)findViewById(R.id.btn_wrtexternal_storage);
        buttonreadcontact = (Button)findViewById(R.id.btn_read_contact);
        buttongrouppermission = (Button)findViewById(R.id.btn_grouppermission);


        buttonreadcontact.setOnClickListener(this);
        buttonwestorage.setOnClickListener(this);
        buttoncamera.setOnClickListener(this);
        buttongrouppermission.setOnClickListener(this);

        permissionUtil = new PermissionUtil(this);

    }



    private int checkPermission(int permission)
    {
        int status = PackageManager.PERMISSION_DENIED;
        switch (permission)
        {
            case TXT_CAMERA:
                status = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
                break;
            case TXT_STORAGE:
                status = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            case TXT_CONTACTS:
                status = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS);
                break;
        }
        return status;
    }

    private void requestPermission(int permission)
    {
        switch (permission)
        {
            case TXT_CAMERA:
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA);
                break;
            case TXT_STORAGE:
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_STORGE);
                break;
            case TXT_CONTACTS:
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},REQUEST_CONTACTS);
                break;
        }
    }

    private void showPermissionExplination(final int permission)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (permission==TXT_CAMERA)
        {
            builder.setMessage("This app is need to access your device Camera...Please Allow");
            builder.setTitle("Camera Permission Needed");
        }
        else if (permission==TXT_STORAGE)
        {
            builder.setMessage("This app is need to access your device external storage... Please Allow");
            builder.setTitle("Storage Permission needed");
        }
        else if (permission==TXT_CONTACTS)
        {
            builder.setMessage("This app is need to access your device Contacts... Please Allow");
            builder.setTitle("Contacts Permission needed");
        }
        builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (permission==TXT_CAMERA)
                    requestPermission(TXT_CAMERA);
                else if (permission==TXT_STORAGE)
                    requestPermission(TXT_STORAGE);
                else if (permission==TXT_CONTACTS)
                    requestPermission(TXT_CONTACTS);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void usecameraPermission()
    {
        if (checkPermission(TXT_CAMERA)!=PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this
            ,Manifest.permission.CAMERA))
            {
                showPermissionExplination(TXT_CAMERA);
            }
            else if (!permissionUtil.chcekPermissionPreference("camera"))
            {
                requestPermission(TXT_CAMERA);
                permissionUtil.updatePermissionPreference("camera");
            }
            else
            {
                Toast.makeText(this,"Please Allow camera permission in your app settings",
                        Toast.LENGTH_LONG).show();
                Intent i = new Intent();
                i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package",this.getPackageName(),null);
                i.setData(uri);
                this.startActivity(i);
            }

        }
        else
        {
            Toast.makeText(this,"you have camra permission",Toast.LENGTH_LONG).show();
            Intent i = new Intent(this,ResultActivity.class);
            i.putExtra("message","you can now take photo & videos...");
            startActivity(i);

        }

    }

    public void useexternalstoragePermission()
    {
        if (checkPermission(TXT_STORAGE)!=PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this
                    ,Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                showPermissionExplination(TXT_STORAGE);
            }
            else if (!permissionUtil.chcekPermissionPreference("storage"))
            {
                requestPermission(TXT_STORAGE);
                permissionUtil.updatePermissionPreference("storage");
            }
            else
            {
                Toast.makeText(this,"Please Allow storage permission in your app settings",
                        Toast.LENGTH_LONG).show();
                Intent i = new Intent();
                i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package",this.getPackageName(),null);
                i.setData(uri);
                this.startActivity(i);
            }

        }
        else
        {
            Toast.makeText(this,"you have storage permission",Toast.LENGTH_LONG).show();
            Intent i = new Intent(this,ResultActivity.class);
            i.putExtra("message","you can now use storage...");
            startActivity(i);

        }


    }
    public void readcontactPermission()
    {
        if (checkPermission(TXT_CONTACTS)!=PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this
                    ,Manifest.permission.READ_CONTACTS))
            {
                showPermissionExplination(TXT_CONTACTS);
            }
            else if (!permissionUtil.chcekPermissionPreference("contacts"))
            {
                requestPermission(TXT_CONTACTS);
                permissionUtil.updatePermissionPreference("contacts");
            }
            else
            {
                Toast.makeText(this,"Please Allow contacts permission in your app settings",
                        Toast.LENGTH_LONG).show();
                Intent i = new Intent();
                i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package",this.getPackageName(),null);
                i.setData(uri);
                this.startActivity(i);
            }

        }
        else
        {
            Toast.makeText(this,"you have contacts permission",Toast.LENGTH_LONG).show();
            Intent i = new Intent(this,ResultActivity.class);
            i.putExtra("message","you can now read contacts...");
            startActivity(i);

        }



    }
    private void requestGroupPermission(ArrayList<String> permission)
    {
        String [] permissionList = new String[permission.size()];
        permission.toArray(permissionList);
        ActivityCompat.requestPermissions(MainActivity.this,permissionList,REQUEST_GROUPP_PERMISSION);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btn_read_contact:
                readcontactPermission();
                break;
            case R.id.btn_use_camera:
                usecameraPermission();
                break;
            case R.id.btn_wrtexternal_storage:
                useexternalstoragePermission();
                break;
            case R.id.btn_grouppermission:
                grouppermission();
                break;
        }
    }
    private void grouppermission()
    {
        ArrayList<String> permissionsNeeded = new ArrayList<>();
        ArrayList<String> permissionsAvaliable = new ArrayList<>();
        permissionsAvaliable.add(Manifest.permission.READ_CONTACTS);
        permissionsAvaliable.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionsAvaliable.add(Manifest.permission.CAMERA);

        for (String permission : permissionsAvaliable)
        {
            if (ContextCompat.checkSelfPermission(this,permission)!=PackageManager.PERMISSION_GRANTED)
                permissionsNeeded.add(permission);
        }
        requestGroupPermission(permissionsNeeded);


    }
}
