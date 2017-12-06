package com.example.abnlt.myphotozigtask;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;


/**
 * Created by asim on 3/29/2016.
 *
 * @author hassanjamil
 */
public class PermissionUtils {

    public static final int REQUEST_CODE_LOCATION_PERMISSION = 9;
    public static final int REQUEST_CODE_LOCATION_STORAGE_PERMISSION = 10;
    public static final int REQUEST_CODE_STORAGE_PERMISSION = 11;
    public static final int REQUEST_CODE_CALL_PHONE_PERMISSION = 12;
    public static final int REQUEST_CODE_CAMERA = 13;

    //public static final int REQUEST_CODE_READ_PHONE_STATE_PERMISSION = 13;

    public static final String PERMISSION_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String PERMISSION_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PERMISSION_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    //private static final String PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE;
    private static final String PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    private static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;

    public static void requestPermission(Activity activity, String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isLocationPermissionGranted(activity)) {
                return;
            }
            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI
            activity.requestPermissions(permissions, requestCode);
        }
    }

    public static void requestLocationPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isLocationPermissionGranted(activity)) {
                return;
            }
            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI
            activity.requestPermissions(new String[]{PERMISSION_COARSE_LOCATION, PERMISSION_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION);
        }
    }

    public static boolean isLocationPermissionGranted(Activity activity) {
        boolean flag = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flag = activity.checkSelfPermission(PERMISSION_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && activity.checkSelfPermission(PERMISSION_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        }
        return flag;
    }



    public static void requestStoragePermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isStoragePermissionGranted(activity)) {
                return;
            }

            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI
            activity.requestPermissions(new String[]{PERMISSION_STORAGE},
                    REQUEST_CODE_STORAGE_PERMISSION);
        }
    }

    public static boolean isStoragePermissionGranted(Activity activity) {
        boolean flag = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flag = activity.checkSelfPermission(PERMISSION_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return flag;
    }




    /*
     CAMERA PERMISSION RELATED UTILITY METHODS
     */

    public static boolean isCameraPermissionGranted(Activity activity) {
        boolean flag = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flag = activity.checkSelfPermission(PERMISSION_CAMERA) == PackageManager.PERMISSION_GRANTED;
        }
        return flag;
    }

    public static void requestCameraPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isCameraPermissionGranted(activity)) {
                return;
            }

            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI
            activity.requestPermissions(new String[]{PERMISSION_CAMERA}, REQUEST_CODE_CAMERA);
        }
    }

    /*public static void requestPhonePermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(PERMISSION_CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                //Show Reason if Needed
                if (activity.shouldShowRequestPermissionRationale(
                        PERMISSION_CALL_PHONE)) {
                    DialogUtils.createAlertDialog(activity, 0, null,
                            "Phone Permission is required for making calls", true, true,
                            new String[]{"OK"},
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            dialogInterface.cancel();
                                            break;
                                    }
                                }
                            }
                    );
                }

                // Fire off an async request to actually get the permission
                // This will show the standard permission request dialog UI
                activity.requestPermissions(new String[]{PERMISSION_CALL_PHONE},
                        REQUEST_CODE_CALL_PHONE_PERMISSION);
            }
        }
    }*/

    public static void requestReadPhoneStatePermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isReadPhoneStatePermissionGranted(activity)) {
                return;
            }

            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI
            activity.requestPermissions(new String[]{PERMISSION_READ_PHONE_STATE},
                    REQUEST_CODE_STORAGE_PERMISSION);
        }
    }

    public static boolean isReadPhoneStatePermissionGranted(Activity activity) {
        boolean flag = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(PERMISSION_READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                flag = false;
            }
        }
        return flag;
    }


    public static void onRequestPermissionResult(Activity activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionUtils.REQUEST_CODE_STORAGE_PERMISSION:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (grantResults.length > 0) {
                        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                            if (PermissionUtils.isStoragePermissionGranted(activity))
                                return;

                            boolean shouldShowRationale = activity.shouldShowRequestPermissionRationale(permissions[0]);
                            if (!shouldShowRationale) {
                                // user denied flagging NEVER ASK AGAIN, you can either enable some fall back,
                                // disable features of your app or open another dialog explaining again the permission and directing to
                                // the app setting
                                AppUtils.dialogReasonPermissionSettings(activity, activity.getString(R.string.reason_storage_permission),
                                        new String[]{activity.getString(R.string.go_to_settings), activity.getString(R.string.dismiss)});
                            } else if (PermissionUtils.PERMISSION_STORAGE.equals(permissions[0])) {
                                // user denied WITHOUT never ask again, this is a good place to explain the user
                                // why you need the permission and ask if he want to accept it (the rationale)
                                AppUtils.dialogReasonStoragePermission(activity);
                            }
                        } /*else {
                            // Do on permission granted work here
                        }*/
                    }
                }
                break;
            case PermissionUtils.REQUEST_CODE_LOCATION_PERMISSION:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (grantResults.length > 0) {
                        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                            if (PermissionUtils.isLocationPermissionGranted(activity))
                                return;

                            boolean shouldShowRationale = activity.shouldShowRequestPermissionRationale(permissions[0]);
                            if (!shouldShowRationale) {
                                // user denied flagging NEVER ASK AGAIN, you can either enable some fall back,
                                // disable features of your app or open another dialog explaining again the permission and directing to
                                // the app setting
                                AppUtils.dialogReasonLocationPermissionToSettings(activity);
                            } else if (PermissionUtils.PERMISSION_COARSE_LOCATION.equals(permissions[0])) {
                                // user denied WITHOUT never ask again, this is a good place to explain the user
                                // why you need the permission and ask if he want to accept it (the rationale)
                                AppUtils.dialogReasonLocationPermission(activity);
                            }
                        } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            // Do on permission granted work here
