package com.agriscienceapp.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IGS on 1/3/2016.
 */
public class Utility {
    private static Pattern pattern;
    private static Matcher matcher;
    //Email Pattern

    private Context mContext;

    public Utility(Context mContext) {
        this.mContext = mContext;
    }

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }


    public void isAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage("Please check your internet connection.");

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
            }
        });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }



    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    //    public static boolean validate(String email) {
//        pattern = Pattern.compile(String.valueOf(EMAIL_PATTERN));
//        matcher = pattern.matcher(email);
//        return matcher.matches();
//    }
    public static boolean validate(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
