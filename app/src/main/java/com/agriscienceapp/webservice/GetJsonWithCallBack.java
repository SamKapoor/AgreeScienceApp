package com.agriscienceapp.webservice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.agriscienceapp.common.ConnectivityDetector;

import org.json.JSONObject;

import java.net.URLEncoder;


public class GetJsonWithCallBack extends
        AsyncTask<String, JSONObject, JSONObject> {

    private OnUpdateListener onUpdateListener;
    private Activity activity;
    private ProgressDialog progressDialog;
    private JSONObject jsonObject;
    private int intDialogShow = 0;

    private String strName;
    private String strMobilNo;
    private String strDeviceID;
    private String TokenId;
    int cropId, distId, taluId;

    public GetJsonWithCallBack(Activity context, JSONObject jsonObject,
                               String TokenId, String strName, String strMobilNo, String strDeviceID, int intDialogShow, OnUpdateListener onUpdateListener, int cropId, int distId, int taluId) {


        this.onUpdateListener = onUpdateListener;
        this.jsonObject = jsonObject;
        this.TokenId = TokenId;
        this.strName = strName;
        this.strMobilNo = strMobilNo;
        this.strDeviceID = strDeviceID;
        this.intDialogShow = intDialogShow;
        this.activity = context;
        this.cropId = cropId;
        this.distId = distId;
        this.taluId = taluId;
    }

    @Override
    protected void onPreExecute() {
        if (!ConnectivityDetector.isConnectingToInternet(activity)) {
            Toast.makeText(activity, "Please Check Your Internet.", Toast.LENGTH_SHORT).show();
            return;
        }

        super.onPreExecute();
        if (intDialogShow == 1) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Jai Kisaan...");
            progressDialog.setTitle("");
            progressDialog.show();
        }
    }

    @Override
    protected JSONObject doInBackground(String... param) {
        JSONObject getJsonObject = null;
        try {
            JSONParser jsonParser = new JSONParser();
//            getJsonObject = jsonParser.getJSONFromUrl("http://agriscienceindia.com/api/MasterDetail/AppsUserInsert", jsonObject);

            getJsonObject = jsonParser.getJSONFromUrl("http://agriscienceindia.com/api/MasterDetailV2/AppsUserInsert?TokenId=" + URLEncoder.encode(TokenId) + "&DeviceID=" + URLEncoder.encode(strDeviceID) + "&UserName=" + URLEncoder.encode(strName).toString().trim() + "&MobileNo=" + strMobilNo.toString().trim()+ "&CropId=" + cropId+ "&DistID=" + distId+ "&TaluID=" + taluId);


        } catch (Exception e) {

            e.printStackTrace();
        }
        return getJsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonResult) {
        super.onPostExecute(jsonResult);
        try {
            Log.e("TAG", "jsonResult  JSON===========> " + jsonResult);

            if (jsonResult != null) {
                if (jsonResult.getInt("result") == 1) {
                    onUpdateListener.onUpdateComplete(jsonResult, true);
                } else {
                    onUpdateListener.onUpdateComplete(jsonResult, false);
                }
            } else {
                Log.e("TAG", "Error in JSON===========> ");
            }
            if (intDialogShow == 1) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}