//                            if (activity instanceof ActivityMain) {
//                                ((ActivityMain) activity).checkLocationSettings();
//                                ((ActivityMain) activity).startGpsStatusListener();
//                            }
                        }
                    }
                }
                break;
            case PermissionUtils.REQUEST_CODE_LOCATION_STORAGE_PERMISSION:
                if (grantResults.length > 0) {
                    if (grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                        AppUtils.dialogReasonStoragePermission(activity);
                    }
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                            && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    //    ((ActivityMain) activity).checkLocationSettings();
                    } /*else {
                        CommonUtilities.toastLong(ActivityMain.this, "Location permission is highly required for the " +
                                "application, please allow it from application's settings");
                    }*/
                }
                break;
            case PermissionUtils.REQUEST_CODE_CAMERA:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (grantResults.length > 0) {
                        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                            if (PermissionUtils.isCameraPermissionGranted(activity))
                                return;

                            boolean shouldShowRationale = activity.shouldShowRequestPermissionRationale(permissions[0]);
                            if (!shouldShowRationale) {
                                // user denied flagging NEVER ASK AGAIN, you can either enable some fall back,
                                // disable features of your app or open another dialog explaining again the permission and directing to
                                // the app setting
                                AppUtils.dialogReasonPermissionSettings(activity, activity.getString(R.string.reason_storage_permission),
                                        new String[]{activity.getString(R.string.go_to_settings), activity.getString(R.string.dismiss)});
                            } else if (PermissionUtils.PERMISSION_STORAGE.equals(permissions[0])) {
                                // user denied WITHOUT never ask again, this is a good place to explain the user
                                // why you need the permission and ask if he want to accept it (the rationale)
                                AppUtils.dialogReasonStoragePermission(activity);
                            }
                        } /*else {
                            // Do on permission granted work here
                        }*/
                    }
                }
                break;
        }
    }

    public static void dialogReasonLocationPermission(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(activity.getString(R.string.reason_location_permission));
        builder.setCancelable(false);
        builder.setPositiveButton(activity.getString(R.string.str_retry), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                PermissionUtils.requestLocationPermission(activity);
            }
        });
        builder.setNegativeButton(activity.getString(R.string.dismiss), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
