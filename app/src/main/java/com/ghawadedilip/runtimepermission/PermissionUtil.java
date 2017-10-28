package com.ghawadedilip.runtimepermission;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Dilip on 28-Oct-17.
 */

public class PermissionUtil {
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PermissionUtil(Context context)
    {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.permission_preference),Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void updatePermissionPreference(String permisson)
    {
        switch (permisson)
        {
            case "camera":
                editor.putBoolean(context.getString(R.string.permission_camera),true);
                editor.commit();
                break;
            case "storage":
                editor.putBoolean(context.getString(R.string.permission_storage),true);
                editor.commit();
                break;
            case "contacts":
                editor.putBoolean(context.getString(R.string.permission_contacts),true);
                editor.commit();
                break;
        }
    }
    public boolean chcekPermissionPreference(String perrmission)
    {
        boolean isshown =false;
        switch (perrmission)
        {
            case "camera":
                isshown = sharedPreferences.getBoolean(context.getString(R.string.permission_camera),false);
                break;
            case "storage":
                isshown = sharedPreferences.getBoolean(context.getString(R.string.permission_storage),false);
                break;
            case "contacts":
                isshown =  sharedPreferences.getBoolean(context.getString(R.string.permission_contacts),false);
                break;
        }
        return isshown;
    }

}
