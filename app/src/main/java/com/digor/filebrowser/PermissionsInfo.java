package com.digor.filebrowser;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Arrays;
import java.util.List;

public class PermissionsInfo {

    public List<String> RequestPermissions() {
        try {
            Context context = MainActivity.mainContext;
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            return pInfo != null ? Arrays.asList(pInfo.requestedPermissions) : null;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean IsOnPermissionsGranted() {
        List<String> requestedPermissions = RequestPermissions();
        if (requestedPermissions == null) {
            return true;
        }

        for (String permissions : requestedPermissions) {
            if (!IsPermissionGranted(permissions)) {
                return false;
            }
        }
        return true;
    }

    public boolean IsPermissionGranted(String permission) {
        Context context = MainActivity.mainContext;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P && permission.equals(Manifest.permission.FOREGROUND_SERVICE)) {
            return true;
        }
        if (permission.equals(Manifest.permission.SYSTEM_ALERT_WINDOW)) {
            return true;
        }
        return context.checkPermission(permission, Process.myPid(), Process.myUid()) == PackageManager.PERMISSION_GRANTED;
    }

}
